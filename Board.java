import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class models the board state of Battleship. It creates a board object (which is essentially
 * just a 2D String array whose elements describe the state of the coordinate board (is there water,
 * a ship, a sunk ship, etc... there).
 */
public class Board {

	private String[][] board;	// the string array representing the game board
	
	/**
	 * Constructs a board object (representing a board in Battleship). This board object is
	 * essentially just a 2D String array (which is initialized to have "Water" in each element).
	 */
	public Board() {
	
	String[][] theBoard = new String[10][10];
	
	for (int row = 0; row < theBoard.length; row++) {
		
		for (int col = 0; col < theBoard[0].length; col++) {
			
			theBoard[row][col] = "Water";
		}
	}
	
	board = theBoard;
	
	}
	
	/**
	 * Simple getter function to get the 2D String array field of the board
	 * @return the 2D String array representing the board
	 */
	public String[][] getBoardArray() {
		
		String[][] boardArray = board;
		
		return boardArray;
	}
	
	/**
	 * Places a ship on the board
	 * @param shipName the ship object to be placed on the board
	 * @param rowStart the row of the board for the boat to be placed
	 * @param colStart the column of the board for the boat to be placed
	 * @return an int representing what happened (-1 if didn't place, 1 if placed)
	 */
	public int placeShip(Ship shipName, int rowStart, int colStart) {
		
		String[] theShip = shipName.getShip();
		
		int shipLength = theShip.length;
		
		if ((rowStart + shipLength) > 10) {
			
			return -1;
		}
		
		String shipType = shipName.getType();
		
		for (int row = rowStart; row < (rowStart + shipLength); row++) {
			
			if (!board[row][colStart].equals("Water")) {
				
				return -1;
			}
		}
		
		for (int row = rowStart; row < (rowStart + shipLength); row++) {
			
			board[row][colStart] = shipType;
		}
		
		shipName.colStart = colStart;
		shipName.rowStart = rowStart;
		
		return 1;
	}
	
	/**
	 * Removes a ship from the board
	 * @param shipName the name of the ship to be removed from the board
	 */
	public void removeShip(Ship shipName) {
		
		int colStart = shipName.getColStart();
		int rowStart = shipName.getRowStart();
		
		String[] shipArray = shipName.getShip();
		int shipLength = shipArray.length;
		
		for (int row = rowStart; row < (rowStart + shipLength); row++) {
			
			board[row][colStart] = "Water";
		}
	}
	
	/**
	 * Shoots a coordinate (element) on the board
	 * @param row the row of the board to be shot
	 * @param col the column of the board to be shot
	 * @return an int representing what happened (-1 if shot invalid spot, 1 if shot water, 2 if
	 * shot boat, and 0 if somehow shot something else)
	 */
	public int shoot(int row, int col) {
		
		String element = board[row][col];
		
		if (element.equals("Hit") || element.equals("Sunk") || element.equals("Miss")) {
			
			return -1;
		}
		
		else if (element.equals("Water")) {
			
			board[row][col] = "Miss";
			return 1;
		}
		
		else if (element.equals("carrier") || (element.equals("battleship")) 
				|| (element.equals("cruiser")) || (element.equals("submarine")) 
				|| (element.equals("destroyer"))) {
			
			board[row][col] = "Hit";
			return 2;
		}
		
		return 0;
	}
	
	/**
	 * Sinks a specific ship on the board
	 * @param shipName the name of the ship to be sunk by the function
	 */
	public void sink(Ship shipName) {
		
		int colStart = shipName.getColStart();
		int rowStart = shipName.getRowStart();
		
		String[] shipArray = shipName.getShip();
		int shipLength = shipArray.length;
		
		for (int row = rowStart; row < (rowStart + shipLength); row++) {
			
			board[row][colStart] = "Sunk";
		}
	}
}

