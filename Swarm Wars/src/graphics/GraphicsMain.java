package graphics;
import game.Main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GraphicsMain extends Canvas implements Runnable  {
	boolean running = false;
	JFrame frame;
	public static final int WIDTH = 1024, HEIGHT = 768, SCALE = 1;
	public static final String NAME = "Swarm Wars";
	private static Graphics2D g;
	
	
	public GraphicsMain() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setIgnoreRepaint(true);
		
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
	}
	
	public static void drawBackground() {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

}
