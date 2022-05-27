package com.gdx.L2048;

import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;


public class L2048 extends ApplicationAdapter {
	
	Color backgroundColor = Color.ORANGE;
	
	FreeTypeFontGenerator fontGenerator;
	FreeTypeFontParameter fontParameter;
	BitmapFont font, creditsFont;
	
	SpriteBatch batch;
	ShapeRenderer sr;
	
	Texture logo;
	float logoX = 50;
	float logoY = 690;
	
	boolean bound = false;
	boolean addNewTile;
	boolean gameOver = false;
	
	Tile[] tileList = new Tile[16];
	
	Random random;
	final int GAMEBOX_X = 50;
	final int GAMEBOX_Y = 50;
	final int GAP = 10;
	int keyFactor = 0;


	@Override
	public void create() {
		
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		
		//Initiation
		random = new Random();
		
		// Font generation
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
		fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 50; //Text size
		fontParameter.borderWidth = 0; //Stroke
		fontParameter.borderColor = Color.RED; //Stroke color
		fontParameter.color = Color.WHITE; //Text Color
		font = fontGenerator.generateFont(fontParameter);
		
		fontParameter.color = Color.BLACK;
		fontParameter.size = 25;		
		creditsFont = fontGenerator.generateFont(fontParameter);
		
		for(int i = 0; i < 6; i++) {
			addRandomTile();
		}
		
		logo = new Texture(Gdx.files.internal("2048_logo.png"));
		
	}

	public void addRandomTile() {
		int randomChoice = random.nextInt(tileList.length);
		while(tileList[randomChoice] != null) {
			randomChoice = random.nextInt(tileList.length);
		}
		int value = random.nextInt(2 - 0) + 0;
		
		value = (value == 0) ? 2 : 4; //Short hand if statement!!
		
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(4);
		for(int j = 0; j < 4; j++) {
			int index = (int)(alphabet.length() * Math.random());
			sb.append(alphabet.charAt(index));
		}
		String tileID = sb.toString();
		
		Tile tile = new Tile(randomChoice, value, tileID);
		tileList[randomChoice] = tile;
	}
	
		
	public boolean updateBounds(int keyFactor, Tile tile) {
		if(keyFactor == 1) bound = tile.cordY < 3;
		if(keyFactor == -1) bound = tile.cordY > 0;
		if(keyFactor == 4) bound = tile.cordX < 3;
		if(keyFactor == -4) bound = tile.cordX > 0;
		return bound;
	}
	
	public void checkTiles(int keyFactor) {
		for(int i = 0; i < 3; i++) {
			for(Tile tile: tileList) {
				if(tile != null) {
					try {
						bound = updateBounds(keyFactor, tile);
						while(bound & tileList[tile.position + keyFactor] == null) {
							tileList[tile.position] = null;
							if(keyFactor == 1) tile.moveUp();
							if(keyFactor == -1) tile.moveDown();
							if(keyFactor == 4) tile.moveRight();
							if(keyFactor == -4) tile.moveLeft();
							tileList[tile.position] = tile;
							bound = updateBounds(keyFactor, tile);
						}
					}
					catch(Exception e) {
					
				}}}}}
	
	
	public void addTiles(int keyFactor) {
		for(int i = 0; i < 3; i++) {
			for(Tile tile: tileList) {
				if(tile != null) {
					try {
						if(tile.value == tileList[tile.position + keyFactor].value) {
							tileList[tile.position + keyFactor].nextValue();
							tileList[tile.position] = null;
						}
					}
					catch(Exception e) {}//e.printStackTrace();}
			}}}}
	
	@Override
	public void render() {
	
	ScreenUtils.clear(Color.WHITE);
	addNewTile = false;
	keyFactor = 99;
	
	// REMOVE LOOPS IN KEYS UP
	
	
	//Get input		
		if(Gdx.input.isKeyJustPressed(Keys.UP)) keyFactor = 1;
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) keyFactor = -1;
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) keyFactor = -4;
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) keyFactor = 4; 
		
		
		if(keyFactor != 99) {
			addNewTile = true;
			checkTiles(keyFactor);
			addTiles(keyFactor);
			checkTiles(keyFactor);
		}
		
		
		if(addNewTile) {
			int nullCount = 0;
			for(Tile tile: tileList)
				if(tile == null) {
					nullCount =+ 1;
				}
			if(nullCount > 0) addRandomTile();
		}
		
		for(Tile tile: tileList) {
			if(tile != null) {
				if(tile.value == 2048) {
					System.out.println("You win! Game OVER");
					gameOver = true;
				}}}
		
	//Drawing the backgroundGrid
		
		sr.begin(ShapeType.Filled);
		sr.setColor(212/255f, 184/255f, 103/255f, 0.5f);
		sr.rect(GAMEBOX_X, GAMEBOX_Y, 630, 630);
		sr.end();
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				int x = GAMEBOX_X + GAP + (j * (GAP + 145));
				int y = GAMEBOX_Y + GAP + (i * (GAP + 145));
				sr.begin(ShapeType.Filled);
				sr.setColor(221/255f, 193/255f, 113/255f, 0.5f);
				sr.rect(x, y, 145, 145);
				sr.end();
			}
		}
		
		
	//Drawing the tiles
		
	for(Tile tile: tileList) {
		if(tile != null) {
			tile.draw(sr, batch, font);
	}}
	
	batch.begin();
	batch.draw(logo, logoX, logoY, 630, 100);
	creditsFont.draw(batch, "By Leo Tovell :D", 500, 35);
	batch.end();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		sr.dispose();
	}
}