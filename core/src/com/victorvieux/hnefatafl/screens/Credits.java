package com.victorvieux.hnefatafl.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.victorvieux.hnefatafl.Cache;
import com.victorvieux.hnefatafl.HnefataflGdxGame;
import com.victorvieux.hnefatafl.TouchDetector;
import com.victorvieux.hnefatafl.Touchable;
import com.victorvieux.hnefatafl.tween.ActorAccessor;

public class Credits extends TitleScreen implements Screen, Touchable {

	public Credits(HnefataflGdxGame game) {
		super(game);
	}
	
	private Label credits;
	
	@Override
	public void show() {
		super.show();
		
		credits = new Label("Developed by: Victor Vieux\n\nGraphics by: Sharm, Curt, Dalonedrau\n\nMusic by: Skrjablin\n\nFont by: Andrea Hfeld" ,new LabelStyle(Cache.getFont(24), Color.LIGHT_GRAY));
		credits.setAlignment(Align.center);
		credits.setPosition(Gdx.graphics.getWidth()/2 - credits.getWidth()/2, Gdx.graphics.getHeight()/2 - 100); 
		getStage().addActor(credits);
	
		Tween.set(credits, ActorAccessor.ALPHA).target(0).start(getTweenManager());
		Tween.to(credits, ActorAccessor.ALPHA, 1).target(1).start(getTweenManager());

			
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
	public void touch(int x, int y) {	
		getGame().setMenuScreen();
	}

	@Override
	public void escape() {
		Tween.set(credits, ActorAccessor.ALPHA).target(credits.getColor().a).start(getTweenManager());
		Tween.to(credits, ActorAccessor.ALPHA, 0.5f).target(0).delay(0.2f).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				getGame().setMenuScreen();
			}
		}).start(getTweenManager());
	}
}
