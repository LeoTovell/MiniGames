package com.gdx.Tetris;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gdx.Tetris.utils.Info;

public class Tetromino{	
	int column = 0;
	int row = 0;
	int TILE_WIDTH;
	boolean active = true;
	
	Tile[][] tileList = new Tile[][]{{null, null, null, null},
									 {null, null, null, null},
									 {null, null, null, null},
									 {null, null, null, null}};
	int[] tilePlacement;
	int globalX = 5; // Middle column
	int globalY = 19; //Top row

	private TextureAtlas textureAtlas;
	private Sprite sprite;
	String type;
	
	public Tetromino(String type) {
		this.type = type;
		
		//Create tiles
		this.createTiles();
	}
	
	public void render(SpriteBatch batch) {
		ArrayList<Tile> tiles = getTiles();
		for(Tile tile: tiles) {
			tile.draw(batch);
		}}
				
	public ArrayList<Tile> getTiles() {
		ArrayList<Tile> tiles = new ArrayList<>();
		for(int i = 0; i < tileList.length; i ++) {
			for(int j = 0; j < tileList[i].length; j++) {
				if(tileList[i][j] instanceof Tile) {
					tiles.add(tileList[i][j]);
				}}}
		return tiles;
	}
	
	public void createTiles() {		
		if(this.type == "I") tilePlacement = Info.I;
		if(this.type == "J") tilePlacement = Info.J;
		if(this.type == "L") tilePlacement = Info.L;
		if(this.type == "O") tilePlacement = Info.O;
		if(this.type == "S") tilePlacement = Info.S;
		if(this.type == "T") tilePlacement = Info.T;
		if(this.type == "Z") tilePlacement = Info.Z;
		
		textureAtlas = new TextureAtlas("tetrominos/atlas/tetrominosAtlas");
		sprite = textureAtlas.createSprite(this.type);
		
		for(int i = 0; i < tilePlacement.length; i++) {
			if(tilePlacement[i] == 1) {
				createTile(i, sprite);
			}}}
	
	public void createTile(int position, Sprite sprite) {
		Tile tile = new Tile(position, sprite);
		
		int x = this.globalX - position%4;
		int y = this.globalY - position/4;

		tile.setGlobalX(x);
		tile.setGlobalY(y);

		tileList[position%4][position/4] = tile;
	}
	
	public void moveTilesDown() {		
		ArrayList<Tile> tiles = getTiles();
		for(Tile tile: tiles) {
			tile.setGlobalY(tile.getGlobalY() -1);
		}
		this.globalY --;
	}
	
	public void moveTilesLeft() {
		ArrayList<Tile> tiles = getTiles();
		for(Tile tile: tiles) {
				tile.setGlobalX(tile.getGlobalX() -1);
		}
		this.globalX --;
	}
	
	public void moveTilesRight() {
		ArrayList<Tile> tiles = getTiles();
		for(Tile tile: tiles) {
				tile.setGlobalX(tile.getGlobalX() +1);
			}
		this.globalX ++;
	}
	
	public void rotateClockwise() {
		tileList = createNewRotatedTileArray(tileList);
		assignNewRotationPositions(tileList);
	}

	public static Tile[][] createNewRotatedTileArray(Tile[][] array) {
		int N = array.length;
		
		//Transpose Matrix(rows to column)
		for(int i = 0; i < N; i++) {
			for(int j = i; j < N; j++) {
				Tile temp = array[i][j];
				array[i][j] = array[j][i];
				array[j][i] = temp;
			}
		}
		
		//Swap corners.
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < (N/2); j++) {
				Tile temp = array[i][j];
				array[i][j] = array[i][N-1-j];
				array[i][N-1-j] = temp;
			}
		}

		
		return array;
	}
	
	public void assignNewRotationPositions(Tile[][] array) {
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				if(array[i][j] instanceof Tile) {
					int x = (j) * 1; //Get X from array. j
					int y = (i) * 1; //Get Y from array. i
					array[i][j].setGlobalX(this.globalX - x);
					array[i][j].setGlobalY(this.globalY - y);
				}
			}
		}
	}
	
	public void setInactive(){
		this.active = false;
	}

	public boolean isActive(){
		return this.active;
	}
	
	public boolean isGrounded() {
		boolean grounded = false;
		ArrayList<Tile> tiles = getTiles();
		for(Tile tile: tiles) {
			if(!(grounded) && tile.globalY == 0) {
				grounded = true;
			}
		}
		
		return grounded;
	}
	
}
