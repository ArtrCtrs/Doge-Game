package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Green1 extends Ennemy {
	public Green1(Difficulty d) {
		super(d);

		this.source = "com/wow/images/greenDogeF.png";

		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = Math.random() * BOARD_WIDTH * 0.8 + BOARD_WIDTH * 0.1;
		this.dy = 2 * SPEED_RATIO;
		this.dx = (Math.random() * 1.5 - 0.75) * SPEED_RATIO;

		switch (d) {
		case EASY:
			this.life = GREEN_LIFE_EASY;
			this.exp = 10;
			break;
		case MEDIUM:
			this.life = GREEN_LIFE_MEDIUM;
			this.exp = 12;
			break;
		case HARD:
			this.life = GREEN_LIFE_HARD;
			this.exp = 15;
			break;
		}

		
		this.money = 1;
		this.category = 1;

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 3;
		this.width = (int) (this.sourceWidth / this.sprites * ENNEMIES_RATIO);
		this.height = (int) (this.sourceHeight * ENNEMIES_RATIO);
		this.sourceX = 0;
		this.sourceY = 0;

		this.y = -this.height;
	}

	public void handleSprite() {
		if (this.dying) {
			this.sourceX = 1 * this.sourceWidth / this.sprites;
		} else if (this.hit) {
			this.sourceX = 2 * this.sourceWidth / this.sprites;
		} else {
			this.sourceX = 0 * this.sourceWidth / this.sprites;

		}
	}

	public void die() {
		this.dying = true;
		this.dx = 0;
		this.dy = 0;
		setTimeout(() -> this.visible = false, 1000);

	}
}
