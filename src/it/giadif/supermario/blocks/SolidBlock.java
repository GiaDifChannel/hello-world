package it.giadif.supermario.blocks;

import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.CollisionModule;

public class SolidBlock extends Block {
	
	private static final Bitmap squareBlock = new Bitmap("/res/images/bricks.png", 96, 0, 16, 16);
	private static final Bitmap stoneBlock = new Bitmap("/res/images/bricks.png", 112, 0, 16, 16);
	
	private Bitmap bitmap;
	
	public SolidBlock(int x, int y, int type)
	{
		super(x, y);
		switch(type) {
		case 0:
			bitmap = squareBlock;
			break;
		case 1:
			bitmap = stoneBlock;
			break;
		default:
			bitmap = null;
		}
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		
	}

	@Override
	public Bitmap getBitmap()
	{
		return bitmap;
	}
}
