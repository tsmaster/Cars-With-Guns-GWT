package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.graphics;
import forplay.core.GroupLayer;
import forplay.core.Image;
import forplay.core.ImageLayer;

public class WeaponDisplay {
	private static final int SELECTED_HEIGHT = 690;
	private static final int DESELECTED_HEIGHT = 730;

	private static final int MG_X = 100;
	private static final int ROCKET_X = 200;
	private static final int OJ_X = 300;
	private static final int FT_X = 400;

	private Image rocketTabImage;
	private ImageLayer rocketTabLayer;

	private Image flamethrowerTabImage;
	private ImageLayer flamethrowerTabLayer;

	private Image machineGunTabImage;
	private ImageLayer machineGunTabLayer;

	private Image oilJetTabImage;
	private ImageLayer oilJetTabLayer;
	private GroupLayer layer;
	private WeaponType weapon;

	public enum WeaponType {
		WEAPON_MACHINEGUN, WEAPON_ROCKET, WEAPON_OILJET, WEAPON_FLAMETHROWER,
	}

	public WeaponDisplay() {
		layer = graphics().createGroupLayer();

		rocketTabImage = assetManager().getImage("images/weaponTab_rocket.png");
		rocketTabLayer = graphics().createImageLayer(rocketTabImage);
		layer.add(rocketTabLayer);

		flamethrowerTabImage = assetManager().getImage(
				"images/weaponTab_flamethrower.png");
		flamethrowerTabLayer = graphics()
				.createImageLayer(flamethrowerTabImage);
		layer.add(flamethrowerTabLayer);

		machineGunTabImage = assetManager().getImage(
				"images/weaponTab_machinegun.png");
		machineGunTabLayer = graphics().createImageLayer(machineGunTabImage);
		layer.add(machineGunTabLayer);

		oilJetTabImage = assetManager().getImage("images/weaponTab_oilJet.png");
		oilJetTabLayer = graphics().createImageLayer(oilJetTabImage);
		layer.add(oilJetTabLayer);

		selectWeapon(WeaponType.WEAPON_ROCKET);
	}

	public GroupLayer getLayer() {
		return layer;
	}

	public void selectWeapon(WeaponType weapon) {
		this.weapon = weapon;
		if (weapon == WeaponType.WEAPON_ROCKET) {
			rocketTabLayer.setTranslation(ROCKET_X, SELECTED_HEIGHT);
		} else {
			rocketTabLayer.setTranslation(ROCKET_X, DESELECTED_HEIGHT);
		}
		if (weapon == WeaponType.WEAPON_FLAMETHROWER) {
			flamethrowerTabLayer.setTranslation(FT_X, SELECTED_HEIGHT);
		} else {
			flamethrowerTabLayer.setTranslation(FT_X, DESELECTED_HEIGHT);
		}
		if (weapon == WeaponType.WEAPON_MACHINEGUN) {
			machineGunTabLayer.setTranslation(MG_X, SELECTED_HEIGHT);
		} else {
			machineGunTabLayer.setTranslation(MG_X, DESELECTED_HEIGHT);
		}
		if (weapon == WeaponType.WEAPON_OILJET) {
			oilJetTabLayer.setTranslation(OJ_X, SELECTED_HEIGHT);
		} else {
			oilJetTabLayer.setTranslation(OJ_X, DESELECTED_HEIGHT);
		}
	}

	public void incrementWeapon() {
		int oldWeaponIndex = weapon.ordinal();
		WeaponType[] values = WeaponType.values();
		int newWeaponIndex = (oldWeaponIndex + 1) % values.length;
		WeaponType wt = values[newWeaponIndex];
		selectWeapon(wt);
	}

	public void decrementWeapon() {
		int oldWeaponIndex = weapon.ordinal();
		WeaponType[] values = WeaponType.values();
		int newWeaponIndex = (oldWeaponIndex - 1 + values.length)
				% values.length;
		WeaponType wt = values[newWeaponIndex];
		selectWeapon(wt);
	}

	public WeaponType getWeapon() {
		return this.weapon;
	}
}
