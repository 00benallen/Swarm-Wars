package graphics;
import game.Listener;
import game.Main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import objects.Base;
import objects.LevelElement;

/**
 * Graphics class for Swarm Wars, draws all of the screen objects for the game. Also contains the run loop and the main method, starts the new thread for the game.
 * @author Ben Pinhorn
 * @version 1.0
 */
public class GraphicsMain extends Canvas implements Runnable  {
	private static final long serialVersionUID = 8452193232912354072L;
	boolean running = false;
	JFrame frame;
	public static final int  SCALE = 256, WIDTH = 4 * SCALE, HEIGHT = 3 * SCALE;
	public final String NAME = "Swarm Wars";
	private Graphics2D g;
	public static Listener listener = new Listener();
	private static final int STATE_MENU = 0, STATE_GAME = 1, STATE_WIN = 2, STATE_LOSE = 3;
	public static int gameState = STATE_MENU;
	private static GraphicsMain graphicsMain = new GraphicsMain();
	private Thread thread;
	
	public GraphicsMain() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setIgnoreRepaint(true);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		frame = new JFrame(this.NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		graphicsMain.start();
	}

	@Override
	public void run() {
		
		while(running) {
			long lastTime = System.nanoTime();
			double nanoPerUpdate = 1000000000D/60D;
			
			long lastTimer = System.currentTimeMillis();
			double delta = 0D;
			
			Main.init();
			
			while(running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / nanoPerUpdate;
				lastTime = now;
				boolean shouldRender = false;
				
				while(delta >= 1) {
					if(gameState == STATE_GAME) {
						Main.update();
					}
					delta--;
					shouldRender = true;
				}
				
				if(shouldRender) {
					render();
					
				}
				
				if(System.currentTimeMillis() - lastTimer >= 1000) {
					lastTimer += 100;
				}
			}
			stop();
		}
		
	}
	
	public synchronized void start() {
		running = true;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		thread = null;
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy(); //method from Canvas class
		
		if(bs == null) {
			createBufferStrategy(3); //creates it only for the first time the loop runs (trip buff)
			return;
		}
		
		
		g = (Graphics2D)bs.getDrawGraphics();
		draw();
		
		
 		g.dispose();
 		bs.show();
	}
	
	public void draw() {
		if(gameState == STATE_GAME) {
			drawBackground();
			drawLevel();
			drawBases();
			drawDrones();
			drawSelectionBox();
		}
		if(gameState == STATE_MENU) {
			drawStartMessage();
		}
		if(gameState == STATE_WIN) {
			drawWinMessage();
			this.stop();
			System.exit(0);
		}
		if(gameState == STATE_LOSE) {
			drawLoseMessage();
			this.stop();
			System.exit(0);
		}
	}
	
	public void drawStartMessage() {
		JOptionPane.showMessageDialog(null, "Welcome to Swarm Wars! Select your units by dragging over them, left click if you want the drones to move, and right click if you want your base to move! Kill all enemy bases to win!");
		gameState = STATE_GAME;
	}
	
	public void drawWinMessage() {
		JOptionPane.showMessageDialog(null, "You have won! Good work!");
	}
	
	public void drawLoseMessage() {
		JOptionPane.showMessageDialog(null, "I'm sorry, you lost! Please play again!");
	}
	
	public void drawBackground() {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH + 100, HEIGHT + 100);
	}
	
	public void drawLevel() {
		ArrayList<LevelElement> levelList = Main.getLevel().getLevelArray();
		for(int i = 0; i < levelList.size(); i++) {
			LevelElement element = levelList.get(i);
			if(element != null && !(element instanceof Base)) {
				g.drawImage(element.getImage(), element.getX(), element.getY(), element.getWidth(), element.getHeight(), null);
			}
		}
		
	}
	
	public void drawDrones() {
		ArrayList<Base> bases = Main.getLevel().getBases();
		for(int i = 0; i < bases.size(); i++) {
			for(int j = 0; j < bases.get(i).getSwarmCount(); j++) {
				g.setColor(bases.get(i).getColor().brighter());
				g.fill(bases.get(i).getDrone(j).getBoundBox());
			}
		}
	}
	
	public void drawBases() {
		ArrayList<Base> bases = Main.getBases();
		for(int i = 0; i < bases.size(); i++) {
			Base newBase = bases.get(i);
			g.drawImage(newBase.getImage(), newBase.getX(), newBase.getY(), newBase.getWidth(), newBase.getHeight(), null);
			g.setColor(newBase.getColor());
			g.fillRect(newBase.getX() + 3, newBase.getY() + 3, newBase.getWidth() - 6, newBase.getHeight() - 6);
		}
	}
	
	public void drawSelectionBox() {
		if(listener.getSelectionBox() != null) {
			g.draw(listener.getSelectionBox());
		}
	}
}
