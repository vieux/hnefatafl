package com.victorvieux.hnefatafl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.victorvieux.hnefatafl.Cache;
import com.victorvieux.hnefatafl.HnefataflGdxGame;

public abstract class BaseScreen {
	private CheckBox sound;
	private Stage stage;
	private final HnefataflGdxGame game;

	public BaseScreen(final HnefataflGdxGame game) {
		this.game = game;
		this.stage = new Stage(new ScreenViewport());
		
		CheckBoxStyle style =  new CheckBoxStyle();
		style.font = new BitmapFont();
		style.checkboxOff = new SpriteDrawable(new Sprite(new Texture(Gdx.app.getFiles().internal("data/ui/music.png"))));
		style.checkboxOff.setMinHeight(14*3);
		style.checkboxOff.setMinWidth(15*3);
		style.checkboxOn = new SpriteDrawable(new Sprite(new Texture(Gdx.app.getFiles().internal("data/ui/no_music.png"))));
		style.checkboxOn.setMinHeight(14*3);
		style.checkboxOn.setMinWidth(15*3);
		sound =  new CheckBox("", style);
		sound.setPosition(Gdx.graphics.getWidth() - 60, 16);
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.Music(sound.isChecked());
			}
		});
		stage.addActor(sound);
		TextButtonStyle bStyle = new TextButtonStyle();
		bStyle.font = Cache.getFont(12);
		bStyle.fontColor= Color.LIGHT_GRAY;
		final TextButton version = new TextButton("v" + HnefataflGdxGame.VERSION, bStyle);
		version.setPosition(2, 2);
		stage.addActor(version);
		Gdx.input.setInputProcessor(stage);

	}
	
	protected void resize(int width, int height) {
	    stage.getViewport().update(width, height, true);
		sound.setPosition(Gdx.graphics.getWidth() - 60, 16);
	}
	
	protected Stage getStage() {
		return stage;
	}
	
	protected HnefataflGdxGame getGame() {
		return game;
	}
	
	protected void render(float delta) {
		sound.setChecked(!game.isPlayingMusic());
		stage.act(delta);		
		stage.draw();		
	}
	
	protected void dispose() {
		stage.dispose();	
	}

}
