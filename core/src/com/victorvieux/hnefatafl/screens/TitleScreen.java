package com.victorvieux.hnefatafl.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.victorvieux.hnefatafl.Cache;
import com.victorvieux.hnefatafl.HnefataflGdxGame;
import com.victorvieux.hnefatafl.tween.ActorAccessor;

public class TitleScreen extends BaseScreen implements Screen{

	private Label title;
	private TweenManager tweenManager;
    public static Sprite backgroundSprite;
	private SpriteBatch batch;
    
	public TitleScreen(HnefataflGdxGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		Cache.getBackground().draw(batch);
		batch.end();
		tweenManager.update(delta);

		super.render(delta);
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		
		title = new Label("HNEFATAFL:\n\nThe Game of Vikings", new LabelStyle(Cache.getFont(48), Color.WHITE));
		title.setAlignment(Align.center);
		title.setPosition(Gdx.graphics.getWidth()/2 - title.getWidth()/2, 100 + Gdx.graphics.getHeight()/2); 
		
		getStage().addActor(title);
	
		Gdx.input.setInputProcessor(new InputMultiplexer(getStage()));
	}

	@Override
	public void hide() {
		dispose();		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		super.dispose();		
	}
	
	public TweenManager getTweenManager() {
		return tweenManager;
	}
}
