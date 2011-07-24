package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.graphics;
import static forplay.core.ForPlay.log;
import forplay.core.Image;
import forplay.core.ImageLayer;
import forplay.core.ResourceCallback;

public class Bullet implements Collidable, Weapon {
	private Image image;
	private ImageLayer layer;
	private float x;
	private float y;
	private float angle;
	private float speed;
	private boolean alive;
	private float elapsedLifetime;
	private float maxLifetime = 3.0f;

	public Bullet(float startX, float startY, float angle) {
		image = assetManager().getImage("images/bullet.png");
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
		this.angle = angle;
		this.speed = 600.0f;
		this.alive = true;
	}

	public ImageLayer getLayer() {
		return layer;
	}

	public void update(float delta) {
		x += Math.cos(angle) * speed * delta;
		y += Math.sin(angle) * speed * delta;

		layer.setTranslation(x, y);

		elapsedLifetime += delta;
		if (elapsedLifetime > maxLifetime) {
			alive = false;
		}
	}

	public boolean isAlive() {
		return alive;
	}

	@Override
	public float getCollisionRadius() {
		return 20.0f;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public int getCollidableType() {
		return CollisionMgr.CollisionType.COLLISION_TYPE_WEAPON.ordinal();
	}

	@Override
	public void onCollide(Collidable other) {
		if (other.getCollidableType() == CollisionMgr.CollisionType.COLLISION_TYPE_CAR
				.ordinal()) {
			// kapow!
			alive = false;
		}
	}
}
