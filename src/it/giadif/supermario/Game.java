package it.giadif.supermario;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import it.giadif.supermario.blocks.Block;
import it.giadif.supermario.entities.Player;
import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.graphics.Renderer;
import it.giadif.supermario.world.Level1;

public class Game extends Canvas implements Runnable {

	private int width, height;
	private static final double FPS = 60.0;
	
	private BufferedImage image;
	private int[] pixels;
	
	private Thread thread;
	private boolean running;
	
	private Input input;
	
	private Renderer renderer;
	private Level1 level1;
	
	private int cameraX;
	
	public Game(int width, int height, float scale)
	{
		this.width = (int) (width * scale);
		this.height = (int) (height * scale);
		setSize(this.width, this.height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		input = new Input();
		addMouseListener(input);
		addMouseMotionListener(input);
		addKeyListener(input);
		renderer = new Renderer(width, height, pixels);
		renderer.setCameraY(110);
		cameraX = 240;
		
		level1 = new Level1();
	}
	
	public void start()
	{
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() throws InterruptedException
	{
		if(!running) return;
		running = false;
		thread.join();
	}
	
	@Override
	public void run()
	{
		requestFocus();
		double nsPerUpdate = 1000000000.0 / FPS;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		double delta = 0.0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerUpdate;
			lastTime = now;
			while(delta >= 1.0) {
				delta -= 1.0;
				update();
				updates++;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				Main.frame.setTitle("UPS: " + updates + " | FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void update()
	{
		if(input.esc) System.exit(0);
		renderer.clear();
		
		Player player = level1.getPlayer();
		
		player.move(input.left, input.right);
		if(input.up) player.jump();
		if(input.down) player.crouch();
		else player.stand();
		
		level1.update();
		
		if(player.getX() > cameraX) cameraX = Math.round(player.getX());
		renderer.setCameraX(cameraX);
		
		level1.render(renderer);
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(1);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		bs.show();
	}

}
