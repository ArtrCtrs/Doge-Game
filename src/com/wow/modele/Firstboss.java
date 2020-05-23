package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.wow.m.Globals.Difficulty;

public class Firstboss extends Ennemy{
	private int phase;
	private double valuezigX;
	private double valuezigY;
	private int destX;
	private int destY;
	private double dist;

	public Firstboss(Difficulty d) {

		super(d);

		this.source = "com/wow/images/boss1.png";

		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 3;
		this.width = (int) (this.sourceWidth / this.sprites * BOSS_RATIO);
		this.height = (int) (this.sourceHeight * BOSS_RATIO);
		this.sourceX = 0;
		this.sourceY = 0;
		this.phase = 0;

		this.x = (BOARD_WIDTH / 2 - this.width);
		// this.y=(BOARD_HEIGHT - this.height) / 4;
		this.y = -this.height;
		this.dy = 3*SPEED_RATIO;
		this.dx = 0;

		this.destX = (BOARD_WIDTH / 2 - this.width);
		this.destY = (BOARD_HEIGHT - this.height) / 4;
		this.dist = 1000;
		
		switch (d) {
		case EASY:
			this.life = BOSS_LIFE_EASY;
			this.exp = 500;
			break;
		case MEDIUM:
			this.life = BOSS_LIFE_MEDIUM;
			this.exp = 600;
			break;
		case HARD:
			this.life = BOSS_LIFE_HARD;
			this.exp = 750;
			break;
		}
		this.money=50;
		
		this.category = 4;

		this.cycle = 0;
		this.valuezigX = Math.random()*SPEED_RATIO;// 0.3;
		this.valuezigY = Math.random()*SPEED_RATIO;// 0.3;

	}

	public void die() {
		this.dying = true;
		this.dx = 0;
		this.dy = 0;
		this.phase=0;
		this.destX=BOARD_WIDTH/2;
		this.destY=-500;
		this.sourceX = 2*this.sourceWidth / this.sprites;
		setTimeout(() -> this.visible = false, 2000);
	}

	public void move(int playerX, int playerY) {
		this.cycle++;

		switch (this.phase) {
		case 0:
			
			this.dist = (Math.sqrt(Math.pow((this.destX - this.x), 2) + Math.pow((this.destY - this.y), 2)));
			
			this.dx = (this.destX - this.x) / this.dist * 3*SPEED_RATIO;
			this.dy = (this.destY - this.y) / this.dist * 3*SPEED_RATIO;

			break;
		case 1:

			this.dx += this.valuezigX;
			this.dy += this.valuezigY;
			if (this.dx > 12*SPEED_RATIO || this.dx < -12*SPEED_RATIO) {
				this.valuezigX = -this.valuezigX;
			}
			if (this.dy > 12*SPEED_RATIO || this.dy < -12*SPEED_RATIO) {
				this.valuezigY = -this.valuezigY;
			}

			// System.out.println("1");
			break;
		case 2:
			this.dy = 0;
			this.sourceX=(int)(1)*this.sourceWidth/this.sprites;

			if (this.x < playerX+25 - this.width / 2 - 5) {
				this.dx = 4*SPEED_RATIO;
			} else if (this.x > playerX+25 - this.width / 2 + 5) {
				this.dx = -4*SPEED_RATIO;
			} else {
				this.dx = 0;
			}

			break;
		case 3:
			if (this.x < playerX+25 - this.width / 2 - 5) {
				this.dx = 1.5*SPEED_RATIO;
			} else if (this.x > playerX+25 - this.width / 2 + 5) {
				this.dx = -1.5*SPEED_RATIO;
			} else {
				this.dx = 0;
			}
			break;

		case 4:
			this.sourceX=0;
			this.dx += this.valuezigX;
			this.dy += valuezigY;
			if (this.dx > 6*SPEED_RATIO || this.dx < -6*SPEED_RATIO) {
				this.valuezigX = -this.valuezigX;
			}
			if (this.dy > 6*SPEED_RATIO || this.dy < -6*SPEED_RATIO) {
				this.valuezigY = -this.valuezigY;
			}
			break;
		}
		
		this.x += this.dx;
		this.y += this.dy;

	}

	public double getDist() {
		return this.dist;
	}

	public int getPhase() {
		return this.phase;
	}

	public int getDestX() {
		return this.destX;
	}

	public void setDestX(int destX) {
		this.destX = destX;

	}

	public int getDestY() {
		return this.destY;
	}

	public void setDestY(int destY) {
		this.destY = destY;

	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	public void incrementPhase() {
		this.phase++;
	}

	public void setValuezigX(double valuezigX) {
		this.valuezigX = valuezigX;
	}

	public void setValuezigY(double valuezigY) {
		this.valuezigY = valuezigY;
	}

	public Bossmissile launchMissile(int x, int y, double dx, double dy) {
		Bossmissile m = new Bossmissile(x, y, dx, dy);
		return m;
	}

	public Bossprojectile launchProjectile(int x, int y, double dx, double dy) {
		Bossprojectile m = new Bossprojectile(x, y, dx, dy);
		return m;
	}

}
