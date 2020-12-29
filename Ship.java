import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ship {

	private String[] ship;	// string array representation of the ship
	
	private String type;	// string representing which type of ship (Carrier, Submarine, etc...)
	
	public int rowStart;	// the row containing the ship's starting element
	
	public int colStart;	// the col containing the ship's starting element
	
	/**
	 * Creates a new ship object given a specified length. It initializes the direciton to
	 * horizontal and the starting row and col to 0. These fields will be truly initialized within
	 * placeShip.
	 * @param length  the length of the ship (how many nodes does it have?)
	 */
	public Ship(int length, String shipType) {
		
		String[] newShip = new String[length];
		
		for (int i = 0; i < newShip.length; i++) {
			
			newShip[i] = "shipType";
		}
		
		ship = newShip;
		type = shipType;
		rowStart = 0;
		colStart = 0;
		
	}
	
	/**
	 * Simple getter function for the type of the ship
	 * @return String representing the kind of ship
	 */
	public String getType() {
		
		String shipType = type;
		
		return shipType;
	}
	
	/**
	 * Simple getter function for the ship's array field
	 * @return String[] representing the ship
	 */
	public String[] getShip() {
		
		String[] shipArray = ship;
		
		return shipArray;
	}
	
	/**
	 * Simple getter function for the ship's starting row
	 * @return an int representing the ship's starting row
	 */
	public int getRowStart() {
		
		int startingRow = rowStart;
		
		return startingRow;
	}
	
	/**
	 * Simple getter function for the ship's starting col
	 * @return an int representing the ship's starting col
	 */
	public int getColStart() {
		
		int startingCol = colStart;
		
		return startingCol;
	}
	
	/**
	 * This function takes in the location of a successful shot and marks the ship accordingly
	 * (changing one of its elements from "Boat" to "Hit"). Also, if this is the final hit to sink
	 * a boat, it calls upon sinkShip, which will change all the ship's elements to "Sunk".
	 * @param rowShot the row in which the successful shot occurred
	 * @param colShot the col in which the successful shot occurred
	 */
	public void hitShip(int rowShot, int colShot) {
		
		String[] shipArray = getShip();
			
		int shot = rowShot - getRowStart();
			
		shipArray[shot] = "Hit";
		
		if (shouldSink()) {
			
			sinkShip();
		}
	}
	
	/**
	 * This function determines whether or not a ship should sink or not (have all of its elements
	 * been hit).
	 * @return boolean representing whether it should sink or not
	 */
	public boolean shouldSink() {
		
		String[] shipArray = getShip();
		int numHits = 0;
		
		for (int i = 0; i < shipArray.length; i++) {
			
			if (shipArray[i].equals("Hit")) {
				
				numHits++;
			}
		}		
		
		return (numHits == shipArray.length);
	}
	
	/**
	 * This function simply takes a boat that has now been sunk and changes all its elements to
	 * "Sunk".
	 */
	public void sinkShip() {
		
		String[] shipArray = getShip();
		
		for (int i = 0; i < shipArray.length; i++) {
			
			shipArray[i] = "Sunk";
		}
	}
	
	/**
	 * This function checks if a ship has been sunk
	 * @return boolean representing if it's sunk or not
	 */
	public boolean isSunk() {
		
		String[] shipArray = getShip();
		
		for (int i = 0; i < shipArray.length; i++) {
			
			if (shipArray[i] != "Sunk") {
				
				return false;
			}
		}
		
		return true;
	}
}
