package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.graphics;

import java.util.ArrayList;
import java.util.List;

import com.bigdicegames.carswithguns.core.InputMgr;
import com.bigdicegames.carswithguns.core.engine.GameMode;
import com.bigdicegames.carswithguns.core.engine.ModeMgr;
import com.bigdicegames.carswithguns.core.modes.crashed.ModeCrashedDialog;

import forplay.core.GroupLayer;

public class ModeGameplay implements GameMode {
	private static final int OFFSET_Y = 380;
	private static final int OFFSET_X = 512;
	GroupLayer carLayer;
	List<Car> cars = new ArrayList<Car>(0);

	int carIndex = 0;
	private CarManager carManager;
	private MapMgr mapMgr;
	private WeaponMgr weaponMgr;
	private InputMgr inputMgr;
	private GroupLayer weaponLayer;
	private WeaponDisplay weaponDisplay;
	private GroupLayer particleLayer;
	private ParticleMgr particleMgr;
	private CollisionMgr collisionMgr;
	private ModeCrashedDialog crashedDialogMode;
	private float screenY;
	private float screenX;

	public ModeGameplay() {
		mapMgr = new MapMgr();

		graphics().setSize(1024, 768);
		graphics().rootLayer().add(mapMgr.getLayer());

		weaponLayer = graphics().createGroupLayer();
		weaponMgr = new WeaponMgr(weaponLayer);

		particleLayer = graphics().createGroupLayer();
		particleMgr = new ParticleMgr(particleLayer);

		collisionMgr = new CollisionMgr();
		inputMgr = new InputMgr();

		// create a group layer to hold the cars
		carLayer = graphics().createGroupLayer();

		mapMgr.getLayer().add(carLayer);
		mapMgr.getLayer().add(particleLayer);
		mapMgr.getLayer().add(weaponLayer);

		crashedDialogMode = new ModeCrashedDialog();

		carManager = new CarManager(carLayer);
		carManager.spawnPlayerCar();
		carManager.setCrashedCallback(new CrashCallback() {

			@Override
			public void onCrashed() {
				// TODO: clean up weapon display reset
				weaponDisplay
						.selectWeapon(WeaponDisplay.WeaponType.WEAPON_MACHINEGUN);
				ModeMgr.get().pushGameMode(crashedDialogMode);
			}
		});

		for (String carImage : carManager.getImages()) {
			assetManager().getImage(carImage);
		}

		weaponDisplay = new WeaponDisplay();
		weaponDisplay.selectWeapon(WeaponDisplay.WeaponType.WEAPON_MACHINEGUN);
		carManager.getPlayerCar().setWeapon(weaponDisplay.getWeapon());
		graphics().rootLayer().add(weaponDisplay.getLayer());
	}

	@Override
	public void onUpdate(float deltaSeconds) {
		carManager.update(deltaSeconds);
		weaponMgr.update(deltaSeconds);
		particleMgr.update(deltaSeconds);
		collisionMgr.update();

		float x = carManager.getX();
		float y = carManager.getY();

		mapMgr.getLayer().setTranslation(OFFSET_X - x, OFFSET_Y - y);
		InputMgr.get().onMouseMove(screenX, screenY, screenToWorldX(screenX),
				screenToWorldY(screenY));
	}

	@Override
	public void onPushed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPopped() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPointerStart(float x, float y) {
		float wx = screenToWorldX(x);
		float wy = screenToWorldY(y);

		inputMgr.onPointerDown(wx, wy);
	}

	@Override
	public void onPointerEnd(float x, float y) {
		// TODO Auto-generated method stub

	}

	private float screenToWorldX(float screenX) {
		return screenX + carManager.getX() - OFFSET_X;
	}

	private float screenToWorldY(float screenY) {
		return screenY + carManager.getY() - OFFSET_Y;
	}

	@Override
	public void onKeyDown(int keyCode) {
		InputMgr.get().onKeyDown(keyCode);
	}

	@Override
	public void onKeyUp(int keyCode) {
		InputMgr.get().onKeyUp(keyCode);
	}

	@Override
	public void onMouseMove(float x, float y) {
		screenX = x;
		screenY = y;
	}

	@Override
	public void onMouseWheelScroll(float velocity) {
		if (velocity > 0) {
			weaponDisplay.incrementWeapon();
		} else {
			weaponDisplay.decrementWeapon();
		}

		carManager.getPlayerCar().setWeapon(weaponDisplay.getWeapon());
	}

	@Override
	public void paint(float alpha) {
		carManager.paint(alpha);

	}
}
