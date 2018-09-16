/*
 * Assignment: Final Project - Bullet Hell
 * Name: Daniel Kim and Dan Diaconescu
 * Date: January 18, 2017
 * Teacher: Ms. Strelkovska
 * Course Code: ICS4U
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JOptionPane;

// Player Bullet class 
public class PBullet extends EBullet {
	//List of enemies 
	public List<Enemy> enemies;
	List <EBullet> bullets;

	//Constructor Use of inheritance
	public PBullet(String bulletTexturePath, int x, int y, double angle) {
		//Constructor of EBullet is called 
		super(bulletTexturePath, x, y, angle);
		bullets = new ArrayList<EBullet>();
	}

	//Bullets of Player are coloured green
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
		//Method to check if enemies are hit is called 
		enemyHit();
		gotHit();
	}

	//Enemies are set for the player to target 
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	//Bullet movement for player 
	public void move(int deltaY) {

		if (y > 0) {
			this.y -= deltaY;
		}
		else if (y <= 0) {
			//gets rid of green bullets on screen that missed enemy 
			decreaseSize();
		}
	}
	
	//Collision for if player bullets hit enemy, so that bullets don't go through enemy block 
	public void gotHit() {
			
		for(Enemy enemy : enemies) {
		//if player bullet hits enemy, enemy disappears, dies 
			if (this.intersects(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
				decreaseSize();
			}
		}
	}
		//erases bullet from screen 
		private void decreaseSize() {
			
			this.grow(-1, -1);
			while (this.getWidth() > 0 && this.getHeight() > 0) {
				//recursion until the size of bullet is 0
				decreaseSize();
			}
		}

	//Checks to see if player bullet intersects with location of any enemy 
	public void enemyHit() {
		
		//loops through all enemies 
		for(Enemy enemy : enemies) {
			//if player bullet and enemy intersect, gotHit method is called to update HP and score 
			if (this.intersects(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
				enemy.gotHit();
			}
		}
		
		for(int i = 0; i < enemies.size(); i++)
		{
			for(int j = 0; j < bullets.size(); j++)
			{	
				if(enemies.get(i).getBounds().intersects(bullets.get(j).getBounds()))
				{
					enemies.remove(j);
				}				
			}
		}
		
	}
}