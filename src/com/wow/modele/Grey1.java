package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.wow.m.Globals.Difficulty;

public class Grey1 extends Ennemy {
	private double distance;

	public Grey1(Difficulty d) {
		super(d);
		this.source = "com/wow/images/greyDogeF.png";
		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.distance=0;
		this.x = Math.random() * BOARD_WIDTH * 0.9 + BOARD_WIDTH * 0.05;
		this.dy = 1*SPEED_RATIO;
		this.dx = 0;// Math.random()-0.5;
		switch (d) {
		case EASY:
			this.life = GREY_LIFE_EASY;
			this.exp = 50;
			break;
		case MEDIUM:
			this.life = GREY_LIFE_MEDIUM;
			this.exp = 60;
			break;
		case HARD:
			this.life = GREY_LIFE_HARD;
			this.exp = 75;
			break;
		}
		this.money=5;
		this.category = 3;

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 3;
		this.width = (int) (this.sourceWidth / this.sprites * ENNEMIES_RATIO*2);
		this.height = (int) (this.sourceHeight * ENNEMIES_RATIO*2);
		this.sourceX = 0;
		this.sourceY = 0;
		
		this.y = -this.height;
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
		this.dx = (playerX - this.x) / this.distance * 0.6*SPEED_RATIO;
		this.dy = (playerY - this.y) / this.distance * 0.6*SPEED_RATIO;
		super.move();
		
	}

}
