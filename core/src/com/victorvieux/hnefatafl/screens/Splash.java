package com.victorvieux.hnefatafl.screens;


import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.victorvieux.hnefatafl.Cache;
import com.victorvieux.hnefatafl.HnefataflGdxGame;
import com.victorvieux.hnefatafl.Teams;
import com.victorvieux.hnefatafl.TouchDetector;
import com.victorvieux.hnefatafl.Touchable;
import com.victorvieux.hnefatafl.entities.Board;
import com.victorvieux.hnefatafl.entities.sprites.Soldier;
import com.victorvieux.hnefatafl.tween.ActorAccessor;
import com.victorvieux.hnefatafl.tween.SpriteAccessor;

public class Splash extends BaseScreen implements Screen, Touchable{

	private SpriteBatch batch;
	private Label title, pressStart;
	private Sprite[] attackers, defenders;
	private TweenManager tweenManager;
	
	TextureRegion[][] attackerTexture;
	public Splash(HnefataflGdxGame game) {
		super(game);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tweenManager.update(delta);
		
		batch.begin();
		Cache.getBackground().draw(batch);
		for (int i = 0; i < 20; ++i)
			attackers[i].draw(batch);
		for (int i = 0; i < 10; ++i)
			defenders[i].draw(batch);
		batch.end();
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		
		title = new Label("HNEFATAFL:\n\nThe Game of Vikings", new LabelStyle(Cache.getFont(48), Color.WHITE));
		title.setAlignment(Align.center);
		title.setPosition(Gdx.graphics.getWidth()/2 - title.getWidth()/2, Gdx.graphics.getHeight()/2); 
		getStage().addActor(title);
		
		pressStart = new Label("PRESS START", new LabelStyle(Cache.getFont(36), Color.LIGHT_GRAY));
		pressStart.setPosition(Gdx.graphics.getWidth()/2 - pressStart.getWidth()/2, Gdx.graphics.getHeight()/3); 
		getStage().addActor(pressStart);

	
		attackers = new Soldier[20];
		for (int i = 0; i < 20; ++i) {
			attackers[i] = new Soldier(Cache.getAttackerTextures(), 64f/Play.scale, 2, Teams.ATTACKER);
			int offset = (int) (Math.random() * 50) - 25;
			if (i %2 == 0) {
				attackers[i].setY(1.5f*Board.cellSize + i/2*Board.cellSize*2);
				System.out.println(1.5f*Board.cellSize + i/2*Board.cellSize*2);
				Tween.set(attackers[i], SpriteAccessor.X).target(-64).start(tweenManager);
				Tween.to(attackers[i], SpriteAccessor.X, 2).target(64 + offset).delay(0.2f*i).start(tweenManager);
			} else {
				attackers[i].setY(1.5f*Board.cellSize + (i-1)/2*Board.cellSize*2);
				Tween.set(attackers[i], SpriteAccessor.X).target(Gdx.graphics.getWidth()).start(tweenManager);
				Tween.to(attackers[i], SpriteAccessor.X, 2).target(Gdx.graphics.getWidth() - 128 - (64 + offset)).delay(0.2f*i).start(tweenManager);
			}
			Tween.set(attackers[i], SpriteAccessor.ALPHA).target(0).start(tweenManager);
			Tween.to(attackers[i], SpriteAccessor.ALPHA, 1).target(1).start(tweenManager);
		}
		
		defenders = new Soldier[10];
		for (int i = 0; i < 10; ++i) {
			defenders[i] = new Soldier(Cache.getDefenderTextures(), 64f/Play.scale, 2, Teams.DEFENDER);
			if (i %2 == 0) {
				defenders[i].setX(Gdx.graphics.getWidth()/2 - 5*Board.cellSize + i/2*Board.cellSize*2);
				Tween.set(defenders[i], SpriteAccessor.Y).target(-64).start(tweenManager);
				Tween.to(defenders[i], SpriteAccessor.Y, 2).target(112).delay(0.2f*i).start(tweenManager);
			} else {
				if (i == 5)
					defenders[i] = new Soldier(Cache.getKingTextures(), 64f/Play.scale, 2, Teams.ATTACKER);
				defenders[i].setX(Gdx.graphics.getWidth()/2 - 5*Board.cellSize + (i-1)/2*Board.cellSize*2);
				Tween.set(defenders[i], SpriteAccessor.Y).target(-64 - 64).start(tweenManager);
				Tween.to(defenders[i], SpriteAccessor.Y, 2).target(48).delay(0.2f*i).start(tweenManager);
			}
			Tween.set(defenders[i], SpriteAccessor.ALPHA).target(0).start(tweenManager);
			Tween.to(defenders[i], SpriteAccessor.ALPHA, 1).target(1).start(tweenManager);
		}
		
		Tween.set(title, ActorAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(title, ActorAccessor.ALPHA, 2).target(1).start(tweenManager);	
		
		Tween.set(pressStart, ActorAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(pressStart, ActorAccessor.ALPHA, 1).target(1).repeatYoyo(Tween.INFINITY, 0.5f).start(tweenManager);	
		
		Gdx.input.setInputProcessor(new InputMultiplexer(getStage(), new TouchDetector(this)));

	}

	@Override
	public void hide() {
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
		batch.dispose();
	}

	@Override
	public void touch(int x, int y) {
		Gdx.input.setInputProcessor(null);

		for (int i = 0; i < 20; ++i) {
			Tween.set(attackers[i], SpriteAccessor.ALPHA).target(attackers[i].getColor().a).start(tweenManager);
			Tween.to(attackers[i], SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
		}
		for (int i = 0; i < 10; ++i) {
			Tween.set(defenders[i], SpriteAccessor.ALPHA).target(defenders[i].getColor().a).start(tweenManager);
			Tween.to(defenders[i], SpriteAccessor.ALPHA, 2).target(0).start(tweenManager);
		}
		
		Tween.set(pressStart, ActorAccessor.ALPHA).target(pressStart.getColor().a).start(tweenManager);
		Tween.to(pressStart, ActorAccessor.ALPHA, 2).target(0).start(tweenManager);
		
		Tween.set(title, ActorAccessor.Y).target(title.getY()).start(tweenManager);
		Tween.to(title, ActorAccessor.Y, 2).target(title.getY() + 100).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				getGame().setMenuScreen();		
				
			}
		}).start(tweenManager);
	}

	@Override
	public void escape() {
		Gdx.app.exit();
	}

}
