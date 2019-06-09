package it.giadif.supermario.graphics;

public class Renderer {

	private int width, height;
	private int[] pixels;
	
	private int cameraX, cameraY;
	
	private static final int ALPHA_COLOR = 0xFFFF00FF;
	
	public Renderer(int width, int height, int[] pixels)
	{
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; i++) pixels[i] = 0xFFA1ADFF;
	}
	
	public void renderBitmap(Bitmap bitmap, int x0, int y0, boolean flip)
	{
		if(bitmap == null) return;
		x0 -= cameraX;
		y0 -= cameraY;
		int bitmapWidth = bitmap.getWidth();
		int bitmapHeight = bitmap.getHeight();
		int[] bitmapPixels = bitmap.getPixels();
		for(int y = 0; y < bitmapHeight; y++) {
			int yScreen = y + y0;
			if(yScreen < 0) continue;
			else if(yScreen >= height) break;
			int screenStride = yScreen * width;
			int bitmapStride = y * bitmapWidth;
			for(int x = 0; x < bitmapWidth; x++) {
				int xScreen = x + x0;
				if(xScreen < 0) continue;
				else if(xScreen >= width) break;
				int color = flip ?
						bitmapPixels[bitmapWidth - 1 - x + bitmapStride] :
						bitmapPixels[x + bitmapStride];
				if(color != ALPHA_COLOR) {
					pixels[xScreen + screenStride] = color;
				}
			}
		}
	}
	
	public int getCameraX()
	{
		return cameraX;
	}
	
	public int getCameraY()
	{
		return cameraY;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setCameraX(int cameraX)
	{
		this.cameraX = cameraX - width / 2;
	}
	
	public void setCameraY(int cameraY)
	{
		this.cameraY = cameraY - height / 2;
	}
	
}
