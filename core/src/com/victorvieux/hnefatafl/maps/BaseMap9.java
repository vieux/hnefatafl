package com.victorvieux.hnefatafl.maps;

import com.victorvieux.hnefatafl.HnefataflGdxGame;

public abstract class BaseMap9 extends BaseMap {

	public BaseMap9(String map) {
		super(map);
	}
	
	@Override
	public int getBoardSize() {
		return 9;
	}

	@Override
	public int getOffset() {
		return HnefataflGdxGame.isAndroid ? 1 : 3;
	}

	@Override
	public int getXCells() {
		return HnefataflGdxGame.isAndroid ? 17 : 20;
	}

	@Override
	public int getYCells() {
		return HnefataflGdxGame.isAndroid ? 11 : 15;
	}

	@Override
	public String getTMXPath() {
		return  HnefataflGdxGame.isAndroid ? "data/maps/map9_small.tmx" : "data/maps/map9.tmx";
	}
}
