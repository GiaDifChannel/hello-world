package it.giadif.supermario.entities.decorations;

import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;

public class Castle extends Entity {
	
	public static final Bitmap bitmap = new Bitmap("/res/images/scenery.png", 0, 128, 80, 80);

	public Castle(float x, float y)
	{
		super(x, y, false);
	}

	@Override
	public boolean canMove()
	{
		return false;
	}

	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		
	}

	@Override
	public float getVx()
	{
		return 0;
	}

	@Override
	public float getVy()
	{
		return 0;
	}

	@Override
	public void setVx(float vx)
	{
		
	}

	@Override
	public void setVy(float vy)
	{
		
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public int getZ()
	{
		return -3;
	}

	@Override
	public Bitmap getBitmap()
	{
		return bitmap;
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return null;
	}

}
