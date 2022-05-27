package com.gdx.Tetris;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.Tetris.utils.Info;

public class Tile {
	
	int x,y, globalX, globalY;
	Sprite sprite;
	int position;

	public Tile(int position, Sprite sprite) {
		this.sprite = sprite;
		this.position = position;
	}
	
	public void draw(SpriteBatch batch) {
		x = this.globalX * Info.TILE_WIDTH + Info.GAMEBOX_X;
		y = this.globalY * Info.TILE_WIDTH + Info.GAMEBOX_Y;
		int width = Info.TILE_WIDTH;
		int height = Info.TILE_WIDTH;

		batch.begin();
		batch.draw(this.sprite, x, y, width, height); //this.sprite.draw(batch, x, y, width, height)
		batch.end();
		
		System.out.println(globalX);
		System.out.println(globalY);
		System.out.println("--");
	}

	public void setGlobalY(int globalY){
		this.globalY = globalY;
	}

	public void setGlobalX(int globalX){
		this.globalX = globalX;
	}

	public int getGlobalY(){
		return this.globalY;
	}

	public int getGlobalX(){
		return this.globalX;
	}

}
