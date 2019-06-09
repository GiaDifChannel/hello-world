package it.giadif.supermario.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.giadif.supermario.blocks.Block;
import it.giadif.supermario.blocks.BrickBlock;
import it.giadif.supermario.blocks.QuestionBlock;
import it.giadif.supermario.blocks.SolidBlock;
import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.entities.Player;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.graphics.Renderer;
import it.giadif.supermario.utils.BoundingBox;
import it.giadif.supermario.utils.CollisionModule;
import it.giadif.supermario.utils.Renderable;

public abstract class Level {

public static final float GRAVITY = 0.2f;
	
	protected Player player;
	protected List<Block> blocks;
	protected List<Entity> entities;
	protected List<Renderable> toRender;
	
	public Level(Bitmap map)
	{
		blocks = new ArrayList<>();
		entities = new ArrayList<>();
		toRender = new ArrayList<>();
		decodeBlocks(map);
	}
	
	protected void decodeBlocks(Bitmap map)
	{
		int[] pixels = map.getPixels();
		for(int y = 0; y < map.getHeight(); y++) {
			for(int x = 0; x < map.getWidth(); x++) {
				switch(pixels[x + y * map.getWidth()]) {
				case 0xFFFF00FF:
					blocks.add(new SolidBlock(x * Block.SIZE, y * Block.SIZE, 1));
					break;
				case 0xFF000000:
					blocks.add(new BrickBlock(x * Block.SIZE, y * Block.SIZE));
					break;
				case 0xFFFFFF00:
				case 0xFFFFFF01:
					blocks.add(new QuestionBlock(x * Block.SIZE, y * Block.SIZE));
					break;
				case 0xFF0000FF:
					blocks.add(new SolidBlock(x * Block.SIZE, y * Block.SIZE, 0));
					break;
				}
			}
		}
	}
	
	public abstract void update();
	
	public void render(Renderer renderer)
	{
		int screenW = renderer.getWidth();
		int cameraX = renderer.getCameraX();
		for (Block block : blocks) {
			if(block.getX() >= cameraX + screenW || block.getX() <= cameraX - Block.SIZE) continue;
			toRender.add(block);
		}
		for (Entity entity : entities) {
			if(entity.getX() >= cameraX + screenW || entity.getX() <= cameraX - entity.getBitmap().getWidth()) continue;
			toRender.add(entity);
		}
		toRender.sort(new Comparator<Renderable>() {

			@Override
			public int compare(Renderable arg0, Renderable arg1)
			{
				return Integer.compare(arg0.getZ(), arg1.getZ());
			}
			
		});
		for(Renderable renderable : toRender) {
			renderable.render(renderer);
		}
		toRender.clear();
	}
	
	protected int checkCollision(CollisionModule c0, CollisionModule c1)
	{
		if(c0 == c1) return -1;
		if(!c0.canMove() && !c1.canMove()) return -1;
		BoundingBox bb0 = c0.getBoundingBox();
		if(bb0 == null) return -1;
		BoundingBox bb1 = c1.getBoundingBox();
		if(bb1 == null) return -1;
		
		float[] x = new float[2];
		float[] y = new float[2];
		float[] vx = new float[2];
		float[] vy = new float[2];
		
		x[0] = c0.getX() + bb0.x;
		y[0] = c0.getY() + bb0.y;
		vx[0] = c0.getVx();
		vy[0] = c0.getVy();

		x[1] = c1.getX() + bb1.x;
		y[1] = c1.getY() + bb1.y;
		vx[1] = c1.getVx();
		vy[1] = c1.getVy();
		
		float[] Dx = new float[2];
		float[] Dy = new float[2];
		
		Dx[0] = x[0] + bb0.width - x[1];
		if(Dx[0] <= 0.0f) return -1;
		Dx[1] = x[1] + bb1.width - x[0];
		if(Dx[1] <= 0.0f) return -1;
		Dy[0] = y[0] + bb0.height - y[1];
		if(Dy[0] <= 0.0f) return -1;
		Dy[1] = y[1] + bb1.height - y[0];
		if(Dy[1] <= 0.0f) return -1;

		float dvx = vx[0] - vx[1];
		float dvy = vy[0] - vy[1];
		
		boolean dvxgez = dvx >= 0.0f;
		boolean dvygez = dvy >= 0.0f;
		
		float kx = dvxgez ? -Dx[0] / dvx : Dx[1] / dvx;
		float ky = dvygez ? -Dy[0] / dvy : Dy[1] / dvy;
		float k = Math.max(kx, ky);
		
		float dx = 0, dy = 0;
		int result = 0;
		if(k == kx) {
			dx = dvxgez ? -Dx[0] : Dx[1];
			result += 1;
		}
		if(k == ky) {
			dy = dvygez ? -Dy[0] : Dy[1];
			result += 2;
		}
		c0.notifyCollision(c1, dx, dy);
		c1.notifyCollision(c0, -dx, -dy);
		return result;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public List<Block> getBlocks()
	{
		return blocks;
	}
	
	public List<Entity> getEntities()
	{
		return entities;
	}
	
}
