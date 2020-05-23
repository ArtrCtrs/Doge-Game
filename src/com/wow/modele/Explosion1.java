package com.wow.modele;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Explosion1 extends Sprite {
	private int damage;
	private int cycle;
	private int actualSprite;

	public Explosion1(int x, int y) {
		super();

		this.source = "com/wow/images/explosionF.png";

		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 3;
		this.width = (int) (this.sourceWidth / this.sprites * EXPLOSION_RATIO);
		this.height = (int) (this.sourceHeight * EXPLOSION_RATIO);
		this.sourceX = 0;
		this.sourceY = 0;

		this.x = x - this.width/2;
		this.y = y - this.height/2;
		this.cycle=0;
		this.actualSprite=0;

		this.damage = 10;
	}
	public void move(){
		this.cycle++;
		if (this.cycle >= 6) {
			this.cycle = 0;
			actualSprite=actualSprite+1>=sprites?actualSprite:actualSprite+1;
		}

		this.sourceX =  actualSprite * this.sourceWidth / this.sprites;
	}

	public void die() {
		this.dying = true;
		setTimeout(() -> this.visible = false, 500);

	}
	
	public Rectangle getBounds() {
		//System.out.println(BOUNDS_CORRECTION);
		return new Rectangle((int) this.x+BOUNDS_CORRECTION, (int) this.y+BOUNDS_CORRECTION, this.width-2*BOUNDS_CORRECTION, this.height-2*BOUNDS_CORRECTION);
	}

	public int getDamage() {
		return this.damage;
	}

}
