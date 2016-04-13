package com.victorvieux.hnefatafl.maps;

import com.badlogic.gdx.graphics.Texture;


public class Brandubh extends BaseMap7 {

	public Brandubh() {
		super("@..*..@"
			+ "...*..."
			+ "...%..."
			+ "**%#%**"
			+ "...%..."
			+ "...*..."
			+ "@..*..@");
	}
	
	public Texture getThumbnail() {
		return new Texture("data/maps/Brandubh.png");
	}
}
