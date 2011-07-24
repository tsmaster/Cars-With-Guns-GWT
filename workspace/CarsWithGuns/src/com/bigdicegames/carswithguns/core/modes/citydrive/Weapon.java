package com.bigdicegames.carswithguns.core.modes.citydrive;

import forplay.core.Layer;

public interface Weapon extends Collidable {

	void update(float deltaSeconds);

	boolean isAlive();

	Layer getLayer();

}
