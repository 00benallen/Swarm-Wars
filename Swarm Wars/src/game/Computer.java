package game;

import graphics.GraphicsMain;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import objects.Base;
import objects.LevelElement;

public class Computer {
	private String teamName;
	private Base base;
	private int x, y, width, height;
	
	public Computer(String teamName, Base base) {
		this.teamName = teamName;
		this.setBase(base);
		x = base.getX();
		y = base.getY();
		width = base.getWidth();
		height = base.getHeight();
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	private boolean hasEnemy, isHit, isHurt, attacking, victory;
	private Base enemyBase = null;
	private int prevHealth = -1;
	ArrayList<Base> bases = Main.getBases();
	
	public void runAI() {
		ArrayList<LevelElement> selectedElements = new ArrayList<LevelElement>();
		if(!victory) {
			
			if(!hasEnemy) {
				getEnemy();
			}
			
			if(hasEnemy && !isHurt) {
				attacking = true;
			}
			else {
				attacking = false;
			}
			
			if(attacking) {
				attack(selectedElements);
			}
			
			if(prevHealth > base.getHealth() && base.isMoving() == false) {
				isHit = true;
				
			}
			prevHealth = base.getHealth();
			
			if(base.getHealth() < 25) {
				isHurt = true;
			}
			
			if(isHit && !base.isMoving()) {
				if(base.getX() + 10 < GraphicsMain.WIDTH) {
					this.base.setMoveX(base.getX() + 10);
				}
				else {
					this.base.setMoveX(base.getX() - 10);
				}
				isHit = false;
				base.setMoving(true);
			}
			
			if(isHurt) {
				avoid(selectedElements);
			}
			
			if(hasEnemy) {
				if(!bases.contains(enemyBase)) {
					enemyBase = null;
					hasEnemy = false;
				}
			}
			
			if(bases.size() == 1) {
				victory = true;
			}
			
			
			
			
		}
	}
	
	public void getEnemy() {
		double minDistance = 10000;
		for(int i = 0; i < bases.size(); i++) {
			Base curBase = bases.get(i);
			double curDistance = Point2D.distance(this.x, this.y, curBase.getX(), curBase.getX());
			if(curDistance < minDistance && !curBase.getTeamName().equals(this.teamName)) {
				enemyBase = curBase;
				minDistance = curDistance;
			}
		}
		hasEnemy = true;
	}
	
	public void attack(ArrayList<LevelElement> selectedElements) {
		Rectangle2D selectBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		for(int j = 0; j < bases.size(); j++) {
			for(int k = 0; k < bases.get(j).getSwarmCount(); k++) {
				if(bases.get(j).getDrone(k).getTeamName().equals(this.teamName)) {
					if(selectBox.contains(bases.get(j).getDrone(k).getBoundBox()) && !selectedElements.contains(bases.get(j).getDrone(k))) {
						selectedElements.add(bases.get(j).getDrone(k));
					}
				}
			}
		}
		
		for(int i = 0; i < selectedElements.size(); i++) {
			selectedElements.get(i).setMoveX(enemyBase.getX());
			selectedElements.get(i).setMoveY(enemyBase.getY());
			selectedElements.get(i).setMoving(true);
		}
	}
	
	public void avoid(ArrayList<LevelElement> selectedElements) {
		attacking = false;
		Rectangle2D selectBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		for(int j = 0; j < bases.size(); j++) {
			for(int k = 0; k < bases.get(j).getSwarmCount(); k++) {
				if(bases.get(j).getTeamName().equals(this.teamName)) {
					if(selectBox.contains(bases.get(j).getBoundBox()) && !selectedElements.contains(bases.get(j))) {
						selectedElements.add(bases.get(j));
					}
				}
			}
		}
		
		for(int i = 0; i < selectedElements.size(); i++) {
			selectedElements.get(i).setMoveX(this.x);
			selectedElements.get(i).setMoveY(this.y);
			selectedElements.get(i).setMoving(true);
		}
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
}
