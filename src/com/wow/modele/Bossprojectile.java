package com.wow.modele;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bossprojectile extends Projectile{
	public Bossprojectile(int xpos, int ypos, double dx, double dy) {
		super(xpos, ypos, dx, dy);
		this.source = "com/wow/images/greenDogeF.png";

		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		} 

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 3;
		this.width = (int) (this.sourceWidth / this.sprites * BOSSPROJECTILE_RATIO);
		this.height = (int) (this.sourceHeight * BOSSPROJECTILE_RATIO);
	}
	
	public Rectangle getBounds() {
		//System.out.println(BOUNDS_CORRECTION);
		return new Rectangle((int) this.x+BOUNDS_CORRECTION, (int) this.y+BOUNDS_CORRECTION, this.width-2*BOUNDS_CORRECTION, this.height-2*BOUNDS_CORRECTION);
	}

}
