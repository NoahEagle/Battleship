import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {

    @Test
    public void testPlaceShipOutOfBounds() {
        
    	Board gameBoard = new Board();
    	Ship carrier = new Ship(5, "carrier");
    	
    	gameBoard.placeShip(carrier, 7, 0);
    	
    	String[][] gameArray = gameBoard.getBoardArray();
    	
    	assertEquals("[7][0] shouldn't be filled", "Water", gameArray[7][0]);
    	assertEquals("[8][0] shouldn't be filled", "Water", gameArray[8][0]);
    	assertEquals("[9][0] shouldn't be filled", "Water", gameArray[8][0]);
    }
    
    @Test
    public void testPlaceShipInBounds() {
        
    	Board gameBoard = new Board();
    	Ship carrier = new Ship(5, "carrier");
    	
    	gameBoard.placeShip(carrier, 0, 0);
    	
    	String[][] gameArray = gameBoard.getBoardArray();
    	
    	assertEquals("[0][0] should be filled", "carrier", gameArray[0][0]);
    	assertEquals("[1][0] should be filled", "carrier", gameArray[1][0]);
    	assertEquals("[2][0] should be filled", "carrier", gameArray[2][0]);
    	assertEquals("[3][0] should be filled", "carrier", gameArray[3][0]);
    	assertEquals("[4][0] should be filled", "carrier", gameArray[4][0]);
    }
    
    @Test
    public void testPlaceShipOverlappingAnother() {
        
    	Board gameBoard = new Board();
    	Ship carrier = new Ship(5, "carrier");
    	Ship battleship = new Ship(4, "battleship");
    	
    	gameBoard.placeShip(carrier, 4, 2);    	
    	gameBoard.placeShip(battleship, 2, 2);
    	
    	String[][] gameArray = gameBoard.getBoardArray();
    	
    	assertEquals("[4][2] should be carrier", "carrier", gameArray[4][2]);
    	assertEquals("[5][2] should be carrier", "carrier", gameArray[5][2]);
    	assertEquals("[6][2] should be carrier", "carrier", gameArray[6][2]);
    	assertEquals("[7][2] should be carrier", "carrier", gameArray[7][2]);
    	assertEquals("[8][2] should be carrier", "carrier", gameArray[8][2]);
    	
    	assertEquals("[2][2] shouldn't be filled", "Water", gameArray[2][2]);
    	assertEquals("[3][2] shouldn't be filled", "Water", gameArray[3][2]);
    }
    
    @Test
    public void testShootWater() {
        
    	Board gameBoard = new Board();
    	
    	String[][] gameArray = gameBoard.getBoardArray();

    	assertEquals("[5][5] should be Water", "Water", gameArray[5][5]);
    	
    	gameBoard.shoot(5, 5);
    	
    	assertEquals("[5][5] should be Miss", "Miss", gameArray[5][5]);

    }
    
    @Test
    public void testShootShip() {
        
    	Board gameBoard = new Board();
    	
    	String[][] gameArray = gameBoard.getBoardArray();

    	assertEquals("[5][5] should be Water", "Water", gameArray[5][5]);
    	
    	Ship carrier = new Ship(5, "carrier");
    	
    	gameBoard.placeShip(carrier, 5, 5);
    	
    	assertEquals("[5][5] should be carrier", "carrier", gameArray[5][5]);
    	
    	gameBoard.shoot(5, 5);
    	
    	assertEquals("[5][5] should be Hit", "Hit", gameArray[5][5]);

    }
    
    @Test
    public void testShootMiss() {
        
    	Board gameBoard = new Board();
    	
    	String[][] gameArray = gameBoard.getBoardArray();

    	assertEquals("[5][5] should be Water", "Water", gameArray[5][5]);
    	
    	gameBoard.shoot(5, 5);
    	
    	assertEquals("[5][5] should be Miss", "Miss", gameArray[5][5]);
    	
    	int outcome = gameBoard.shoot(5, 5);
    	
    	assertEquals("outcome should be -1 (does nothing)", -1, outcome);
    	assertEquals("[5][5] should still be Miss", "Miss", gameArray[5][5]);

    }
    
    @Test
    public void testShootHitShipSpot() {
        
    	Board gameBoard = new Board();
    	
    	String[][] gameArray = gameBoard.getBoardArray();

    	assertEquals("[5][5] should be Water", "Water", gameArray[5][5]);
    	
    	Ship carrier = new Ship(5, "carrier");
    	
    	gameBoard.placeShip(carrier, 5, 5);
    	
    	assertEquals("[5][5] should be carrier", "carrier", gameArray[5][5]);
    	
    	gameBoard.shoot(5, 5);
    	
    	assertEquals("[5][5] should be Hit", "Hit", gameArray[5][5]);
    	
    	int outcome = gameBoard.shoot(5, 5);
    	
    	gameBoard.shoot(5, 5);
    	
    	assertEquals("outcome should be -1 (does nothing)", -1, outcome);
    	assertEquals("[5][5] should stil be Hit", "Hit", gameArray[5][5]);

    }
    
    @Test
    public void testRemoveShip() {
        
    	Board gameBoard = new Board();
    	Ship carrier = new Ship(5, "carrier");
    	
    	gameBoard.placeShip(carrier, 0, 0);
    	
    	String[][] gameArray = gameBoard.getBoardArray();
    	
    	assertEquals("[0][0] should be filled", "carrier", gameArray[0][0]);
    	assertEquals("[1][0] should be filled", "carrier", gameArray[1][0]);
    	assertEquals("[2][0] should be filled", "carrier", gameArray[2][0]);
    	assertEquals("[3][0] should be filled", "carrier", gameArray[3][0]);
    	assertEquals("[4][0] should be filled", "carrier", gameArray[4][0]);
    	
    	gameBoard.removeShip(carrier);
    	
    	assertEquals("[0][0] shouldn't be filled", "Water", gameArray[0][0]);
    	assertEquals("[1][0] shouldn't be filled", "Water", gameArray[1][0]);
    	assertEquals("[2][0] shouldn't be filled", "Water", gameArray[2][0]);
    	assertEquals("[3][0] shouldn't be filled", "Water", gameArray[3][0]);
    	assertEquals("[4][0] shouldn't be filled", "Water", gameArray[4][0]);
    }
    
    @Test
    public void testIsSunk() {
    	
    	Ship carrier = new Ship(5, "carrier");
    	
    	carrier.hitShip(0, 0);
    	carrier.hitShip(1, 0);
    	carrier.hitShip(2, 0);
    	carrier.hitShip(3, 0);
    	carrier.hitShip(4, 0);
    	
    	boolean isSunk = carrier.isSunk();
    	
    	assertTrue("carrier should be sunk", isSunk);
    }
    
    @Test
    public void testSinkShip() {
        
    	Board gameBoard = new Board();
    	
    	String[][] gameArray = gameBoard.getBoardArray();
    	
    	Ship carrier = new Ship(5, "carrier");
    	
    	gameBoard.placeShip(carrier, 0, 0);
    	
    	assertEquals("[0][0] should be filled", "carrier", gameArray[0][0]);
    	assertEquals("[1][0] should be filled", "carrier", gameArray[1][0]);
    	assertEquals("[2][0] should be filled", "carrier", gameArray[2][0]);
    	assertEquals("[3][0] should be filled", "carrier", gameArray[3][0]);
    	assertEquals("[4][0] should be filled", "carrier", gameArray[4][0]);
    	
    	gameBoard.sink(carrier);
    	
    	assertEquals("[0][0] should be Sunk", "Sunk", gameArray[0][0]);
    	assertEquals("[1][0] should be Sunk", "Sunk", gameArray[1][0]);
    	assertEquals("[2][0] should be Sunk", "Sunk", gameArray[2][0]);
    	assertEquals("[3][0] should be Sunk", "Sunk", gameArray[3][0]);
    	assertEquals("[4][0] should be Sunk", "Sunk", gameArray[4][0]);
    }
    
    @Test
    public void testShootSunkShip() {
        
    	Board gameBoard = new Board();
    	
    	String[][] gameArray = gameBoard.getBoardArray();
    	
    	Ship carrier = new Ship(5, "carrier");
    	
    	gameBoard.placeShip(carrier, 0, 0);
    	
    	gameBoard.sink(carrier);
    	
    	assertEquals("[0][0] should be Sunk", "Sunk", gameArray[0][0]);
    	assertEquals("[1][0] should be Sunk", "Sunk", gameArray[1][0]);
    	assertEquals("[2][0] should be Sunk", "Sunk", gameArray[2][0]);
    	assertEquals("[3][0] should be Sunk", "Sunk", gameArray[3][0]);
    	assertEquals("[4][0] should be Sunk", "Sunk", gameArray[4][0]);
    	
    	int outcome1 = gameBoard.shoot(0, 0);
    	int outcome2 = gameBoard.shoot(1, 0);
    	int outcome3 = gameBoard.shoot(2, 0);
    	int outcome4 = gameBoard.shoot(3, 0);
    	int outcome5 = gameBoard.shoot(4, 0);
    	
    	assertEquals("outcome should be -1 (does nothing)", -1, outcome1);
    	assertEquals("outcome should be -1 (does nothing)", -1, outcome2);
    	assertEquals("outcome should be -1 (does nothing)", -1, outcome3);
    	assertEquals("outcome should be -1 (does nothing)", -1, outcome4);
    	assertEquals("outcome should be -1 (does nothing)", -1, outcome5);
    	
    	assertEquals("[0][0] should still be Sunk", "Sunk", gameArray[0][0]);
    	assertEquals("[1][0] should still be Sunk", "Sunk", gameArray[1][0]);
    	assertEquals("[2][0] should still be Sunk", "Sunk", gameArray[2][0]);
    	assertEquals("[3][0] should still be Sunk", "Sunk", gameArray[3][0]);
    	assertEquals("[4][0] should still be Sunk", "Sunk", gameArray[4][0]);
    }

}
