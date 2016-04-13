package com.victorvieux.hnefatafl.entities.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.victorvieux.hnefatafl.entities.Board;
import com.victorvieux.hnefatafl.screens.Play;

public class BaseSprite extends Sprite {	
	private float scaledSize;
	protected final float size;
	private final int offset;
	
	public BaseSprite(TextureRegion region, float size, int offset) {
		super(region);
		this.size = size;
		this.offset = offset;
		resize();
	}
	
	public void setPosition(float x, float y) {
    	super.setPosition((x + offset)  * Board.cellSize * Play.scale, (y + offset) * Board.cellSize * Play.scale);

	}
	
	public void resize() {
		this.scaledSize = size * Play.scale;
		setSize(scaledSize, scaledSize);
	}
	
	public void resize(float scale) {
		this.scaledSize = size * scale;
		setSize(scaledSize, scaledSize);
	}
	
	public void draw(SpriteBatch batch, float x, float y) {
		setPosition(x, y);
		super.draw(batch);
	}
}
