package it.giadif.supermario.entities.decorations;

import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;

public class Hill extends Entity {
	
	public static final Bitmap bitmap = new Bitmap("/res/images/scenery.png", 32, 64, 80, 48);

	public Hill(float x, float y)
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
		return -2;
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
