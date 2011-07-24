package com.bigdicegames.carswithguns.core;

import static forplay.core.ForPlay.graphics;
import static forplay.core.ForPlay.keyboard;
import static forplay.core.ForPlay.mouse;
import static forplay.core.ForPlay.pointer;

import java.util.ArrayList;

import com.bigdicegames.carswithguns.core.engine.AbstractCompletionCallback;
import com.bigdicegames.carswithguns.core.engine.ModeMgr;
import com.bigdicegames.carswithguns.core.modes.citydrive.ModeGameplay;
import com.bigdicegames.carswithguns.core.modes.loading.ModeLoading;

import forplay.core.Game;
import forplay.core.Keyboard;
import forplay.core.Mouse;
import forplay.core.Pointer;

public class CarsWithGuns implements Game, Pointer.Listener, Keyboard.Listener, Mouse.Listener {

	private ModeMgr modeMgr;

	@Override
	public void init() {
	    graphics().setSize(1024, 768);
		modeMgr = new ModeMgr();
		ArrayList<String> assetNames = new ArrayList<String>();
		assetNames.add("images/title.png");
		assetNames.add("images/crashed.png");
		assetNames.add("images/missile.png");
		assetNames.add("images/car_100.png");
		assetNames.add("images/car_101.png");
		assetNames.add("images/car_102.png");
		assetNames.add("images/car_103.png");
		assetNames.add("images/car_104.png");
		assetNames.add("images/car_105.png");
		assetNames.add("images/car_106.png");
		assetNames.add("images/car_107.png");
		assetNames.add("images/car_108.png");
		assetNames.add("images/car_109.png");
		assetNames.add("images/missile.png");
		assetNames.add("images/map-0123.png");
	    assetNames.add("images/map-01.png");
	    assetNames.add("images/map-013.png");
	    assetNames.add("images/map-03.png");
	    assetNames.add("images/map-12.png");
	    assetNames.add("images/map-123.png");
	    assetNames.add("images/map-13.png");
	    assetNames.add("images/map-23.png");
	    assetNames.add("images/crashed.png");
		
		ModeLoading loadingScreen = new ModeLoading(assetNames);
		loadingScreen.setCallback(new AbstractCompletionCallback(){
			@Override
			public void onComplete() {
				modeMgr.clearModeStack();
			    ModeGameplay gameplayMode = new ModeGameplay();
				modeMgr.pushGameMode(gameplayMode);
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
			}});
		modeMgr.pushGameMode(loadingScreen);

	    // add a listener for pointer (mouse, touch) input
	    pointer().setListener(this);
	    
	    keyboard().setListener(this);
	    
	    mouse().setListener(this);
	}

	@Override
	public void update(float delta) {
		  float deltaSeconds = delta/1000.0f;
		  modeMgr.onUpdate(deltaSeconds);
	}

	@Override
	public void paint(float alpha) {
		  modeMgr.paint(alpha);
	}

	@Override
	public int updateRate() {
		  // TODO(davelecompte): make mode-dependent
	    //return 25;
		  return 0;
	}

	@Override
	public void onMouseDown(float x, float y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseUp(float x, float y, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseMove(float x, float y) {
		modeMgr.onMouseMove(x, y);
	}

	@Override
	public void onMouseWheelScroll(float velocity) {
		modeMgr.onMouseWheelScroll(velocity);
	}

	@Override
	public void onKeyDown(int keyCode) {
		modeMgr.onKeyDown(keyCode);
	}

	@Override
	public void onKeyUp(int keyCode) {
		modeMgr.onKeyUp(keyCode);
	}

	@Override
	public void onPointerStart(float x, float y) {
		  modeMgr.onPointerStart(x, y);
	}

	@Override
	public void onPointerEnd(float x, float y) {
		  modeMgr.onPointerEnd(x, y);
	}

	@Override
	public void onPointerDrag(float x, float y) {
		// TODO Auto-generated method stub
		
	}

}
