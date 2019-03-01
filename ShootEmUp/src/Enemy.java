/*
 * Assignment: Final Project - Bullet Hell
 * Name: Daniel Kim and Dan Diaconescu
 * Date: January 18, 2017
 * Teacher: Ms. Strelkovska
 * Course Code: ICS4U
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

//Enemy class - enemies are rectangle shaped 
class Enemy extends Rectangle implements ActionListener {

	//Variable Declaration 
	boolean up;
	boolean center;
	boolean down;
	//List of bullets 
	List<EBullet> bullets;
	//timer 
	Timer shootingTimer;
	//tick delays bullet shooting
	int tick;
	int counter;
	public static int HP; 
	int type;
	Player player;
	//if dontShoot == false, enemy stops shooting 
	boolean dontShoot;

	//Constructor for creating enemies 
	public Enemy(int x, int y, int type) {
		this.x = x;
		this.y = y;
		//enemy hit points
		HP = 3;
		counter = 0;
		tick = 0;
		//width and height for size of enemies 
		width = 40;
		height = 40;
		this.type = type;
		dontShoot = false;
		//arraylist for bullets 
		bullets = new ArrayList<EBullet>();
	}

	//Drawing bullets 
	public void draw(Graphics g) {
		g.fillRect(x, y, width, height);
		//If the arraylist for bullets is empty, create bullets using for loop 
		if (bullets != null) {
			for (EBullet bullet: bullets) {
				//draw bullets
				bullet.draw(g);
			}
		}
	}

	//method to move bullets across screen 
	private void moveBullets() {
		List<EBullet> removeBulets = new ArrayList<EBullet>();
		//For loop for all the bullets 
		for (EBullet bullet : bullets) {
			bullet.move(0, -10);
			//once bullet is off screen, add it to arraylist of bullets to be removed
			if (bullet.getY() >= 804) {
				removeBulets.add(bullet);
			}
		}
		//removes all bullets shot and off the screen 
		bullets.removeAll(removeBulets);
		
	}

	
	public void actionPerformed(ActionEvent e) {
		//Variables 
		double angle;
		//Finds the centre coordinate of the enemy 
		double enemyXCenter = this.getX() + (this.getWidth() / 2);
		double enemyYCenter = this.getY() + (this.getHeight() / 2);
		//Finds centre coordinate of player 
		double playerXCenter = player.getX() + (player.getWidth() / 2);
		double playerYCenter = player.getY() + (player.getHeight() / 2);
		//Calculate differences in x and y components to help find angle 
		angle = (enemyXCenter - playerXCenter) / (enemyYCenter - playerYCenter);

		//Creates an enemy bullet object 
		EBullet b1 = new EBullet("./bin/aaa/Bullets/bullet.png", x + width / 2, y + height, angle);
		b1.setPlayer(player);

		//When the enemy is not retreating upwards off the screen, add bullets to arraylist and shoot them  
		if (!up) {
			bullets.add(b1);
			moveBullets();
		}
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	//method for movement of bullets 
	public void move() {
		
		//first type of bullet movement 
		if (type == 1) {
			//If moving up 
			if (up) {
				//if timer is going on, and HP is 0, stop shooting 
				if (shootingTimer != null || HP == 0) {
					stopShooting();
				}
				y -= 10;
				//enemy is hit when y - 40. no more bullets shot 
				if (y == -40)
					up = false;
				//calls moveBullets method to move bullets on screen 
				moveBullets();
			}
			//enemy comes to center of screen 
			if (center) {
				//will start shooting 
				if (tick == 1) {
					startShooting();
				}
				if (tick < 200) {
					tick++;
					if (tick > 2) {
						moveBullets();
					}
				}
				//if not at center, won't shoot and up becomes true
				else {
					center = false;
					up = true;
				}
			}
			//enemy moving down 
			if (down) {
				//Once enemy moves to y-level of 300, it is at the center 
				if (y == 300) {
					down = false;
					center = true;
				} else {
					//if it hasn't reached 300, continues to move down 
					y += 10;
				}
			}
		//type 2 enemies 
		} else if (type == 2) {
			//if enemy is off screen, will begin to move onto screen 
			if (x > -180) {
				x -= 5;
				//starts shooting 
				if (tick == 1) {
					startShooting();
				}
				//
				if (tick < 1100) {
					tick++;
					if (tick > 2) {
						moveBullets();
					}
				}
			} 
			//type 3 enemies 
		} else if (type == 3) {
			//if enemy hasn't reached 900 x-coordinate, move it 
			if (x < 1500) {
				x += 5;
				if (tick == 1) {
					//starts shooting bullets 
					startShooting();
				}
				if (tick < 1100) {
					tick++;
					if (tick > 2) {
						moveBullets();
					}
				}
			}
		}
	}
	
	

	//method that allows enemy to start shooting bullets 
	private void startShooting() {
		if (!dontShoot) {
			shootingTimer = new Timer(500, this); 
			shootingTimer.start();
		}
	}

	//method that stops enemy from shooting bullets 
	private void stopShooting() {
		if (shootingTimer != null) {
			shootingTimer.stop(); // stop TIMER
			shootingTimer.removeActionListener(this);
		}
		dontShoot = true;
	}

	//method for when enemy is hit 
	public void gotHit() {
		
		Enemy.HP--;
		//if enemy HP is 0, player gets 5 points 
		if (HP == 0) {
			Game.score += 5;
			//enemy is gone by making its area 0
			decreaseSize();
		}
	}

	//erases enemy when its life is lost 
	private void decreaseSize() {
		this.grow(-1, -1);
		while (this.getWidth() > 0 && this.getHeight() > 0) {
			//recursion until the size of enemy is 0 
			decreaseSize();
		}
		//enemy stops shooting when it's gone 
		stopShooting();
	}
}
