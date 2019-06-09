package it.giadif.supermario.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bitmap {
	
	private int width, height;
	private int[] pixels;
	
	public Bitmap(String path)
	{
		try {
			BufferedImage image = ImageIO.read(Bitmap.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			System.out.println("Impossibile importare l'immagine " + path);
			e.printStackTrace();
		}
	}
	
	public Bitmap(String path, int x0, int y0, int width, int height)
	{
		this(new Bitmap(path), x0, y0, width, height);
	}
	
	public Bitmap(Bitmap bitmap, int x0, int y0, int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[x + y * width] = bitmap.pixels[(x + x0) + (y + y0) * bitmap.width];
			}
		}
	}
	
	public static Bitmap[] bitmapArray(Bitmap bitmap, int x0, int y0, int width, int height, int n)
	{
		Bitmap[] result = new Bitmap[n];
		for(int i = 0; i < n; i++) {
			result[i] = new Bitmap(bitmap, x0, y0, width, height);
			x0 += width;
		}
		return result;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
}
