package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Player {
	
	int x;
	int y;
	int width;
	int height;
	Color c;
	int leftSide;
	int rightSide;
	int bottomSide;
	int topSide;
	double XVelocity;
	double YVelocity;
	double gravity;
	
	public Player(int x, int y, int width, int height, Color c, double gravity) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.c = c;
		this.gravity = gravity;
		
		this.leftSide = this.x;
		this.rightSide = this.x + this.width;
		this.topSide = this.y;
		this.bottomSide = this.y+this.height;
		
	}
	
	public void moveX(double v) {
		this.x += v*9.8;
	}
	
	public void moveY(double v) {
		this.y += v*9.8;
	}
	

	public double[] getVelocity() {
		double[] vel = {this.XVelocity, this.YVelocity};
		return vel;
	}
	
	public void setVelocity(double[] vel) {
		this.XVelocity = vel[0];
		this.YVelocity = vel[1];
	}
	
	public void draw(ShapeRenderer sr) {
		
		this.y += this.YVelocity * gravity;
		this.x += this.XVelocity * gravity;
		
		this.x = (int) MyPlatformerGame.round(this.x, 1);
		this.y = (int) MyPlatformerGame.round(this.y, 1);
				
		sr.begin(ShapeType.Filled);
		sr.setColor(this.c);
		sr.rect(this.x, this.y, this.width, this.height);
		sr.end();
	}
}
