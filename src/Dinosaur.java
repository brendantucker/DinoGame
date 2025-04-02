/*
 * Represents the game's main character. Contains methods for drawing, moving,
 *  animation, and jumping for the dinosaur
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Dinosaur {
	private static int posX ;
	private static int posY ;
	private final int width = 125;
	private final int height = 150;
	private static boolean isJumping = false;
	private static boolean isFalling ;
	private Timer timer;
	private int delay = 5;
	private int neutralHeight = 600;
	
	//Data fields for jumping action and image
	static int jumpHeight = 300; //Pixels from top of window
	static int jumpChange = 5; //amount of pixels dino moves each frame during jump
	BufferedImage img;
	
	
	//Constructors for dino object
	public Dinosaur() throws IOException {
		img = ImageIO.read(new File("talldino.png"));
		posX=100;
		posY=600;
	}
	public Dinosaur(int posX, int posY) throws IOException{
		this.posX = posX;
		this.posY = posY;
		img = ImageIO.read(new File("talldino.png"));
	}
	

	
	//Makes dino jump up when called, uses timer to move dino smoothly
	 public void jump(Graphics g, DrawingPanel panel) throws IOException {
	        if (isJumping) {
	            return;
	        }

	        isJumping = true;
	        isFalling = false;
	        clearDino(g);

	        //Creates timer that performs an action (jumping) after every delay
	        timer = new Timer(delay, new ActionListener() {
	            int posY = getY();

	            public void actionPerformed(ActionEvent e) {
	                if (posY > jumpHeight && isFalling==false) {
	                    try {
	                    	//Checks if game is over
	                    	if(DinoGame.getGameOver()) {
	                			timer.stop();
	                		}
	                    	//Clears and re adds dino in new location
	                    	clearDino(g);
							addDino(g, getX(), posY - jumpChange);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
	                    setY(posY - jumpChange);
	                    posY -= jumpChange;
	                    
	                    //Once dino reaches top of jump, isFalling is set to true, and movement direction flips
	                    if(posY<=jumpHeight) {
	                    	isFalling=true;
	                    }
	                    //Enters else if when isFalling changes to true
	                } else if(posY>=jumpHeight && isFalling==true) {
	                	try {
	                		if(DinoGame.getGameOver()) {
	                			timer.stop();
	                		}
	                    	clearDino(g);
							addDino(g, getX(), posY + jumpChange);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
	                    setY(posY + jumpChange);
	                    posY += jumpChange;
	                    if(posY==neutralHeight) {
	                    	timer.stop();
		                    isJumping = false;
	                    }
	                }
	                //Enters else branch and stops timer when jump is done
	                //Sets isJumping to false, so dino can activate jump again
	                else {
	                    timer.stop();
	                    isJumping = false;
	                }
	            }
	        });

	        timer.start();
	    }
	
	//Draws dinosaur at given x,y point
	public void addDino(Graphics g, int posX, int posY) throws IOException {
		g.drawImage(img, posX, posY, width, height, null);
		
	}
	//Removes box around dinosaur
	public void clearDino(Graphics g) {
		g.clearRect(this.posX, this.posY, width, height);
		g.setColor(new Color(135,206,235));
		g.fillRect(this.posX, this.posY, width, height);
	}
	
	//Getters and setters for coordinates
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
	public void setY(int posY) {
		this.posY=posY;
	}
	public static void setIsJumping(boolean bool) {
		isJumping = bool;
	}

	
}


