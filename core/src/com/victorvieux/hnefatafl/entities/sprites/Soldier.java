package com.victorvieux.hnefatafl.entities.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.victorvieux.hnefatafl.Teams;
import com.victorvieux.hnefatafl.entities.Board;
import com.victorvieux.hnefatafl.screens.Play;

public class Soldier extends BaseSprite {
	private float xOffset;
	private float yOffset;
	private int  team;
	private boolean king;
	    
	private float x, y;
	
	public Soldier(TextureRegion region, float size, int offset, int team) {
		super(region, size, offset);
		this.king = (team == Teams.KING);
		this.team = (team == Teams.KING ? Teams.DEFENDER : team);
	}
	
	public int getTeam() {
		return this.team;
	}
	
	public boolean isKing() {
		return this.king;
	}
	
	public void resize() {
		super.resize();
		this.xOffset = 5 * Play.scale;
		this.yOffset = Board.cellSize * Play.scale / 3f;
	}
	
	public void resize(float scale) {
		super.resize(scale);
		this.xOffset = 5 * scale;
		this.yOffset = Board.cellSize * scale / 3f;
	}
	
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
    	super.setPosition(x, y);
    	setX(getX() - xOffset);
    	setY(getY() + yOffset);
	}
	
	public int X() {
		return (int) this.x;
	}
	public int Y() {
		return (int) this.y;
	}
}
