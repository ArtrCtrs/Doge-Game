package com.wow.modele;

public abstract class Projectile extends Sprite {
	protected int damage;
	protected int category;
	public Projectile(int xpos, int ypos, double dx, double dy) {
		super();

		this.x = xpos;
		this.y = ypos;
		this.dy = dy*SPEED_RATIO;
		this.dx = dx*SPEED_RATIO;
		
		this.sourceX=0;
		this.sourceY=0;
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public int getCategory(){
		return this.category;
	}

}
