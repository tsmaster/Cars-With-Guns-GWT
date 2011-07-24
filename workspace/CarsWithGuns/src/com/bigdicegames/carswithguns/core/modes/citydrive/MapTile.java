package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.graphics;
import forplay.core.GroupLayer;
import forplay.core.ImageLayer;

/**
 * See also AbstractTile, which holds the image and (abstract) adjacency
 * information. This holds information about the specific placement of a tile.
 * 
 * @author Dave LeCompte
 * 
 */
public class MapTile {

	private float x;
	private float y;
	public AbstractTile tile;
	private ImageLayer tilelayer;

	public MapTile(GroupLayer parentLayer, float x, float y, AbstractTile tile) {
		this.x = x;
		this.y = y;
		this.tile = tile;

		tilelayer = graphics().createImageLayer(tile.getImage());
		tilelayer.setTranslation(x, y);
		// tilelayer.setScale(0.25f, 0.25f);
		parentLayer.add(tilelayer);
	}

	public float getCenterX() {
		return x + 400.0f;
	}

	public float getCenterY() {
		return y + 400.0f;
	}
}
