package it.giadif.supermario.entities.enemies;

import it.giadif.supermario.blocks.BrickBlock;
import it.giadif.supermario.blocks.QuestionBlock;
import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.entities.Player;
import it.giadif.supermario.graphics.Animation;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;

public class Goomba extends Entity {
	
	private static final BoundingBox bb = new BoundingBox(0, 0, 16, 16);
	
	private float vx, vy;
	private Animation animation;
	private static final Bitmap dead = new Bitmap("/res/images/enemies.png", 32, 16, 16, 16);
	private int ttl;
	
	public Goomba(float x, float y)
	{
		super(x, y, true);
		animation = new Animation(Bitmap.bitmapArray(new Bitmap("/res/images/enemies.png"), 0, 16, 16, 16, 2), 8);
		alive = true;
		vx = -1.0f;
		ttl = -1;
	}

	@Override
	public void update()
	{
		x += vx;
		y += vy;
		
		if(ttl == -1) {
			animation.update();
		}
		else if(ttl > 0) ttl--;
		else if(ttl == 0) toRemove = true;
	}

	@Override
	public boolean canMove()
	{
		return true;
	}
	
	private void die(boolean death)
	{
		alive = false;
		solid = false;
		if(death) {
			vy = -2.0f;
			y -= 8;
			animation.setActive(false);
		}
		else {
			ttl = 600;
		}
		vx = 0;
	}
	
	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		if(!other.isSolid() || (!alive && ttl == -1) || (!alive && ttl != -1 && other.canMove())) return;
		if((other instanceof BrickBlock && ((BrickBlock) other).getDisplY() != 0)
		|| (other instanceof QuestionBlock && ((QuestionBlock) other).getDisplY() != 0)
		|| (other instanceof Turtle && ((Turtle) other).isInShell() && other.getVx() != 0)) {
			die(true);
			return;
		}
		if(dx != 0) {
			if(!(other instanceof Player)) {
				x += dx;
				vx = -vx;
			}
		}
		if(dy != 0) {
			if(other instanceof Player && ((Player) other).isAlive() && dy > 0) {
				die(false);
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

	public void setVx(float vx)
	{
		this.vx = vx;
	}

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
	public Bitmap getBitmap()
	{
		return ttl == -1 ? animation.getCurrentFrame() : dead;
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return bb;
	}

}
