package it.giadif.supermario.entities.decorations;

import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;

public class Bush extends Entity {

	public static final Bitmap bush1 = new Bitmap("/res/images/scenery.png", 0, 32, 32, 32);
	public static final Bitmap bush2 = new Bitmap("/res/images/scenery.png", 32, 32, 48, 32);
	public static final Bitmap bush3 = new Bitmap("/res/images/scenery.png", 80, 32, 64, 32);
	
	private Bitmap bitmap;
	
	public Bush(float x, float y, int type)
	{
		super(x, y, false);
		switch(type) {
		case 1:
			bitmap = bush1;
			break;
		case 2:
			bitmap = bush2;
			break;
		case 3:
			bitmap = bush3;
			break;
		}
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
		return -1;
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
