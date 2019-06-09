package it.giadif.supermario.utils;

public interface CollisionModule {
	
	BoundingBox getBoundingBox();
	boolean isSolid();
	boolean canMove();
	void notifyCollision(CollisionModule other, float dx, float dy);
	
	float getX();
	float getY();
	float getVx();
	float getVy();
}
