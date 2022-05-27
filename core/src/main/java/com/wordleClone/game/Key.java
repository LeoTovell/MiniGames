package com.wordleClone.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Key {

	Color c;
	String letter;
	ShapeType shapetype;
	int x;
	int y;
	
	public Key(String letter, Color c, ShapeType shapetype, int x, int y){
		this.letter = letter;
		this.c = c;
		this.shapetype = shapetype;
		this.x = x;
		this.y = y;
		
	}
	
	public void draw(ShapeRenderer sr) {
		sr.begin(this.shapetype);
		sr.setColor(this.c);
		sr.rect(this.x, this.y, 50, 50);
		sr.end();
		
	}
}
