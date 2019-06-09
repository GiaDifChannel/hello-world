package it.giadif.supermario.graphics;

public class Animation {
	
	private Bitmap[] frames;
	private int frameIndex;
	private int upsPerFrame;
	private int clock;
	private boolean active;
	
	public Animation(Bitmap[] frames, int upsPerFrame)
	{
		this.frames = frames;
		this.upsPerFrame = upsPerFrame;
		frameIndex = 0;
		clock = 0;
		active = true;
	}
	
	public void update()
	{
		if(!active) return;
		clock++;
		if(clock == upsPerFrame) {
			clock = 0;
			frameIndex++;
			if(frameIndex == frames.length) {
				frameIndex = 0;
			}
		}
	}
	
	public Bitmap getCurrentFrame()
	{
		return frames[frameIndex];
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public void reset()
	{
		frameIndex = 0;
		clock = 0;
	}
}
