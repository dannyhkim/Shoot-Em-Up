/*
 * Assignment: Final Project - Bullet Hell
 * Name: Daniel Kim and Dan Diaconescu
 * Date: January 18, 2017
 * Teacher: Ms. Strelkovska
 * Course Code: ICS4U
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

//Player class for Bullet Hell Game 
//Player is a rectangle 
class Player extends Rectangle implements ActionListener {

	//Variable Declaration 
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	List<PBullet> bullets;
	Timer shootingTimer;
	int life;
	int score;
	List<Enemy> enemies;

	//Constructor to make player
	public Player(int x, int y) {
		
		//Initializing variables 
		up = false;
		down = false;
		left = false;
		right = false;
		
		this.x = x;
		this.y = y;
		width = 30;
		height = 30;
		life = 5;
		score = 0;
		shootingTimer = new Timer(40, this);
		shootingTimer.start();
	}

	//Draws the player 
	public void draw(Graphics g) {
		//If bullets arraylist is empty, draw bullets for enemy 
		if (bullets != null) {
			for (EBullet bullet : bullets) {
				bullet.draw(g);
			}
		}
		//White rectangle player 
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);

	}

	//Movement for player 
	public void move() {

		//Move up by decreasing y (closer to top of screen) 
		if (up && y > 0) {
			y -= 10;
		}
		//Move right by increasing x
		if (right && x < 1090) {
			x += 10;
		}
		//Move to left by decreasing x
		if (left && x > 0) {
			x -= 10;
		}
		//Move down by increasing value of y 
		if (down && y < 800) {
			y += 10;
		}
		
		gameOver();

	}

	//Method for player to fire bullets 
	public void fire() {
		//Object instance 
		PBullet pB1 = new PBullet("./bin/aaa/Bullets/bullet.png", x + width / 2, y, 0);
		//Setting enemies for player bullets 
		pB1.setEnemies(enemies);
		//If no bullets in array, initialize arraylist for player bullets
		if (bullets == null) {
			bullets = new ArrayList<PBullet>();
		}
		//add object instance 
		bullets.add(pB1);
	}

	
	public void actionPerformed(ActionEvent e) {
		//Method called 
		moveBullets();
	}

	//Method to update variables when player is hit by bullet 
	public void gotHit() {
		//loses one life if hit 
		Game.life--;
		//If player loses all lives, he is erased from screen
		if (Game.life == 0) {
			decreaseSize();
		}
	}
	
	
	//Game is over once life == 0, player is dead
	public void gameOver() {
		if (Game.life == 0) {
			Game.lives = new JLabel("LIVES: " + 0);
			Game.lives.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			Game.lives.setForeground(Color.RED);
			//GAME OVER - If player loses 5 lives, window shows up indicating game is over. Then program exits.
			JOptionPane.showMessageDialog( null, "GAME OVER. YOU LOST ALL YOUR LIVES. YOUR SCORE IS " + Game.score + "! YOU WILL BE EXITING NOW.", null, JOptionPane.INFORMATION_MESSAGE);
			Game.gp.removeAll();
			Game.saveBeforeClose();
			System.exit(0);
		}
	}
	
	//Erases player by decreasing size so area of rect is 0
	private void decreaseSize() {
		this.grow(-1, -1);
		while (this.getWidth() > 0 && this.getHeight() > 0) {
			//recursion 
			decreaseSize();
		}
	}

	//Method for movement of bullets
	private void moveBullets() {
		if (bullets == null) {
			return;
		}
		//loops through all player bullets 
		for (PBullet bullet : bullets) {
			//moves bullets 
			bullet.move(20);
		}
	}
	
	//Sets enemies for player 
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

}
