package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyPlatformerGame extends ApplicationAdapter {

	ShapeRenderer sr;
	
	ArrayList<Platform> platformList = new ArrayList<>();
		
	int playerX = 20;
	int playerY = 50;
	int playerW = 30;
	int playerH = 60;
	Color playerC = Color.WHITE;
	
	double gravity = 5;
	
	Player player = new Player(playerX, playerY, playerW, playerH, playerC, gravity);
	
	public boolean isGrounded(Player player) {
		boolean grounded = false;
		if(player.y <= 0) {
			grounded = true;
		}
		
		for(Platform platform: platformList) {
			if(player.y <= platform.y + platform.height & player.y > platform.y) {
				if(player.x+player.width > platform.x & player.x < platform.x+platform.width) {
					grounded = true;
					platform.setActive();
				}
			}
		}
				
		return grounded;
	}
	
	static double round (double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}
	
	@Override
	public void create () {
		
		sr = new ShapeRenderer();
		
		Platform bottomPlatform = new Platform(0, 0, Color.BROWN,  1280, 10, "Lower platform");
		platformList.add(bottomPlatform);

		try {
			File file = new File("platforms.txt");
			Scanner Reader = new Scanner(file);
			while(Reader.hasNextLine()) {
				String line = Reader.nextLine();
				if (line.contains("//")) { //Skips lines with // notation.
					line = Reader.nextLine();
				}
				String[] splitLine = line.split(" ");
				int x = Integer.valueOf(splitLine[0]);
				int y = Integer.valueOf(splitLine[1]);
				Color color = Color.valueOf(splitLine[2]);
				String note = splitLine[3];
				Platform platform = new Platform(x, y, color, 200, 10, note);
				platformList.add(platform);
			}
		Reader.close();
		} catch (FileNotFoundException e ) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void render () {
		
		System.out.println("---------");

		ScreenUtils.clear(0, 0, 0, 1);

		for(Platform platform: platformList) {
			platform.draw(sr);
		}
		
		double XVelocity = player.getVelocity()[0];
		double YVelocity = player.getVelocity()[1];
	
				
//		if(Gdx.input.isKeyPressed(Keys.W) & isGrounded(player.y)) {
//			YVelocity = 2;
//		}
//		if(Gdx.input.isKeyPressed(Keys.A) & player.x >= 5 & XVelocity > -1) {
//			XVelocity -= 0.1;
//		}
//		
//		if(Gdx.input.isKeyPressed(Keys.D) & player.x <= 1280 - player.width & XVelocity < 1) {
//			XVelocity += 0.1;
//		}
//		
//		System.out.println(isGrounded(player.y));
//		
//		if(!isGrounded(player.y)) {
//			YVelocity -= 0.07;
//		}
//			
//		System.out.println(XVelocity);
//		
//		if(XVelocity < 0) {
//			XVelocity += 0.02;
//		}
//		else if(XVelocity > 0) {
//			XVelocity -= 0.02;
//		}
//		
//		XVelocity = round(XVelocity, 10);
//		
//	
//		int predictedX = (int) (player.x + (XVelocity * gravity));
//		int predictedY = (int) (player.y + (YVelocity * gravity));
//		
//		if(predictedY <= 0) {
//			player.y = 0;
//			YVelocity = 0;
//		}
//						
//		if(predictedX < 0) {
//			XVelocity = -XVelocity;
//			player.x = 0;
//		}
//		
//		if(predictedX > 1280-player.width) {
//			XVelocity = -XVelocity;
//			player.x = 1280-player.width;
//		}
//		
//		
//		for(Platform platform: platformList) {
//			if(player.y < platform.x+platform.height & player.y > platform.y) {
//				player.y = platform.y+platform.height;
//			}
//		}
//
//		System.out.println(YVelocity);
//		System.out.println(player.y);
		
		//Constantly resets player velocity to 0, also rounds it to a nearest 1.dp
		
		XVelocity = round(XVelocity, 1);
		
		System.out.println("1 X VEL: " + XVelocity);
		System.out.println("1 Y VEL: " + YVelocity);
		
		if(XVelocity < 0) { 
			XVelocity += 0.1;}
		
		else if(XVelocity > 0) {
			XVelocity -= 0.1;}
		
		if(!isGrounded(player) & YVelocity < 2) {
			YVelocity -= 0.1;}
		
		System.out.println("2 X VEL AFTER Gravity : " + XVelocity);
		System.out.println("2 Y VEL AFTER Gravity : " + YVelocity);
		
		//Takes user input and moves accordingly
		
		if(Gdx.input.isKeyPressed(Keys.W) & !isGrounded(player)){		
			YVelocity = 2;}

		if(Gdx.input.isKeyPressed(Keys.A)) {
			XVelocity -= 0.2;}

		if(Gdx.input.isKeyPressed(Keys.D)) {
			XVelocity += 0.2;}
		
		System.out.println("3 X VEL AFTER Moving : " + XVelocity);
		System.out.println("3 Y VEL AFTER Moving : " + YVelocity);
		
		//Is user over the edge? If so reverses their velocity and sets to the edge.
		
		if((XVelocity * gravity + player.x) < 0) {
			XVelocity = 0;
			player.x = 0;
		}
		else if((XVelocity * gravity + player.x) > 1280-player.width) {
			XVelocity = 0;
			player.x = 1280-player.width;
		}
		
		//Is user on a platform?
		

				
		//Pass Velocity to player.
		double[] vel = {XVelocity, YVelocity};
		player.setVelocity(vel);
		
		player.draw(sr); //Processes the movement as well.
			
		
	}};
	