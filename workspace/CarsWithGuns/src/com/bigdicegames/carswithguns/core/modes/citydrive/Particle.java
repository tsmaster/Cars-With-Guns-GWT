package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.graphics;
import static forplay.core.ForPlay.log;
import forplay.core.ForPlay;
import forplay.core.Image;
import forplay.core.ImageLayer;
import forplay.core.Layer;
import forplay.core.ResourceCallback;

public class Particle {

	private Image image;
	private ImageLayer layer;
	private float x;
	private float y;
	private boolean alive;
	private float elapsedLifetime;
	private float maxLifetime;

	public Particle(float startX, float startY) {
		String particleNames[] = { "images/particle-00-100.png",
				"images/particle-00-50.png", "images/particle-55-100.png",
				"images/particle-55-50.png", "images/particle-bb-100.png",
				"images/particle-bb-50.png", "images/particle-ff-100.png",
				"images/particle-ff-50.png", };
		int index = (int) (ForPlay.random() * particleNames.length);
		image = assetManager().getImage(particleNames[index]);

		layer = graphics().createImageLayer(image);

		// Add a callback for when the image loads.
		// This is necessary because we can't use the width/height (to center
		// the
		// image) until after the image has been loaded
		image.addCallback(new ResourceCallback<Image>() {
			@Override
			public void done(Image image) {
				getLayer().setOrigin(image.width() / 2f, image.height() / 2f);
			}

			@Override
			public void error(Throwable err) {
				log().error("Error loading image!", err);
			}
		});

		layer.setTranslation(startX, startY);

		this.x = startX;
		this.y = startY;
		this.alive = true;
		this.elapsedLifetime = 0;
		this.maxLifetime = 1.5f;
	}

	public Layer getLayer() {
		return layer;
	}

	public void update(float delta) {
		elapsedLifetime += delta;
		if (elapsedLifetime > maxLifetime) {
			alive = false;
		}
	}

	public boolean isAlive() {
		return alive;
	}

}
