package com.victorvieux.hnefatafl.screens;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.victorvieux.hnefatafl.HnefataflGdxGame;
import com.victorvieux.hnefatafl.TouchDetector;
import com.victorvieux.hnefatafl.Touchable;
import com.victorvieux.hnefatafl.maps.ArdRi;
import com.victorvieux.hnefatafl.maps.BaseMap;
import com.victorvieux.hnefatafl.maps.Brandubh;
import com.victorvieux.hnefatafl.maps.Hnefatafl;
import com.victorvieux.hnefatafl.maps.Tablut;
import com.victorvieux.hnefatafl.maps.Tawlbwrdd;
import com.victorvieux.hnefatafl.tween.ActorAccessor;

public class LevelSelect extends TitleScreen implements Screen, Touchable {

	private final BaseMap[] maps = {
			new ArdRi(),
			new Tablut(),
			new Hnefatafl(),
			new Brandubh(),
			new Tawlbwrdd(),
	};
	
	public LevelSelect(HnefataflGdxGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		super.show();
		
		Table table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		for (int i = 0; i < maps.length; ++i) {
			if (i == 3)
				table.row();
			
			final BaseMap map = maps[i];
			Image img = new Image(map.getThumbnail());
			img.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					getGame().setPlayScreen(map);
				}
			});
			if (i == 4) 
				table.add().pad(img.getHeight()/2);
			table.add(img).pad(10);
			Tween.set(img, ActorAccessor.ALPHA).target(0).start(getTweenManager());
			Tween.to(img, ActorAccessor.ALPHA, 1).target(1).delay(i*0.1f).start(getTweenManager());
		}
		
		table.pad(200, 0, 0, 0);
		getStage().addActor(table);

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
	}

	@Override
	public void escape() {
		getGame().setMenuScreen();
	}

}
