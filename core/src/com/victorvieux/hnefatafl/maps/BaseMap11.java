package com.victorvieux.hnefatafl.maps;

import com.victorvieux.hnefatafl.HnefataflGdxGame;

public abstract class BaseMap11 extends BaseMap {

	public BaseMap11(String map) {
		super(map);
	}
	
	@Override
	public int getBoardSize() {
		return 11;
	}

	@Override
	public int getOffset() {
		return HnefataflGdxGame.isAndroid ? 1 :2;
	}

	@Override
	public int getXCells() {
		return 20;
	}

	@Override
	public int getYCells() {
		return HnefataflGdxGame.isAndroid ? 13 : 15;
	}

	@Override
	public String getTMXPath() {
		return HnefataflGdxGame.isAndroid ? "data/maps/map11_small.tmx" : "data/maps/map11.tmx";
	}
}
