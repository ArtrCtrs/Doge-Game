package com.wow.m;

public interface Globals {
	
	public static final int PLAYER_HEALTH = 3;
	public static final int BEGINNING_ROUND = 1;
	public static final int BEGINNING_MONEY = 0;
	
	public static final int BOARD_WIDTH = 1300;      // Sean=1000 -- normal=1300
	public static final int BOARD_HEIGHT = (int) (BOARD_WIDTH * 3 / 4);
	

	public static final int DELAY = 1000 / 60;
	
	public static final double PLAYER_RATIO = (double) BOARD_WIDTH / 600;  //500
	public static final double MISSILE_RATIO = (double) BOARD_WIDTH / 4000;
	public static final double ENNEMIES_RATIO = (double) BOARD_WIDTH / 600;  //500

	public static final double BOSS_RATIO = (double) BOARD_WIDTH / 130; //112
	public static final double EXPLOSION_RATIO = (double) BOARD_WIDTH / 720;
	public static final double BOSSMISSILE_RATIO = (double) BOARD_WIDTH / 666;
	public static final double BOSSPROJECTILE_RATIO = (double) BOARD_WIDTH / 350;
	public static final double MENU_RATIO = (double) BOARD_WIDTH / 900;
	public static final double SPEED_RATIO = (double) BOARD_WIDTH / 1300; //1000
	
	public static final int BOUNDS_CORRECTION=BOARD_WIDTH/180;
	
	public static final int MISSILE_WIDTH=24;

	public static final int GREEN_LIFE_EASY = 4;
	public static final int GREEN_LIFE_MEDIUM = 5;
	public static final int GREEN_LIFE_HARD = 7;
	public static final int RED_LIFE_EASY = 5;
	public static final int RED_LIFE_MEDIUM =6;
	public static final int RED_LIFE_HARD = 8;
	public static final int BLUE_LIFE_EASY = 8;
	public static final int BLUE_LIFE_MEDIUM = 9;
	public static final int BLUE_LIFE_HARD = 12;
	public static final int GREY_LIFE_EASY = 20;
	public static final int GREY_LIFE_MEDIUM = 22;
	public static final int GREY_LIFE_HARD = 31;
	
	public static final int BOSS_LIFE_EASY = 320;
	public static final int BOSS_LIFE_MEDIUM = 420;
	public static final int BOSS_LIFE_HARD = 720;
	
	public enum Difficulty{
		EASY,MEDIUM,HARD
	};
}
