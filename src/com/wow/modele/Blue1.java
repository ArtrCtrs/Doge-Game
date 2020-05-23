package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.wow.m.Globals.Difficulty;

public class Blue1 extends Ennemy {
	private double distance;

	public Blue1(Difficulty d) {
		super(d);
		this.source = "com/wow/images/blueDogeF.png";
		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.distance=0;
		this.x = Math.floor(Math.random() * 2) == 0 ? -50 : BOARD_WIDTH + 20;
		this.y = Math.floor(Math.random() * BOARD_HEIGHT / 2);
		this.dy = 1*SPEED_RATIO;
		this.dx = 0;// Math.random()-0.5;
		
		switch (d) {
		case EASY:
			this.life = BLUE_LIFE_EASY;
			this.exp = 30;
			break;
		case MEDIUM:
			this.life = BLUE_LIFE_MEDIUM;
			this.exp = 36;
			break;
		case HARD:
			this.life = BLUE_LIFE_HARD;
			this.exp = 45;
			break;
		}
		this.money=3;
		
		this.category = 3;

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 3;
		this.width = (int) (this.sourceWidth / this.sprites * ENNEMIES_RATIO);
		this.height = (int) (this.sourceHeight * ENNEMIES_RATIO);
		this.sourceX = 0;
		this.sourceY = 0;
	}

	public void die() {
		this.dying = true;
		this.dx = 0;
		this.dy = 0;
		setTimeout(() -> this.visible = false, 1000);

	}
	
	public void handleSprite(){
		if(this.dying){
			this.sourceX = 1 * this.sourceWidth / this.sprites;
		}else if(this.hit){
			this.sourceX = 2 * this.sourceWidth / this.sprites;
		}else{
			this.sourceX = 0 * this.sourceWidth / this.sprites;
	
		}
	}
	
	public void move(int playerX,int playerY){
		this.distance = (Math.sqrt(Math.pow((playerX - this.x), 2) + Math.pow((playerY - this.y), 2)));
		this.dx = (playerX - this.x) / this.distance * 1.8*SPEED_RATIO;
		this.dy = (playerY - this.y) / this.distance * 1.8*SPEED_RATIO;
		super.move();
		
	}


}
