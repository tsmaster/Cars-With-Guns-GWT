package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.graphics;
import static forplay.core.ForPlay.log;

import java.util.ArrayList;

import com.bigdicegames.carswithguns.core.modes.citydrive.WeaponDisplay.WeaponType;

import forplay.core.Canvas;
import forplay.core.CanvasImage;
import forplay.core.ForPlay;
import forplay.core.GroupLayer;
import forplay.core.Image;
import forplay.core.ImageLayer;
import forplay.core.ResourceCallback;
import forplay.core.Sound;
import forplay.core.SurfaceLayer;

public class Car implements Collidable {
	static final boolean USE_COMPOSITE = false;
	public static ArrayList<String> IMAGES = new ArrayList<String>();
	private ImageLayer imageLayer;
	private GroupLayer groupLayer;
	private float angle;
	private float x, y;
	private GroupLayer parentLayer;
	static public boolean showMessage;
	private float speed;
	private DriverBrain brain;
	public float collisionRadius;
	private boolean alive;
	private CanvasImage imageCanvas;
	private SurfaceLayer surface;
	private WeaponType weapon;
	private Sound explodeSound;

	public Car(final GroupLayer carLayer, final float x, final float y,
			final int imageIndex) {
		getImages();
		Image image = assetManager().getImage(IMAGES.get(imageIndex));
		imageLayer = graphics().createImageLayer(image);
		groupLayer = graphics().createGroupLayer();

		if (!USE_COMPOSITE) {
			groupLayer.add(imageLayer);
		}

		this.x = x;
		this.y = y;
		parentLayer = carLayer;
		float minSpeed = 60;
		float maxSpeed = 120;
		float speedRange = maxSpeed - minSpeed;
		this.speed = ForPlay.random() * speedRange + minSpeed;
		setHeading(ForPlay.random() * 6.28f);
		alive = true;

		explodeSound = assetManager().getSound("sounds/boom");

		// see CuteWorld for more canvasy stuff

		if (USE_COMPOSITE) {
			// will draw into this at runtime
			surface = graphics().createSurfaceLayer(100, 50);
			groupLayer.add(surface);
		}

		// Add a callback for when the image loads.
		// This is necessary because we can't use the width/height (to center
		// the
		// image) until after the image has been loaded
		image.addCallback(new ResourceCallback<Image>() {
			@Override
			public void done(Image image) {
				int w = image.width();
				int h = image.height();

				groupLayer.setOrigin(w / 2f, h / 2f);
				groupLayer.setTranslation(x, y);
				parentLayer.add(groupLayer);

				if (USE_COMPOSITE) {
					imageCanvas = graphics().createImage(w, h);
					imageCanvas.canvas().drawImage(image, 0, 0);
					// imageCanvas.canvas().setStrokeColor(0xff00ff00);
					// imageCanvas.canvas().setStrokeWidth(2);
					// imageCanvas.canvas().strokeRect(1, 1, 90, 45);
					imageCanvas.canvas().setCompositeOperation(
							Canvas.Composite.SRC_ATOP);
					int color = getRandomColor();
					imageCanvas.canvas().setFillColor(color);
					imageCanvas.canvas().fillRect(0, 0, w, h);
					imageCanvas.canvas().setFillColor(0xff000000);
					String carName = "Car " + imageIndex;
					imageCanvas.canvas().drawText(carName, 10, h / 2);
				}

				collisionRadius = image.width() / 2.0f;
			}

			@Override
			public void error(Throwable err) {
				log().error("Error loading image!", err);
			}
		});
	}

	protected int getRandomColor() {
		int oldColors[] = { 0x80ff0000, 0x8000ff00, 0x800000ff, 0x80ffff00,
				0x8000ffff, 0x80ff8000, 0x80ff0080, 0x8000ff80, 0x8080ff00,
				0x800080ff, 0x808000ff };
		int colors[] = { 0x80ff0000, 0x8000ff00, 0x800000ff, };

		int index = (int) (ForPlay.random() * colors.length);
		return colors[index];
	}

	public void remove() {
		parentLayer.remove(groupLayer);
	}

	public void setBrain(DriverBrain brain) {
		this.brain = brain;
	}

	public void update(float delta) {
		if (brain != null) {
			brain.update(delta);
		}
		groupLayer.setRotation(angle);

		x += Math.cos(angle) * speed * delta;
		y += Math.sin(angle) * speed * delta;

		groupLayer.setTranslation(x, y);
	}

	public static ArrayList<String> getImages() {
		if (IMAGES.size() == 0) {
			/*
			 * IMAGES.add("images/car_01.png"); IMAGES.add("images/car_03.png");
			 * IMAGES.add("images/car_04.png"); IMAGES.add("images/car_05.png");
			 * IMAGES.add("images/car_06.png"); IMAGES.add("images/car_07.png");
			 * IMAGES.add("images/car_08.png"); IMAGES.add("images/car_09.png");
			 * IMAGES.add("images/car_10.png"); IMAGES.add("images/car_11.png");
			 * IMAGES.add("images/car_12.png"); IMAGES.add("images/car_13.png");
			 * IMAGES.add("images/car_14.png"); IMAGES.add("images/car_15.png");
			 * IMAGES.add("images/car_16.png");
			 * IMAGES.add("images/motorcycle_01.png");
			 * IMAGES.add("images/motorcycle_02.png");
			 * IMAGES.add("images/motorcycle_03.png");
			 * IMAGES.add("images/motorcycle_04.png");
			 * IMAGES.add("images/motorcycle_05.png");
			 * IMAGES.add("images/motorcycle_06.png");
			 * IMAGES.add("images/motorcycle_07.png");
			 * IMAGES.add("images/van_01.png"); IMAGES.add("images/van_02.png");
			 * IMAGES.add("images/van_03.png"); IMAGES.add("images/van_04.png");
			 */

			IMAGES.add("images/car_100.png");
			IMAGES.add("images/car_101.png");
			IMAGES.add("images/car_102.png");
			IMAGES.add("images/car_103.png");
			IMAGES.add("images/car_104.png");
			IMAGES.add("images/car_105.png");
			IMAGES.add("images/car_106.png");
			IMAGES.add("images/car_107.png");
			IMAGES.add("images/car_108.png");
			IMAGES.add("images/car_109.png");
		}
		return IMAGES;
	}

	public boolean inbounds(float i, float j) {
		if (x < i || x > j) {
			return false;
		}
		if (y < i || y > j) {
			return false;
		}
		return true;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void onKeyDown(int keyCode, float mouseWX, float mouseWY) {
		if (brain == null) {
			return;
		}

		brain.keyDown(keyCode, mouseWX, mouseWY);
	}

	public void onKeyUp(int keyCode, float mouseWX, float mouseWY) {
		if (brain == null) {
			return;
		}

		brain.keyUp(keyCode, mouseWX, mouseWY);
	}

	public void onPointerDown(float mouseWX, float mouseWY) {
		if (brain == null) {
			return;
		}
		brain.pointerDown(mouseWX, mouseWY);
	}

	public float getHeading() {
		return angle;
	}

	public void setHeading(float heading) {
		angle = heading;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public float getCollisionRadius() {
		return collisionRadius;
	}

	@Override
	public int getCollidableType() {
		return CollisionMgr.CollisionType.COLLISION_TYPE_CAR.ordinal();
	}

	@Override
	public void onCollide(Collidable other) {
		if (CollisionMgr.CollisionType.COLLISION_TYPE_CAR.ordinal() == other
				.getCollidableType()) {
			// ouch!
			// explode(2);

			// TODO: steer away
		} else if (CollisionMgr.CollisionType.COLLISION_TYPE_WEAPON.ordinal() == other
				.getCollidableType()) {
			if (this == CarManager.get().getPlayerCar()) {
				// TODO: nicer way of avoiding shooting yourself
			} else {
				alive = false;

				explode(60);
			}
		}
	}

	private void explode(int numParticles) {
		ParticleMgr pm = ParticleMgr.get();

		for (int i = 0; i < numParticles; ++i) {
			float px = x + ForPlay.random() * collisionRadius * 2.0f
					- ForPlay.random() * collisionRadius * 2.0f;
			float py = y + ForPlay.random() * collisionRadius * 2.0f
					- ForPlay.random() * collisionRadius * 2.0f;

			pm.spawnParticle(px, py);
		}
		explodeSound.play();
	}

	public boolean isAlive() {
		return alive;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void paint(float alpha) {
		if (USE_COMPOSITE) {
			surface.surface().clear();
			surface.surface().drawImage(imageCanvas, 0, 0);
		}
	}

	public void setWeapon(WeaponType weapon) {
		this.weapon = weapon;
	}

	public WeaponType getWeapon() {
		return this.weapon;
	}

	public void reset() {
		if (brain != null) {
			brain.reset();
		}
	}
}
