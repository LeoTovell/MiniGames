package com.gdx.Tetris;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.Tetris.utils.Info;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Tetris extends ApplicationAdapter {

	Random random = new Random();

	private boolean[] input;
	private long currentTime;
	private long previousTime = System.currentTimeMillis();
	private int columns = 10;
	public int WINDOW_WIDTH = 400;
	public int WINDOW_HEIGHT = 800;
	private ArrayList<Tetromino> tetrominoList = new ArrayList<>();
	SpriteBatch batch;
	
	public int timeBetweenTicks = 500;
	public int GAMEBOX_X = 30;
	public int GAMEBOX_Y = 30;
	public int GAMEBOX_GAP = 1;
	public int GAMEBOX_WIDTH = WINDOW_WIDTH - (2 * GAMEBOX_X); // Window width without the two margins either side
	public int GAMEBOX_HEIGHT = GAMEBOX_WIDTH * 2; // Twice the width ( 10 boxes --> 20 boxes)
	public int TILE_WIDTH = GAMEBOX_WIDTH / columns;
		
	private ShapeRenderer sr;
	
	@Override
	public void create() {
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		
		//tetrominoList.add(createRandomTetromino());
		tetrominoList.add(createSpecificTetromino("Z"));
	}

	@Override
	public void render() {
		ScreenUtils.clear(Color.BLACK);
		drawGrid();
		drawTetrominos();

		input = getInput(Keys.A, Keys.D);
		rotateActiveTetromino(input);
		
		input = getInput(Keys.LEFT, Keys.RIGHT);
		moveActiveTetrominoHorizontally(input);
		
		currentTime = System.currentTimeMillis();
						
		if(currentTime - previousTime > timeBetweenTicks) {
			previousTime = currentTime;
			moveActiveTetrominoDown();
		}
		
		checkTetrominoIsGrounded();
	}

	@Override
	public void dispose() {
		sr.dispose();
	}
	
	public void checkTetrominoIsGrounded() {
		Tetromino tetromino = getActiveTetromino();
		if(tetromino.isGrounded()) {
			tetromino.setInactive();
			tetrominoList.add(createRandomTetromino());
		}
	}
	
	public void moveActiveTetrominoHorizontally(boolean[] values) {
		Tetromino tetromino = getActiveTetromino();
		if(values[1]) {
			tetromino.moveTilesLeft();
		}
		if(values[0]) {
			tetromino.moveTilesRight();
		}
	}
	
	public void moveActiveTetrominoDown() {
		Tetromino activeTetromino = getActiveTetromino();
		activeTetromino.moveTilesDown();
	}
	
	public void rotateActiveTetromino(boolean[] values){
		Tetromino activeTetromino = getActiveTetromino();
		if(values[0]){
			activeTetromino.rotateClockwise();
			}
		if(values[1]){
			activeTetromino.rotateClockwise();
			}
		}

	public boolean[] getInput(int... keys){
		boolean[] input = new boolean[2];
		int count = 0;
		for(int key: keys) {
			input[count] = Gdx.input.isKeyJustPressed(key);
		}
		return input;
	}

	public void drawTetrominos(){
		for(Tetromino t: tetrominoList) {
			t.render(batch);
		}
	}

	public void drawGrid(){

		// Draw Grid;

		int x = GAMEBOX_X;
		int y = GAMEBOX_Y;

		sr.begin(ShapeType.Filled);
		sr.setColor(22f/255f, 100f/255f, 8f/255f, 0.5f);

		for(int i = 0; i < 21; i++) {
			sr.rect(x, y, GAMEBOX_WIDTH, GAMEBOX_GAP);
			y += TILE_WIDTH;
		}

		x = GAMEBOX_X;
		y = GAMEBOX_Y;

		for(int i = 0; i < 11; i++) {
			sr.rect(x, y, GAMEBOX_GAP, GAMEBOX_HEIGHT);
			x += TILE_WIDTH;
		}

		sr.end();
	}
	
	public Tetromino createRandomTetromino() {
		int length = Info.TetrominoList.length;
		int tetrominoChoice = random.nextInt(length);
		Tetromino t = new Tetromino(Info.TetrominoList[tetrominoChoice]);
		return t;
		
	}
	
	public Tetromino createSpecificTetromino(String type) {
		Tetromino t = new Tetromino(type);
		return t;
	}
	
	public Tetromino getActiveTetromino() {
		Tetromino activeTetromino = null;
		for(Tetromino tetromino: tetrominoList) {
			if(tetromino.isActive()) {
				activeTetromino = tetromino;
			}
		}
		return activeTetromino;
	}
	
}