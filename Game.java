import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	
	// represents if the high score button's been pressed previously
	private boolean highScoresAlreadyDisplayed = false;
	
	public static String initials;	// the initials of the player (used for a potential high score)
	
    public void run() {
        
        initials = JOptionPane.showInputDialog("Initials");
    	
        // Frame which holds the instructions
        final JFrame instructionsFrame = new JFrame("Instructions");
        instructionsFrame.setLocation(1000, 1000);
        
        // Frame which holds the high scores
        final JFrame highScoresFrame = new JFrame("High Scores");
        highScoresFrame.setLocation(1000, 1000);
    	
    	// Top-level frame in which game components live
    	final JFrame frame = new JFrame("Battleship");
        frame.setLocation(1000, 1000);
       
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        
        // Main playing area
        final PlayerCourt playerCourt = new PlayerCourt(status);
        frame.add(playerCourt, BorderLayout.EAST);
        
        // Main playing area
        final ComputerCourt compCourt = new ComputerCourt(status, playerCourt);
        frame.add(compCourt, BorderLayout.WEST);
        
        // Control panel
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // adds the undo button to the control panel
    	final JButton undo = new JButton("Undo");
    	undo.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    			java.util.List<Ship> orderOfTheBoats = 
    					(java.util.List<Ship>) playerCourt.getOrderOfShips();
    			int numberOfShips = playerCourt.getNumShips();
    			
    			Board thePlayerBoard = playerCourt.getPlayerBoard();
    			
    			if (numberOfShips <= 0) {
    				
    				return;
    			}
    			
    			else {
    				
    				if (ComputerCourt.shotsTaken == 0) {
    				
    				Ship lastShipPlaced = orderOfTheBoats.remove(numberOfShips - 1);
    				
    				thePlayerBoard.removeShip(lastShipPlaced);
    				playerCourt.decrementNumShips();
    				playerCourt.repaint();
    				}
    			}
    		}
    	});
    	control_panel.add(undo);
    	
    	// adds the instructions button to the control panel
    	final JButton instructions = new JButton("Instructions");
    	instructions.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			highScoresFrame.setVisible(false);
    			instructionsFrame.setVisible(true);
    		}
    	});
    	control_panel.add(instructions);
    	
    	// adds the high scores button to the control panel
    	final JButton highScores = new JButton("High Scores");
    	highScores.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			instructionsFrame.setVisible(false);
    			highScoresFrame.setVisible(true);
    		}
    	});
    	control_panel.add(highScores);
    	
        // "Back" panel (FOR INSTRUCTIONS FRAME)
        final JPanel back_panel = new JPanel();
        instructionsFrame.add(back_panel, BorderLayout.SOUTH);
        final JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		highScoresFrame.setVisible(false);
        		instructionsFrame.setVisible(false);
        		frame.setVisible(true);
        	}
        });
        back_panel.add(back);
        
        // "Return" panel (FOR HIGH SCORES FRAME)
        final JPanel return_panel = new JPanel();
        highScoresFrame.add(return_panel, BorderLayout.SOUTH);
        final JButton returnNow = new JButton("Return");
        returnNow.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		instructionsFrame.setVisible(false);
        		highScoresFrame.setVisible(false);
        		frame.setVisible(true);
        	}
        });
        return_panel.add(returnNow);
        
        // Actually reads the instructions file and displays it in the instructions frame
        JTextArea theHighScores = new JTextArea(100, 100);
        highScoresFrame.add(theHighScores, BorderLayout.CENTER);
        
        // "Scores" panel (FOR HIGH SCORES FRAME)
        final JPanel scores_panel = new JPanel();
        highScoresFrame.add(scores_panel, BorderLayout.NORTH);       
        final JButton readHighScores = new JButton("View High Scores");
        readHighScores.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		if (!highScoresAlreadyDisplayed) {        		
        		
	        		try	{
	        			String nextLineOfText;
	        			
	        			FileReader fileReader = new FileReader("HighScores.txt");
	        			BufferedReader bufferedReader = new BufferedReader(fileReader);
	
	        			int lineCounter = 0;
	        			
	        			while(((nextLineOfText = bufferedReader.readLine()) != null) 
	        						&& lineCounter < 8) {
	        				
	        				theHighScores.append(nextLineOfText + "\n");
	        				lineCounter++;
	        				
	        			}
	        		}
	        		catch (Exception ex) {
	        			return;       			
	        		}
        		}
        		highScoresAlreadyDisplayed = true;
        	}
        });
        scores_panel.add(readHighScores);     
        
        // Actually reads the instructions file and displays it in the instructions frame
        JTextArea theInstructions = new JTextArea(100, 100);
        instructionsFrame.add(theInstructions, BorderLayout.CENTER);
        
        final JPanel read_panel = new JPanel();
        instructionsFrame.add(read_panel, BorderLayout.NORTH);       
        final JButton readInstructions = new JButton("View Instructions");
        readInstructions.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try	{
        			String nextLineOfText;
        			
        			FileReader fileReader = new FileReader("Instructions.txt");
        			BufferedReader bufferedReader = new BufferedReader(fileReader);

        			while((nextLineOfText = bufferedReader.readLine()) != null) {
        				
        				theInstructions.read(bufferedReader, "reading instructions...");
        			}
        		}
        		catch (Exception ex) {
        			return;       			
        		}
        	}
        });
        read_panel.add(readInstructions);      
    	
        // Put the main frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());               
    }
}
