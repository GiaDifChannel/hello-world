package it.giadif.supermario.entities;

import it.giadif.supermario.graphics.Renderer;
import it.giadif.supermario.utils.CollisionModule;
import it.giadif.supermario.utils.Renderable;

public abstract class Entity implements CollisionModule, Renderable {
	
	protected float x, y;
	protected boolean solid;
	protected boolean alive;
	protected boolean toRemove;
	
	public Entity(float x, float y, boolean solid)
	{
		this.x = x;
		this.y = y;
		this.solid = solid;
		alive = true;
		toRemove = false;
	}
	
	public abstract void update();
	
	public boolean toRemove()
	{
		return toRemove;
	}
	
	@Override
	public void render(Renderer renderer)
	{
		renderer.renderBitmap(getBitmap(), Math.round(x), Math.round(y), false);
	}
	
	public boolean isAlive()
	{
		return alive;
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
	public boolean isSolid()
	{
		return solid;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public abstract void setVx(float vx);
	
	public abstract void setVy(float vy);
	
	public void setSolid(boolean solid)
	{
		this.solid = solid;
	}

}
