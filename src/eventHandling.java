/*
 * This class handles keyevents
 */

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class eventHandling implements KeyListener{

	public Dinosaur dino;
	public Graphics g;
	public DrawingPanel panel;

	public eventHandling(Dinosaur dino, Graphics g, DrawingPanel panel) {
        this.dino = dino;
        this.g = g;
        this.panel = panel;
    }
	
	//Calls dino's jump method when spacebar is pressed
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			try {
				dino.jump(g, panel);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {

	}

	
	
}
