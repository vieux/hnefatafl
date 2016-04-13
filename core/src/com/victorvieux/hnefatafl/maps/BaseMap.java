package com.victorvieux.hnefatafl.maps;

import com.badlogic.gdx.graphics.Texture;


public abstract class BaseMap {
	public enum SQUARE{
		CORNER,
		EMPTY,
		ATTACKER,
		DEFENDER,
		THRONE
	}
	
	private String map;
	
	public abstract int getBoardSize();
	public abstract int getOffset();
	public abstract int getXCells();
	public abstract int getYCells();
	public abstract String getTMXPath();
	public abstract Texture getThumbnail();
	
	
	protected BaseMap(String map) {
		this.map = map;
	}
	
	public int length() {
		return map.length();
	}
	
	
	public SQUARE at(int i) {
		switch (map.charAt(i)) {
		case '*':
			return SQUARE.ATTACKER;
		case '%':
			return SQUARE.DEFENDER;
		case '#':
			return SQUARE.THRONE;
		case '@': 
			return SQUARE.CORNER;
		default:
			return SQUARE.EMPTY;
		}
	}
}
