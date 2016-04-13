package com.victorvieux.hnefatafl.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorAccessor implements TweenAccessor<Actor>{

	public static final int ALPHA = 0;
	public static final int X = 1;
	public static final int Y = 2;
	
	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
			case ALPHA:
				returnValues[0] = target.getColor().a;
				return 1;
			case X:
				returnValues[0] = target.getX();
				return 1;
			case Y:
				returnValues[0] = target.getY();
				return 1;
			default:
				return -1;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		case X:
			target.setX(newValues[0]);
			break;
		case Y:
			target.setY(newValues[0]);
			break;
		}
	}

}
