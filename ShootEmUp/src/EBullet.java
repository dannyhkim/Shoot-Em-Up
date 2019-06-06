import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

//Class for enemy bullets 
public class EBullet extends Rectangle{

	//Variables - angle and player 
	private double angle = 0;
	private Player player;
	
	//Constructor 
	public EBullet(String bulletTexturePath, int x, int y, double angle){
		this.angle = angle;
		this.x = x;
		this.y = y;
		//Width and height bullets (small) 
		width = 4;
		height = 4;
		
	}
	
	//method to help create movement and path of bullets 
	//deltaX and deltaY are vector components 
	 public void move(int deltaX, int deltaY){

		 //When x is greater than 0, subtract difference from x coordinate 
		 if (x > 0) {
			 this.x -= (angle*deltaY);
		 }
		 //when y is greater than 0, subtract from y-coordinate 
		 if (y > 0) {
			 this.y -= deltaY;
		 }
	 }
	 
	//Drawing and filling the bullets white 
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		
		//If playerHit == true, calls gotHit method to update life count and scores 
		if (playerHit()) {
			player.gotHit();
			
		gotHit();
			
		}
	}
	//Method that sets player 
	public void setPlayer(Player player) {
		this.player = player;
	}
	//Collision for enemy bullets with player 
	public void gotHit() {
		
		if(this.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight()))
			decreaseSize();
	}
	//erases bullet from screen 
	private void decreaseSize() {
		
		this.grow(-1, -1);
		while (this.getWidth() > 0 && this.getHeight() > 0) {
			//recursion until the size of bullet is 0
			decreaseSize();
		}
	}
	
	//boolean method to determine whether player was hit by bullet or not 
	private boolean playerHit() {
		//Checks if bullet intersects the player by finding x, y coordinates to find location of player, and uses width and height to check if it hits any part of player
		return this.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight());
	}
}
