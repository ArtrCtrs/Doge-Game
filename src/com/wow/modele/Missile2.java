package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Missile2 extends Projectile {
	private int startX;
	private double counter;

	public Missile2(int xpos, int ypos, double dx, double dy) {
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
		this.width = (int) (this.sourceWidth / this.sprites * MISSILE_RATIO);
		this.height = (int) (this.sourceHeight * MISSILE_RATIO);

		this.startX = (int) this.x;
		this.counter = 0;

		this.damage = 1;
		this.category = 1;

	}

	public void move() {
		this.counter++;
		this.x = this.startX + 30*SPEED_RATIO * Math.sin(this.counter / 5);
		super.move();
	}

}
