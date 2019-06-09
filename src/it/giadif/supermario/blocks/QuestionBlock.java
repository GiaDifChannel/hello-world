package it.giadif.supermario.blocks;

import it.giadif.supermario.entities.Player;
import it.giadif.supermario.graphics.Animation;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.CollisionModule;

public class QuestionBlock extends Block {
	
	private static final Bitmap[] frames = Bitmap.bitmapArray(new Bitmap("/res/images/bricks.png"), 0, 0, 16, 16, 3);
	private static final Bitmap dead = new Bitmap("/res/images/bricks.png", 48, 0, 16, 16);
	
	private Animation animation;
	private int lives;
	private int displY;

	public QuestionBlock(int x, int y)
	{
		super(x, y);
		animation = new Animation(frames, 10);
		lives = 3;
		displY = 0;
	}
	
	@Override
	public void update()
	{
		if(displY > 0) {
			displY--;
			y++;
		}
		if(lives != 0) animation.update();
	}

	@Override
	public void notifyCollision(CollisionModule other, float dx, float dy)
	{
		if(lives != 0) {
			if(other instanceof Player) {
				if(dy != 0) {
					if(other.getY() > y && displY == 0) {
						displY = 6;
						y -= 6;
						lives--;
					}
				}
			}
		}
	}

	@Override
	public Bitmap getBitmap()
	{
		return lives != 0 ? animation.getCurrentFrame() : dead;
	}
	
	public int getDisplY()
	{
		return displY;
	}
	
}
