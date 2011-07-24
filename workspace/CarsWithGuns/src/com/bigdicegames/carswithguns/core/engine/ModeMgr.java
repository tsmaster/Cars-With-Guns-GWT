package com.bigdicegames.carswithguns.core.engine;

import java.util.ArrayList;


/**
 * ModeMgr
 * 
 * This class handles a stack of Modes, which gives us the ability to swap in modal dialog boxes,
 * loading screens, that sort of fun stuff.
 */

public class ModeMgr {
	static ModeMgr singleton;
	private ArrayList<GameMode> modeStack = new ArrayList<GameMode> ();
	
	public ModeMgr() {
		singleton = this;
	}
	
	public void pushGameMode(GameMode mode) {
		modeStack.add(mode);
		mode.onPushed();
	}
	
	public void popGameMode(GameMode mode) {
		if (mode == null) {
			mode = getTopMode();
		}
		mode.onPopped();
		modeStack.remove(mode);
	}
	
	public void clearModeStack() {
		for (GameMode m : modeStack) {
			m.onPopped();
		}
		modeStack.clear();
	}
	
	private GameMode getTopMode() {
		if (modeStack.size() == 0) {
			return null;
		}
		return modeStack.get(modeStack.size() -1);
	}
	
	public void onUpdate(float deltaSeconds) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.onUpdate(deltaSeconds);
		}
	}

	public static ModeMgr get() {
		return singleton;
	}

	public boolean active() {
		return modeStack.size() > 0;
	}

	public void onPointerEnd(float x, float y) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.onPointerEnd(x, y);
		}
	}

	public void onPointerStart(float x, float y) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.onPointerStart(x, y);
		}
	}

	public void onKeyDown(int keyCode) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.onKeyDown(keyCode);
		}
	}
	
	public void onKeyUp(int keyCode) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.onKeyUp(keyCode);
		}
	}

	public void onMouseMove(float x, float y) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.onMouseMove(x, y);
		}
	}

	public void onMouseWheelScroll(float velocity) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.onMouseWheelScroll(velocity);
		}
	}

	public void paint(float alpha) {
		GameMode mode = getTopMode();
		if (mode != null) {
			mode.paint(alpha);
		}
	}
}
