package com.bigdicegames.carswithguns.java;

import com.bigdicegames.carswithguns.core.CarsWithGuns;

import forplay.core.ForPlay;
import forplay.java.JavaAssetManager;
import forplay.java.JavaPlatform;

public class CarsWithGunsEntryJava {
	public static void main(String[] args) {
		JavaAssetManager assets = JavaPlatform.register().assetManager();
		assets.setPathPrefix("src/com/bigdicegames/carswithguns/resources");
		ForPlay.run(new CarsWithGuns());
	}
}
