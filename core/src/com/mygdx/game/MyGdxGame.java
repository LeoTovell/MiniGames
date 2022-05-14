package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGdxGame extends ApplicationAdapter {
	// Scanner scan = new Scanner(System.in);
	// String menuResponse;

	ShapeRenderer sr;

	SpriteBatch batch;
	BitmapFont font;
	
	boolean endGame = false;
	int ballSize = 20; //Ball Radius
	int ballY = 720/2-ballSize/2;
	int ballX = 1280/2-ballSize/2;
	Color ballColor = Color.WHITE;
	
	double gravity = 9.8;
	double velocity = 0;
	
	private Stage stage;
	private Texture myTexture;
	private TextureRegion myTextureRegion;
	private TextureRegionDrawable myTextRegionDrawable;
	private ImageButton button;

	public int gap = 300;	
	public int score;
	
	Random random = new Random();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

	int topBlock = random.nextInt(50 - 0) + 0;	//(max - min) + min
	int bottomBlock = topBlock + gap;

	Obstacle obstacle = new Obstacle(topBlock, bottomBlock);
	
	public void retry() {
		endGame = false;
		obstacles.clear();
		ballY = 720/2-ballSize/2;
		ballX = 1280/2-ballSize/2;
		
		int topBlock = random.nextInt(50 - 0) + 0;	//(max - min) + min
		int bottomBlock = topBlock + gap;
		Obstacle obstacle = new Obstacle(topBlock, bottomBlock);
		
		obstacles.add(obstacle);
		
		score = 0;
		
	}


	@Override
	public void create() {
		sr = new ShapeRenderer();
		obstacles.add(obstacle);
		
		batch = new SpriteBatch();
		
		//FONT
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AmaticSC-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 100;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
		font = generator.generateFont(parameter);
		font.setColor(Color.WHITE);
		generator.dispose();
		
		myTexture = new Texture(Gdx.files.internal("retry.png"));
		myTextureRegion = new TextureRegion(myTexture);
		myTextRegionDrawable = new TextureRegionDrawable(myTextureRegion);
		button = new ImageButton(myTextRegionDrawable);
		
		button.setX(200);
		button.setY(200);
		
		button.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				retry();
			}
			
		});
		
		stage = new Stage(new ScreenViewport());
		stage.addActor(button);
		Gdx.input.setInputProcessor(stage);
		
	

		
	}


	@Override    
	public void render() {

		if(!endGame) {
			ScreenUtils.clear(0,0,0,1);

			Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);
			Obstacle firstObstacle = obstacles.get(0);


			if(lastObstacle.x < 960) {
				int topBlock = random.nextInt(500 - 0);	
				int bottomBlock = topBlock + gap;
				Obstacle b = new Obstacle(topBlock, bottomBlock);
				obstacles.add(b);
			}


			if(firstObstacle.x < -200) {
				obstacles.remove(0);
			}
			
			for(Obstacle obstacle: obstacles) {
				obstacle.draw(sr, obstacle.x, 0, obstacle.top, Color.GREEN);
				obstacle.draw(sr, obstacle.x, obstacle.bottom, 720-gap, Color.GREEN);

				if(ballX+20 > obstacle.x && ballX-20 < obstacle.x+100) { //obstacle.x = left side. //obstacle.x+100 = right side //ballX+20 + "104" because we want it to check for the whole pillar (100 wide) and 4 for the accounting of move(3)
					if(ballY-20 < obstacle.top || ballY+20 > obstacle.bottom) {
						obstacle.gameEnder();
						endGame = true;
					}
				}
				else if (obstacle.x < ballX-20 & !obstacle.passed) {
					score += 1;
					obstacle.passed();
				}

				   obstacle.move();

			}



			sr.begin(ShapeType.Filled);
			sr.setColor(ballColor);
			sr.circle(ballX,  ballY, ballSize);
			sr.end();

			if(Gdx.input.isKeyPressed(Keys.SPACE) & !endGame) {
				if(velocity < 1) {
					velocity = 1;
				}}
			else if (!Gdx.input.isKeyPressed(Keys.SPACE) & !endGame){
				if(velocity > -1) {
					velocity -= 0.05;
				}
			}
			
			double predictedPosition = ballY + (velocity*gravity);
			if(velocity > 0) {
				if(predictedPosition > 700) {
					ballY = 700;
					velocity = 0;
				}
				else {
					ballY = (int) predictedPosition;
				}
			}
			else if(velocity < 0) {
				if(predictedPosition < 20) {
					ballY = 20;
				}
				else {
					ballY = (int) predictedPosition;
				}
			}
			
			//Render the text			
			batch.begin();
			
			font.draw(batch,  String.valueOf(score), 100, 200);
			
			batch.end();

		}
		
		

		if(endGame) {
			

			ScreenUtils.clear(0,0,0,1);

			for(Obstacle obstacle: obstacles) {
				if(obstacle.gameEnd) {
					obstacle.draw(sr, obstacle.x, 0, obstacle.top, Color.RED);
					obstacle.draw(sr, obstacle.x, obstacle.bottom, 720-gap, Color.RED);
				}
				else {
					obstacle.draw(sr, obstacle.x, 0, obstacle.top, Color.DARK_GRAY);
					obstacle.draw(sr, obstacle.x, obstacle.bottom, 720-gap, Color.DARK_GRAY);
				}
				
				batch.begin();
				
				font.draw(batch, "Final Score: " + String.valueOf(score), 100,150);
				font.draw(batch, "Game Over!", 100, 250);
			
								
				batch.end();
				
				stage.act(Gdx.graphics.getDeltaTime());
				stage.draw();
				
				sr.begin(ShapeType.Filled);
				sr.setColor(ballColor);
				sr.circle(ballX,  ballY, ballSize);
				sr.end();


			}

		}



	}}