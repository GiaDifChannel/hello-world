package it.giadif.supermario.entities.decorations;

import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;

public class Tube extends Entity {
	
	public static final Bitmap bitmap = new Bitmap("/res/images/scenery.png", 0, 64, 32, 64);
	public static final BoundingBox bb = new BoundingBox(0, 0, 32, 64);
	
	public Tube(float x, float y)
	{
		super(x, y, true);
	}

	@Override
	public void update()
	{
		
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
		return 0.0f;
	}

	@Override
	public float getVy()
	{
		return 0.0f;
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
	public int getZ()
	{
		return 0;
	}

	@Override
	public Bitmap getBitmap()
	{
		return bitmap;
	}

	@Override
	public BoundingBox getBoundingBox()
	{
		return bb;
	}

}
