package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Obstacle {
	int x = 1280;
	int y;
	int top;
	int bottom;
	boolean gameEnd;
	boolean passed;

	public Obstacle(int top, int bottom) {
		this.top = top;
		this.bottom = bottom;
		this.gameEnd = false;
	}
	
	public void move() {
		this.x -= 3;
	}
	
	public void gameEnder() {
		this.gameEnd = true;
		this.x += 3;
	}
	
	public void passed() {
		this.passed = true;
	}
	
	public void draw(ShapeRenderer sr, int x, int y, int h, Color c) {
			
		this.x = x;
		this.y = y;
		
		sr.begin(ShapeType.Filled);
		sr.setColor(c);
		sr.rect(x, y, 100, h); //width is always 100
		sr.end();
	}
}
