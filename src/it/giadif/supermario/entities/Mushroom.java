package it.giadif.supermario.entities;

import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;

public class Mushroom extends Entity {
	
	private static final BoundingBox bb = new BoundingBox(0, 0, 16, 16);
	private static final Bitmap bitmap = new Bitmap("/res/images/items.png", 0, 0, 16, 16);
	
	private float vx, vy;

	public Mushroom(float x, float y)
	{
		super(x, y, false);
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return bb;
	}

	@Override
	public boolean canMove()
	{
		return true;
	}

	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		if(other instanceof Player) {
			toRemove = true;
			return;
		}
		if(!other.isSolid() || other.canMove()) return;
		if(dx != 0) {
			x += dx;
			vx = -vx;
		}
		if(dy != 0) {
			y += dy;
			vy = 0;
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
	public Bitmap getBitmap()
	{
		return bitmap;
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

}
