package com.victorvieux.hnefatafl.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.victorvieux.hnefatafl.Cache;
import com.victorvieux.hnefatafl.Teams;
import com.victorvieux.hnefatafl.entities.sprites.BaseSprite;
import com.victorvieux.hnefatafl.entities.sprites.Case;
import com.victorvieux.hnefatafl.entities.sprites.Soldier;
import com.victorvieux.hnefatafl.maps.BaseMap;
import com.victorvieux.hnefatafl.maps.BaseMap.SQUARE;
import com.victorvieux.hnefatafl.screens.Play;

public class Board implements Disposable{
	public static int cellSize = 32;
    private final Soldier[][] board;
    private final Case[][] cases;
    private BaseSprite[][] selection;

    private final Play screen;
    private final BaseMap map;	
    
    int selectedX = -1, selectedY = -1;
    
    public Board(Play screen, BaseMap map) {
    	this.screen = screen;
    	this.map = map;
        board = new Soldier[map.getBoardSize()][map.getBoardSize()];
        cases = new Case[map.getBoardSize()][map.getBoardSize()];
        selection = new BaseSprite[map.getBoardSize()][map.getBoardSize()];
    	
        for (int x, y, i = 0; i < map.length(); ++i) {
        	x = i % map.getBoardSize();
        	y = map.getBoardSize() - i / map.getBoardSize() - 1;
        	if (map.at(i) == SQUARE.ATTACKER) {
        		cases[x][y] = new Case(Cache.getBoardTextures()[0][1], cellSize, map.getOffset(), SQUARE.EMPTY);
        		board[x][y] = new Soldier(Cache.getAttackerTextures(), 64f/Play.scale, map.getOffset(), Teams.ATTACKER);
        	} else if (map.at(i) == SQUARE.DEFENDER) {
        		cases[x][y] = new Case(Cache.getBoardTextures()[0][1], cellSize, map.getOffset(), SQUARE.EMPTY);
        		board[x][y] = new Soldier(Cache.getDefenderTextures(), 64f/Play.scale, map.getOffset(), Teams.DEFENDER);
        	} else if (map.at(i) == SQUARE.THRONE) {
        		cases[x][y] = new Case(Cache.getBoardTextures()[1][0], cellSize, map.getOffset(), SQUARE.THRONE);
        		board[x][y] = new Soldier(Cache.getKingTextures(), 64f/Play.scale, map.getOffset(), Teams.KING);
        	} else if (map.at(i) == SQUARE.CORNER) {
        		cases[x][y] = new Case(Cache.getBoardTextures()[1][0], cellSize, map.getOffset(), SQUARE.CORNER);
        	} else {
        		cases[x][y] = new Case(Cache.getBoardTextures()[0][0], cellSize, map.getOffset(), SQUARE.EMPTY);	
        	}
        }
    }
    
    public void resize() {
    	for (int x = 0; x < map.getBoardSize(); ++x) {
        	for (int y = 0; y < map.getBoardSize(); ++y) {
        		cases[x][y].resize();
        		if (selection[x][y] != null) 
        			selection[x][y].resize();
        		if (board[x][y] != null) 
        			board[x][y].resize();
        	}
    	}
    }
    
    public void draw(Batch batch) {
    	for (int x = 0; x < map.getBoardSize(); x++) {
            for (int y = map.getBoardSize() - 1; y >= 0; y--) {
            	cases[x][y].draw((SpriteBatch) batch, x ,y);
            	BaseSprite select = selection[x][y];
            	if (select != null) {
            		select.draw((SpriteBatch) batch, x, y);
            	}
            	Soldier soldier = board[x][y];
            	if (soldier != null) {
            		soldier.draw((SpriteBatch) batch, x, y);
            	}
            }
		}
    }
    
    private boolean isCorner(int x, int y) {
    	return (x == 0 && y == 0 ||
    			x == 0 && y == map.getBoardSize() - 1 ||
    			x == map.getBoardSize() - 1 && y == 0 ||
    			x == map.getBoardSize() - 1 && y == map.getBoardSize() - 1);
    }
    
    private boolean setSelection(boolean king, int x, int y) {
    	if (board[x][y] != null)
			return false;
    	if (!king) {
    		if (isCorner(x, y))
    				return false; //corners
    		if (x == Math.ceil(map.getBoardSize()/2f-1) && y == x)
    			return true; //throne
    	}
    	selection[x][y] = new BaseSprite(Cache.getBoardTextures()[1][1], cellSize, map.getOffset());	
		return true;
    }
    
    private int getTeamAt(int x, int y) {
    	if (x < 0 || x >= map.getBoardSize() || y < 0 || y >= map.getBoardSize()) {
    		return Teams.EMPTY;
    	}    	
    	Soldier s = board[x][y];
    	if (s == null)
    		return Teams.EMPTY;
    	return s.getTeam();
    }
    
    private boolean isHostile(int x, int y) {
    	if (x < 0 || x >= map.getBoardSize() || y < 0 || y >= map.getBoardSize()) {
    		return false;
    	}   
    	//[New] [Opponent] [Current] 
    	if (getTeamAt(x, y) == Teams.currentPlayer) {
    		return true;
    	}
    	//[New] [Opponent] [Corner]
    	if (cases[x][y].getType() == SQUARE.CORNER) {
    		return true;
    	}
    	//[New] [Attacker] [Throne] 
    	if (Teams.currentPlayer == Teams.DEFENDER && cases[x][y].getType() == SQUARE.THRONE) {
    		return true;
    	}
    	//[New] [Defender] [Thone_empty] 
    	if (Teams.currentPlayer == Teams.ATTACKER && cases[x][y].getType() == SQUARE.THRONE && getTeamAt(x, y) == Teams.EMPTY) {
    		return true;
    	}
    	return false;
    }
    
    private void movePlayer(int fromX, int fromY, int toX, int toY) {
    	board[toX][toY] = board[fromX][fromY];
    	board[fromX][fromY] = null;

    	int opponent = Teams.currentPlayer * -1;
    	
    	Soldier toRemove = null;
    	
    	//[Hostile] [Opponent] [New]
    	if (getTeamAt(toX-1, toY) == opponent && isHostile(toX-2, toY)){
    		toRemove = board[toX-1][toY];
    		board[toX-1][toY] = null;
    	}
    	
    	//[New] [Opponent] [Hostile]
    	if (getTeamAt(toX+1, toY) == opponent && isHostile(toX+2, toY)){
    		toRemove = board[toX+1][toY];
    		board[toX+1][toY] = null;
    	}
    	
    	//[New]
    	//[Opponent]
    	//[Hostile]
    	if (getTeamAt(toX, toY-1) == opponent && isHostile(toX, toY-2)){
    		toRemove = board[toX][toY-1];
    		board[toX][toY-1] = null;
    	}

    	//[Hostile]
    	//[Opponent]
    	//[New]
    	if (getTeamAt(toX, toY+1) == opponent && isHostile(toX, toY+2)){
    		toRemove = board[toX][toY+1];
    		board[toX][toY+1] = null;
    	}
    	
    	//Special rule for the king
    	if (toRemove != null && toRemove.isKing()) {
    		if (cases[toRemove.X()][toRemove.Y()].getType() == SQUARE.THRONE ||
    				cases[toRemove.X()-1][toRemove.Y()].getType() == SQUARE.THRONE ||
    				cases[toRemove.X()+1][toRemove.Y()].getType() == SQUARE.THRONE ||
    				cases[toRemove.X()][toRemove.Y()-1].getType() == SQUARE.THRONE ||
    				cases[toRemove.X()][toRemove.Y()+1].getType() == SQUARE.THRONE) {
    				if ((cases[toRemove.X()-1][toRemove.Y()].getType() == SQUARE.THRONE || getTeamAt(toRemove.X()-1, toRemove.Y()) == Teams.currentPlayer)&&
    						(cases[toRemove.X()+1][toRemove.Y()].getType() == SQUARE.THRONE || getTeamAt(toRemove.X()+1, toRemove.Y()) == Teams.currentPlayer)&&
    						(cases[toRemove.X()][toRemove.Y()-1].getType() == SQUARE.THRONE || getTeamAt(toRemove.X(), toRemove.Y()-1) == Teams.currentPlayer)&&
    						(cases[toRemove.X()][toRemove.Y()+1].getType() == SQUARE.THRONE || getTeamAt(toRemove.X(), toRemove.Y()+1) == Teams.currentPlayer)) {
    	    			screen.Win("ATTACKERS WON THE GAME:\nThe King is dead");
    				}
    		} else {
    			screen.Win("ATTACKERS WON THE GAME:\nThe King is dead");
    		}
    	}
    	//King touches a corner, defenders won
    	if (board[toX][toY].isKing()) {
    		if (isCorner(toX, toY)) {
    			screen.Win("DEFENDERS WON THE GAME:\nThe King escaped");
    		}
    	}
    	
    	
    }
    
    public void touch(int x, int y) {
    	x = x - map.getOffset();
    	y = y - map.getOffset();
    	if (x < 0 || x >= map.getBoardSize() || y < 0 || y >= map.getBoardSize()) {
    		return;
    	}
    	Soldier s = board[x][y];
    	if (s != null && s.getTeam() == Teams.currentPlayer) { //touch a player, display selection cross
    		selectedX = x;
    		selectedY = y;
        	selection = new BaseSprite[map.getBoardSize()][map.getBoardSize()];
    		selection[x][y] = new BaseSprite(Cache.getBoardTextures()[1][1], cellSize, map.getOffset());
    		for (int xx = x+1; xx < map.getBoardSize(); ++xx) {
    			if (!setSelection(s.isKing(), xx, y))
    				break;
    		}
    		for (int xx = x-1; xx >= 0; --xx) {
    			if (!setSelection(s.isKing(), xx, y))
    				break;
    		}
    		for (int yy = y+1; yy < map.getBoardSize(); ++yy) {
    			if (!setSelection(s.isKing(), x, yy))
    				break;
    		}
    		for (int yy = y-1; yy >= 0; --yy) {
    			if (!setSelection(s.isKing(), x, yy))
    				break;
    		}
    	} else if ((selectedX != x || selectedY != y) && (selectedX != -1 || selectedY != -1) && selection[x][y] != null) { //touch a selection, move the soldier
        	selection = new BaseSprite[map.getBoardSize()][map.getBoardSize()];
        	movePlayer(selectedX, selectedY, x, y);
        	selectedX = selectedY = 1;
    		Teams.Swap();
    	} else if (s == null && selection[x][y] == null){ //touch empty, discard selection
        	selection = new BaseSprite[map.getBoardSize()][map.getBoardSize()];
    	}
    }
    

	@Override
	public void dispose() {
	}
	
	public BaseMap getMap() {
		return map;
	}
}
