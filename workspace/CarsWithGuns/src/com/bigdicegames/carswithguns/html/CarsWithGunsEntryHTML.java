package com.bigdicegames.carswithguns.html;

import com.bigdicegames.carswithguns.core.CarsWithGuns;

import forplay.core.ForPlay;
import forplay.html.HtmlAssetManager;
import forplay.html.HtmlGame;
import forplay.html.HtmlPlatform;

public class CarsWithGunsEntryHTML extends HtmlGame {
	@Override
	public void start() {
		HtmlAssetManager assets = HtmlPlatform.register().assetManager();
		assets.setPathPrefix("carswithguns/");
		ForPlay.run(new CarsWithGuns());
	}
}
