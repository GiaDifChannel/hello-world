package it.giadif.supermario.world;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;

import it.giadif.supermario.blocks.Block;
import it.giadif.supermario.entities.Entity;
import it.giadif.supermario.entities.Mushroom;
import it.giadif.supermario.entities.Player;
import it.giadif.supermario.entities.decorations.Bush;
import it.giadif.supermario.entities.decorations.Castle;
import it.giadif.supermario.entities.decorations.Cloud;
import it.giadif.supermario.entities.decorations.Hill;
import it.giadif.supermario.entities.decorations.Tube;
import it.giadif.supermario.entities.enemies.Goomba;
import it.giadif.supermario.entities.enemies.Turtle;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.utils.BoundingBox;

public class Level1 extends Level {
	
	public Level1()
	{	
		super(new Bitmap("/res/images/level1.png"));
		Bitmap bitmap = new Bitmap("/res/images/level1.png");
		player = new Player(400, 100);
		entities.add(player);

		entities.add(new Goomba(500, 120));
		entities.add(new Turtle(550, 120));
		entities.add(new Mushroom(400, 0));
		
		Random r = new Random();
		
		for(int y = 0; y < bitmap.getHeight(); y++) {
			for(int x = 0; x < bitmap.getWidth(); x++) {
				switch(bitmap.getPixels()[x + y * bitmap.getWidth()]) {
				case 0xFF00FF00:
					entities.add(new Bush(x * 16, y * 16, r.nextInt(3) + 1));
					break;
				case 0xFF007F00:
					entities.add(new Hill(x * 16, y * 16));
					break;
				case 0xFFFF0000:
					entities.add(new Tube(x * 16, y * 16));
					break;
				case 0xFF7F7F7F:
					entities.add(new Castle(x * 16, y * 16));
					break;
				case 0xFFFFFFFF:
					entities.add(new Cloud(x * 16, y * 16, r.nextInt(3) + 1));
					break;
				}
			}
		}
	}
	
	@Override
	public void update()
	{
		List<Block> toRemove_blocks = new ArrayList<>();
		List<Entity> toRemove_entities = new ArrayList<>();
		for (Block block : blocks) {
			if(block.toRemove()) toRemove_blocks.add(block);
			else block.update();
		}
		for(Entity entity : entities) {
			if(entity.toRemove() || entity.getY() > 250) toRemove_entities.add(entity);
			else {
				entity.setVy(entity.getVy() + GRAVITY);
				entity.update();
			}
		}
		for(Entity entity : toRemove_entities) {
			entities.remove(entity);
		}
		for(Block block : toRemove_blocks) {
			blocks.remove(block);
		}
		
		for(Entity entity : entities) {
			List<Block> toCheck = new ArrayList<>();
			for (Block block : blocks) {
				BoundingBox bb = entity.getBoundingBox();
				if(bb == null) continue;
				float dx = entity.getX() + bb.x - block.getX();
				float dy = entity.getY() + bb.y - block.getY();
				float threshold = Math.max(bb.width, bb.height);
				threshold *= threshold * 2;
				float distance = dx * dx + dy * dy;
				if(distance <= threshold) toCheck.add(block);
			}
			toCheck.sort(new Comparator<Block>() {
				@Override
				public int compare(Block arg0, Block arg1) {
					float dx0 = entity.getX() - arg0.getX();
					float dy0 = entity.getY() - arg0.getY();
					float dx1 = entity.getX() - arg1.getX();
					float dy1 = entity.getY() - arg1.getY();
					float d0 = dx0 * dx0 + dy0 * dy0;
					float d1 = dx1 * dx1 + dy1 * dy1;
					return Float.compare(d0, d1);
				}
			});
			boolean hor = false, ver = false;
			for (Block block : toCheck) {
				int result = checkCollision(entity, block);
				if(result == -1) continue;
				if((result & 1) == 1) hor = true;
				if((result & 2) == 2) ver = true;
				if(hor && ver) break;
			}
		}
		
		for(int i = 0; i < entities.size(); i++) {
			for(int j = i + 1; j < entities.size(); j++) {
				checkCollision(entities.get(i), entities.get(j));
			}
		}
	}

}
