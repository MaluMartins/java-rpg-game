package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	//screen settings
	
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	final int tileSize = originalTileSize * scale; //48x48 tile
	
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = maxScreenCol * tileSize;
	final int screenHeight = maxScreenRow * tileSize;
	
	int fps = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	// set players default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set the size of the class
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //improve rendering
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if (delta >= 1) {
				// 1 update: update information such as char position
				update();
				
				//2 draw: draw the updated information on the screen
				repaint(); //call paintComponent
				
				delta--;
			}
		}
	}
	
	public void update() {
		if (keyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		else if (keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if (keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if (keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
}
