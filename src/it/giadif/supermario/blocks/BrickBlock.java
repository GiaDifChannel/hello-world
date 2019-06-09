package it.giadif.supermario.blocks;

import it.giadif.supermario.entities.Player;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.CollisionModule;

public class BrickBlock extends Block {
	
	private static final Bitmap bitmap = new Bitmap("/res/images/bricks.png", 64, 0, 16, 16);
	private int displY;

	public BrickBlock(int x, int y)
	{
		super(x, y);
		displY = 0;
	}

	@Override
	public void update()
	{
		if(displY > 0) {
			displY--;
			y++;
		}
	}

	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		if(other instanceof Player) {
			if(dy != 0) {
				if(other.getY() > y && displY == 0) {
					displY = 6;
					y -= 6;
				}
			}
		}
	}

	@Override
	public Bitmap getBitmap()
	{
		return bitmap;
	}
	
	public int getDisplY()
	{
		return displY;
	}
	
}
