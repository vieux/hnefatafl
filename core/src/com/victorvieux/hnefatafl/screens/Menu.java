package com.victorvieux.hnefatafl.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.victorvieux.hnefatafl.Cache;
import com.victorvieux.hnefatafl.HnefataflGdxGame;
import com.victorvieux.hnefatafl.TouchDetector;
import com.victorvieux.hnefatafl.Touchable;
import com.victorvieux.hnefatafl.tween.ActorAccessor;

public class Menu extends TitleScreen implements Screen, Touchable {

	private Table table;
	
	public Menu(HnefataflGdxGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		
		table = new Table();
		table.setFillParent(true);
		
		TextButtonStyle bStyle = new TextButtonStyle();
		bStyle.font = Cache.getFont(48);
		bStyle.fontColor=Color.LIGHT_GRAY;
		final TextButton buttonPlay = new TextButton("PLAY", bStyle);
		
		buttonPlay.pad(20);
		table.add(buttonPlay);
		
		table.row();
		final TextButton buttonCredits = new TextButton("CREDITS", bStyle);
	
		buttonCredits.pad(20);
		table.add(buttonCredits);
		table.row();
		final TextButton buttonExit = new TextButton("EXIT", bStyle);
		
		buttonExit.pad(20);
		table.add(buttonExit);
		table.pad(150, 0, 0, 0);
		getStage().addActor(table);

		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				onExit(new LevelSelect(getGame()), buttonPlay, buttonCredits, buttonExit);
			}
		});
		buttonCredits.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				onExit(new Credits(getGame()), buttonPlay, buttonCredits, buttonExit);
			}
		});
		buttonExit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0).start(getTweenManager());
		Tween.to(buttonPlay, ActorAccessor.ALPHA, 1).target(1).start(getTweenManager());
		Tween.set(buttonCredits, ActorAccessor.ALPHA).target(0).start(getTweenManager());
		Tween.to(buttonCredits, ActorAccessor.ALPHA, 1).target(1).delay(0.1f).start(getTweenManager());
		Tween.set(buttonExit, ActorAccessor.ALPHA).target(0).start(getTweenManager());
		Tween.to(buttonExit, ActorAccessor.ALPHA, 1).target(1).delay(0.2f).start(getTweenManager());
		
		Gdx.input.setInputProcessor(new InputMultiplexer(getStage(), new TouchDetector(this)));
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
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		table.invalidateHierarchy();
	}

	@Override
	public void touch(int x, int y) {		
	}

	@Override
	public void escape() {
		Gdx.app.exit();		
	}
	
	private void onExit(final Screen screen, TextButton buttonPlay, TextButton buttonCredits, TextButton buttonExit) {

		Tween.set(buttonPlay, ActorAccessor.ALPHA).target(buttonPlay.getColor().a).start(getTweenManager());
		Tween.to(buttonPlay, ActorAccessor.ALPHA, 0.5f).target(0).delay(0.2f).start(getTweenManager());
		Tween.set(buttonCredits, ActorAccessor.ALPHA).target(buttonCredits.getColor().a).start(getTweenManager());
		Tween.to(buttonCredits, ActorAccessor.ALPHA, 0.5f).target(0).delay(0.1f).start(getTweenManager());
		Tween.set(buttonExit, ActorAccessor.ALPHA).target(buttonExit.getColor().a).start(getTweenManager());
		Tween.to(buttonExit, ActorAccessor.ALPHA, 0.5f).target(0).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				getGame().setScreen(screen);
			}
		}).start(getTweenManager());
	}

}
