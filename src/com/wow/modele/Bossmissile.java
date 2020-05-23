package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Bossmissile extends Projectile {
	public Bossmissile(int xpos, int ypos, double dx, double dy) {
		super(xpos, ypos, dx, dy);
		this.source = "com/wow/images/missile.png";

		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 1;
		this.width = (int)(this.sourceWidth / this.sprites*BOSSMISSILE_RATIO);
		this.height = (int)(this.sourceHeight * BOSSMISSILE_RATIO);
	}

}
