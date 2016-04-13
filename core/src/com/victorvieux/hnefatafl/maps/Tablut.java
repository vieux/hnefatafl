package com.victorvieux.hnefatafl.maps;

import com.badlogic.gdx.graphics.Texture;


public class Tablut extends BaseMap9 {

	public Tablut() {
		super("@..***..@"
			+ "....*...."
			+ "....%...."
			+ "*...%...*"
			+ "**%%#%%**"
			+ "*...%...*"
			+ "....%...."
			+ "....*...."
			+ "@..***..@");
	}
	
	public Texture getThumbnail() {
		return new Texture("data/maps/Tablut.png");
	}
}
