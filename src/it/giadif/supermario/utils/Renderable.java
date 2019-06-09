package it.giadif.supermario.utils;

import it.giadif.supermario.graphics.Bitmap;
import it.giadif.supermario.graphics.Renderer;

public interface Renderable {

	void render(Renderer renderer);
	
	Bitmap getBitmap();
	
	int getZ();
}
