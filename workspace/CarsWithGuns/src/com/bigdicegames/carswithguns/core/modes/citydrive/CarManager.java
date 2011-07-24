package com.bigdicegames.carswithguns.core.modes.citydrive;

import java.util.ArrayList;

import forplay.core.ForPlay;
import forplay.core.GroupLayer;

public class CarManager {
	static public CarManager singleton;

	private GroupLayer layer;
	int carIndex = 0;

	ArrayList<Car> cars = new ArrayList<Car>();
	private Car playerCar;

	private CrashCallback crashedCallback;

	public CarManager(GroupLayer carLayer) {
		this.layer = carLayer;
		singleton = this;
	}

	public ArrayList<String> getImages() {
		return Car.getImages();
	}

	public void update(float delta) {
		for (Car car : cars) {
			car.update(delta);
		}

		for (int i = cars.size() - 1; i >= 0; --i) {
			Car car = cars.get(i);
			if (!car.isAlive() || !car.inbounds(0.0f, 4 * 800.0f)) {
				if (car == playerCar) {
					if (crashedCallback != null) {
						crashedCallback.onCrashed();
					}
					playerReset();
				} else {
					car.remove();
					cars.remove(car);
					CollisionMgr.get().remove(car);
				}
			}
		}

		if (cars.size() < 36) {
			int tx = (int) (ForPlay.random() * 4);
			int ty = (int) (ForPlay.random() * 4);

			spawn(ForPlay.random() * 200.0f + 300.0f + 800f * tx,
					ForPlay.random() * 200.0f + 300.0f + 800f * ty);
		}
	}

	public void spawn(float x, float y) {
		Car car = new Car(layer, x, y, carIndex);
		car.setBrain(new RandomWalkDriver(car));

		cars.add(car);
		carIndex += 1;
		carIndex %= Car.IMAGES.size();

		CollisionMgr.get().add(car);
	}

	public void spawnPlayerCar() {
		playerCar = new Car(layer, 0, 0, 4);
		playerCar.setBrain(new PlayerDriver(playerCar));

		playerCar.setSpeed(150.0f);

		cars.add(playerCar);
		// TODO(davelecompte): fix collision so that colliding with the player
		// makes sense.
		// CollisionMgr.get().add(playerCar);
		playerReset();
	}

	private void playerReset() {
		int tx = (int) (ForPlay.random() * 4);
		int ty = (int) (ForPlay.random() * 4);

		float x = tx * 800.0f + 400.0f;
		float y = ty * 800.0f + 400.0f;
		playerCar.setPosition(x, y);
		MapTile tile = MapMgr.get().getTile(x, y);
		if (tile.tile.north) {
			playerCar.setHeading((float) (-Math.PI / 2.0f));
		} else if (tile.tile.east) {
			playerCar.setHeading(0.0f);
		} else if (tile.tile.west) {
			playerCar.setHeading((float) Math.PI);
		} else {
			playerCar.setHeading((float) (Math.PI / 2.0f));
		}
		playerCar.reset();
	}

	public float getX() {
		if (cars.size() == 0) {
			return 0.0f;
		}
		return cars.get(0).getX();
	}

	public float getY() {
		if (cars.size() == 0) {
			return 0.0f;
		}
		return cars.get(0).getY();
	}

	public Car getCar(int index) {
		if (index < 0 || index >= cars.size()) {
			return null;
		}
		return cars.get(index);
	}

	public Car getPlayerCar() {
		return playerCar;
	}

	public static CarManager get() {
		return singleton;
	}

	public void setCrashedCallback(CrashCallback crashCallback) {
		crashedCallback = crashCallback;
	}

	public void paint(float alpha) {
		for (Car car : cars) {
			car.paint(alpha);
		}
	}
}
