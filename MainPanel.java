/*
g * Name: Leon Xu
 * Purpose: MainPanel class 
 * Date: January 8, 2021
 */
package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public class MainPanel extends JPanel implements KeyListener, ActionListener {

	Image background, blacksquare, powerpellet, scaredghosts; // background image, black square (6x6), power pellet, and scared ghost images
	Timer tm = new Timer(5, this); // Timer object. This tm object will basically put the GUI to sleep for 5ms; refreshes GUI
	PacMan player = new PacMan(); // create player object
	Ghost[] ghosts; //Ghost object list
	TimerGame ghostTimer = new TimerGame(-1); //initialize the ghostTimer with a null value for now
	boolean scared = false; //scared tells us if the ghosts are in scared state or not
	int numberOfGhosts; //tells us number of ghosts
	Coins coins = new Coins(); // create coins object
	int check = 0; // check variable. 1 = coin eaten. 0 = no coin eaten.
	ArrayList<Integer> coinsx = new ArrayList<Integer>(); // array list with integers; contains x values of coins
	ArrayList<Integer> coinsy = new ArrayList<Integer>(); // array list with integers; cointains y values of coins
	int cooldown = 0; // cooldown variable. This variable can speed up or slow down the ghost's movement. Basically a timer that controls how often the ghost can update its position.
	boolean[] collision; //boolean array tells us if the user has collided with any of the ghosts
	//class variables
	static int lives = 1; //# of lives remaining
	static int coinseaten = 0; //# of coins eaten
	static int ghostseaten = 0; //# of ghosts eaten
/*
 * Purpose: gets images like the background, power pellets, etc. Adds to the ghosts array an appropriate number of ghosts needed for the game. Start a GUI timer, and add KeyListener for the game. Basically prepare for the game’s start. 
 * Pre:int numGhosts: represents number of ghosts to be added to the game
 * Post: N/A
 */
	public MainPanel(int numGhosts) { // Class constructor
		super(); // create JPanel
		Toolkit kit = Toolkit.getDefaultToolkit(); // use Toolkit to get images easier
		background = kit.getImage(getClass().getClassLoader().getResource("background_coins_588_651.png")); // get background maze map
		blacksquare = kit.getImage(getClass().getClassLoader().getResource("black_square_6x6.png")); // get black square image
		blacksquare = blacksquare.getScaledInstance(8, 8, Image.SCALE_SMOOTH); //make square 8x8
		powerpellet = kit.getImage(getClass().getClassLoader().getResource("coin.png")); //get power pellets image
		powerpellet = powerpellet.getScaledInstance(8, 8, Image.SCALE_SMOOTH); //make power pellets image 8x8
		scaredghosts = kit.getImage(getClass().getClassLoader().getResource("scaredGhost.png")); //get scared ghost image
		numberOfGhosts = numGhosts; //save # of ghosts
		ghosts = new Ghost[numGhosts]; //make appropriate number of Ghost objects
		collision = new boolean[numberOfGhosts]; //create appropriate number of Ghost collision checkers 
		for (int i = 0; i < numGhosts; i++) { //loop for # of ghosts
			int diff = i / 4; //cycle through all 4 colours
			ghosts[i] = new Ghost(i - (diff * 4)); //initialize Ghost objects
		}
		setDoubleBuffered(true); // set double buffering to true
		addKeyListener(this); // add keylistener
		setFocusable(true); // set focusable to true
		tm.start(); // start sleep timer for GUI
	}

	/*
	 * Purpose: paints images onto the GUI
	 * Pre: Graphics comp; used to paint images onto the screen
	 * Post: N/A
	 */
	public void paintComponent(Graphics comp) { // paint images onto the GUI
		Graphics2D comp2D = (Graphics2D) comp; // create Graphics2D object that will help us draw images onto the screen
		comp2D.drawImage(background, 0, 0, this); // draw background onto the screen
		//draw all 4 power pellets
		comp2D.drawImage(powerpellet, 25, 68, this); 
		comp2D.drawImage(powerpellet, 553, 68, this);
		comp2D.drawImage(powerpellet, 25, 489, this);
		comp2D.drawImage(powerpellet, 553, 489, this);
		for (int i = 0; i < coinsx.size(); i++) { // loop for the length of the arraylist coinsx
			comp2D.drawImage(blacksquare, coinsx.get(i) - 1, coinsy.get(i) - 1, this); // draw black squares where coins have been eaten
		}
		comp2D.drawImage(player.pacmanimage(), player.getX(), player.getY(), this); // draw Pac-Man onto GUI
		for (int i = 0; i < numberOfGhosts; i++) { //loop for the amount of ghosts necessary
			if (scared == true) { //if the ghosts are scared b/c Pac-Man ate a power pellet
				comp2D.drawImage(scaredghosts, ghosts[i].getDrawingX(), ghosts[i].getDrawingY(), this); // Draw scared ghosts onto GUI
																										
			} else { //if not scared
				comp2D.drawImage(ghosts[i].getImage(), ghosts[i].getDrawingX(), ghosts[i].getDrawingY(), this); // Draw normal ghosts onto GUI
			}
		}

	}

	/*
	 * Purpose: event handler method for action listener. Helps to move the ghosts and the player, as well as the player’s collision detection. Method also is used to count number of ghosts eaten and the status of the ghosts (scared or not). Also calls the repaint() method. This is an all purpose method. 
	 * Pre: ActionEvent e; constantly checks for an action performed
	 * Post: N/A 
	 */
	public void actionPerformed(ActionEvent e) {
		player.move(); // move pac-man
		player.collisionDetection(); // look for pac-man collisions with walls
		cooldown++; // increment cooldown timer
		if (cooldown == 2) { // if cooldown timer = 2
			for (int i = 0; i < numberOfGhosts; i++) { //for every ghost
				ghosts[i].move(); //move ghost
			}
			cooldown = 0; // reset the timer
		}
		check = 0; // set check variable to 0
		check = coins.check(player.getX(), player.getY()); // set check variable using Coins class method. This basically tells us if a coin got eaten and if it did, the program will add the coin's location to paint over with black image.
		if (check == 1 || check == 2) {// if coin got eaten or power pellet got eaten
			coinseaten++; //increase # of coins eaten
			coinsx.add(coins.getX()); // add to coinsx array. This will be used to paint over eaten coins.
			coinsy.add(coins.getY()); // add to coinsy array. This will be used to paint over eaten coins.
			if (check == 2) { //if the coin eaten was a power pellet
				ghostTimer = new TimerGame(5); //start this ghostTimer counting down from 5. For 5s, the User can eat ghosts
			}
		}
		if (ghostTimer.timer <= 0) { //If 5 seconds is up
			scared = false; //return ghosts to normal state
		} else { 
			scared = true;
		}
		for (int i = 0; i < numberOfGhosts; i++) { // repeat ghost # amount of times
			if (collision[i] == false) { // if no collisions detected between player and ghosts
				collision[i] = CollisionWithGhost(ghosts[i]); // look for more collisions between user and ghosts
			} else {// if there already has been a collision
				break; // break loop
			}
		}
		repaint(); // repaint the images
	}

	/*
	 * Purpose: Detects if Pac-Man has collided with a ghost
	 * Pre: Ghost ghost; this variable houses the properties (x and y values) of a specific ghost
	 * Post: true or false depending on if the User touched a ghost. 
	 */
	public boolean CollisionWithGhost(Ghost ghost) {
		int pcenteredx = player.getX() + 15; // get centered x value of pac man
		int pcenteredy = player.getY() + 15; // get centered y value of pac man
		int gcenteredx = ghost.getDrawingX() + 15; // get centered x value of ghost in question
		int gcenteredy = ghost.getDrawingY() + 15; // get centered y value of ghost in question
		if (pcenteredx > gcenteredx - 15 && pcenteredx < gcenteredx + 15 && pcenteredy > gcenteredy - 15 && pcenteredy < gcenteredy + 15 && scared == false) {// if collided & Pac-Man hasn't eaten a power pellet
			lives--; //decrease lives 
			player.reset(); //reset Pac-Man to original position 
			return true; // return true
		} else if (pcenteredx > gcenteredx - 15 && pcenteredx < gcenteredx + 15 && pcenteredy > gcenteredy - 15 && pcenteredy < gcenteredy + 15 && scared == true) { //if there was a collision and Pac-Man did eat a power pellet
			ghost.reset(); //reset that ghost back to inside the gate
			ghostseaten++; //increase ghosts eaten
		}
		return false;// if no collision return false
	}

	/*
	 * Method checks if the game has ended in a loss
	 * Pre: N/A
	 * Post: returns a true or false. If true, the user has lost the game.
	 */
	public boolean endGameLose() {
		for (int i = 0; i < numberOfGhosts; i++) { // loop for all ghosts
			if (collision[i] == true) { // if a collision has happened
				if (lives == 0) //if a collision happened and lives = 0
					return collision[i]; // return true; user has lost
				collision[i] = false; //if user collided but still has lives, reset this collision array index back to false
			}
		}
		return false;
	}

	/*
	 * Purpose: Method checks if the user has won
	 * Pre: N/A
	 * Post: true or false based on if the user won or not
	 */
	public boolean endGameWin() {
		if (coinseaten >= 244) { //if user ate all coins
			return true; //return true
		}
		return false; //return false if user hasn't won
	}

	/*
	 * Purpose: event handler method for KeyListener. Handles Pac-Man's movement.
	 * Pre: KeyEvent e; key that was pressed 
	 * Post: N/A
	 */
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode(); // get keycode of the key that was pressed
		if (keycode == KeyEvent.VK_LEFT) { // if key pressed was left
			player.moveLeft(); // move pac-man left
		} else if (keycode == KeyEvent.VK_UP) { // if key pressed was up
			player.moveUp();// move pac-man up
		} else if (keycode == KeyEvent.VK_RIGHT) { // if key pressed was right
			player.moveRight(); // move pac-man right
		} else if (keycode == KeyEvent.VK_DOWN) { // if key pressed was down
			player.moveDown(); // move pac-man down
		}
	}

	//mandatory overridden methods
	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}