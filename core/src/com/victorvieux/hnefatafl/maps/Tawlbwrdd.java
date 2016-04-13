package com.victorvieux.hnefatafl.maps;

import com.badlogic.gdx.graphics.Texture;


public class Tawlbwrdd extends BaseMap11 {

	public Tawlbwrdd() {
		super("@...***...@"
			+ "....*.*...."
			+ ".....*....."
			+ ".....%....."
			+ "**..%%%..**"
			+ "*.*%%#%%*.*"
			+ "**..%%%..**"
			+ ".....%....."
			+ ".....*....."
			+ "....*.*...."
			+ "@...***...@");
	}
	
	public Texture getThumbnail() {
		return new Texture("data/maps/Tawlbwrdd.png");
	}
}
