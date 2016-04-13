package com.victorvieux.hnefatafl;

public class Teams {
	public static final int ATTACKER = -1;
	public static final int EMPTY = 0;
	public static final int DEFENDER = 1;
	public static final int KING = 2;
      
	public static int currentPlayer = ATTACKER;
	
	public static void Swap() {
		currentPlayer *= -1;
	}
}
