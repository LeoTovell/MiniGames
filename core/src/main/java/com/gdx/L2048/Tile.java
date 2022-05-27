package com.gdx.L2048;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Tile {

	final int GAMEBOX_X = 50;
	final int GAMEBOX_Y = 50;
	final int GAP = 10;
	int x, y, cordX, cordY;
	int width = 145;
	int height = 145;
	int position, value;
	String tileID;
	
	Color color = Color.FIREBRICK;
		
	boolean moving = false;
	
	public Tile(int position, int value, String tileID) {
		this.cordX = position / 4;
		this.cordY = position % 4;
		
		this.x = (GAMEBOX_X + GAP) + ((width + GAP) * cordX);
		this.y = (GAMEBOX_Y + GAP) + ((height + GAP) * cordY);
		
		this.position = position;
		this.value = value;
		this.tileID = tileID;
		
	}
	
	public void draw(ShapeRenderer sr, SpriteBatch batch, BitmapFont font) {
		
		//Click info
		
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		boolean M1 = Gdx.input.isButtonPressed(Buttons.LEFT);
		GlyphLayout text = new GlyphLayout(font, Integer.toString(this.value));
		
		if((mouseX >= x & mouseX <= x+width) & (mouseY <= y + height & mouseY >= y)) {
			if(M1) {
				System.out.println(("Cord X: %d, Cord Y: %d").formatted(cordY, cordX));
			}
			else {
			color = Color.BLACK;
			text = new GlyphLayout(font, this.tileID);
			}}
		else {
			color = Color.FIREBRICK;
		}
		
		
		
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(x, y, width, height);
		sr.end();
		
		float fontX = x + (width - text.width) /2;
		float fontY = y + (height + text.height) /2;
		batch.begin();
		font.draw(batch, text, fontX, fontY);
		batch.end();
		
		
		
		

	}
	
	public void moveLeft() {
		position -= 4;
		cordX --;
		updateXY();
	}
	
	public void moveRight() {
		position += 4;
		cordX ++;
		updateXY();
	}
	
	public void moveUp() {
		position += 1;
		cordY += 1;
		updateXY();
	}
	
	public void moveDown() {
		position -= 1;
		cordY -= 1;
		updateXY();
	}
	
	public void updateXY() {
		x = (GAMEBOX_X + GAP) + ((width + GAP) * cordX);
		y = (GAMEBOX_Y + GAP) + ((height + GAP) * cordY);
	}
	
	public void nextValue() {
		this.value = this.value * 2;
	}
}
