package com.bigdicegames.carswithguns.core.modes.citydrive;

import com.bigdicegames.carswithguns.core.InputMgr;
import com.bigdicegames.carswithguns.core.modes.citydrive.WeaponDisplay.WeaponType;

import forplay.core.ForPlay;
import forplay.core.Keyboard;

public class PlayerDriver implements DriverBrain {
	private Car car;
	private Float steerToX;
	private Float steerToY;
	private float triggerTimer;
	private boolean triggerDown;

	public PlayerDriver(Car car) {
		this.car = car;
	}

	@Override
	public void update(float delta) {
		updateWeapons(delta);
		updateSteering(delta);
	}

	public void updateWeapons(float delta) {
		triggerTimer += delta;
		if (triggerDown) {
			shootWeapon();
		}
	}

	public void updateSteering(float delta) {
		float x = car.getX();
		float y = car.getY();

		if (steerToX == null) {
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

	@Override
	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public void pointerDown(float mouseWX, float mouseWY) {
		steerToX = mouseWX;
		steerToY = mouseWY;
	}

	@Override
	public void keyDown(int keyCode, float mouseWX, float mouseWY) {
		switch (keyCode) {
		case '1':
			car.setSpeed(20.0f);
			break;
		case '2':
			car.setSpeed(40.0f);
			break;
		case '3':
			car.setSpeed(60.0f);
			break;
		case '4':
			car.setSpeed(80.0f);
			break;
		case '5':
			car.setSpeed(100.0f);
			break;
		case '6':
			car.setSpeed(120.0f);
			break;
		case '7':
			car.setSpeed(140.0f);
			break;
		case '8':
			car.setSpeed(160.0f);
			break;
		case '9':
			car.setSpeed(180.0f);
			break;
		case '0':
			car.setSpeed(200.0f);
			break;

		case 'Z':
			steerToX = mouseWX;
			steerToY = mouseWY;
			break;

		case Keyboard.KEY_SPACE:
			setTriggerDown(true);
			break;
		}
	}

	private void setTriggerDown(boolean isDown) {
		if (!triggerDown && isDown) {
			shootWeapon();
		}
		triggerDown = isDown;
	}

	private void shootWeapon() {
		WeaponType weaponType = car.getWeapon();
		if (weaponType == null) {
			ForPlay.log().info("no weapon");
		} else {
			if (triggerTimer >= WeaponMgr.get().getWeaponCooldown(weaponType)) {
				float x = car.getX();
				float y = car.getY();

				float deltaX = InputMgr.get().getMouseWX() - x;
				float deltaY = InputMgr.get().getMouseWY() - y;

				shootAt(x, y, (float) Math.atan2(deltaY, deltaX));
				triggerTimer = 0.0f;
			}
		}
	}

	private void shootAt(float wx, float wy, float angle) {
		WeaponType weapon = car.getWeapon();
		WeaponMgr.get().shootAt(wx, wy, angle, weapon);
	}

	@Override
	public void keyUp(int keyCode, float mouseWX, float mouseWY) {
		switch (keyCode) {

		case Keyboard.KEY_SPACE:
			setTriggerDown(false);
			break;
		}
	}

	@Override
	public void reset() {
		setTriggerDown(false);
		triggerTimer = 0.0f;
		car.setWeapon(WeaponDisplay.WeaponType.WEAPON_MACHINEGUN);
	}
}
