package com.gdx.Tetris.utils;

import com.badlogic.gdx.Gdx;

public class Info {

	private Info() {}
	
	public static final int[] I = {
			1, 1, 1, 1,
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0};

	public static final int[][] I0 = {
			{1, 1, 1, 1,
		 	0, 0, 0, 0,
		 	0, 0, 0, 0,
		 	0, 0, 0, 0},

			{1, 0, 0, 0,
			 1, 0, 0, 0,
			 1, 0, 0, 0,
			 1, 0, 0, 0}
	};


	public static final int[] J = {
			0, 0, 0, 0,
			1, 0, 0, 0,
			1, 1, 1, 0,
			0, 0, 0, 0};

	public static final int[][] J0 = {
			{1, 0, 0, 0,
			 1, 1, 1, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{1, 1, 0, 0,
			 1, 0, 0, 0,
			 1, 0, 0, 0,
			 0, 0, 0, 0},

			{1, 1, 1, 0,
			 0, 0, 1, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{0, 1, 0, 0,
			 0, 1, 0, 0,
			 1, 1, 0, 0,
			 0, 0, 0, 0}
	};

	public static final int[] L = {
			0, 0, 0, 0,
			0, 0, 0, 1,
			0, 1, 1, 1,
			0, 0, 0, 0};

	public static final int[][] L0 = {
			{0, 0, 1, 0,
			 1, 1, 1, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{1, 0, 0, 0,
			 1, 0, 0, 0,
			 1, 1, 0, 0,
			 0, 0, 0, 0},

			{1, 1, 1, 0,
			 1, 0, 0, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{1, 1, 0, 0,
			 0, 1, 0, 0,
			 0, 1, 0, 0,
			 0, 0, 0, 0}
	};

	public static final int[] O = {
			0, 0, 0, 0,
			0, 1, 1, 0,
			0, 1, 1, 0,
			0, 0, 0, 0};

	public static final int[][] O0 = {
			{1, 1, 0, 0,
			 1, 1, 0, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0}
	};

	public static final int[] S = {
			0, 0, 0, 0,
			0, 1, 1, 0,
			1, 1, 0, 0,
			0, 0, 0, 0};

	public static final int[][] S0 = {
			{0, 1, 1, 0,
			 1, 1, 0, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{1, 0, 0, 0,
			 1, 1, 0, 0,
			 0, 1, 0, 0,
			 0, 0, 0, 0}
	};

	public static final int[] T = {
			0, 0, 0, 0,
			0, 1, 0, 0,
			1, 1, 1, 0,
			0, 0, 0, 0};

	public static final int[][] T0 = {
			{1, 1, 1, 0,
			 0, 1, 0, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{0, 1, 0, 0,
			 1, 1, 0, 0,
			 0, 1, 0, 0,
			 0, 0, 0, 0},

			{0, 1, 0, 0,
			 1, 1, 1, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{1, 0, 0, 0,
			 1, 1, 0, 0,
			 1, 0, 0, 0,
			 0, 0, 0, 0}
	};

	public static final int[] Z = {
			0, 0, 0, 0,
			1, 1, 0, 0,
			0, 1, 1, 0,
			0, 0, 0, 0};

	public static final int[][] Z0 = {
			{1, 1, 0, 0,
			 0, 1, 1, 0,
			 0, 0, 0, 0,
			 0, 0, 0, 0},

			{0, 1, 0, 0,
			 1, 1, 0, 0,
			 1, 0, 0, 0,
			 0, 0, 0, 0}
	};

	public static final String[] TetrominoList = {"I", "J", "L", "O", "S", "T", "Z"};

	public static final int GAMEBOX_X = 30;
	public static final int GAMEBOX_Y = 30;
	public static final int TILE_GAP = 1;

	public static final int WINDOW_WIDTH = Gdx.graphics.getWidth();
	public static final int columns = 10;
	public static final int rows = 20;

	public static int GAMEBOX_WIDTH = WINDOW_WIDTH - (2 * GAMEBOX_X); // Window width without the two margins either side
	public static int GAMEBOX_HEIGHT = GAMEBOX_WIDTH * 2; // Twice the width ( 10 boxes --> 20 boxes)
	public static int TILE_WIDTH = GAMEBOX_WIDTH / columns;
}
