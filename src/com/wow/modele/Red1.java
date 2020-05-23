package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.wow.m.Globals.Difficulty;

public class Red1 extends Ennemy {
	private double valueZig;

	public Red1(Difficulty d) {
		super(d);
		this.source = "com/wow/images/redDogeF2.png";
		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = Math.random() * BOARD_WIDTH * 0.9 + BOARD_WIDTH * 0.05;
		this.dy = (Math.floor(Math.random() * 2) + 3 )*SPEED_RATIO;
		this.dx = 0;// Math.random()-0.5;
		switch (d) {
		case EASY:
			this.life = RED_LIFE_EASY;
			this.exp = 20;
			break;
		case MEDIUM:
			this.life = RED_LIFE_MEDIUM;
			this.exp = 24;
			break;
		case HARD:
			this.life = RED_LIFE_HARD;
			this.exp = 30;
			break;
		}
		this.money=2;
		this.category = 2;
		this.valueZig = Math.floor(Math.random() * 2) == 0 ? 0.1*SPEED_RATIO : -0.1*SPEED_RATIO;

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 5;
		this.width = (int) (this.sourceWidth / this.sprites * ENNEMIES_RATIO);
		this.height = (int) (this.sourceHeight * ENNEMIES_RATIO);
		this.sourceX = 0;
		this.sourceY = 0;
		
		this.y = -this.height;
	}

	public void die() {
		this.dying = true;
		this.dx = 0;
		this.dy = 0;
		this.valueZig = 0;
		//this.sourceX = 3 * this.sourceWidth / this.sprites;
		setTimeout(() -> this.visible = false, 1000);

	}
	
	public void handleSprite(){
		if(this.dying){
			this.sourceX = 3 * this.sourceWidth / this.sprites;
		}else if(this.hit){
			this.sourceX = 4 * this.sourceWidth / this.sprites;
		}else{
			this.cycle++;
			if (this.cycle >= 20) {
				this.cycle = 0;
				this.actualSprite=this.actualSprite>=2?0:this.actualSprite+1;
				this.sourceX = (int) this.actualSprite * this.sourceWidth / this.sprites;
			}
		}
	}

	public void move() {
		

		this.dx += valueZig;
		if (this.dx > 5*SPEED_RATIO || this.dx < -5*SPEED_RATIO) {
			valueZig = -valueZig;
		}
		super.move();

	}

}
