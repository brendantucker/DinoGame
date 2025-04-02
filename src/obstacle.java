/*
 * The obstacle class represents the obstacles in the game. It holds the
 *  properties of an obstacle object and methods for moving and adding 
 *  obstacles to the games canvas. It uses an arraylist to keep track 
 *  of the obstacles on screen
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Timer;


public class obstacle {
	
	private final int posY = 628;
	private int posX = DinoGame.getCanvasWidth();
	private final int width = 250;
	private final int height = 150;
	private final int groundHeight = 750;
	private final int rockHighestPoint = 780;
	private Timer timer;
	private static Timer timer2;
	private int speed = 15;			//Controls speed obstacles move left
	private final static int delay = 30; //How many ms per every time obstacle is redrawn
	private final static int spawnDelay = 4000; //How many ms in between each rock spawn
	BufferedImage img;
	private static ArrayList<obstacle> obstacleArray = new ArrayList<>();
	
	//Obstacle constructors
	public obstacle() throws IOException{
		img = ImageIO.read(new File("rock.png"));
	}

	public obstacle(int posX) throws IOException{
		this.posX = posX;
		img = ImageIO.read(new File("rock.png"));
	}
	
	//Draws an obstacle at the given x coordinate
	public void addObstacle(Graphics g, int posX) {
		g.drawImage(img, posX, posY, width, height, null);
	}
	
	//Clears the obstacle and replaces background color
	public void clearObstacle(Graphics g) {
		g.clearRect(this.posX, this.posY, width, height); // Clear png and redraw ground
		g.setColor(new Color(135,206,235)); //sky blue
		g.fillRect(this.posX, this.posY, width, groundHeight);
		g.setColor( new Color(212,166,80)); //ground
		g.fillRect(this.posX, groundHeight+1, width, rockHighestPoint);
	}
	
	//Uses timer to move obstacle to the left until it is offscreen then discards
	public void moveLeft(Graphics g) {
		timer = new Timer(delay, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            clearObstacle(g);
	            setX(getX() - speed);
	            addObstacle(g, getX());
	            
	            //If rock is offscreen
	            if (getX() < -1*width) {
	            	timer.stop();
	                clearObstacle(g);
	                obstacleArray.remove(0);
	                DinoGame.setRocksJumped(DinoGame.getRocksJumped()+1);
	                System.out.println(DinoGame.getRocksJumped());
	            }
	            //Checks to see if game is over before continuing
	            if(DinoGame.getGameOver()==true) {
	            	timer.stop();
	            }
	        }
	    });
	    timer.start(); 
		
	}
	
	//Uses timer to create a new obstacle, add it to an array, and make it move left,
	//  waiting for delay variable to create a new one
	public static void startRockSpawning(Graphics g) {
		timer2 = new Timer(spawnDelay , new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					obstacle newRock = new obstacle();
					obstacleArray.add(newRock);
					newRock.addObstacle(g, DinoGame.getCanvasWidth());
					newRock.moveLeft(g);
					if(DinoGame.getGameOver()==true) {
						timer2.stop();
						obstacleArray.clear();
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		timer2.start();
	}
	
	//Getters and setters for obstacle coordinates and array
	public int getX() {
		return posX;
	}
	public int getY() {
		return posY;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setX(int posX) {
		this.posX = posX;
	}

	public static ArrayList<obstacle> getObstacleArray() {
		return obstacleArray;
	}
}
