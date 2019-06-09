package it.giadif.supermario.blocks;

import it.giadif.supermario.graphics.Renderer;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;
import it.giadif.supermario.utils.Renderable;

public abstract class Block implements CollisionModule, Renderable {

	public static final int SIZE = 16;
	public static final BoundingBox bb = new BoundingBox(0, 0, SIZE, SIZE);
	private boolean toRemove;
	
	protected float x, y;
	
	public Block(int x, int y)
	{
		this.x = x;
		this.y = y;
		toRemove = false;
	}
	
	public abstract void update();
	
	@Override
	public void render(Renderer renderer)
	{
		renderer.renderBitmap(getBitmap(), (int)x, (int)y, false);
	}
	
	public boolean toRemove()
	{
		return toRemove;
	}
	
	@Override
	public float getX()
	{
		return x;
	}
	
	@Override
	public float getY()
	{
		return y;
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
	public boolean isSolid()
	{
		return true;
	}

	@Override
	public boolean canMove()
	{
		return false;
	}
	
	@Override
	public BoundingBox getBoundingBox()
	{
		return bb;
	}
	
	@Override
	public int getZ()
	{
		return 1;
	}
}
