package com.wow.modele;

import java.awt.Rectangle;

import com.wow.m.Globals.Difficulty;

public abstract class Ennemy extends Sprite {
	protected int life;
	protected int exp;
	protected int category;
	protected int cycle;
	protected boolean hit;
	protected int actualSprite;
	protected long beforeTime;
	protected int money;

	public Ennemy(Difficulty d) {
		super();
		this.cycle = 0;
		this.hit = false;

	}

	public void hit(int attack) {
		this.beforeTime = System.currentTimeMillis();
		
		this.life -= attack;
		this.hit = true;
		setTimeout(() -> unhit(this.beforeTime), 300);
		if (this.life <= 0) {
			this.die();
		}
	}
	
	public int getMoney(){
		return this.money;
	}

	public void unhit(long beforeTime){
		if(System.currentTimeMillis()-this.beforeTime>290){
			this.hit = false;
		}
	}
	
	public Rectangle getBounds() {
		//System.out.println(BOUNDS_CORRECTION);
		return new Rectangle((int) this.x+BOUNDS_CORRECTION, (int) this.y+BOUNDS_CORRECTION, this.width-2*BOUNDS_CORRECTION, this.height-2*BOUNDS_CORRECTION);
	}
	
	public void handleSprite() {

	}

	public int getExp() {
		return this.exp;
	}

	public void move(int a, int b) {
		this.x += this.dx;
		this.y += this.dy;
	}

	public int getCategory() {
		return this.category;
	}

	public int getLife() {
		return this.life;
	}

}
