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

import objects.Base;
import objects.LevelElement;

public class GraphicsMain extends Canvas implements Runnable  {
	private static final long serialVersionUID = 8452193232912354072L;
	boolean running = false;
	JFrame frame;
	public static final int  SCALE = 256, WIDTH = 4 * SCALE, HEIGHT = 3 * SCALE;
	public static final String NAME = "Swarm Wars";
	private static Graphics2D g;
	public static Listener listener = new Listener();
	private static Color[] colorArray = {Color.red, Color.red, Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.pink, Color.yellow};
	
	
	public GraphicsMain() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setIgnoreRepaint(true);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		frame = new JFrame(GraphicsMain.NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new GraphicsMain().start();
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
					Main.update();
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
		}
		
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
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
	
	public static void draw() {
		drawBackground();
		drawLevel();
		drawBases();
		drawDrones();
		drawSelectionBox();
	}
	
	public static void drawBackground() {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH + 100, HEIGHT + 100);
	}
	
	public static void drawLevel() {
		ArrayList<LevelElement> levelList = Main.getLevel().getLevelArray();
		for(int i = 0; i < levelList.size(); i++) {
			LevelElement element = levelList.get(i);
			if(element != null && !(element instanceof Base)) {
				g.drawImage(element.getImage(), element.getX(), element.getY(), element.getWidth(), element.getHeight(), null);
			}
		}
		
	}
	
	public static void drawDrones() {
		ArrayList<Base> bases = Main.getLevel().getBases();
		for(int i = 0; i < bases.size(); i++) {
			for(int j = 0; j < bases.get(i).getSwarmCount(); j++) {
				g.setColor(bases.get(i).getColor());
				g.fill(bases.get(i).getDrone(j).getBoundBox());
			}
		}
	}
	
	public static void drawBases() {
		ArrayList<Base> bases = Main.getBases();
		for(int i = 0; i < bases.size(); i++) {
			Base newBase = bases.get(i);
			g.drawImage(newBase.getImage(), newBase.getX(), newBase.getY(), newBase.getWidth(), newBase.getHeight(), null);
			g.setColor(newBase.getColor());
			g.fillRect(newBase.getX() + 3, newBase.getY() + 3, newBase.getWidth() - 6, newBase.getHeight() - 6);
		}
	}
	
	public static void drawSelectionBox() {
		if(listener.getSelectionBox() != null) {
			g.draw(listener.getSelectionBox());
		}
	}
}
