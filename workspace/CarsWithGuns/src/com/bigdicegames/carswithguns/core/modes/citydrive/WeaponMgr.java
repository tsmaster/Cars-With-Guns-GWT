package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.assetManager;

import java.util.ArrayList;

import com.bigdicegames.carswithguns.core.modes.citydrive.WeaponDisplay.WeaponType;

import forplay.core.GroupLayer;
import forplay.core.Sound;

public class WeaponMgr {
	private static WeaponMgr singleton;
	private GroupLayer weaponLayer;
	private Sound missileSound;
	private Sound gunSound;

	ArrayList<Weapon> weaponArray = new ArrayList<Weapon>();

	public WeaponMgr(GroupLayer weaponLayer) {
		singleton = this;
		this.weaponLayer = weaponLayer;
		missileSound = assetManager().getSound("sounds/rocket");
		gunSound = assetManager().getSound("sounds/click");
	}

	public static WeaponMgr get() {
		return singleton;
	}

	public void spawnMissile(float startX, float startY, float angle) {
		Missile missile = new Missile(startX, startY, angle);
		weaponLayer.add(missile.getLayer());
		weaponArray.add(missile);
		CollisionMgr.get().add(missile);
		missileSound.play();
	}

	public void spawnBullet(float startX, float startY, float angle) {
		Bullet bullet = new Bullet(startX, startY, angle);
		weaponLayer.add(bullet.getLayer());
		weaponArray.add(bullet);
		CollisionMgr.get().add(bullet);
		gunSound.play();
	}

	public void update(float delta) {
		for (Weapon weapon : weaponArray) {
			weapon.update(delta);
		}

		for (int i = weaponArray.size() - 1; i >= 0; --i) {
			Weapon weapon = weaponArray.get(i);
			if (!weapon.isAlive()) {
				weaponLayer.remove(weapon.getLayer());
				weaponArray.remove(weapon);
				CollisionMgr.get().remove(weapon);
			}
		}

	}

	public void shootAt(float wx, float wy, float angle, WeaponType weapon) {
		switch (weapon) {
		case WEAPON_MACHINEGUN:
			spawnBullet(wx, wy, angle);
			break;
		case WEAPON_ROCKET:
			spawnMissile(wx, wy, angle);
			break;
		case WEAPON_OILJET:
			break;
		case WEAPON_FLAMETHROWER:
			break;
		}
	}

	public float getWeaponCooldown(WeaponType weapon) {
		switch (weapon) {
		case WEAPON_MACHINEGUN:
			return 0.1f;
		case WEAPON_ROCKET:
			return 1.0f;
		case WEAPON_OILJET:
			return 1.0f;
		case WEAPON_FLAMETHROWER:
			return 2.0f;
		}
		return 0.0f;
	}
}
