package it.giadif.supermario.entities.enemies;

import it.giadif.supermario.blocks.BrickBlock;
import it.giadif.supermario.blocks.QuestionBlock;
import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.entities.Player;
import it.giadif.supermario.graphics.Animation;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.graphics.Renderer;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;

public class Turtle extends Entity {

	private static final Bitmap shell = new Bitmap("/res/images/enemies.png", 80, 8, 16, 24);
	private static final BoundingBox bb = new BoundingBox(1, 9, 14, 15);
	private boolean inShell;
	private Animation animation;
	private float vx, vy;

	public Turtle(float x, float y)
	{
		super(x, y, true);
		animation = new Animation(Bitmap.bitmapArray(new Bitmap("/res/images/enemies.png"), 48, 8, 16, 24, 2), 15);
		inShell = false;
		vx = 1.0f;
	}

	@Override
	public void render(Renderer renderer)
	{
		renderer.renderBitmap(getBitmap(), Math.round(x), Math.round(y), vx > 0);
	}
	
	@Override
	public boolean canMove()
	{
		return true;
	}
	
	private void die()
	{
		alive = false;
		vy = -1.0f;
		y -= 8;
		solid = false;
		animation.setActive(false);
	}

	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		if(!other.isSolid() || !alive) return;
		if(other instanceof BrickBlock && ((BrickBlock) other).getDisplY() != 0
		|| other instanceof QuestionBlock && ((QuestionBlock) other).getDisplY() != 0) {
			die();
			return;
		}
		if(dx != 0) {
			if(!(other instanceof Player) && !(inShell && other.canMove())) {
				x += dx;
				vx = -vx;
			}
		}
		if(dy != 0) {
			if(other instanceof Player) {
				if(dy > 0) {
					if(!inShell) {
						inShell = true;
						vx = 0;
						vy = -2.0f;
					}
					else {
						if(((Player) other).getX() > x) vx = -3.0f;
						else vx = 3.0f;
					}
				}
			}
			else {
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
		return 1;
	}

	@Override
	public void update()
	{
		x += vx;
		y += vy;
		if(!inShell) animation.update();
	}

	@Override
	public Bitmap getBitmap()
	{
		return inShell ? shell : animation.getCurrentFrame();
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return bb;
	}
	
	public boolean isInShell()
	{
		return inShell;
	}
}
