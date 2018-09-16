/*
 * Assignment: Final Project - Bullet Hell
 * Name: Daniel Kim and Dan Diaconescu
 * Date: January 18, 2017
 * Teacher: Ms. Strelkovska
 * Course Code: ICS4U
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import javax.swing.*;

//Main class 
public class Game extends JFrame implements ActionListener {

	//Variable Declaration
	static Container c;
	JPanel p1;
	JButton exit;
	JButton start, instruct;
	public static JLabel prev, scores, lives;
	static GamePanel gp;
	public static int score, life; 
	//Previous Score and lines in score.txt
	String line, prevsc;
	
	//Constructor
	public Game(){

		//Variable Initialization 
		c = getContentPane();
		p1 = new JPanel();
		//size of JPanel 1
		p1.setSize(400, 800);
		//Grid Layout for 1 column with 4 rows 
		p1.setLayout(new GridLayout(4, 1));
		

		//initialize a game panel 
		gp = new GamePanel();
		//exit button 
		exit = new JButton("EXIT");
		exit.setFont(new Font("TimesRoman", Font.PLAIN, 16));
		exit.setForeground(Color.BLACK);

		//Score 
		score = 0;
		//player begins with 5 lives 
		life = 5;
		//Labels showing the score and lives of the player 
		scores = new JLabel("SCORE: " + Game.score);
		scores.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		scores.setForeground(Color.BLUE);
		lives = new JLabel("LIVES: " + Game.life);
		lives.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		lives.setForeground(Color.RED);

		//size of the game 
		setSize(1200, 800);

		//Container border layout 
		c.setLayout(new BorderLayout());
		c.add(p1, BorderLayout.EAST);
		c.add(gp, BorderLayout.CENTER);
		exit.addKeyListener(gp);
		exit.addActionListener(this);

		p1.setBackground(Color.WHITE);
		gp.setBackground(Color.BLACK);
		
		//Add JLabels and buttons to panel 
		p1.add(scores);
		p1.add(lives);
		p1.add(exit);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			//Used to find most recent score of player from txt file 
			BufferedReader input;
			//reads file score.txt
			input = new BufferedReader(new FileReader("score.txt"));
			//Finds the last line of input which is most recent score
			while ((line = input.readLine()) != null)
			prevsc = line;
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Previous Score label is updated using most recent score from score.txt file 
		prev = new JLabel("<html> Previous <br><html/>" + prevsc);
		prev.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		prev.setForeground(Color.GRAY);
		
		//Add JLabels and buttons to panel 
		p1.add(prev);
		p1.add(scores);
		p1.add(lives);
		p1.add(exit);

		setVisible(true);
		
		//Window Listener 
		addWindowListener(new WindowListener() {
			
		//default methods (unused) 
		public void windowOpened(WindowEvent e) {
		}

		public void windowIconified(WindowEvent e) {
		}
			
		public void windowDeiconified(WindowEvent e) {
		}
		
		public void windowDeactivated(WindowEvent e) {
		}
		
		//saves the score before closing into score.txt
		public void windowClosing(WindowEvent e) {
			saveBeforeClose();
		}
		//Exits when window closes 
		public void windowClosed(WindowEvent e) {
			System.out.println("Window is closed....");
			System.exit(0);
		}
		
		public void windowActivated(WindowEvent e) {
		}
		});

	}

	//Method for adding functions to buttons 
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		String act = b.getActionCommand();

		//When exit button is pressed, application closes 
		if (act.equals("EXIT")) {
			saveBeforeClose(); 
			System.exit(0);
		}
	}
	
	//Saves the score of the game before closing 
	public static void saveBeforeClose() {
		PrintWriter writer = null;
		File file= null;
		FileWriter fr = null;
		BufferedWriter br = null;
		
		//try and catch block 
		try {
			file = new File("score.txt");
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			writer = new PrintWriter(br);
			//adds the score to the score.txt file 
			writer.append("Score: " + Game.score+"\n");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			//when br is not null, closes 
			if (br != null) {
				try {
					br.close();
				} catch (IOException e3) {
					e3.printStackTrace();
				}
			}
			//when fr is not null, closes 
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e4) {
					e4.printStackTrace();
				}
			}
			//When PrintWriter is not null, closes 
			if (writer != null) {
				writer.close();
			}
		}
	}

	//Main method 
	public static void main(String[] args) throws FileNotFoundException{
		//Creates object instance of the game
		Game g = new Game();
		//Allows the game to appear visible 
		g.setVisible(true);
		
	}
}
