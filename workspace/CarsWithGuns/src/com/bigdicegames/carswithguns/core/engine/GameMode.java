package com.bigdicegames.carswithguns.core.engine;

public interface GameMode {
	void onUpdate(float deltaSeconds);
	
	void onPushed();
	
	void onPopped();

	void onPointerStart(float x, float y);
	void onPointerEnd(float x, float y);

	void onKeyDown(int keyCode);
	void onKeyUp(int keyCode);

	void onMouseMove(float x, float y);

	void onMouseWheelScroll(float velocity);

	void paint(float alpha);
}
