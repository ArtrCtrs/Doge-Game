package com.wow.modele;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Sprite {

	private final double START_Y = BOARD_HEIGHT/2;
	private final double START_X = BOARD_WIDTH / 2;
	private final double QUICK_D =  (7 * SPEED_RATIO);
	private final double SLOW_D =  (4 * SPEED_RATIO);
	private boolean leftPressed, rightPressed, upPressed, downPressed;

	private int score;
	private int money;
	private boolean attacking;
	private int life;
	private boolean vulnerable;

	private int missileDelay;
	private int missileType;

	public Player() {

		super();

		this.source = "com/wow/images/player2.png";

		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = START_X;
		this.y = START_Y;
		this.score = 0;
		this.money = BEGINNING_MONEY;
		this.attacking = false;
		this.life = PLAYER_HEALTH;
		this.vulnerable = true;

		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites = 2;
		this.width = (int) (this.sourceWidth / this.sprites * PLAYER_RATIO);
		this.height = (int) (this.sourceHeight * PLAYER_RATIO);
		this.sourceX = 0;
		this.sourceY = 0;

		this.missileDelay = 180;
		this.missileType = 1;
		System.out.println(QUICK_D+" - - "+SLOW_D);

	}

	public int getMoney() {
		return this.money;
	}

	public void addMoney(int m) {
		this.money += m;
	}

	public void buy(int m) {
		this.money -= m;
	}

	public int getScore() {
		return this.score;
	}

	public void addToScore(int s) {
		this.score += s;
	}

	public int getMissileType() {
		return this.missileType;
	}

	public void setMissileType(int missileType) {
		this.missileType = missileType;
	}

	public int getMissileDelay() {
		return this.missileDelay;
	}

	public void setMissileDelay(int missileDelay) {
		this.missileDelay = missileDelay;
	}

	public boolean getAttacking() {
		return this.attacking;
	}

	public int getLife() {
		return this.life;
	}

	public boolean getVulnerable() {
		return this.vulnerable;
	}

	public void hit() {
		this.life--;
		this.vulnerable = false;
		this.sourceX = this.sourceWidth / this.sprites;

		setTimeout(() -> handleRecover(), 2000);
		if (this.life <= 0) {
			this.die();
		}
	}

	private void handleRecover() {
		this.vulnerable = true;
		this.sourceX = 0;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		// System.out.println(key);

		if (key == KeyEvent.VK_LEFT) {

			leftPressed = true;
		}

		if (key == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}

		if (key == KeyEvent.VK_UP) {
			upPressed = true;
		}

		if (key == KeyEvent.VK_DOWN) {
			downPressed = true;
		}

		if (key == KeyEvent.VK_CONTROL) {
			this.attacking = true;
		}
		handlePressed();
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}

		if (key == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}

		if (key == KeyEvent.VK_UP) {
			upPressed = false;
		}

		if (key == KeyEvent.VK_DOWN) {
			downPressed = false;
		}

		if (key == KeyEvent.VK_CONTROL) {
			this.attacking = false;
		}
		handlePressed();
	}

	public void handlePressed() {

		if (rightPressed && this.x < BOARD_WIDTH && upPressed && this.y > 0) {
			this.dx = SLOW_D;
			this.dy = -SLOW_D;
		} else if (rightPressed && this.x < BOARD_WIDTH && downPressed && this.y < BOARD_HEIGHT) {
			this.dx = SLOW_D;
			this.dy = SLOW_D;
		} else if (leftPressed && this.x > 0 && upPressed && this.y > 0) {
			this.dx = -SLOW_D;
			this.dy = -SLOW_D;
		} else if (leftPressed && this.x > 0 && downPressed && this.y < BOARD_HEIGHT) {
			this.dx = -SLOW_D;
			this.dy = SLOW_D;
		} else if (rightPressed && this.x < BOARD_WIDTH) {
			this.dx = QUICK_D;
			this.dy = 0;
		} else if (leftPressed && this.x > 0) {
			this.dx = -QUICK_D;
			this.dy = 0;
		} else if (upPressed && this.y > 0) {
			this.dx = 0;
			this.dy = -QUICK_D;
		} else if (downPressed && this.y < BOARD_HEIGHT) {
			this.dx = 0;
			this.dy = QUICK_D;
		} else {
			this.dx = 0;
			this.dy = 0;
		}

	}

}
