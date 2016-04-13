package com.victorvieux.hnefatafl.entities.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.victorvieux.hnefatafl.maps.BaseMap.SQUARE;

public class Case extends BaseSprite {
	private SQUARE type;
	
	public Case(TextureRegion region, float size, int offset, SQUARE type) {
		super(region, size, offset);
		this.type = type;
	}
	
	public SQUARE getType() {
		return this.type;
	}
}
