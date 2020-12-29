import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;


/**
 * ComputerCourt
 * 
 * This class holds the primary game logic for how the computer's board should be interacted with.
 */
	@SuppressWarnings("serial")
	public class ComputerCourt extends JPanel {

	    // the state of the game logic
	    private Board computerBoard; // the computer's battleship board, clickable to shoot
	   
	    // the 5 ships hidden within the computer's battleship board
	    private Ship carrier;
	    private Ship battleship;
	    private Ship cruiser;
	    private Ship submarine;
	    private Ship destroyer;
	 
	    // number of shots the player has taken on the computer board
	    public static int shotsTaken;
	    
	    // number of shots on ships the player has taken on the computer board
	    private static int successfulShots;	
	    
	    public boolean playing = false; // whether the game is running 
	    private JLabel status; // Current status text, i.e. "Running..."
	    
	    // Game constants
	    public static final int COURT_WIDTH = 500;
	    public static final int COURT_HEIGHT = 500;

	    public ComputerCourt(JLabel status, PlayerCourt playerCourt) {
	        // creates border around the court area, JComponent method
	        setBorder(BorderFactory.createLineBorder(Color.RED));
	        
	        computerBoard = new Board();

	        carrier = new Ship(5, "carrier");
	        battleship = new Ship(4, "battleship");
	        cruiser = new Ship(3, "cruiser");
	        submarine = new Ship(3, "submarine");
	        destroyer = new Ship(2, "destroyer");
	        
	        computerBoard.placeShip(carrier, 0, 9);
	        computerBoard.placeShip(battleship, 6, 7);
	        computerBoard.placeShip(cruiser, 0, 0);
	        computerBoard.placeShip(submarine, 2, 2);
	        computerBoard.placeShip(destroyer, 8, 0);
	        
	        playing = true;
	        
	        setFocusable(true);
	        
	        // The timer is an object which triggers an action periodically with the given INTERVAL.
	        // We register an ActionListener with this timer, whose actionPerformed() method is
	        // called each time the timer triggers. We define a helper method called tick() that
	        // actually does everything that should be done in a single timestep.
	        Timer timer = new Timer(35, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                tick();
	            }
	        });
	        timer.start();
	        
	        
	        addMouseListener(new MouseAdapter() {
	    	    
	        	@Override
	    	    public void mouseClicked(MouseEvent e) {	    	    	
	        		
	        		if (playing) {
	        		
		        		if (PlayerCourt.numShips == 5) {	
	
		    	    	int xCoord = e.getX();
		    	    	int yCoord = e.getY();
		    	    	
		    	    	String[][] boardArray = computerBoard.getBoardArray();
	    	    		
	    	    		String origElement = boardArray[yCoord / 50][ xCoord / 50];
		    	    	
		    	    	int outcome = computerBoard.shoot(yCoord / 50, xCoord / 50);
		    	    	
		    	    	if (outcome == 1 || outcome == 2) {
		    	    		
		    	    		shotsTaken++;
		    	    	}
		    	    	
		    	    	if (outcome == 2) {
		    	    		
		    	    		successfulShots++;
		    	    		
		    	    		/**
		    	    		 * shoot the appropriate ship object as well (which will check if it sinks)
		    	    		 */
		    	    		if (origElement.equals("carrier")) {
		    	    				    	    			;
		    	    			carrier.hitShip(yCoord / 50, xCoord / 50);
		    	    			if (carrier.isSunk()) {
		    	    				
		    	    				computerBoard.sink(carrier);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (origElement.equals("battleship")) {
		    	    				    	    		
		    	    			battleship.hitShip(yCoord / 50, xCoord / 50);
		    	    			if (battleship.isSunk()) {
		    	    				
		    	    				computerBoard.sink(battleship);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (origElement.equals("cruiser")) {
	
		    	    			cruiser.hitShip(yCoord / 50, xCoord / 50);
		    	    			if (cruiser.isSunk()) {
		    	    				
		    	    				computerBoard.sink(cruiser);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (origElement.equals("submarine")) {
		    	    			
		    	    			submarine.hitShip(yCoord / 50, xCoord / 50);
		    	    			if (submarine.isSunk()) {
		    	    				
		    	    				computerBoard.sink(submarine);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (origElement.equals("destroyer")) {
		    	    			
		    	    			destroyer.hitShip(yCoord / 50, xCoord / 50);
		    	    			if (destroyer.isSunk()) {
		    	    				
		    	    				computerBoard.sink(destroyer);
		    	    			}
		    	    		}
		    	    		
		    	    	}
		        		
		        		Board playerBoard = playerCourt.getPlayerBoard();
		        		
		        		String[][] playerBoardArray = playerBoard.getBoardArray();
		        		
		        		System.out.println(shotsTaken - (((shotsTaken / 10) * 10)));
		        		
		        		/* where was shot? */
		        		
		        		int rowPlayerShot = shotsTaken / 10;
		        		int colPlayerShot = 0;
		        		
		        		if ((1 <= shotsTaken) && (10 >= shotsTaken)) {
		        			
		        			colPlayerShot = shotsTaken - 1;
		        		}
		        		else if ((11 <= shotsTaken) && (20 >= shotsTaken)) {
		        			
		        			colPlayerShot = shotsTaken - 11;
		        		}
		        		else if ((21 <= shotsTaken) && (30 >= shotsTaken)) {
		        			
		        			colPlayerShot = shotsTaken - 21;
		        		}
		        		else if ((31 <= shotsTaken) && (40 >= shotsTaken)) {
		        			
		        			colPlayerShot = shotsTaken - 31;
		        		}
		        		else if ((41 <= shotsTaken) && (50 >= shotsTaken)) {
		        			
		        			colPlayerShot = shotsTaken - 41;
		        		}
		        		else if ((51 <= shotsTaken) && (60 >= shotsTaken)) {
	
		        			colPlayerShot = shotsTaken - 51;
		        		}
		        		else if ((61 <= shotsTaken) && (70 >= shotsTaken)) {
	
		        			colPlayerShot = shotsTaken - 61;
		        		}
		        		else if ((71 <= shotsTaken) && (80 >= shotsTaken)) {
	
		        			colPlayerShot = shotsTaken - 71;
		        		}
		        		else if ((81 <= shotsTaken) && (90 >= shotsTaken)) {
	
		        			colPlayerShot = shotsTaken - 81;
		        		}
		        		else if ((91 <= shotsTaken) && (100 >= shotsTaken)) {
	
		        			colPlayerShot = shotsTaken - 91;
		        		}
	    	    		
	    	    		String playerOrigElement = 
	    	    	playerBoardArray[rowPlayerShot][colPlayerShot];
		    	    	
		    	    	int playerOutcome = playerCourt.shootPlayer();
		    	    	
		    	    	if (playerOutcome == 1 || playerOutcome == 2) {
		    	    		
		    	    		playerCourt.shotsTaken++;
		    	    	}
		    	    	
		    	    	if (playerOutcome == 2) {
		    	    		
		    	    		playerCourt.successfulShots++;
		    	    		
		    	    		/**
		    	    		 * shoot the appropriate ship object as well (which will check if it sinks)
		    	    		 */
		    	    		if (playerOrigElement.equals("carrier")) {
		    	    				    	
		    	    			Ship playerCarrier = playerCourt.carrier;
		    	    			
		    	    			playerCarrier.hitShip(rowPlayerShot, colPlayerShot);
		    	    			if (playerCarrier.isSunk()) {
		    	    				
		    	    				playerBoard.sink(playerCarrier);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (playerOrigElement.equals("battleship")) {
		    	    				
		    	    			Ship playerBattleship = playerCourt.battleship;
		    	    			
		    	    			playerBattleship.hitShip(rowPlayerShot, colPlayerShot);
		    	    			if (playerBattleship.isSunk()) {
		    	    				
		    	    				playerBoard.sink(playerBattleship);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (playerOrigElement.equals("cruiser")) {
	
		    	    			Ship playerCruiser = playerCourt.cruiser;
		    	    			
		    	    			playerCruiser.hitShip(rowPlayerShot, colPlayerShot);
		    	    			if (playerCruiser.isSunk()) {
		    	    				
		    	    				playerBoard.sink(playerCruiser);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (playerOrigElement.equals("submarine")) {
		    	    			
		    	    			Ship playerSubmarine = playerCourt.submarine;
		    	    			
		    	    			playerSubmarine.hitShip(rowPlayerShot, colPlayerShot);
		    	    			if (playerSubmarine.isSunk()) {
		    	    				
		    	    				playerBoard.sink(playerSubmarine);
		    	    			}
		    	    		}
		    	    		
		    	    		else if (playerOrigElement.equals("destroyer")) {
		    	    			
		    	    			Ship playerDestroyer = playerCourt.destroyer;
		    	    			
		    	    			playerDestroyer.hitShip(rowPlayerShot, colPlayerShot);
		    	    			if (playerDestroyer.isSunk()) {
		    	    				
		    	    				playerBoard.sink(playerDestroyer);
		    	    			}
		    	    		}
		    	    		
		    	    	}
		    	    		
		    	    	}
		    	    	
		    	    	repaint();
		    	    	
	        		}
		        		}
		        	
		    	    });
	        
	        
	        this.status = status;
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	    }
	   
	    /**
	     * Redefines the paint command for ComputerCourt. It paints each element of the board
	     * a different color based on its contents (blue for water, red for hit boat, and black
	     * for sunk boat)
	     */
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        
	        g.setColor(Color.BLUE);
	        for (int x = 0; x < 500; x += 50) {
	        	
	        	for (int y = 0; y < 500; y += 50) {
	        		
	        		g.fillRect(x, y, 50, 50);
	        	}
	        }
	        
	        String[][] boardArray = computerBoard.getBoardArray();
	        
	        for (int row = 0; row < 10; row ++) {
	        	
	        	for (int col = 0; col < 10; col++) {
	        		
	        		String element = boardArray[row][col];
	        		
	        /*		if (element.equals("carrier") || element.equals("battleship") 
	        				|| (element.equals("cruiser"))|| (element.equals("submarine")
	        						|| (element.equals("destroyer")))) {
	        			
	        			g.setColor(Color.GRAY);
	        			g.fillRect(col * 50, row * 50, 50, 50);
	        		} */
	        		
	        		if (element.equals("Hit")) {
	        			
	        			g.setColor(Color.RED);
	        			g.fillRect(col * 50, row * 50, 50, 50);
	        		}
	        		
	        		else if (element.equals("Sunk")) {
	        			
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
	    }
	    
	    /**
         * This method is called every time the timer defined in the constructor triggers.
         */
        void tick() {
            if (playing) {
                // check for the game end conditions
            	
            	status.setText("Running...          Shots taken: " + String.valueOf(shotsTaken));
            	
                if (successfulShots == 17) {
                    playing = false;
                    status.setText("Gratz! You beat the dumbest AI ever!");  
                    alterHighScores();
                }
                
                else if (PlayerCourt.successfulShots == 17) {
                	playing = false;
                	status.setText("Wait... You actually lost? That's embarrasing...");
                	
                }
                // update the display
                repaint();
            }
        }
      
        /**
         * This function changes the high scores stored within the HighScores.txt file if needed
         */
        public void alterHighScores() {
        	
        	BufferedReader bufferedReader = null;
        	BufferedWriter bufferedWriter = null;
        	
        	try {
        		FileReader fileReader = new FileReader("HighScores.txt");
    			bufferedReader = new BufferedReader(fileReader);
    			
    			// Skips the unchanging text at the top of the file (skips to actual scores)
        		String line1 = bufferedReader.readLine();
        		String line2 = bufferedReader.readLine();
        		String line3 = bufferedReader.readLine();
        		String line4 = bufferedReader.readLine();
        		String line5 = bufferedReader.readLine();
        		
        		/**
        		 * Finds top score
        		 */
        		
        		int topScore;
        		
        		String topScoreLine = bufferedReader.readLine();
        		
        		String topScoreString = "";
        		
        		for (int i = 0; Character.isDigit(topScoreLine.charAt(i)); i++) {
        			
        			topScoreString += topScoreLine.charAt(i);
        		}
        		
        		topScore = Integer.parseInt(topScoreString);
        		
        		/**
        		 * Finds mid score
        		 */
        		
        		int midScore;
        		
        		String midScoreLine = bufferedReader.readLine();
        		
        		String midScoreString = "";
        		
        		for (int i = 0; Character.isDigit(midScoreLine.charAt(i)); i++) {
        		
        			midScoreString += midScoreLine.charAt(i);
        		}
        		
        		midScore = Integer.parseInt(midScoreString);
        		
        		/**
        		 * Finds low score
        		 */
        		
        		int lowScore;
        		
        		String lowScoreLine = bufferedReader.readLine();
        		
        		String lowScoreString = "";
        		
        		for (int i = 0; Character.isDigit(lowScoreLine.charAt(i)); i++) {
        			
        			lowScoreString += lowScoreLine.charAt(i);
        		}
        		
        		lowScore = Integer.parseInt(lowScoreString);
        		
        		
        		
        		bufferedWriter = new BufferedWriter(new FileWriter("HighScores.txt"));
    			
        		
        		if (shotsTaken > lowScore) {
        			
        			return;
        		}
        		
        		else if (shotsTaken > midScore && shotsTaken < lowScore) {
        			
        			/*
        			 * Insert it in the 3rd spot
        			 */
        			
        			bufferedWriter.write(line1 + "\n");
        			bufferedWriter.write(line2 + "\n");
        			bufferedWriter.write(line3 + "\n");
        			bufferedWriter.write(line4 + "\n");
        			bufferedWriter.write(line5 + "\n");
        			bufferedWriter.write(topScoreLine + "\n");
        			bufferedWriter.write(midScoreLine + "\n");
        			bufferedWriter.write(String.valueOf(shotsTaken) + " \t\t\t\t" + 
        										Game.initials + "\n");
        			
        		}
        		
        		else if (shotsTaken > topScore && shotsTaken < midScore) {
        			
        			/*
        			 * Insert it in the 2nd spot
        			 */
        			
        			bufferedWriter.write(line1 + "\n");
        			bufferedWriter.write(line2 + "\n");
        			bufferedWriter.write(line3 + "\n");
        			bufferedWriter.write(line4 + "\n");
        			bufferedWriter.write(line5 + "\n");
        			bufferedWriter.write(topScoreLine + "\n");
        			bufferedWriter.write(String.valueOf(shotsTaken) + " \t\t\t\t" + 
        										Game.initials + "\n");
        			bufferedWriter.write(midScoreLine + "\n");
        		}
        		
        		else if (shotsTaken < topScore) {
        			
        			/*
        			 * Insert it in the 1st spot
        			 */
        			
        			bufferedWriter.write(line1 + "\n");
        			bufferedWriter.write(line2 + "\n");
        			bufferedWriter.write(line3 + "\n");
        			bufferedWriter.write(line4 + "\n");
        			bufferedWriter.write(line5 + "\n");
        			bufferedWriter.write(String.valueOf(shotsTaken) + " \t\t\t\t" + 
        										Game.initials + "\n");
        			bufferedWriter.write(topScoreLine + "\n");
        			bufferedWriter.write(midScoreLine + "\n");
        		}
        	}
        	
        	catch (Exception e) {
        		
        	}
        	
        	finally {
        		
        		try {
					bufferedReader.close();
					bufferedWriter.flush();
					bufferedWriter.close();
				} catch (IOException e) {
					
				}
        	}
        }    
	}
