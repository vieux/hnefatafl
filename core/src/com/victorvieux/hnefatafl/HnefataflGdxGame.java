package com.victorvieux.hnefatafl;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.victorvieux.hnefatafl.maps.BaseMap;
import com.victorvieux.hnefatafl.screens.LevelSelect;
import com.victorvieux.hnefatafl.screens.Menu;
import com.victorvieux.hnefatafl.screens.Play;
import com.victorvieux.hnefatafl.screens.Splash;

public class HnefataflGdxGame extends Game {
	public static final String  TITLE = "Hnefatafl: the Game of Vikings";
	public static final String  VERSION = "0.5.0";
	public static boolean isAndroid;
	public static final boolean DEBUG = true;
	public static float scale = 1.5f;

	private Music mainMusic;
	private Preferences mainPreferences;
	
	@Override
	public void create() {	
		mainMusic = Gdx.audio.newMusic(Gdx.files.internal("data/musics/melodic_adventure_theme_and_rhythmic_variation_c64_style_0.ogg"));
		mainMusic.setLooping(true);
	
		mainPreferences = Gdx.app.getPreferences("com.victorvieux.hnefatafl");
		
		isAndroid = Gdx.app.getType() == ApplicationType.Android;
		if (DEBUG)
			setScreen(new LevelSelect(this));
		else {		
			if (mainPreferences.getBoolean("music", false)) {
				mainMusic.play();	
			}
			setScreen(new Splash(this));
		}
	}	

	@Override
	public void dispose() {
		super.dispose();
		Cache.dispose();
		mainMusic.stop();
		mainMusic.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public void setMenuScreen() {
		setScreen(new Menu(this));
	}

	public void setPlayScreen(BaseMap map) {
		setScreen(new Play(this, map));		
	}
	
	public boolean isPlayingMusic() {
		return mainMusic.isPlaying();
	}

	public void Music(boolean checked) {
		if (checked)	
			mainMusic.pause();
		else
			mainMusic.play();
		mainPreferences.putBoolean("music", !checked);
	}

	public void setLevelSelectScreen() {
		setScreen(new LevelSelect(this));		
		
	}
}
