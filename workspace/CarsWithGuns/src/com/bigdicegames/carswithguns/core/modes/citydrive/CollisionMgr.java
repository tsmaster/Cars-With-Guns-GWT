package com.bigdicegames.carswithguns.core.modes.citydrive;

import java.util.ArrayList;

public class CollisionMgr {
	private static CollisionMgr singleton;

	public enum CollisionType {
		COLLISION_TYPE_CAR, COLLISION_TYPE_WEAPON
	}

	private ArrayList<Collidable> objectList = new ArrayList<Collidable>();

	public CollisionMgr() {
		singleton = this;
	}

	public void update() {
		for (int i = 0; i < objectList.size(); ++i) {
			Collidable c1 = objectList.get(i);
			float x1 = c1.getX();
			float y1 = c1.getY();
			float r1 = c1.getCollisionRadius();

			for (int j = 0; j < i; ++j) {
				Collidable c2 = objectList.get(j);
				float x2 = c2.getX();
				float y2 = c2.getY();
				float r2 = c2.getCollisionRadius();

				float combinedRadius = r1 + r2;
				float deltaX = x2 - x1;
				float deltaY = y2 - y1;
				float distanceSquared = deltaX * deltaX + deltaY * deltaY;
				if (distanceSquared <= combinedRadius * combinedRadius) {
					c1.onCollide(c2);
					c2.onCollide(c1);
				}
			}
		}
	}

	public static CollisionMgr get() {
		return singleton;
	}

	public void add(Collidable object) {
		objectList.add(object);
	}

	public void remove(Collidable object) {
		objectList.remove(object);
	}
}
