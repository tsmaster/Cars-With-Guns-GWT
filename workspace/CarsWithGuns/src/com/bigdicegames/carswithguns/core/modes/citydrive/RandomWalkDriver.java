package com.bigdicegames.carswithguns.core.modes.citydrive;

import java.util.ArrayList;

import forplay.core.ForPlay;

public class RandomWalkDriver implements DriverBrain {
	private Car car;
	private Float steerToX;
	private Float steerToY;

	public RandomWalkDriver(Car car) {
		this.car = car;
	}

	@Override
	public void update(float delta) {
		float x = car.getX();
		float y = car.getY();

		if (steerToX == null) {
			pickDestination();
			return;
		}

		float deltaX = steerToX - x;
		float deltaY = steerToY - y;

		float distanceSqr = deltaX * deltaX + deltaY * deltaY;
		if (distanceSqr < 50 * 50) {
			steerToX = null;
			steerToY = null;
			return;
		}

		float desiredAngle = (float) Math.atan2(deltaY, deltaX);

		float currentHeading = car.getHeading();
		float deltaAngle = desiredAngle - currentHeading;

		while (deltaAngle > Math.PI) {
			deltaAngle -= Math.PI * 2;
		}

		while (deltaAngle < -Math.PI) {
			deltaAngle += Math.PI * 2;
		}

		float maxTurnSpeed = (float) (Math.PI / 2.0f);
		if (deltaAngle > maxTurnSpeed) {
			deltaAngle = maxTurnSpeed;
		}
		if (deltaAngle < -maxTurnSpeed) {
			deltaAngle = -maxTurnSpeed;
		}

		car.setHeading(deltaAngle * delta + currentHeading);
	}

	private void pickDestination() {
		float x = car.getX();
		float y = car.getY();

		MapTile myTile = MapMgr.get().getTile(x, y);

		ArrayList<Float> xs = new ArrayList<Float>();
		ArrayList<Float> ys = new ArrayList<Float>();
		if (myTile.tile.east) {
			xs.add(myTile.getCenterX() + 800.0f);
			ys.add(myTile.getCenterY());
		}
		if (myTile.tile.south) {
			xs.add(myTile.getCenterX());
			ys.add(myTile.getCenterY() + 800.0f);
		}
		if (myTile.tile.west) {
			xs.add(myTile.getCenterX() - 800.0f);
			ys.add(myTile.getCenterY());
		}
		if (myTile.tile.north) {
			xs.add(myTile.getCenterX());
			ys.add(myTile.getCenterY() - 800.0f);
		}

		int selectedIndex = (int) (ForPlay.random() * xs.size());
		steerToX = xs.get(selectedIndex);
		steerToY = ys.get(selectedIndex);
	}

	@Override
	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public void pointerDown(float mouseWX, float mouseWY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyDown(int keyCode, float mouseWX, float mouseWY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyUp(int keyCode, float mouseWX, float mouseWY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		steerToX = null;
		steerToY = null;
	}

}
