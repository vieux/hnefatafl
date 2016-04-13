package com.victorvieux.hnefatafl;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class TouchDetector implements InputProcessor {
	Touchable screen;
	Camera camera;
	
	public TouchDetector(Touchable screen, Camera camera) {
        super();
        this.screen = screen;
        this.camera = camera;
}
	
	public TouchDetector(Touchable screen) {
		super();
        this.screen = screen;
	}
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE) {
			screen.escape();
			return true;
		}
		return touchDown(-1, -1, -1, -1);
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		 Vector3 touchPoint = new Vector3(x, y, 0);
		 if (camera != null)
			 camera.unproject(touchPoint);
         screen.touch((int) touchPoint.x, (int) touchPoint.y);
         return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
