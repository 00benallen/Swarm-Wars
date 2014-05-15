package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Listener implements MouseListener, MouseMotionListener {
	private Point2D firstPoint;
	private Rectangle2D selectRect;
	private boolean mousePressed, mouseDragged, itemsSelected;

	@Override
	public void mouseClicked(MouseEvent e) {
		itemsSelected = false;
		selectRect = null;
		firstPoint = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		firstPoint = new Point2D.Double(e.getX(), e.getY());
		mousePressed = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		mouseDragged = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseDragged = true;
		itemsSelected = true;
		selectRect = new Rectangle2D.Double(firstPoint.getX(), firstPoint.getY(), e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public Rectangle2D getSelectionBox() {
		return selectRect;
	}

	public boolean mousePressed() {
		return mousePressed;
	}

	public boolean mouseDragged() {
		return mouseDragged;
	}

	public boolean itemsSelected() {
		return itemsSelected;
	}
}
