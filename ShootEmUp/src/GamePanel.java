/*
 * Assignment: Final Project - Bullet Hell
 * Name: Daniel Kim and Dan Diaconescu
 * Date: January 18, 2017
 * Teacher: Ms. Strelkovska
 * Course Code: ICS4U
 */

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

//Game Panel class 
public class GamePanel extends JPanel implements KeyListener, ActionListener {

	//Variable Declaration 
	int wave;
	int ticks;
	int code;
	
	Player pl1 = new Player(550, 600);
	Enemy en1 = new Enemy(550, 0, 1);
	Enemy en2 = new Enemy(350, -80, 1);
	Enemy en3 = new Enemy(750, -80, 1);
	//ArrayList of enemies 
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static Timer myTimer;
	
	//constructor
	public GamePanel() {
		//calls init method to set players to enemies and add enemies to arraylist 
		init();
		
		this.addKeyListener(this);
		
		wave = 0;
		ticks = 0;
		//Timer 
		myTimer = new Timer(40, this);
		myTimer.start();

		//Enemies move down in beginning
		en1.down = true;
		en2.down = true;
		en3.down = true;
	}

	private void init() {
		//ArrayList is cleared, Player 1 is set to each enemy 
		enemies.clear();
		en1.setPlayer(pl1);
		en2.setPlayer(pl1);
		en3.setPlayer(pl1);
		//enemies added to ArrayList
		enemies.add(en1);
		enemies.add(en2);
		enemies.add(en3);
		pl1.setEnemies(enemies);
	}

	//Method to move 3 different types of enemies 
	public void actionPerformed(ActionEvent e) {

		//Player 1 can move 
		pl1.move();
		ticks++;
		if (ticks % 100000 == 0) // if int overloads
			ticks = 0;
		if (ticks % 300 == 0) {
			if (wave == 3)
				wave = 0;
			wave++;
			//If player survives one wave of enemies, rewarded 1 point
			Game.score+=1;
			//During wave 1 of enemies, 3 enemies created 
			if (wave == 1) {
				en1 = new Enemy(550, 0, wave);
				en2 = new Enemy(350, -80, wave);
				en3 = new Enemy(750, -80, wave);
				//Will move down 
				en1.down = true;
				en2.down = true;
				en3.down = true;
				//Enemies reset for next wave 
				init();
				//Type 2 enemies during wave 2 
			} else if (wave == 2) {
				en1 = new Enemy(1100, 40, wave);
				en2 = new Enemy(1160, 40, wave);
				en3 = new Enemy(1220, 40, wave);
				init();
				//Type 3 enemies during wave 3 
			} else if (wave == 3) {
				en1 = new Enemy(-100, 40, wave);
				en2 = new Enemy(-160, 40, wave);
				en3 = new Enemy(-220, 40, wave);
				init();
			}
		}
		//All enemies in ArrayList move as called by move method  
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).move();
		}
		//Game scores and player lives are updated 
		Game.scores.setText("SCORE: " + Game.score);
		Game.lives.setText("LIVES: " + Game.life);
		repaint();
	}

	public void keyTyped(KeyEvent e) {
	}

	//Keys are pressed for boolean values to become true, allowing player to move 
	public void keyPressed(KeyEvent e) {

		code = e.getKeyCode();
		 
		// W
		if (code == 87) {
			pl1.up = true;
		}
		// A
		else if (code == 65) {
			pl1.left = true;
		}
		// S
		else if (code == 83) {
			pl1.down = true;
		}
		// D
		else if (code == 68) {
			pl1.right = true;
		// V is used to fire bullets 
		} else if (code == KeyEvent.VK_V && Game.life > 0)
			pl1.fire();
	}

	// When the key is released, player stops moving 
	public void keyReleased(KeyEvent e) {

		code = e.getKeyCode();
		// W
		if (code == 87) {
			pl1.up = false;
		}
		// A
		else if (code == 65) {
			pl1.left = false;
		}
		// S
		else if (code == 83) {
			pl1.down = false;
		}
		// D
		else if (code == 68) {
			pl1.right = false;
		}
	}

	//Player and enemies are drawn 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		pl1.draw(g);
		en1.draw(g);
		en2.draw(g);
		en3.draw(g);
	}

}
