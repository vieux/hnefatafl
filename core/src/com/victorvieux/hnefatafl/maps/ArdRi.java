package com.victorvieux.hnefatafl.maps;

import com.badlogic.gdx.graphics.Texture;


public class ArdRi extends BaseMap7 {

	public ArdRi() {
		super("@.***.@"
			+ "...*..."
			+ "*.%%%.*"
			+ "**%#%**"
			+ "*.%%%.*"
			+ "...*..."
			+ "@.***.@");
	}
	
	public Texture getThumbnail() {
		return new Texture("data/maps/ArdRi.png");
	}
}
