package com.victorvieux.hnefatafl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Cache {

	/*
	 * Font
	 */
	
	private static Map<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();
	public static BitmapFont getFont(int size ) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.app.getFiles().internal("data/fonts/jorvik.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		
		
		BitmapFont font;
		if (!fonts.containsKey(size)){
			font = generator.generateFont(parameter);
			fonts.put(size, font);
		} else {
			font = fonts.get(size);
		}
		return font;
	}
	
	/*
	 * Textures
	 */
	private static TextureRegion[][] attackerTexture;
	private static TextureRegion[][] defenderTexture;
	private static TextureRegion[][] kingTexture;
	private static TextureRegion[][] boardTexture;	
	
	public static TextureRegion getAttackerTextures() {
		if (attackerTexture == null)
			attackerTexture = TextureRegion.split(new Texture("data/sprites/attacker2.png"), 64, 64);	
		return attackerTexture[10][Math.random() > 0.5f ? 8 : 9];
	}
	public static TextureRegion getDefenderTextures() {
		if (defenderTexture == null)
			defenderTexture = TextureRegion.split(new Texture("data/sprites/defender.png"), 64, 64);	
		return defenderTexture[10][8];
	}
	public static TextureRegion getKingTextures() {
		if (kingTexture == null)
			kingTexture = TextureRegion.split(new Texture("data/sprites/king.png"), 64, 64);
		return kingTexture[10][2];
	}
	public static TextureRegion[][] getBoardTextures() {
		if (boardTexture == null)
			boardTexture = TextureRegion.split(new Texture("data/maps/board.png"), 96, 96);
		return boardTexture;
	}
	
	/*
	 * Background
	 */
	private static Sprite backgroundSprite;
	private static Texture backgroundTexture;
	
	public static Sprite getBackground() {
		backgroundTexture = new Texture("data/maps/background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return backgroundSprite;
	}
	
	public static void dispose() {
		Iterator<Integer> it = fonts.keySet().iterator();
		while (it.hasNext()) {
			fonts.get(it.next()).dispose();
		}
		fonts.clear();
		
		if (attackerTexture != null) {
			attackerTexture[0][0].getTexture().dispose();
			attackerTexture = null;
		}
		if (defenderTexture != null) {
			defenderTexture[0][0].getTexture().dispose();
			defenderTexture = null;
		}
		if (kingTexture != null) {
			kingTexture[0][0].getTexture().dispose();
			kingTexture = null;
		}
		
		if (backgroundTexture != null) {
			backgroundTexture.dispose();
		}
	}
}
