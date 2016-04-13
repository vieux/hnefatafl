package com.victorvieux.hnefatafl.maps;

import com.victorvieux.hnefatafl.HnefataflGdxGame;

public abstract class BaseMap7 extends BaseMap {

	public BaseMap7(String map) {
		super(map);
	}
	
	@Override
	public int getBoardSize() {
		return 7;
	}

	@Override
	public int getOffset() {
		return HnefataflGdxGame.isAndroid ? 1 : 4;
	}

	@Override
	public int getXCells() {
		return HnefataflGdxGame.isAndroid ? 14 : 20;
	}

	@Override
	public int getYCells() {
		return HnefataflGdxGame.isAndroid ? 9 : 15;
	}

	@Override
	public String getTMXPath() {
		return  HnefataflGdxGame.isAndroid ? "data/maps/map7_small.tmx" : "data/maps/map7.tmx";
	}
}
