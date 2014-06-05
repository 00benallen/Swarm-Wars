package game;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import objects.Base;
import objects.LevelElement;


/**
 * Class that contains the AI for the computer opponents, has access to lots of data from all over the game
 * @author Ben Pinhorn
 * @version 1.0
 */
public class Computer {
	private String teamName;
	private Base base;
	private int x, y, width, height;
	
	public Computer(String teamName, Base base) { //constructor for all AIs
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
	
	private boolean hasEnemy, isHurt, attacking, victory;
	private Base enemyBase = null;
	ArrayList<Base> bases = Main.getBases();
	
	public void runAI() { //AI code for all AIs
		ArrayList<LevelElement> selectedElements = new ArrayList<LevelElement>();
		if(!victory) {
			
			if(!hasEnemy) {
				getEnemy(); //finds the nearest enemy
			}
			
			if(hasEnemy && !isHurt) {
				attacking = true;
			}
			else {
				attacking = false;
			}
			
			if(attacking) {
				attack(selectedElements); //sends all drones above the base to the enemy base
			}
			
			if(base.getHealth() < 250) {
				isHurt = true;
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

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
}
