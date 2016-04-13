package com.victorvieux.hnefatalf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.victorvieux.hnefatafl.HnefataflGdxGame;
import com.victorvieux.hnefatafl.entities.Board;
import com.victorvieux.hnefatafl.screens.Play;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = HnefataflGdxGame.TITLE;
		cfg.vSyncEnabled = true;
		cfg.resizable = false;
		cfg.width = (int) (Board.cellSize * 20 * Play.scale);
		cfg.height = (int) (Board.cellSize * 15 * Play.scale);
		new LwjglApplication(new HnefataflGdxGame(), cfg);
	}
}
