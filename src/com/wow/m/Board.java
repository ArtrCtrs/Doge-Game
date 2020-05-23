package com.wow.m;

import com.wow.modele.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Board extends JPanel implements Globals, KeyListener {

	private enum Modes {
		MENU, RUNNING, PAUSED, OFF, GAMEOVER, SHOP
	};

	private Modes actualMode = Modes.OFF;
	public ClassLoader cl = this.getClass().getClassLoader();
	private BufferedImage bgImage, fgImage1;
	private int fgposY, fgposYbis;
	private Menusprite menu;

	private ArrayList<Ennemy> ennemies;
	private ArrayList<Projectile> missiles;
	private ArrayList<Projectile> missilesTemp;
	private ArrayList<Projectile> missilesBoss;
	private ArrayList<Explosion1> explosions;
	private Missiletype[] shop;
	private Roundproperties[] roundProperties;
	private String shopMessage;
	private Player player;
	private boolean missilesON;
	private boolean bossBattle1;
	private Firstboss boss1;
	private int boss1Phase;
	private double distance;
	private Font calibri20, doge20, calibri50, doge50, doge15, doge18, pixel20;
	private Color orange, green, red;

	private boolean missileSounds;

	private int round;

	private Difficulty actualdifficulty;

	private int roundCount, ennemiesCount, boss1Count;
	private int boss1Cycle, allbosslife;

	private Media bossS, dogS, pewS, pewS2, explosionS, danceS, normalS, orchestraS, shootingStarsS;
	private MediaPlayer bossMusic, mainMusic, dogMusic, pewSound, pewSound2, explosionSound;
	private int chosenMusic;

	private Thread missilesT;

	public Board() {
		// initBoard();
	}

	public void initBoard() {
		

		addKeyListener(this);
		setFocusable(true);
		setDoubleBuffered(true);

		try {

			bgImage = ImageIO.read(cl.getResource("com/wow/images/space2.png"));

			fgImage1 = ImageIO.read(cl.getResource("com/wow/images/fg2F.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		initSound();

		GraphicsEnvironment gez = null;
		try {
			gez = GraphicsEnvironment.getLocalGraphicsEnvironment();
			gez.registerFont(Font.createFont(Font.TRUETYPE_FONT, cl.getResourceAsStream("com/wow/images/font1.TTF")));
			gez.registerFont(Font.createFont(Font.TRUETYPE_FONT, cl.getResourceAsStream("com/wow/images/font2.TTF")));
		} catch (FontFormatException e) {
		} catch (IOException e) {
		}

		doge15 = new Font("Pixelade", Font.PLAIN, (int) (20 * SPEED_RATIO));
		doge18 = new Font("Pixelade", Font.PLAIN, (int) (30 * SPEED_RATIO));
		doge20 = new Font("Pixelade", Font.PLAIN, (int) (40 * SPEED_RATIO));
		doge50 = new Font("04b", Font.PLAIN, (int) (50 * SPEED_RATIO));
		orange = new Color(252, 150, 30);
		green = new Color(119, 255, 109);
		red = new Color(255, 52, 52);

		calibri20 = new Font("Calibri", Font.PLAIN, (int) (20 * SPEED_RATIO));
		calibri50 = new Font("Calibri", Font.PLAIN, (int) (50 * SPEED_RATIO));

		// this.repaint();
		menu = new Menusprite();
		actualMode = Modes.MENU;
		actualdifficulty = Difficulty.EASY;
		chosenMusic = 1;

		missileSounds = false;

		allbosslife = BOSS_LIFE_EASY;
		roundProperties = new Roundproperties[22];
		roundProperties[0] = new Roundproperties(4, 0, 0, 0, 550, 275);
		roundProperties[1] = new Roundproperties(5, 0, 0, 0, 550, 275);
		roundProperties[2] = new Roundproperties(7, 0, 0, 0, 550, 275);
		roundProperties[3] = new Roundproperties(7, 1, 0, 0, 550, 275);
		roundProperties[4] = new Roundproperties(0, 6, 0, 0, 550, 275);
		roundProperties[5] = new Roundproperties(5, 4, 0, 0, 550, 275);
		roundProperties[6] = new Roundproperties(4, 0, 1, 0, 550, 275);
		roundProperties[7] = new Roundproperties(10, 0, 1, 0, 550, 275);
		roundProperties[8] = new Roundproperties(2, 0, 0, 0, 550, 25);
		roundProperties[9] = new Roundproperties(0, 10, 2, 0, 550, 275);

		roundProperties[10] = new Roundproperties(0, 0, 0, 0, 550, 275);
		roundProperties[11] = new Roundproperties(0, 0, 0, 0, 550, 275);
		roundProperties[12] = new Roundproperties(0, 0, 0, 0, 550, 275);

		roundProperties[13] = new Roundproperties(5, 0, 0, 0, 550, 275);
		roundProperties[14] = new Roundproperties(0, 5, 0, 1, 550, 275);
		roundProperties[15] = new Roundproperties(0, 2, 0, 0, 550, 25);
		roundProperties[16] = new Roundproperties(0, 1, 5, 0, 550, 275);
		roundProperties[17] = new Roundproperties(0, 10, 1, 1, 550, 275);
		roundProperties[18] = new Roundproperties(10, 0, 5, 0, 550, 275);
		roundProperties[19] = new Roundproperties(0, 10, 5, 0, 550, 275);
		roundProperties[20] = new Roundproperties(5, 5, 5, 0, 550, 275);
		roundProperties[21] = new Roundproperties(5, 5, 10, 5, 550, 275);
		// gameInit();
		runGame();
	}

	public void initSound() {
		final JFXPanel fxPanel = new JFXPanel();

		pewS = new Media(cl.getResource("com/wow/sounds/pew.mp3").toString());
		explosionS = new Media(cl.getResource("com/wow/sounds/explosion.mp3").toString());

		bossS = new Media(cl.getResource("com/wow/sounds/boss.mp3").toString());
		dogS = new Media(cl.getResource("com/wow/sounds/dog.mp3").toString());

		danceS = new Media(cl.getResource("com/wow/sounds/dance.mp3").toString());
		normalS = new Media(cl.getResource("com/wow/sounds/main.mp3").toString());
		orchestraS = new Media(cl.getResource("com/wow/sounds/orchestra.mp3").toString());
		shootingStarsS = new Media(cl.getResource("com/wow/sounds/shootingstars.mp3").toString());

		bossMusic = new MediaPlayer(bossS);
		dogMusic = new MediaPlayer(dogS);
		pewSound = new MediaPlayer(pewS);
		pewSound2 = new MediaPlayer(pewS);
		explosionSound = new MediaPlayer(explosionS);

		mainMusic = new MediaPlayer(danceS);

		bossMusic.setCycleCount(20);
		mainMusic.setCycleCount(20);
		dogMusic.setCycleCount(20);
		
		switchSong(mainMusic);

	}

	public void gameInit() {
		switchSong(mainMusic);
		Sopln("run");
		player = new Player();
		ennemies = new ArrayList<>();
		missiles = new ArrayList<>();
		missilesTemp = new ArrayList<>();
		missilesBoss = new ArrayList<>();
		explosions = new ArrayList<>();
		boss1 = null;
		fgposY = 1000;
		fgposYbis = 1200;
		round = BEGINNING_ROUND;
		roundCount = 0;
		ennemiesCount = 0;
		boss1Count = 0;
		boss1Phase = 0;
		boss1Cycle = 200;
		missilesON = false;
		bossBattle1 = false;

		shop = new Missiletype[8];
		shop[0] = new Missiletype(1, "Basic Missile", true, 0, true, 190); // 200
		shop[1] = new Missiletype(2, "Curved Missile", false, 12, false, 155); // 160
		shop[2] = new Missiletype(3, "Machine Gun V1", false, 12, false, 140);// 140
		shop[3] = new Missiletype(4, "Double Missile", false, 0, false, 190);// 200
		shop[4] = new Missiletype(5, "Machine Gun V2", false, 0, false, 80);// 80
		shop[5] = new Missiletype(6, "Explosive Missile", false, 0, false, 1450);// 1500
		shop[6] = new Missiletype(7, "Triple Missile", false, 0, false, 190);// 200
		shop[7] = new Missiletype(8, "Big Boom", false, 1000000, false, 190);// 200
		shopMessage = "";

	}

	public void paintComponent(Graphics g) {

		g.setColor(Color.white);
		// fgposY = fgposY < 0 ? 1000 : fgposY - 2;
		// fgposYbis = fgposYbis < 0 ? 1000 : fgposYbis - 1;

		g.drawImage(bgImage, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, null, this);
		g.drawImage(fgImage1, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, 0, fgposY, BOARD_WIDTH, BOARD_HEIGHT + fgposY, null,
				this);
		g.drawImage(fgImage1, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, 0, fgposYbis, BOARD_WIDTH, BOARD_HEIGHT + fgposYbis,
				null, this);

		switch (actualMode) {
		case MENU:
			drawStart(g);
			fgposY = fgposY < 0 ? 1024 : fgposY - 2;
			fgposYbis = fgposYbis < 0 ? 1024 : fgposYbis - 1;
			drawDifficulty(g);
			break;
		case RUNNING:
			fgposY = fgposY < 0 ? 1024 : fgposY - 2;
			fgposYbis = fgposYbis < 0 ? 1024 : fgposYbis - 1;
			drawEnnemies(g);
			if (bossBattle1) {
				drawBoss(g);
			}

			drawMissiles(g);
			drawPlayer(g);
			drawExplosions(g);
			drawScore(g);

			break;
		case GAMEOVER:
			fgposY = fgposY < 0 ? 1024 : fgposY - 2;
			fgposYbis = fgposYbis < 0 ? 1024 : fgposYbis - 1;

			drawEnnemies(g);
			if (bossBattle1) {
				drawBoss(g);
			}
			drawMissiles(g);
			drawGameOver(g);
			break;
		case SHOP:
			drawShop(g);

			break;

		default:
		}

	}

	public void drawExplosions(Graphics g) {
		if (explosions.size() > 0) {

			for (int i = 0; i < explosions.size(); i++) {
				if (explosions.get(i).getVisible()) {

					g.drawImage(explosions.get(i).getImage(), (int) explosions.get(i).getX(),
							(int) explosions.get(i).getY(),
							(int) explosions.get(i).getX() + explosions.get(i).getWidth(),
							(int) explosions.get(i).getY() + explosions.get(i).getHeight(),
							(int) explosions.get(i).getSourceX(), (int) explosions.get(i).getSourceY(),
							(int) (explosions.get(i).getSourceX()
									+ explosions.get(i).getSourceWidth() / explosions.get(i).getSprites()),
							(int) (explosions.get(i).getSourceY() + explosions.get(i).getSourceHeight()), null, this);
				}
			}
		}
	}

	public void drawShop(Graphics g) {
		g.setFont(doge18);
		g.drawString("Shift to close SHOP", (int) (BOARD_WIDTH - 200 * SPEED_RATIO), 23);
		g.setFont(doge20);
		g.drawString(shopMessage, (int) (BOARD_WIDTH / 20), (int) (BOARD_HEIGHT / 7 * SPEED_RATIO));
		g.drawString("Current money : " + player.getMoney() + "$", (int) (BOARD_WIDTH / 15),
				(int) (BOARD_HEIGHT / 7 * SPEED_RATIO + 40));

		g.setFont(doge18);

		for (int i = 0; i < shop.length; i++) {
			String unlok = shop[i].getUnlocked() ? "UNLOCKED" : "LOCKED";
			String equip = shop[i].getEquiped() ? "EQUIPED" : "NOT EQUIPED";

			if (equip == "EQUIPED") {
				g.setColor(green);
			} else if (unlok == "UNLOCKED") {
				g.setColor(Color.white);
			} else {
				g.setColor(red);
			}

			g.drawString((i + 1) + ") " + shop[i].getName(), 5, BOARD_HEIGHT / 3 + 35 * i);

			g.drawString("-  " + unlok, (int) (BOARD_WIDTH * 3 / 10), BOARD_HEIGHT / 3 + 35 * i);

			g.drawString("- " + equip, (int) (BOARD_WIDTH * 5 / 10), BOARD_HEIGHT / 3 + 35 * i);

			g.drawString("- Price : " + shop[i].getPrice() + "$", (int) (BOARD_WIDTH * 3 / 4),
					BOARD_HEIGHT / 3 + 35 * i);

		}
	}

	public void drawDifficulty(Graphics g) {
		g.setColor(orange);
		g.setFont(doge20);
		g.drawString("Press SPACE to change difficulty : " + actualdifficulty.toString(), 20, 50);

		String a = missileSounds ? "ON" : "OFF";

		g.drawString("Press X to activate (awful) missile sounds : " + a, 20, 100);
		g.drawString("Press A to change song : " + chosenMusic, 20, 150);
	}

	public void drawStart(Graphics g) {
		g.drawImage(menu.getImage(), (int) menu.getX(), (int) menu.getY(), (int) menu.getX() + menu.getWidth(),
				(int) menu.getY() + menu.getHeight(), (int) menu.getSourceX(), (int) menu.getSourceY(),
				(int) (menu.getSourceX() + menu.getSourceWidth() / menu.getSprites()),
				(int) (menu.getSourceY() + menu.getSourceHeight()), null, this);

		/*
		 * g.setFont(new Font("Calibri", Font.PLAIN, 50));
		 * g.drawString("Welcome to wow.wow", BOARD_WIDTH / 3, BOARD_HEIGHT /
		 * 3); g.drawString("Arrow keys to move", BOARD_WIDTH / 3, BOARD_HEIGHT
		 * / 3 + 80); g.drawString("Left Ctrl to shoot", BOARD_WIDTH / 3,
		 * BOARD_HEIGHT / 3 + 160); g.drawString("S to Start/Restart",
		 * BOARD_WIDTH / 3, BOARD_HEIGHT / 3 + 240);
		 * g.drawString("Left Shift to enter/exit Shop", BOARD_WIDTH / 3,
		 * BOARD_HEIGHT / 3 + 320);
		 */

	}

	public void drawGameOver(Graphics g) {
		g.setFont(doge50);

		g.drawString("GAME OVER", BOARD_WIDTH / 3, BOARD_HEIGHT * 30 / 100);
		g.drawString("Final Score : " + Integer.toString(player.getScore()), BOARD_WIDTH * 23 / 100,
				BOARD_HEIGHT * 30 / 100 + 80);
		g.drawString("Press S to restart", BOARD_WIDTH * 14 / 100, BOARD_HEIGHT * 30 / 100 + 160);
		g.drawString("Press M to return to Menu", BOARD_WIDTH * 10 / 100, BOARD_HEIGHT * 30 / 100 + 240);
	}

	public void drawEnnemies(Graphics g) {
		for (int i = 0; i < ennemies.size(); i++) {
			if (ennemies.get(i).getVisible()) {

				g.drawImage(ennemies.get(i).getImage(), (int) ennemies.get(i).getX(), (int) ennemies.get(i).getY(),
						(int) ennemies.get(i).getX() + ennemies.get(i).getWidth(),
						(int) ennemies.get(i).getY() + ennemies.get(i).getHeight(), (int) ennemies.get(i).getSourceX(),
						(int) ennemies.get(i).getSourceY(),
						(int) (ennemies.get(i).getSourceX()
								+ ennemies.get(i).getSourceWidth() / ennemies.get(i).getSprites()),
						(int) (ennemies.get(i).getSourceY() + ennemies.get(i).getSourceHeight()), null, this);

				// g.drawRect((int) ennemies.get(i).getX()+BOUNDS_CORRECTION,
				// (int) ennemies.get(i).getY()+BOUNDS_CORRECTION,
				// ennemies.get(i).getWidth()-2*BOUNDS_CORRECTION,
				// ennemies.get(i).getHeight()-2*BOUNDS_CORRECTION);
			}
		}

	}

	public void drawPlayer(Graphics g) {
		g.drawImage(player.getImage(), (int) player.getX(), (int) player.getY(),
				(int) player.getX() + player.getWidth(), (int) player.getY() + player.getHeight(),
				(int) player.getSourceX(), (int) player.getSourceY(),
				(int) (player.getSourceX() + player.getSourceWidth() / player.getSprites()),
				(int) (player.getSourceY() + player.getSourceHeight()), null, this);

		// g.drawRect((int)player.getX(), (int)player.getY(),
		// (int)player.getWidth(), (int)player.getHeight());

	}

	public void drawMissiles(Graphics g) {
		for (int i = 0; i < missiles.size(); i++) {
			if (missiles.get(i).getVisible()) {

				g.drawImage(missiles.get(i).getImage(), (int) missiles.get(i).getX(), (int) missiles.get(i).getY(),
						(int) missiles.get(i).getX() + missiles.get(i).getWidth(),
						(int) missiles.get(i).getY() + missiles.get(i).getHeight(), (int) missiles.get(i).getSourceX(),
						(int) missiles.get(i).getSourceY(),
						(int) (missiles.get(i).getSourceX()
								+ missiles.get(i).getSourceWidth() / missiles.get(i).getSprites()),
						(int) (missiles.get(i).getSourceY() + missiles.get(i).getSourceHeight()), null, this);
			}
		}

	}

	public void drawScore(Graphics g) {
		g.setFont(doge18);
		g.drawString("Score : " + Integer.toString(player.getScore()), 10, BOARD_HEIGHT - 100);
		g.drawString("Money : " + Integer.toString(player.getMoney()) + "$", 10, BOARD_HEIGHT - 70);
		g.drawString("Life : " + Integer.toString(player.getLife()), 10, BOARD_HEIGHT - 40);

		g.drawString("Shift to open SHOP", (int) (BOARD_WIDTH - 200 * SPEED_RATIO), 23);
	}

	public void drawBoss(Graphics g) {

		for (int i = 0; i < missilesBoss.size(); i++) {
			if (missilesBoss.get(i).getVisible()) {

				g.drawImage(missilesBoss.get(i).getImage(), (int) missilesBoss.get(i).getX(),
						(int) missilesBoss.get(i).getY(),
						(int) missilesBoss.get(i).getX() + missilesBoss.get(i).getWidth(),
						(int) missilesBoss.get(i).getY() + missilesBoss.get(i).getHeight(),
						(int) missilesBoss.get(i).getSourceX(), (int) missilesBoss.get(i).getSourceY(),
						(int) (missilesBoss.get(i).getSourceX()
								+ missilesBoss.get(i).getSourceWidth() / missilesBoss.get(i).getSprites()),
						(int) (missilesBoss.get(i).getSourceY() + missilesBoss.get(i).getSourceHeight()), null, this);
			}
		}

		g.drawImage(boss1.getImage(), (int) boss1.getX(), (int) boss1.getY(), (int) boss1.getX() + boss1.getWidth(),
				(int) boss1.getY() + boss1.getHeight(), (int) boss1.getSourceX(), (int) boss1.getSourceY(),
				(int) (boss1.getSourceX() + boss1.getSourceWidth() / boss1.getSprites()),
				(int) (boss1.getSourceY() + boss1.getSourceHeight()), null, this);

		// g.drawRect((int)boss1.getX(), (int)boss1.getY(),
		// (int)boss1.getWidth(), (int)boss1.getHeight());

		g.setFont(doge18);

		g.drawString("BossLife : " + Integer.toString(boss1.getLife() * 100 / allbosslife) + "%", BOARD_WIDTH / 2, 30);
	}

	public void changeSongMenu() {
		chosenMusic++;
		if (chosenMusic > 4) {
			chosenMusic = 1;
		}
		mainMusic.stop();
		mainMusic = null;
		System.out.println(chosenMusic);
		switch (chosenMusic) {

		case 1:
			mainMusic = new MediaPlayer(danceS);
			break;
		case 2:
			mainMusic = new MediaPlayer(normalS);
			break;
		case 3:
			mainMusic = new MediaPlayer(shootingStarsS);
			break;
		case 4:
			mainMusic = new MediaPlayer(orchestraS);
			break;
		}
		mainMusic.setCycleCount(20);
		System.out.println(mainMusic);

		switchSong(mainMusic);
	}

	public void switchSong(MediaPlayer a) {

		mainMusic.stop();
		dogMusic.stop();
		bossMusic.stop();

		setTimeout(() -> a.play(), 200);

	}

	public void animationCycle() {
		switch (actualMode) {
		case MENU:
			break;
		case RUNNING:
		case GAMEOVER:

			if ((player.getX() < 0 && player.getDx() < 0)
					|| (player.getX() > BOARD_WIDTH - player.getWidth() && player.getDx() > 0)) {
				player.setDx(0);
			}
			if ((player.getY() < 0 && player.getDy() < 0)
					|| (player.getY() > BOARD_HEIGHT - player.getHeight() && player.getDy() > 0)) {
				player.setDy(0);
			}
			player.move();

			if (missilesTemp.size() > 0) {
				for (int i = 0; i < missilesTemp.size(); i++) {
					missiles.add(missilesTemp.get(i));
				}
				missilesTemp.clear();
			}

			handleRounds();
			newEnnemies();
			if (bossBattle1) {
				handleFirstBoss();
			}

			for (Iterator<Ennemy> iterator = ennemies.iterator(); iterator.hasNext();) {
				Ennemy en = iterator.next();
				en.handleSprite();
				if (!en.getDying()) {
					if (en.getCategory() != 3) {
						en.move();
					} else {
						en.move((int) player.getX(), (int) player.getY());
					}

					if (en.getY() > BOARD_WIDTH + 100) {
						en.setVisible(false);
					}
					if (!en.getVisible()) {
						iterator.remove();
					}
				}
			}

			for (Iterator<Projectile> iterator = missiles.iterator(); iterator.hasNext();) {
				Projectile m = iterator.next();
				m.move();
				if (m.getY() < -50) {
					m.setVisible(false);
				}
				if (!m.getVisible()) {
					iterator.remove();
				}
			}
			if (explosions.size() > 0) {
				for (Iterator<Explosion1> iterator = explosions.iterator(); iterator.hasNext();) {
					Explosion1 e = iterator.next();
					e.move();
					if (!e.getVisible()) {
						iterator.remove();
					}
				}
			}

			checkCollisions();

			break;
		default:
		}

	}

	public void checkCollisions() {

		for (Projectile m : missiles) { // ennemies-missiles
			for (Ennemy en : ennemies) {
				if (!m.getDying() && !en.getDying() && m.getBounds().intersects(en.getBounds())) {
					m.die();
					en.hit(m.getDamage()); 

					if (m.getCategory() == 2) { // explosionmissile
						Explosion1 explo = new Explosion1((int) m.getX(), (int) m.getY());
						explo.die();
						for (Ennemy aen : ennemies) {
							if (!aen.getDying() && aen.getBounds().intersects(explo.getBounds())) {
								aen.hit(explo.getDamage());
							}
						}
						explosions.add(explo);
						if (missileSounds) {
							explosionSound.stop();
							explosionSound.play();
						}

					}
					if (en.getLife() <= 0) {

						player.addToScore(en.getExp());
						player.addMoney((int) (en.getExp() / 10));

					}
				}
			}
		}
		for (Ennemy en : ennemies) { // player-ennemies
			if (en.getBounds().intersects(player.getBounds()) && player.getVulnerable() && !en.getDying()) {
				playerHit();
			}
		}
		// bossbattle

		if (bossBattle1) {
			for (Projectile bs : missilesBoss) { // player-missilesboss
				if (bs.getBounds().intersects(player.getBounds()) && player.getVulnerable()) {
					playerHit();
				}
			}
			// boss-player
			if (!boss1.getDying() && boss1.getBounds().intersects(player.getBounds()) && player.getVulnerable()) {
				playerHit();
			}

			for (Projectile m : missiles) { // missiles - boss
				if (!m.getDying()) {
					if (!boss1.getDying() && m.getBounds().intersects(boss1.getBounds())) {
						m.die();
						boss1.hit(m.getDamage());

						if (m.getCategory() == 2) { // explosionmissile
							Explosion1 explo = new Explosion1((int) m.getX(), (int) m.getY());
							explo.die();

							if (boss1.getBounds().intersects(explo.getBounds())) {
								boss1.hit(explo.getDamage());
							}
							explosions.add(explo);
						}

						if (boss1.getLife() <= 0) {
							boss1.setPhase(-1);
							player.addToScore(boss1.getExp());
							player.addMoney((int) (boss1.getExp() / 10));
							round++;
							switchSong(mainMusic);
							// bossbattle = false;
						}
					}
				}
			}
		}
	}

	public void playerHit() {
		player.hit();
		if (player.getLife() == 0) {
			actualMode = Modes.GAMEOVER;
			switchSong(dogMusic);

		}
	}

	public void handleFirstBoss() {
		if (!boss1.getDying()) {
			boss1.move((int) player.getX(), (int) player.getY());
		}

		switch (boss1.getPhase()) {
		case 0:
			if (boss1.getDist() < 20) {
				boss1Phase++;
				boss1Count = 0;
				boss1.setValuezigX(Math.random() / 10 + 0.25);
				boss1.setValuezigY(Math.random() / 10 + 0.25);
				boss1.setPhase(boss1Phase);

			}
			break;

		case 1:
			boss1Count++;
			if (boss1Count == 250) {
				boss1.setDestX((BOARD_WIDTH - boss1.getWidth()) / 2);
				boss1.setDestY((BOARD_HEIGHT - boss1.getHeight()) / 9);
				boss1.setPhase(0);
			}

			break;

		case 2:
			boss1Count++;
			if (boss1Count == 200) {
				boss1Count = 0;
				boss1Phase++;
				boss1.setPhase(boss1Phase);

			}
			break;

		case 3:
			boss1Count++;
			if (boss1Count % 30 == 0) {
				missilesBoss.add(boss1.launchMissile(
						(int) boss1.getX() + (int) (boss1.getWidth() - MISSILE_WIDTH * BOSSMISSILE_RATIO) / 2,
						(int) boss1.getY() + boss1.getHeight() / 2, 0, 10));
			}

			if (boss1Count == 270) {
				boss1Count = 0;
				boss1.setDestX((BOARD_WIDTH - boss1.getWidth()) / 2);
				boss1.setDestY(-boss1.getWidth() / 3);
				boss1.setPhase(0);

			}

			break;
		case 4:
			boss1Count++;
			if (boss1Count % 39 == 0) {
				distance = (Math.sqrt(Math.pow((player.getX() - (boss1.getX() + boss1.getWidth() / 2)), 2)
						+ Math.pow((player.getY() - (boss1.getY() + boss1.getHeight() / 2)), 2)));
				missilesBoss.add(boss1.launchProjectile((int) boss1.getX() + boss1.getWidth() / 2,
						(int) boss1.getY() + boss1.getHeight() / 2,
						(player.getX() - (boss1.getX() + boss1.getWidth() / 2)) / distance * 8,
						(player.getY() - (boss1.getY() + boss1.getHeight() / 2)) / distance * 8));
			}

			if (boss1Count == 470) {
				boss1Count = 0;
				boss1.setDestX((BOARD_WIDTH - boss1.getWidth()) / 3);
				boss1.setDestY((BOARD_HEIGHT - boss1.getHeight()) / 4);
				boss1.setPhase(0);

				boss1Phase = 0;
			}
			break;
		default:
			break;
		}

		for (Iterator<Projectile> iterator = missilesBoss.iterator(); iterator.hasNext();) {
			Projectile bp = iterator.next();

			bp.move();

			if (bp.getY() > BOARD_HEIGHT || bp.getX() > BOARD_WIDTH || bp.getY() < 0 - bp.getHeight()
					|| bp.getX() < 0 - bp.getWidth()) {
				bp.setVisible(false);
			}
			if (!bp.getVisible()) {
				iterator.remove();
			}
		}

	}

	public void handleRounds() {
		roundCount++;
		if (roundCount == roundProperties[round - 1].getRoundCycle()) {
			roundCount = 0;
			ennemiesCount = 0;

			switch (round) {

			case 1:
				round++;
				break;
			case 2:
				round++;
				break;
			case 3:
				round++;
				break;
			case 4:
				round++;
				break;
			case 5:
				round++;
				break;
			case 6:
				round++;
				break;
			case 7:
				round++;
				break;
			case 8:
				round++;
				break;
			case 9:
				round++;
				break;
			case 10:
				round++;
				break;
			case 11:
				round++;
				boss1 = new Firstboss(actualdifficulty);
				bossBattle1 = true;
				if (actualMode == Modes.RUNNING) {

					switchSong(bossMusic);
				}
				// isInFight
				break;
			case 12:
				break;
			case 13:
				round++;
				bossBattle1 = false;
				break;
			case 14:
				round++;
				break;
			case 15:
				round++;
				break;
			case 16:
				round++;
				break;
			case 17:
				round++;
				break;
			case 18:
				round++;
				break;
			case 19:
				round++;
				break;
			case 20:
				round++;
				break;
			case 21:
				round++;
				break;
			default: //
			}

			Sopln("round " + round);
		}

	}

	public void newEnnemies() {

		ennemiesCount++;

		if (ennemiesCount >= roundProperties[round - 1].getEnnemiesCycle()) {
			ennemiesCount = 0;

			for (int i = 0; i < roundProperties[round - 1].getGreen(); i++) {
				Ennemy ennemy = new Green1(actualdifficulty);
				ennemies.add(ennemy);

			}
			for (int i = 0; i < roundProperties[round - 1].getRed(); i++) {
				Ennemy ennemy = new Red1(actualdifficulty);
				ennemies.add(ennemy);

			}
			for (int i = 0; i < roundProperties[round - 1].getBlue(); i++) {
				Ennemy ennemy = new Blue1(actualdifficulty);
				ennemies.add(ennemy);

			}

			for (int i = 0; i < roundProperties[round - 1].getGrey(); i++) {
				Ennemy ennemy = new Grey1(actualdifficulty);
				ennemies.add(ennemy);

			}

		}

	}

	public void runGame() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {
			this.repaint();
			animationCycle();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}

			beforeTime = System.currentTimeMillis();
		}

	}

	Runnable missilesR = new Runnable() {
		public void run() {
			while (player.getAttacking()) {
				if (missileSounds) {
					pewSound.stop();
					pewSound.play();
				}

				switch (player.getMissileType()) {
				case 1:
					Missile1 m1 = new Missile1((int) player.getX() + player.getWidth() / 2, (int) player.getY(), 0,
							-12);

					missilesTemp.add(m1);

					break;
				case 2:
					Missile2 m2 = new Missile2((int) player.getX() + player.getWidth() / 2, (int) player.getY(), 0,
							-15);

					missilesTemp.add(m2);

					break;
				case 3:
					Missile1 m3 = new Missile1((int) player.getX() + player.getWidth() / 2, (int) player.getY(),
							Math.random() * 7 - 3.5, -10);

					missilesTemp.add(m3);

					break;
				case 4:
					Missile1 m4a = new Missile1((int) player.getX() + player.getWidth() / 5, (int) player.getY(), 0,
							-12);

					Missile1 m4b = new Missile1((int) player.getX() + player.getWidth() * 4 / 5, (int) player.getY(), 0,
							-12);

					missilesTemp.add(m4a);
					missilesTemp.add(m4b);
					if (missileSounds) {
						pewSound2.stop();
						pewSound2.play();
					}

					break;

				case 5:
					Missile1 m5 = new Missile1((int) player.getX() + player.getWidth() / 2, (int) player.getY(),
							Math.random() * 5 - 2.5, -10);

					missilesTemp.add(m5);

					break;
				case 6:
					Missileexplosive m6 = new Missileexplosive((int) player.getX() + player.getWidth() / 2,
							(int) player.getY(), 0, -7);

					missilesTemp.add(m6);

					break;
				case 7:
					Missile1 m7a = new Missile1((int) player.getX() + player.getWidth() / 2, (int) player.getY(), -2,
							-12);

					Missile1 m7b = new Missile1((int) player.getX() + player.getWidth() / 2, (int) player.getY(), 0,
							-12);

					Missile1 m7c = new Missile1((int) player.getX() + player.getWidth() / 2, (int) player.getY(), 2,
							-12);

					missilesTemp.add(m7a);
					missilesTemp.add(m7b);
					missilesTemp.add(m7c);

					if (missileSounds) {
						pewSound2.stop();
						pewSound2.play();
					}

					break;

				}

				try {
					Thread.sleep((long) (player.getMissileDelay() * SPEED_RATIO));
				} catch (InterruptedException e) {
					System.out.println("interrupted");
				}
			}
			missilesON = false;
		}
	};

	public void purchase(int n) {

		if (shop[n - 1].getUnlocked()) {
			player.setMissileType(n);
			player.setMissileDelay(shop[n - 1].getMissileDelay());
			for (int i = 0; i < shop.length; i++) {
				shop[i].setEquiped(false);
			}
			shop[n - 1].setEquiped(true);
			shopMessage = shop[n - 1].getName() + " equiped!";
		} else if (shop[n - 1].getPrice() <= player.getMoney()) {
			player.buy(shop[n - 1].getPrice());
			shop[n - 1].unlock();

			player.setMissileType(n);
			player.setMissileDelay(shop[n - 1].getMissileDelay());
			for (int i = 0; i < shop.length; i++) {
				shop[i].setEquiped(false);
			}
			shop[n - 1].setEquiped(true);
			shopMessage = shop[n - 1].getName() + " bought!";

		} else {
			shopMessage = "You don't have enought money to buy *" + shop[n - 1].getName() + "*";
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (actualMode) {
		case MENU:
			if (key == KeyEvent.VK_S) {
				System.out.println("restart");
				gameInit();

				actualMode = Modes.RUNNING;
			} else if (key == KeyEvent.VK_SPACE) {
				switch (actualdifficulty) {
				case EASY:
					actualdifficulty=Difficulty.MEDIUM;
					allbosslife=BOSS_LIFE_MEDIUM;
					break;
				case MEDIUM:
					actualdifficulty=Difficulty.HARD;
					allbosslife=BOSS_LIFE_HARD;
					break;
				case HARD:
					actualdifficulty=Difficulty.EASY;
					allbosslife=BOSS_LIFE_EASY;
					break;
				}
		
			} else if (key == KeyEvent.VK_X) {
				missileSounds = !missileSounds;
			} else if (key == KeyEvent.VK_A) {
				changeSongMenu();
			}
			break;
		case RUNNING:
			if (key == KeyEvent.VK_S) {
				System.out.println("restart");
				gameInit();
			} else if (key == KeyEvent.VK_SHIFT) {
				actualMode = Modes.SHOP;
				shopMessage = "Welcome to the shop! Press the corresponding number to buy/equip";
			} else if (key == KeyEvent.VK_M) {
				System.out.println("backToMenu");
				// gameInit();
				actualMode = Modes.MENU;
			} else {

				player.keyPressed(e);
				if (player.getAttacking() && !missilesON) {
					missilesT = new Thread(missilesR, "missilesT");
					missilesON = true;
					missilesT.start();
				}
			}

			break;
		case GAMEOVER:
			if (key == KeyEvent.VK_S) {
				System.out.println("restart");
				gameInit();
				actualMode = Modes.RUNNING;
			} else if (key == KeyEvent.VK_M) {
				System.out.println("backToMenu");
				// gameInit();
				actualMode = Modes.MENU;
			}

			break;
		case SHOP:
			if (key == KeyEvent.VK_SHIFT) {
				actualMode = Modes.RUNNING;
			} else if (key == KeyEvent.VK_1) {
				purchase(1);
			} else if (key == KeyEvent.VK_2) {
				purchase(2);
			} else if (key == KeyEvent.VK_3) {
				purchase(3);
			} else if (key == KeyEvent.VK_4) {
				purchase(4);
			} else if (key == KeyEvent.VK_5) {
				purchase(5);
			} else if (key == KeyEvent.VK_6) {
				purchase(6);
			} else if (key == KeyEvent.VK_7) {
				purchase(7);
			} else if (key == KeyEvent.VK_8) {
				purchase(8);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (actualMode) {
		case MENU:
			player.keyReleased(e);
			break;
		case RUNNING:
			player.keyReleased(e);

			break;
		case SHOP: {
			player.keyReleased(e);
		}
		case GAMEOVER:
			player.keyReleased(e);
			break;
		default:
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void Sopln(String s) {
		System.out.println(s);
	}

	public void Sopln(int s) {
		System.out.println(s);
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
}
