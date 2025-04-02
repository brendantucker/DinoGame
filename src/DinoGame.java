/*
 * Dino Run (March 2023)
 * 
 * This game consists of the user character jumping over obstacles in the form of rocks.
 *  At the end, the score is output and the user is prompted to play again
 * 
 * This class contains the main method as well as getters and setters for gameOver state
 *  and total number of obstacles jumped over
 */


import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DinoGame {
	
	private static boolean gameOver = false;
	private static int rocksJumped = 0;

	public static void main(String[] args) throws IOException, InterruptedException {
		//Window width and height
		int canvasWidth = getCanvasWidth(); 
		int canvasHeight = getCanvasHeight(); 
		

		DrawingPanel panel = new DrawingPanel(canvasWidth, canvasHeight);
		Graphics g = panel.getGraphics();

		//This loops over the game and exits when the user loses and manually selects to close the game
		//The user can iterate over this as many times as needed
		while(true) {
		
		//Setup background
		drawBackdrop(g,canvasWidth, canvasHeight);
		//Create the main character object then add to panel w/ spacebar key listener to activate jump method
		Dinosaur dino = new Dinosaur();
		dino.addDino(g, 100, 600);
		panel.addKeyListener(new eventHandling(dino, g, panel));
		
		//Start rock spawning sequence
		obstacle.startRockSpawning(g);
		
		//Checks over 50ms for collisions between character and every obstacle in the obstacleArray
		while(!gameOver) {
			ArrayList<obstacle> obstacles = obstacle.getObstacleArray();
			
			for(int i=0; i<obstacles.size();i++) {
				gameOver = collisionCheck(dino, obstacles.get(i));	
			}
			Thread.sleep(30);
		}
		
		//Once game is over, outputs confirmation dialogue box with score and asks to continue playing
		System.out.println("You lose");
		String paneMessage = "You Scored: " + getRocksJumped() + ". Play Again?";
		int choice = JOptionPane.showConfirmDialog(null, paneMessage );
		if (choice ==JOptionPane.YES_OPTION) {
			setGameOver(false);
			setRocksJumped(0);
			Dinosaur.setIsJumping(false);
			panel.clear();
		} else if (choice ==JOptionPane.NO_OPTION) {
			System.exit(0);
		}
		else {
			break;
		}
		
		}
	}
	
	//Getters and setters for canvas size, gameover state, and rocksJumped
	public static int getCanvasWidth() {
		return 1600;
	}
	public static int getCanvasHeight() {
		return 900;
	}
	public static boolean getGameOver() {
		return gameOver;
	}
	public static void setGameOver(boolean bool) {
		gameOver = bool;
	}
	public static void setRocksJumped(int num) {
		rocksJumped = num;
	}
	public static int getRocksJumped() {
		return rocksJumped;
	}

	//Checks if two objects are touching then returns true if they are
	public static boolean collisionCheck(Dinosaur dino, obstacle rock) {
		Rectangle rockBox = new Rectangle(rock.getX(), rock.getY(), rock.getWidth(), rock.getHeight());
	    Rectangle dinoBox = new Rectangle(dino.getX(), dino.getY(), dino.getWidth(), dino.getHeight());
	    return rockBox.intersects(dinoBox);
	}
	
	//Draws background
	public static void drawBackdrop(Graphics g, int canvasWidth, int canvasHeight) {
		Color skyblue = new Color(135,206,235);
		g.setColor(skyblue);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		Color ground = new Color(212,166,80);
		g.setColor(ground);
		g.fillRect(0,750,canvasWidth,150);
		Color sun = new Color(242,226,48);
		g.setColor(sun);
		g.fillOval(1000,150,300,300);
	
	}

}
