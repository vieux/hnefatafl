package com.victorvieux.hnefatafl.maps;

import com.badlogic.gdx.graphics.Texture;


public class Hnefatafl extends BaseMap11 {

	public Hnefatafl() {
		super("@..*****..@"
			+ ".....*....."
			+ "..........."
			+ "*....%....*"
			+ "*...%%%...*"
			+ "**.%%#%%.**"
			+ "*...%%%...*"
			+ "*....%....*"
			+ "..........."
			+ ".....*....."
			+ "@..*****..@");
	}
	
	public Texture getThumbnail() {
		return new Texture("data/maps/Hnefatafl.png");
	}
}
