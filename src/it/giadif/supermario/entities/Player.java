package it.giadif.supermario.entities;

import java.util.ArrayList;
import java.util.List;

import it.giadif.supermario.entities.enemies.Goomba;
import it.giadif.supermario.entities.enemies.Turtle;
import it.giadif.supermario.graphics.Animation;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.graphics.Renderer;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;
import it.giadif.supermario.world.Level;

public class Player extends Entity {
	
	private static final float ACCELERATION_X = 0.25f;
	private static final BoundingBox[] bbs = initBoundingBoxes();
	private static final List<Class<?>> enemies = initEnemies();
	
	private static BoundingBox[] initBoundingBoxes()
	{
		BoundingBox[] bbs = new BoundingBox[3];
		bbs[0] = new BoundingBox(2, 0, 12, 16);
		bbs[1] = new BoundingBox(0, 0, 16, 32);
		bbs[2] = bbs[1];
		return bbs;
	}
	
	private static List<Class<?>> initEnemies()
	{
		List<Class<?>> enemies = new ArrayList<>();
		enemies.add(Goomba.class);
		enemies.add(Turtle.class);
		return enemies;
	}
	
	private static boolean isEnemy(Object obj)
	{
		return enemies.contains(obj.getClass());
	}
	
	private float vx, vy;
	private Bitmap inBetween;
	private Bitmap[] stand;
	private Bitmap[] jump;
	private Bitmap[] crouch;
	private Animation[] walk;
	
	private Bitmap bitmap;
		
	private int state;
	private boolean crouching;
	private boolean jumping;
	private boolean walking;
	private boolean flip;
	
	private int transitionTimer;
	
	public Player(float x, float y)
	{
		super(x, y, true);
		Bitmap sheet = new Bitmap("/res/images/mario.png");
		stand = new Bitmap[3];
		stand[0] = new Bitmap(sheet, 0, 0, 16, 16);
		stand[1] = new Bitmap(sheet, 0, 16, 16, 32);
		stand[2] = new Bitmap(sheet, 0, 48, 16, 32);
		walk = new Animation[3];
		walk[0] = new Animation(Bitmap.bitmapArray(sheet, 16, 0, 16, 16, 3), 8);
		walk[1] = new Animation(Bitmap.bitmapArray(sheet, 16, 16, 16, 32, 3), 8);
		walk[2] = new Animation(Bitmap.bitmapArray(sheet, 16, 48, 16, 32, 3), 8);
		jump = new Bitmap[3];
		jump[0] = new Bitmap(sheet, 64, 0, 16, 16);
		jump[1] = new Bitmap(sheet, 64, 16, 16, 32);
		jump[2] = new Bitmap(sheet, 64, 48, 16, 32);
		crouch = new Bitmap[2];
		crouch[0] = new Bitmap(sheet, 80, 16, 16, 32);
		crouch[1] = new Bitmap(sheet, 80, 48, 16, 32);
		inBetween = new Bitmap(sheet, 128, 16, 16, 32);
		state = 0;
		bitmap = stand[state];
		transitionTimer = 0;
	}
	
	@Override
	public void render(Renderer renderer)
	{
		renderer.renderBitmap(getBitmap(), Math.round(x), Math.round(y), flip);
	}
	
	@Override
	public void update()
	{
		if(transitionTimer != 0) {
			vy -= Level.GRAVITY;
			transitionTimer--;
			if((transitionTimer <= 64 && transitionTimer > 56) 
			|| (transitionTimer <= 48 && transitionTimer > 40)
			|| (transitionTimer <= 32 && transitionTimer > 24)
			||  transitionTimer <= 8) {
				if(bitmap != inBetween) y -= 16;
				bitmap = inBetween;
			}
			else if((transitionTimer <= 56 && transitionTimer > 48)
				 || (transitionTimer <= 40 && transitionTimer > 32)
				 || (transitionTimer <= 16 && transitionTimer > 8)) {
				if(bitmap != stand[0]) y += 16;
				bitmap = stand[0];
			}
			else {
				bitmap = stand[1];
			}
			return;
		}
		if(jumping) {
			bitmap = jump[state];
		}
		else if(crouching) {
			bitmap = crouch[state - 1];
		}
		else if(walking) {
			walk[state].update();
			bitmap = walk[state].getCurrentFrame();
		}
		else {
			bitmap = stand[state];
		}
		
		x += vx;
		y += vy;
	}
	
	public void move(boolean left, boolean right)
	{
		if(transitionTimer != 0) return;
		walking = left ^ right && !crouching;
		if(walking) {
			if(left) {
				if(vx > -2.0f) {
					vx -= ACCELERATION_X;
					if(vx < -2.0f) vx = -2.0f;
				}
				flip = true;
			}
			if(right) {
				if(vx < 2.0f) {
					vx += ACCELERATION_X;
					if(vx > 2.0f) vx = 2.0f;
				}
				flip = false;
			}
		}
		else {
			if(vx < 0) {
				vx += ACCELERATION_X;
				if(vx > 0.0f) vx = 0.0f;
			}
			else if(vx > 0) {
				vx -= ACCELERATION_X;
				if(vx < 0.0f) vx = 0.0f;
			}
			walk[state].reset();
		}
	}
	
	public void jump()
	{
		if(jumping || vy < -0.005f || vy > 0.005f) return;
		jumping = true;
		vy = -6.0f;
	}
	
	public void crouch()
	{
		if(state == 0) return;
		if(!jumping) {
			crouching = true;
		}
	}
	
	public void stand()
	{
		crouching = false;
	}
	
	private void die()
	{
		alive = false;
		solid = false;
	}

	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		if(!solid || transitionTimer != 0
		|| ((other instanceof Entity && !((Entity) other).isAlive()))) return;
		if(!other.isSolid()) {
			if(other instanceof Mushroom) {
				transitionTimer = 64;
				state++;
			}
			return;
		}
		if(dx != 0) {
			if(isEnemy(other)) {
				die();
			}
			else {
				x += dx;
				vx = 0;
			}
		}
		if(dy != 0) {
			if(isEnemy(other)) {
				if(dy > 0) {
					die();
				}
				else {
					y += dy;
					vy = -4.0f;
					jumping = true;
				}
			}
			else {
				if(dy < 0) jumping = false;
				y += dy;
				vy = 0;
			}
		}
	}
	
	@Override
	public float getVx()
	{
		return vx;
	}

	@Override
	public float getVy()
	{
		return vy;
	}

	@Override
	public boolean canMove()
	{
		return true;
	}

	@Override
	public void setVx(float vx)
	{
		this.vx = vx;
	}

	@Override
	public void setVy(float vy)
	{
		this.vy = vy;
	}

	@Override
	public int getZ()
	{
		return 2;
	}

	@Override
	public Bitmap getBitmap()
	{
		return bitmap;
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return bbs[crouching ? 0 : state];
	}
}
