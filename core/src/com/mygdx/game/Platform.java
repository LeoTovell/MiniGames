package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Platform {

	int x;
	int y;
	int width;
	int height;
	Color c;
	String note;
	boolean active;
	
	public Platform(int x,int y,Color c, int width, int height, String note) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.c = c;
		this.note = note;
		System.out.println(note);
	};
	
	public void draw(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(c);
		sr.rect(this.x, this.y, this.width, this.height);
		sr.end();
	}
	
	public void setActive() {
		this.active = true;
	}
	
}
