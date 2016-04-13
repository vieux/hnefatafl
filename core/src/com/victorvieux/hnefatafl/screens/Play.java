 package com.victorvieux.hnefatafl.screens;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.victorvieux.hnefatafl.Cache;
import com.victorvieux.hnefatafl.HnefataflGdxGame;
import com.victorvieux.hnefatafl.Teams;
import com.victorvieux.hnefatafl.TouchDetector;
import com.victorvieux.hnefatafl.Touchable;
import com.victorvieux.hnefatafl.entities.Board;
import com.victorvieux.hnefatafl.entities.sprites.Soldier;
import com.victorvieux.hnefatafl.maps.BaseMap;

public class Play extends BaseScreen implements Screen, Touchable{
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;	
    private BitmapFont debugFont;
    private Soldier attacker, defender;
	private final Board board; 
	private Window pause;
	public static float scale = 1.5f;
	private boolean paused;
	private boolean musicState;
	private TextButton buttonContinue;
	
	public Play(HnefataflGdxGame game, BaseMap map) {
		super(game);
		Teams.currentPlayer = Teams.ATTACKER;
		board = new Board(this, map);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(185f/255f, 134f/255f, 80f/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		renderer.setView(camera);
		if (!paused) {
			AnimatedTiledMapTile.updateAnimationBaseTime();
			renderer.render();
			Batch batch = renderer.getBatch();
			batch.begin();
			board.draw(batch);
			if (HnefataflGdxGame.DEBUG)
				debugFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20); 
			Cache.getFont(36).draw(batch, "Current:", Gdx.graphics.getWidth() - 220, Board.cellSize * (board.getMap().getYCells() - 1) * scale);
			if (Teams.currentPlayer == Teams.ATTACKER) {
				attacker.draw(batch);
			} else {
				defender.draw(batch);
			}
			batch.end();
		}		
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		scale = Math.min((float)width / (Board.cellSize * board.getMap().getXCells()), (float)height / (Board.cellSize * board.getMap().getYCells()));
		pause.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/1.5f);
		board.resize();
		renderer = new OrthogonalTiledMapRenderer(map, scale);
		camera.setToOrtho(false, width, height);
		if (HnefataflGdxGame.isAndroid)
			camera.position.x = Board.cellSize * board.getMap().getXCells() * scale/2 + (Gdx.graphics.getWidth() - Board.cellSize * board.getMap().getXCells() * scale) ;
		else
			camera.position.y = Board.cellSize * board.getMap().getYCells() * scale/2;
		camera.update();
		attacker.setX(Gdx.graphics.getWidth() - 80);
		attacker.setY(Board.cellSize * (board.getMap().getYCells() - 2) * scale);
		defender.setX(Gdx.graphics.getWidth() - 80);
		defender.setY(Board.cellSize * (board.getMap().getYCells() - 2) * scale);
		super.resize(width, height);
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load(board.getMap().getTMXPath());

		Map<String, Array<StaticTiledMapTile>> animations = new HashMap<String, Array<StaticTiledMapTile>>();
		Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("animated_terrain").iterator();
		while (tiles.hasNext()) {
			TiledMapTile tile = tiles.next();
			if (tile.getProperties().containsKey("anim")) {
				Array<StaticTiledMapTile> frames = animations.get(tile.getProperties().get("anim", String.class));
				if (frames == null) {
					frames = new Array<StaticTiledMapTile>();
				}
				frames.add((StaticTiledMapTile) tile);
				animations.put(tile.getProperties().get("anim", String.class), frames);
			}
			
		}
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("water");
		for (int x = 0; x < board.getMap().getXCells(); x++)
			for (int y = 0; y < board.getMap().getYCells(); y++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null && cell.getTile().getProperties().containsKey("anim")) {
					cell.setTile(new AnimatedTiledMapTile(1/2f, animations.get(cell.getTile().getProperties().get("anim", String.class))));
				}
			}
		
		renderer = new OrthogonalTiledMapRenderer(map, scale);
		camera = new OrthographicCamera();
		debugFont = new BitmapFont();
		attacker = new Soldier(Cache.getAttackerTextures(), 64f/scale, 2, Teams.ATTACKER);
		defender = new Soldier(Cache.getDefenderTextures(), 64f/scale, 2, Teams.DEFENDER);
		Gdx.input.setInputProcessor(new InputMultiplexer(getStage(), new TouchDetector(this, camera)));
		
		WindowStyle wStyle = new WindowStyle();
		wStyle.titleFont = Cache.getFont(48);
		pause = new Window("", wStyle);  
		pause.setMovable(false);
		pause.setVisible(paused);
		TextButtonStyle bStyle = new TextButtonStyle();
		bStyle.font = Cache.getFont(48);
		buttonContinue = new TextButton("continue", bStyle);
		buttonContinue.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				escape();
			}
		});
		TextButton buttonQuit = new TextButton("quit", bStyle);
		buttonQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				getGame().setMenuScreen();
			}
		});
		pause.add(buttonContinue).pad(20);
		pause.add(buttonQuit).pad(20);
		getStage().addActor(pause);
	}
	
	public void touch(int x, int y) {
		board.touch((int) (x / (Board.cellSize*scale)), (int) (y / (Board.cellSize*scale)));
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
		map.dispose();
		renderer.dispose();
		debugFont.dispose();
	}

	public void Win(String string) {
		pause.getTitleLabel().setText(string);
		buttonContinue.remove();
		exit();
	}

	public void exit() {
		paused = !paused;
		pause.setVisible(paused);		
		if (paused) {
			musicState = getGame().isPlayingMusic();
			getGame().Music(true);
		} else {
			getGame().Music(!musicState);
		}
	}
	
	@Override
	public void escape() {
		if (buttonContinue.getParent() == null) {
			getGame().setMenuScreen();
			return;
		}
		exit();
	}

}
