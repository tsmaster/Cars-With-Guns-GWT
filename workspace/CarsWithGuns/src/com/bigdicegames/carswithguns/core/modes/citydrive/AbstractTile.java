package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.assetManager;
import forplay.core.Image;

/**
 * 
 * @author Dave LeCompte
 * 
 *         This holds the information that can be reused between tiles that seem
 *         similar - I have a road that goes to the West and to the South, using
 *         map_03.png
 * 
 */

public class AbstractTile {
	private Image tileImage;
	public boolean east;
	public boolean north;
	public boolean west;
	public boolean south;

	public AbstractTile(boolean east, boolean north, boolean west, boolean south) {
		// create and add background image layer
		tileImage = assetManager().getImage(
				getImageName(east, north, west, south));

		this.east = east;
		this.north = north;
		this.west = west;
		this.south = south;
	}

	public Image getImage() {
		return tileImage;
	}

	private String getImageName(boolean east, boolean north, boolean west,
			boolean south) {
		if (east && north && west && south) {
			return "images/map-0123.png";
		} else if (east && north && (!west) && (!south)) {
			return "images/map-01.png";
		} else if (east && north && (!west) && south) {
			return "images/map-013.png";
		} else if (east && (!north) && (!west) && south) {
			return "images/map-03.png";
		} else if ((!east) && north && west && (!south)) {
			return "images/map-12.png";
		} else if ((!east) && north && west && south) {
			return "images/map-123.png";
		} else if ((!east) && north && (!west) && south) {
			return "images/map-13.png";
		} else if ((!east) && (!north) && west && south) {
			return "images/map-23.png";
		} else
			return null;
	}

}
