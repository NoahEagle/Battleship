=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: nkeagle
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array
  
  	I implemented the two Battleship game boards (computer's and player's board) as String 2D 
  	arrays. (Each element is a coordinate that the player can place their boat/shoot).
  	
  	Each element of the array (coordinate on the board) can be "Water" (non-shot + non-boat spot),
  	"Missed" (shot + non-boat spot), "carrier", "battleship", "cruiser", "submarine", or "destroyer" 
  	(non-shot + specific boat spot), "Hit" (shot + boat spot), or "Sunk" (boat spot of a boat that's 
  	been sunk).
  	
  	At the start of the game, all elements are initialized to "Water". When the player and computer
  	place their boats on the board, some elements will be changed to "Boat". Throughout the game,
  	when the players take their shots, elements will be changed to "Missed", "Hit", and "Sunk"
  	accordingly.
  	
  	2D arrays made sense since the game boards were already functioning like a 2D array in real
  	life. Each Battleship board is a 10 x 10 square. That easily correlates to a 10 x 10 2D array.

  2. Collections
  
    I use collections (specifically a LinkedList) in my Undo button (and functionality).
    
    When each boat is placed by the player, it and its location is stored in a LinkedList as the
    new tail. When the Undo button is actually pressed, the ship at the tail is removed, and the
    relevant elements of the Battleship 2D array board are changed from "Boat" back to "Water".
    The removed boat is now ready to be placed again.
    
    A player can choose to keep clicking the Undo button, removing their placed boats in reverse
    order until they have no more boats left (at which point the button will do nothing).
    
    Collections made sense for this functionality since the Undo button is supposed to remove the
    boats in reverse order (the most recent boat, then the most recent boat before that, etc...).
    This means I have to keep track of the order of boat placements. A LinkedList does this very
    nicely with its Next pointers.

  3. File I/O
  
    I'm using I/O to store high scores. The current high scores (in terms of number of shots taken
    to win) will be stored in a text file. If the player presses the high score button, the high
    score file will be read and displayed to the player in-game.
    
    At the end of a game, if the player's shots taken to win is lower than any of the current high
    scores, they'll be prompted to enter a 3 character name, and the high score text file will be
    updated to include their new score and name. (Now, if they click the high score button in a new
    game, they're new score will appear there).
    
    I/O made sense since I want to retain these high scores across different game instances
    (different times the game is opened/played). Keeping the high scores in an outside text file
    that can be read and updated whenever the player wants (or scores well enough) does this. It
    keeps the data in a safe, outside place that can then be accessed by each game instance when
    desired. Everyone in any game instance will see the same scores.

  4.  Testable Component
  
    The main state of my Battleship game is the two boards (the 2D String arrays). The placeShip 
    and shootShip methods take in the location(s) of the placed ship/shot and updates the game
    board accordingly.
    
    I test this functionality via JUnit tests. I test that the game doesn't allow players to place
    ships that partially/fully go out of bounds or that overlap other ships. I also test that my
    Undo button actually removes the ships in the right order (removing one ship works and removing
    all ships so that none are left on the board works right). I also test that the LinkedList of
    placed boats is updated accordingly when a boat is placed. And, I also test that when the final
    coordinate of a boat is shot, the boat is sunk. I also verify that the game doesn't allow the
    player to shoot an already shot location (a "Missed", "Hit", or "Sunk" element).
    
    The functionality of this state of my game is crucial for it to work, so extensively testing it
    via JUnit tests made sense. If my shooting/placing functionalities don't work, the whole game
    falls apart, so it's imperative that I test that they work as intended.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Board.java --> This file builds and performs functions on the underlying state of the boards
(2D String arrays). This file can shoot (change the value of one of its elements) a coordinate,
place a ship (change a line of elements to a certain type of ship), and sink a ship
(change the elements of a certain ship to "Sunk").

Ship.java --> This file builds and performs functions on the underlying state of te ships
(1D String arrays). This file can check if a ship is sunk, it can hit and/or sink a ship (itself),
and it can determine if it should sink (at which point it sinks itself).

PlayerCourt.java --> This file manages the interactions and state regarding the player's board
(the board on the right). It supports placing the player's boats via mouse clicks and shoots itself
(representing the computer's turn) whenever the player clicks on the area handled by ComputerCourt.
It also draws itself (the 2D array with appropriately colored squares) after each shot and boat
placement.

ComputerCourt.java --> This file manages the interactions and state regarding the computer's
board (the board on the left). It places the computer's ships automatically (makes it an easy game),
but it hides their location from the player during the actual game. It supports the player shooting 
the computer's boats via mouse clicks and shoots the PlayerCourt every time the player clicks them.
It also draws itself (the 2D array with appropriately colored squares) after each shot.

Game.java --> This brings all the previous files together to create the actual game. It creates
the frame with all its panels (the Undo, Instructions, and High Score buttons, the two courts
(Player and Computer), and the status panel). It contains the functionality for these buttons,
and provides the function that actually runs the game.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

The first major challenge was figuring out how to "place" my Ship objects on my Board objects. I
didn't know how to combine the 1D and 2D arrays and have them influence each other. This is why
I decided to have each boat make the 2D board array say "Carrier" or "Submarine" instead of just
"Boat" because then I'd know which ship object was where without having to do any weird reference
equality stuff. (I named the "carrier" ship "carrier", so whatever the element was, that's what
Ship object was there).

Another big issue was automating the computer to shoot the player every time the player shot the
computer. I couldn't figure out how to get the ComputerCourt class to call PlayerCourt's shootPlayer
function (since it couldn't access it). My solution was to simply make ComputerCourt take in a
PlayerCourt as an argument (and I made sure that PlayerCourt was the specific PlayerCourt used in
the game within my Game.java class). That way, I could call the shootPlayer method on the specific
PlayerCourt object within the specific ComputerCourt object.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I'll be honest, my design is very messy and probably extremely unoptimized. I should've planned
a lot better before getting started on the code, but I procrastinated for too long. 

I think there's a decent separation of functionality, but it could be improved. I think making 
separate Board and Ship classes makes sense. Each class can handle their own functions (the board 
can place a ship and shoot a coordinate and sink a boat within it) (the ship can shoot itself based
on the coordinates from the board, the ship can see if it should sink, and then it can sink itself).

When I started off, I thought separating the computer's board and player's board into two separate
courts (different classes) would make sense. I thought I needed to since I wanted to handle mouse
actions differently based on them (only place ships on the player's and only shoot coordinates on
the computer's). Ultimately, that made things hard and I ended up just feeding in a PlayerCourt
via the constructor for a ComputerCourt so I could access all the PlayerCourt methods/fields within
ComputerCourt. Maybe, if I were to do this over, I'd try and find a way to combine them into one
GameCourt.java file (and make 1 board where the first half is the computer section and the second
half is the player section).

As for private state encapsulation, that's also pretty bad. I'm heavily relying upon the fact that
only I have access to my code and only I will change it (I'm assuming my TA won't change it). I made
a lot of fields in ComputerCourt and PlayerCourt public and static so that I could access them
wherever I suddenly found that I needed them. This opens up the possibility for someone to just
change the values of crucial fields like shotsTaken and successfulShots (which is used as the
endgame condition) whenever they want (outside of my mouseclick listeners and shoot methods).

If I had more time, I would've tried to find a way to make these fields private and make special
getter functions or changer functions. I wouldn't really needed to have made the shotsTaken and
successfulShots public and static if I had combined the PlayerCourt and ComputerCourt classes.
(I only made them public and static so I could access them without an instance and use each class'
shots fields in each other --> which was crucial for my computer shooting the board).

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  https://www.hasbro.com/common/instruct/Battleship.PDF
  ^^^
  I went there to verify that I was implementing Battleship with the correct instructions
  (right number/types of ships, size of board, sinking ships, etc...).
  
  I looked at the java docs for MouseListeners (to, unsurprisingly, implement my mouse controls)
  and Lists/LinkedLists (to implement my Undo button).
  
  For implementing the I/O aspects of my game (the instructions and high scores), I looked
  at the java docs for BufferedWriters and BufferedReaders to find which methods I need to use.