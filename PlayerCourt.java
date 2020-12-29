import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
 * PlayerCourt
 * 
 * This class holds the primary game logic for how the player's board should be interacted with.
 */
	@SuppressWarnings("serial")
	public class PlayerCourt extends JPanel {

	    // the state of the game logic
	    private Board playerBoard; // the player's battleship board, no controls
	    
	    // the 5 ships hidden within the player's battleship board
	    public Ship carrier;
	    public Ship battleship;
	    public Ship cruiser;
	    public Ship submarine;
	    public Ship destroyer;
	    
	    // number of shots the computer has taken on the player board
	    public static int shotsTaken;
	    
	    // number of shots on ships the computer has taken on the player board
	    public static int successfulShots;	
	    
	    private List<Ship> orderOfShips;	// Ordered list of the ship placements
	    
	    public static int numShips;	// number of ships placed on the board

	    public boolean playing = false; // whether the game is running 
	    private JLabel status; // Current status text, i.e. "Running..."
	    
	    // Game constants
	    public static final int COURT_WIDTH = 500;
	    public static final int COURT_HEIGHT = 500;

	    public PlayerCourt(JLabel status) {
	        // creates border around the court area, JComponent method
	        setBorder(BorderFactory.createLineBorder(Color.GREEN));
	        
	        playerBoard = new Board();
	        
	        // creates the ships
	        carrier = new Ship(5, "carrier");
	        battleship = new Ship(4, "battleship");
	        cruiser = new Ship(3, "cruiser");
	        submarine = new Ship(3, "submarine");
	        destroyer = new Ship(2, "destroyer");
	        
	        numShips = 0;
	        orderOfShips = new LinkedList<Ship>();
	        
	        setFocusable(true);
	        
	        addMouseListener(new MouseAdapter() {
	    	    
	        	@Override
	    	    public void mouseClicked(MouseEvent e) {
	    	    	
	    	    	int xCoord = e.getX();
	    	    	int yCoord = e.getY();	    	    	
		    	    	
	    	    	if (numShips == 0) {
		    	    		
		    	    	int didWork = playerBoard.placeShip(carrier, yCoord / 50, xCoord /50);
		    	    		
		    	    	if (didWork == 1) {
		    	    			
		    	    		numShips++;
			    	    	orderOfShips.add(carrier);	
		    	    			
		    	    	}
		    	   	}
		    	    	
		    	    else if (numShips == 1) {
		    	    		
		    	    	int didWork = playerBoard.placeShip(battleship, yCoord / 50, xCoord /50);
		    	    		
		    	   if (didWork == 1) {
		    	    		
		    	    		numShips++;
		    	    		orderOfShips.add(battleship);	    	    		
		    	    	}
		    	   	}
		    	    	
		    	    else if (numShips == 2) {
		    	    		
		    	    	int didWork = playerBoard.placeShip(cruiser, yCoord / 50, xCoord /50);
		    	    		
		    	    	if (didWork == 1) {
		    	    			
		    	    		numShips++;
		    	    		orderOfShips.add(cruiser);	    	    		
		    	    	}
		    	    		
		    	    }
		    	    	
		    	    else if (numShips == 3) {
		    	    		
		    	    	int didWork = playerBoard.placeShip(submarine, yCoord / 50, xCoord /50);
		    	    		
		    	    	if (didWork == 1) {
		    	    			
		    	    		numShips++;
		    	    		orderOfShips.add(submarine);	    	    		
		    	    	}
		    	   	}
		    	   	
		    	    else if (numShips == 4) {
		    	    		
		    	    	int didWork = playerBoard.placeShip(destroyer, yCoord / 50, xCoord /50);
		    	    		
		    	    	if (didWork == 1) {
		    	    			
		    	    		numShips++;
		    	    		orderOfShips.add(destroyer);	    	    			
		    	    	}
		    	    }
	    	    	
	    	    repaint();
	    	    }
	        
	    	    });
	    }
	    
	    /**
	     * Redefines the paint command for PlayerBoard. It paints all the possible elements
	     * a different color (Water is blue, boats are grey, hit boats are red, sunk boats are
	     * black).
	     */
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);	       
        
	        int numSuccessfulShots = 0;
	        
	        g.setColor(Color.BLUE);
	        for (int x = 0; x < 500; x += 50) {
	        	
	        	for (int y = 0; y < 500; y += 50) {
	        		
	        		g.fillRect(x, y, 50, 50);
	        	}
	        }
	        
	        String[][] boardArray = playerBoard.getBoardArray();
	        
	        for (int row = 0; row < 10; row ++) {
	        	
	        	for (int col = 0; col < 10; col++) {
	        		
	        		String element = boardArray[row][col];
	        		
	        		if (element.equals("carrier") || element.equals("battleship") 
	        				|| (element.equals("cruiser"))|| (element.equals("submarine")
	        						|| (element.equals("destroyer")))) {
	        			
	        			g.setColor(Color.GRAY);
	        			g.fillRect(col * 50, row * 50, 50, 50);
	        		}
	        		
	        		else if (element.equals("Hit")) {
	        			
	        			numSuccessfulShots++;
	        			g.setColor(Color.RED);
	        			g.fillRect(col * 50, row * 50, 50, 50);
	        		}
	        		
	        		else if (element.equals("Sunk")) {
	        			
	        			numSuccessfulShots++;
	        			g.setColor(Color.BLACK);
	        			g.fillRect(col * 50, row * 50, 50, 50);
	        		}
	        		
	        		else if (element.equals("Miss")) {
	        			
	        			g.setColor(Color.WHITE);
	        			g.fillRect(col * 50, row * 50, 50, 50);
	        		}
	        	}
	        }	        
	       
	        g.setColor(Color.BLACK);
	        for (int x = 0; x < 500; x += 50) {
	        	
	        	g.drawLine(x, 0, x, 500);
	        	g.drawLine(0, x, 500, x);
	        }
	        
	        successfulShots = numSuccessfulShots;
	    }
	    
	    /**
	     * Simple getter function for the number of ships on the board
	     * @return an int representing the number of ships on the board
	     */
	    public int getNumShips() {
	    	
	    	int numOfShips = numShips;
	    	
	    	return numOfShips;
	    }
	    
	    /**
	     * This function decreases the number of ships field (representing number of ships on
	     * the board) by one
	     */
	    public void decrementNumShips() {
	    	
	    	int numOfShips = numShips;
	    	
	    	numShips = numOfShips - 1;
	    }
	    
	    /**
	     * Simple getter function for the List representing the order in which the boats were placed
	     * @return the ordered List of boat placements
	     */
	    public List getOrderOfShips() {
	    	
	    	List orderOfTheShips = orderOfShips;
	    	
	    	return orderOfTheShips;
	    }
	    
	    /**
	     * Simple getter function for the player's board object
	     * @return the Board object representing the player's board
	     */
	    public Board getPlayerBoard() {
	    	
	    	Board thePlayerBoard = playerBoard;
	    	
	    	return thePlayerBoard;
	    }
	    
	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	    }
	    
	    /**
	     * This automates the computer's shooting of the player's board
	     * @return an int representing the outcome (-1 if invalid shot, 1 if shot water, 2 if shot
	     * boat, and 0 if somehow shot something else)
	     */
	    public int shootPlayer() {
        
	    int outcome = 0;	
	    	
        if ((1 <= ComputerCourt.shotsTaken) && (10 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(0, ComputerCourt.shotsTaken - 1);
        }
        
        else if ((11 <= ComputerCourt.shotsTaken) && (20 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(1, ComputerCourt.shotsTaken - 11);
        }
        
        else if ((21 <= ComputerCourt.shotsTaken) && (30 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(2, ComputerCourt.shotsTaken - 21);
        }
        
        else if ((31 <= ComputerCourt.shotsTaken) && (40 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(3, ComputerCourt.shotsTaken - 31);
        }
        
        else if ((41 <= ComputerCourt.shotsTaken) && (50 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(4, ComputerCourt.shotsTaken - 41);
        }
        
        else if ((51 <= ComputerCourt.shotsTaken) && (60 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(5, ComputerCourt.shotsTaken - 51);
        }
        
        else if ((61 <= ComputerCourt.shotsTaken) && (70 >= ComputerCourt.shotsTaken)) {

        	outcome = playerBoard.shoot(6, ComputerCourt.shotsTaken - 61);
        }
        
        else if ((71 <= ComputerCourt.shotsTaken) && (80 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(7, ComputerCourt.shotsTaken - 71);
        }
        
        else if ((81 <= ComputerCourt.shotsTaken) && (90 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(8, ComputerCourt.shotsTaken - 81);
        }
        
        else if ((91 <= ComputerCourt.shotsTaken) && (100 >= ComputerCourt.shotsTaken)) {
        	
        	outcome = playerBoard.shoot(9, ComputerCourt.shotsTaken - 91);
        }
        
        repaint();
        return outcome;
        
	    }
	    
	}