package com.bigdicegames.carswithguns.core.modes.crashed;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.graphics;

import com.bigdicegames.carswithguns.core.engine.GameMode;
import com.bigdicegames.carswithguns.core.engine.ModeMgr;

import forplay.core.Image;
import forplay.core.ImageLayer;

public class ModeCrashedDialog implements GameMode {
	private static final float MINIMUM_LIFETIME = 1.0f;

	private float elapsedDuration;
	private Image dialogImage;
	private ImageLayer dialogLayer;
	private boolean clicked;
	
	public ModeCrashedDialog() {
		dialogImage = assetManager().getImage("images/crashed.png");
		dialogLayer = graphics().createImageLayer(dialogImage);
	}
	
	@Override
	public void onUpdate(float deltaSeconds) {
		elapsedDuration += deltaSeconds;
		maybeFinish();
	}

	@Override
	public void onPushed() {
		elapsedDuration = 0.0f;
		clicked = false;

		graphics().rootLayer().add(dialogLayer);
	}

	@Override
	public void onPopped() {
		graphics().rootLayer().remove(dialogLayer);
	}

	@Override
	public void onPointerStart(float x, float y) {
		clicked = true;
		maybeFinish();
	}

	@Override
	public void onPointerEnd(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyDown(int keyCode) {
		clicked = true;
		maybeFinish();
	}

	@Override
	public void onMouseMove(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseWheelScroll(float velocity) {
		// TODO Auto-generated method stub

	}
	
	private void maybeFinish() {
		if (clicked && elapsedDuration >= MINIMUM_LIFETIME) {
			ModeMgr.get().popGameMode(this);
		}
	}

	@Override
	public void paint(float alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyUp(int keyCode) {
		// TODO Auto-generated method stub
		
	}

}
