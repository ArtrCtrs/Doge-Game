package com.wow.modele;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.wow.m.Globals;

abstract class Sprite implements Globals {
	protected boolean visible;
	protected boolean dying;
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected int height;
	protected int width;
	protected int sourceHeight;
	protected int sourceWidth;
	protected double sourceX;
	protected double sourceY;
	protected int sprites;
	protected ClassLoader cl = this.getClass().getClassLoader();
	
	protected BufferedImage img;
	protected String source;

	public Sprite() {
		this.visible = true;
		this.dying = false;

	}

	public static void setTimeout(Runnable runnable, int delay) {
		new Thread(() -> {
			try {
				Thread.sleep(delay);
				runnable.run();
			} catch (Exception e) {
				System.err.println(e);
			}
		}).start();
	}

	public void move() {
		this.x += this.dx;
		this.y += this.dy;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.width, this.height);
	}

	public void die() {
		this.dying = true;
		this.visible = false;
	}

	public boolean getVisible() {
		return this.visible;
	}

	public Image getImage() {
		return this.img;
	}
	public int getSprites(){
		return this.sprites;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
	public double getSourceX() {
		return this.sourceX;
	}

	public double getSourceY() {
		return this.sourceY;
	}
	
	public double getSourceWidth() {
		return this.sourceWidth;
	}

	public double getSourceHeight() {
		return this.sourceHeight;
	}

	public double getDx() {
		return this.dx;
	}

	public double getDy() {
		return this.dy;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean getDying() {
		return this.dying;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setSourceX(double sourcex) {
		this.sourceX = sourcex;
	}

	public void setSourceY(double sourcey) {
		this.sourceY = sourcey;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public void setDying(boolean b) {
		this.dying = b;
	}

}

