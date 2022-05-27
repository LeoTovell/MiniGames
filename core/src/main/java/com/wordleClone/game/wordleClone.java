package com.wordleClone.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class wordleClone extends ApplicationAdapter {

	static TextButton[] field = new TextButton[30];
	static int keyboardOffset = 500;
	static int letterX = 0;
	static int letterY = 345;
	SpriteBatch batch;
	BitmapFont font;
	ShapeRenderer sr;
	Skin skin;
	static ArrayList<TextButton> keyboardList = new ArrayList<>();
	static ArrayList<String> words = new ArrayList<>();
	ArrayList<Key> keyList = new ArrayList<>();
	ArrayList<TextButton> buttonList = new ArrayList<>();
	static TextButtonStyle wordTextButtonStyle;
	static TextButtonStyle textButtonStyle;
	static TextButton button;
	BitmapFont buttonFont;
	TextureAtlas buttonAtlas;
	static Stage keyboardStage;
	static Stage wordStage;
	char[] alphabet;
	static String word;
	int currentField = 0;
	int currentLetter = 0;
	static boolean match = false;
	static String messageText = "Wordle-Clone :D";
	
	public static int addLetter(String letter, int currentLetter, int currentField) {
		if(currentLetter < 5 & currentField < 6) {
			button = new TextButton(letter, wordTextButtonStyle);
			button.setX(115 + ((currentLetter) * 70));
			button.setY(456 - (currentField * 70));
			button.setColor(Color.BLACK);
			field[(currentField * 5) + currentLetter] = button;
			currentLetter += 1;
			wordStage.addActor(button);
		}
				
		return currentLetter;
	}
	
	public static int[] verifyWord(int currentLetter, int currentField) {
		
		try {
		
		String userWord = field[(currentField * 5)].getText().toString() + field[(currentField * 5) + 1].getText().toString() + field[(currentField * 5) + 2].getText().toString() + field[(currentField * 5) + 3].getText().toString() + field[(currentField * 5) + 4].getText().toString();
		
		match = false;
		boolean valid = false;
		String matchedWord = null;
		
		for(String word: words) {
			if(userWord.equals(word.toLowerCase())) {
				String message = "Your word: %s is equal to the word: %s in the wordList!".formatted(userWord, word);
				//System.out.println(message);
				valid = true;
				matchedWord = word.toLowerCase();
				break;
			}};
		
		if(valid) {
			
			for(int i = 0; i < 5; i++) {
				int match = Character.compare(userWord.charAt(i), word.toLowerCase().charAt(i));
				boolean contained = word.contains(Character.toString(userWord.charAt(i)));
				if(match == 0) {
					field[(currentField * 5) + i].setColor(Color.GREEN);
					for(TextButton button: keyboardList) {
						if(button.getText().equals(field[(currentField * 5) + i].getText())) {
							button.setColor(Color.GREEN);
						}
					}
					}
								
				else if(contained) {
					field[(currentField * 5) + i].setColor(Color.YELLOW);
					for(TextButton button: keyboardList) {
						if(button.getText().equals(field[(currentField * 5) + i].getText())) {
							button.setColor(Color.YELLOW);
				}}}

				else if(!(contained)){
					field[(currentField * 5) + i].setColor(Color.GRAY);
					for(TextButton button: keyboardList) {
						if(button.getText().equals(field[(currentField * 5) + i].getText())) {
							button.setColor(Color.GRAY);
						}
				}
				}
		
			}
			
			match = userWord.equals(word);
			
			String updatedText = "";
			
			if(match) {
				updatedText = "You won!";
			}
			

			
			currentLetter = 0;
			currentField += 1;
			
			//System.out.println(currentField);
			
			if(currentField > 5) {
				updatedText = "You lost :( --> '%s'".formatted(word);
			}
			
			TextButton updatedButton = keyboardList.get(keyboardList.size() - 1);
			updatedButton.setText(updatedText);
			keyboardList.set(keyboardList.size() - 1, updatedButton);
		}
		
		if (!(valid)) {
			//System.out.println("Invalid word");
			String updatedText = "Invalid Word: %s".formatted(userWord);
			TextButton updatedButton = keyboardList.get(keyboardList.size() - 1);
			updatedButton.setText(updatedText);
			keyboardList.set(keyboardList.size() - 1, updatedButton);
		}
		
		} catch(Exception e) {}
		

		
				
		int[] currentArr = {currentLetter, currentField};
		
		return currentArr;
	}
	
	public static int removeLetter(int currentLetter, int currentField) {
		//System.out.println(currentLetter);
		if(currentLetter > 0 & currentLetter <= 5) {
			//System.out.println("Deleting Letter");
			field[(currentField * 5) + currentLetter-1] = null; // Clear the letter in the field behind the current (empty one)
			currentLetter -= 1;
		}
		return currentLetter;
	}
	
	@Override
	public void create () {
		keyboardStage = new Stage();
		wordStage = new Stage();
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(keyboardStage);
		buttonFont = new BitmapFont();
		skin = new Skin();
		buttonAtlas = new TextureAtlas();
		skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.overFontColor = Color.GRAY;
		//textButtonStyle.font = font;
		textButtonStyle.fontColor = Color.BLACK;
		
		wordTextButtonStyle = new TextButtonStyle();
		wordTextButtonStyle.overFontColor = Color.WHITE;
		//textButtonStyle.font = font;
		wordTextButtonStyle.fontColor = Color.WHITE;	
		
		
		//FONT
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arial.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
		font = generator.generateFont(parameter);
		font.setColor(Color.BLACK);
		generator.dispose();
		
		textButtonStyle.font = font;
		wordTextButtonStyle.font = font;
		
		sr = new ShapeRenderer();
		
		// Reads all valid words from "words.txt" and adds them to the words array
		
	try {
		File file = new File("words.txt");
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String data = reader.nextLine();
			words.add(data);}
		reader.close();
	}
	catch (FileNotFoundException e){
		e.printStackTrace();
	};
	
	// Chooses random word from the list for the game
	
	Random rand = new Random();
	int upperBound = words.size();
	word = words.get(rand.nextInt(upperBound));
	
	words.clear();
	
	try {
		File file = new File("accepted-words.txt");
		Scanner reader = new Scanner(file);
		while(reader.hasNextLine()) {
			String data = reader.nextLine();
			words.add(data);}
		reader.close();
	}
	catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
	// Initialises alphabet for the keyboard
	
	String[] alphabet = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l","del", "z", "x", "c", "v", "b", "n", "m", "enter"};
	
	// For each string in the alphabet array, a button is created for the keyboard. ClickListeners are added too.
	
	for(String letter: alphabet) {
	
	if(letter == "a") {
		letterX = 35;
		letterY -= 70;
	}
	else if(letter == "del") {
		letterX = 0;
		letterY -= 70;
	};
		letterX += 70;
		
	button = new TextButton(letter, textButtonStyle);
	button.setX(letterX + keyboardOffset);
	button.setY(letterY);
	button.setColor(Color.WHITE);
	button.addListener(new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			if(letter == "del") {
				currentLetter = removeLetter(currentLetter, currentField);
			}
			else if(letter == "enter") {
				int[] currentArr = verifyWord(currentLetter, currentField);
				currentLetter = currentArr[0];
				currentField = currentArr[1];
			}
			else {
				currentLetter = addLetter(letter, currentLetter, currentField);
			}
		}
		
	});
	
	keyboardList.add(button);
	keyboardStage.addActor(button);
	
	}
		
	TextButton selectedWord = new TextButton(word, textButtonStyle);
	selectedWord.setX(150);
	selectedWord.setY(600);
	
	TextButton messageTextButton = new TextButton(messageText, textButtonStyle);
	messageTextButton.setX(750);
	messageTextButton.setY(450);
		
	//keyboardList.add(selectedWord);
	keyboardList.add(messageTextButton);
	//keyboardStage.addActor(selectedWord);
	keyboardStage.addActor(messageTextButton);
	};
	
	@Override
	public void render () {
				
		// Clears the screen/ Initialises variables
		ScreenUtils.clear(Color.WHITE);
		
		wordStage.clear(); // Clears the word boxes
				
		for(TextButton button: field) {
			if(!(button == null)) {
				wordStage.addActor(button); // Populates the word boxes
			}}
		
		int letterX = 100;
		int letterY = 450;
		
		// Creates the 6 game boxes
		
		for(int j = 0; j < 6; j++) {
			for(int i = 0; i < 5; i++) {
				sr.begin(ShapeType.Filled);
				if(field[(j*5) + i] != null) {
					sr.setColor(field[(j*5) + i].getColor());
				}
				else {
					sr.setColor(Color.BLACK);
				}
				sr.rect(letterX, letterY, 50, 50);
				sr.end();
				letterX += 70;
			};
			letterX = 100;
			letterY -= 70;
		};
		
		wordStage.draw(); // Draws the words over the boxes
		
		// Draws the keyboard in the keyboardStage
				
		

		for(TextButton button: keyboardList) {
			String text = (button.getText()).toString();
			if(!(text.equals("enter") | text.equals("del"))){
				float width = button.getWidth() + 26;
				float height = button.getHeight() + 4;
				float x = button.getX() - 13;
				float y = button.getY() -4;
				sr.begin(ShapeType.Filled);
				sr.setColor(button.getColor());
				sr.rect(x, y, width, height);
				sr.end();
			}
		
			keyboardStage.draw(); // Draws the keyboard buttons
}}};