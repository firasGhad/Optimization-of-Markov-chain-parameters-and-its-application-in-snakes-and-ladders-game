package snakesAndLaddersGame;

import controller.MainController;
import javafx.scene.shape.Circle;

import java.util.Scanner;

public class player {
public void player(){
	System.out.println(getId());
}
	private String name;
	private int id;
	private static Dice dice = new Dice();
	public boolean turn = true;
	private int xPosition=5 ;//the coordinates of the player position
	private int xPosition1=31 ;//the coordinates of the player position
	private int xPosition2=5 ;//the coordinates of the player position
	private int xPosition3=31 ;//the coordinates of the player position
	private int yPosition = 545;
	private int yPosition1 = 545;
	private int yPosition2 = 570;
	private int yPosition3 = 570;
	public int CirPos=1;
	public Circle circle;
	public int row=1;
	public Tile PlayerTile;
	public int NumOfMoves=0;
	public int getPlayerPosition() {
		return PlayerPosition;
	}

	public void setPlayerPosition(int playerPosition) {
		PlayerPosition = playerPosition;
	}

	private int PlayerPosition=1;
	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
int xtmp=0;
	public int getxPosition() {
		if (this.name.equals("P1"))
			xtmp=xPosition;
		else if (this.name.equals("P2"))
			xtmp=xPosition1;
		else if (this.name.equals("P3"))
			xtmp=xPosition2;
		else if (this.name.equals("P4"))
			xtmp=xPosition3;
		return xtmp;

	}

	public void setxPosition(int xPosition) {

		if (this.name.equals("P1")) {
			this.xPosition = xPosition;
		} else if (this.name.equals("P2")) {
			this.xPosition1 = xPosition;
		} else if (this.name.equals("P3")) {
			this.xPosition2 = xPosition;
		} else if (this.name.equals("P4")){
			this.xPosition3 = xPosition;
		}
	}
int ytmp=0;
    public int getyPosition() {
		if (this.name.equals("P1"))
			ytmp=yPosition;
		else if (this.name.equals("P2"))
			ytmp=yPosition1;
		else if (this.name.equals("P3"))
			ytmp=yPosition2;
		else if (this.name.equals("P4"))
			ytmp=yPosition3;
		return ytmp;

    }

	public void setyPosition(int yPosition) {
		if (this.name.equals("P1")) {
			this.yPosition = yPosition;
		} else if (this.name.equals("P2")) {
			this.yPosition1 = yPosition;
		} else if (this.name.equals("P3")) {
			this.yPosition2 = yPosition;
		} else if (this.name.equals("P4")){
			this.yPosition3 = yPosition;
		}

	}


	
	public player(String Pname,int Pid) {
		this.id=Pid;
		this.name=Pname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;

	}

	public void setId(int id) {
		this.id = id;
	}
    public int takeTurn(){
		
		//Get the roll
		int roll = 0;
		roll = dice.rollD6();
		
		System.out.println(name + " rolled " + roll + ".");
		
		return roll;
	}
}