package com.bigdicegames.carswithguns.core;

import com.bigdicegames.carswithguns.core.modes.citydrive.Car;
import com.bigdicegames.carswithguns.core.modes.citydrive.CarManager;

public class InputMgr {
	static InputMgr singleton = null;
	
	public static InputMgr get() {
		return singleton;
	}

	private float mouseWX;
	private float mouseWY;
	private float mouseSX;
	private float mouseSY;
	
	public InputMgr() {
		singleton = this;
	}

	public void onKeyDown(int keyCode) {
		Car car = CarManager.get().getCar(0);
		if (car != null) {
			car.onKeyDown(keyCode, mouseWX, mouseWY);
		}
	}
	
	public void onKeyUp(int keyCode) {
		Car car = CarManager.get().getCar(0);
		if (car != null) {
			car.onKeyUp(keyCode, mouseWX, mouseWY);
		}
	}

	public void onMouseMove(float sx, float sy, float wx, float wy) {
		mouseWX = wx;
		mouseWY = wy;
		mouseSX = sx;
		mouseSY = sy;
	}
	
	public void onPointerDown(float wx, float wy) {
		Car car = CarManager.get().getPlayerCar();
		if (car != null) {
			car.onPointerDown(wx, wy);
		}
	}
	
	public float getMouseWX() {
		return mouseWX;
	}
	
	public float getMouseWY() {
		return mouseWY;
	}
}
