package com.bigdicegames.carswithguns.core.modes.citydrive;

public interface Collidable {
	public float getCollisionRadius();

	public float getX();

	public float getY();

	public int getCollidableType();

	public void onCollide(Collidable other);
}
