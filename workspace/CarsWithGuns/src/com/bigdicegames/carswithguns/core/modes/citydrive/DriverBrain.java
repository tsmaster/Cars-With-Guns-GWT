package com.bigdicegames.carswithguns.core.modes.citydrive;

public interface DriverBrain {
	public void setCar(Car car);

	public void update(float delta);

	public void pointerDown(float mouseWX, float mouseWY);

	public void keyDown(int keyCode, float mouseWX, float mouseWY);

	public void keyUp(int keyCode, float mouseWX, float mouseWY);

	public void reset();
}
