package com.bigdicegames.carswithguns.core.modes.citydrive;

import static forplay.core.ForPlay.graphics;
import forplay.core.GroupLayer;

/**
 * 
 * @author DaveLeCompte
 * 
 *         Holds the MapTile objects (which, in turn, refer to AbstractTiles).
 *         Can render and do proximity lookups.
 * 
 */

public class MapMgr {
	private AbstractTile tile_01;
	private AbstractTile tile_0123;
	private AbstractTile tile_013;
	private AbstractTile tile_03;
	private AbstractTile tile_12;
	private AbstractTile tile_123;
	private AbstractTile tile_13;
	private AbstractTile tile_23;
	private GroupLayer mapLayer;
	private MapTile[][] tileArray = new MapTile[4][];

	static private MapMgr singleton;

	public MapMgr() {
		tile_01 = new AbstractTile(true, true, false, false);
		tile_0123 = new AbstractTile(true, true, true, true);
		tile_013 = new AbstractTile(true, true, false, true);
		tile_03 = new AbstractTile(true, false, false, true);
		tile_12 = new AbstractTile(false, true, true, false);
		tile_123 = new AbstractTile(false, true, true, true);
		tile_13 = new AbstractTile(false, true, false, true);
		tile_23 = new AbstractTile(false, false, true, true);

		mapLayer = graphics().createGroupLayer();

		float sc = 800.0f;

		tileArray[0] = new MapTile[4];
		tileArray[1] = new MapTile[4];
		tileArray[2] = new MapTile[4];
		tileArray[3] = new MapTile[4];

		tileArray[0][0] = new MapTile(mapLayer, 0, 0 * sc, tile_03);
		tileArray[1][0] = new MapTile(mapLayer, 1 * sc, 0 * sc, tile_23);
		tileArray[2][0] = new MapTile(mapLayer, 2 * sc, 0 * sc, tile_03);
		tileArray[3][0] = new MapTile(mapLayer, 3 * sc, 0 * sc, tile_23);

		tileArray[0][1] = new MapTile(mapLayer, 0, 1 * sc, tile_013);
		tileArray[1][1] = new MapTile(mapLayer, 1 * sc, 1 * sc, tile_0123);
		tileArray[2][1] = new MapTile(mapLayer, 2 * sc, 1 * sc, tile_123);
		tileArray[3][1] = new MapTile(mapLayer, 3 * sc, 1 * sc, tile_13);

		tileArray[0][2] = new MapTile(mapLayer, 0, 2 * sc, tile_13);
		tileArray[1][2] = new MapTile(mapLayer, 1 * sc, 2 * sc, tile_013);
		tileArray[2][2] = new MapTile(mapLayer, 2 * sc, 2 * sc, tile_0123);
		tileArray[3][2] = new MapTile(mapLayer, 3 * sc, 2 * sc, tile_123);

		tileArray[0][3] = new MapTile(mapLayer, 0, 3 * sc, tile_01);
		tileArray[1][3] = new MapTile(mapLayer, 1 * sc, 3 * sc, tile_12);
		tileArray[2][3] = new MapTile(mapLayer, 2 * sc, 3 * sc, tile_01);
		tileArray[3][3] = new MapTile(mapLayer, 3 * sc, 3 * sc, tile_12);

		singleton = this;
	}

	public GroupLayer getLayer() {
		return mapLayer;
	}

	public static MapMgr get() {
		return singleton;
	}

	public MapTile getTile(float x, float y) {
		int tx = (int) (x / 800);
		int ty = (int) (y / 800);

		return tileArray[tx][ty];
	}
}
