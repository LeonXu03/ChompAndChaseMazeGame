package game;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
/*
 * Name: Leon Xu
 * Purpose: PacMan class
 * Date: January 8, 2021
 */
public class PacMan extends JPanel implements Runnable {
	private Image pacman; // variable for pac-man image
	static int px = 20; // x value of pac-man
	static int py = 20; // y value of pac-man
	private int pvx = 0; // x velocity of pac-man
	private int pvy = 0; // y-velocity of pac-man
	private final int pacwidth = 30; // width of pac-man
	private final int pacheight = 30; // height of pac-man
	private int direction; // direction of Pac-Man's movement. Right = 0, down = 1, left = 2, up = 3. 
	String[][] imagelocation = { { "p.png", "p_r1.png", "p_r2.png", "p_r1.png" }, //image locations for animation in all 4 directions (right (row 0), down (row 1), left(row 2), up(row 3)). Number refers to which direction Pac-Man will be animated towards.
			{ "p.png", "p_d1.png", "p_d2.png", "p_d1.png" }, 
			{ "p.png", "p_l1.png", "p_l2.png", "p_l1.png" },
			{ "p.png", "p_u1.png", "p_u2.png", "p_u1.png" } };
	Image[][] arrayofanimation = new Image[4][4]; // 2D array used to store images of pac-man.
	boolean startmoving = false; // variable that tells the program if Pac-Man has started moving.

	/*
	 * Purpose: gets the images used for the animation of Pac-Man. Starts runner Thread.
	 * Pre:N/A
	 * Post: N/A
	 */
	public PacMan() {// class constructor
		Toolkit kit = Toolkit.getDefaultToolkit(); // use Toolkit to get images easier
		for (int i = 0; i < imagelocation.length; i++) { // loop for # of rows of 2D array
			for (int p = 0; p < imagelocation[0].length; p++) { // loop for # of cols of 2D array
				arrayofanimation[i][p] = kit.getImage(getClass().getClassLoader().getResource(imagelocation[i][p])); // fill in 2D array used to store images of Pac-Man with actual images of Pac-Man. Use the kit tool to get these images from the image locations array.
			}
		}
		Thread runner = new Thread(this); // create a thread for current object
		runner.start(); // start thread
	}

	/*
	 * Purpose: Method sets Pac-Man moving up
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveUp() {
		pvx = 0; // set x velocity to 0
		pvy = -1; // change y velocity to move up
		direction = 3; // set direction to 3
		startmoving = true; // tell program that user has indeed started moving
	}

	/*
	 * Purpose: Method sets Pac-Man moving left
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveLeft() {
		pvx = -1; // change x velocity to move left
		pvy = 0; // set y velocity to 0
		direction = 2; // set direction to 2
		startmoving = true; // tell program that user has indeed started moving
	}

	/*
	 * Purpose: Method sets Pac-Man moving down
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveDown() {
		pvx = 0; // set x velocity to 0
		pvy = 1; // set y velocity to move down
		direction = 1; // set direction to 1
		startmoving = true; // tell program that user has indeed started moving
	}

	/*
	 * Purpose: Method sets Pac-Man moving right
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveRight() {
		pvx = 1; // set x velocity to move right
		pvy = 0; // set y velocity to 0
		direction = 0; // set direction to 0
		startmoving = true; // tell program that user has indeed started moving
	}

	/*
	 * Purpose: move method updates Pac-Man's x and y values
	 * Pre: N/A
	 * Post: N/A
	 */
	public void move() {
		px = px + pvx; // update x
		py = py + pvy; // update y
	}

	/*
	 * Purpose: Method returns x value of Pac-Man
	 * Pre: N/A
	 * Post: int px; x value of Pac-Man
	 */
	public int getX() {
		return px;
	}

	/*
	 * Purpose: Method returns y value of Pac-Man
	 * Pre: N/A
	 * Post: int py; y value of Pac-Man
	 */
	public int getY() {
		return py;
	}
	/*
	 * Purpose: method resets Pac-Man back to top left hand corner
	 * Pre: N/A
	 * Post: N/A
	 */
	public void reset() {
		px=20;
		py=20;
	}

	/*
	 * Purpose: Method returns the sprite of Pac-Man
	 * Pre: N/A
	 * Post: Image pacman; image of Pac-Man
	 */
	public Image pacmanimage() {
		return pacman;
	}

	/*
	 * Purpose: detects collision between Pac-Man and the wall
	 * Pre: N/A
	 * Post: N/A
	 */
	public void collisionDetection() {
		// for escape tubes on left and right side
		if (px > 604 + pacwidth && py >= 283 && py <= 325 - pacheight) { //if Pac-Man went through right tube
			px = -pacwidth; //come out the left tube
		}
		if (px < -pacwidth && py >= 283 && py <= 325 - pacheight) { //if Pac-Man went through the left tube
			px = 604 + pacwidth; //come out the right tube
		}
		Boundaries hitwall = new Boundaries(px, py, pacwidth, pacheight); // Create object hitwall with values of Pac-Man
		if (hitwall.HitBoundary() == true) { // Method in the Boundaries class tells us if Pac-Man has collided with a wall. If this is true for the hitwall object...
			rebound(); // rebound Pac-Man from the wall
		}

	}

	/*
	 * Purpose: Method sends Pac-Man back 1 unit in a certain direction and halts the velocity in a certain direction. Used when Pac-Man has hit a wall boundary.
	 * Pre: N/A
	 * Post: N/A
	 */
	public void rebound() {
		if (pvx == 1) { // if bump happened when facing right
			px--; //move Pac-Man one unit left
			pvx = 0;  //set x velocity to 0
		} 
		if (pvy == 1) { //If Pac-Man was moving down
			py--; //bump Pac-Man up
			pvy = 0; //set y velocity to 0
		} 
		if (pvx == -1) { //If Pac-Man was moving left
			px++; //bump Pac-Man right 
			pvx = 0; //Set x velocity to 0
		} 
		if (pvy == -1) { //if Pac-Man was moving up
			py++; //bump Pac-Man down
			pvy = 0; //set Y velocity to 0
		} 
	}

	/*
	 * Purpose: animate method changes the Pac-Man sprite to look like it is biting in the direction that the user is moving
	 * Pre: N/A
	 * Post: N/A
	 */
	public void animate() {
		while (true) { // repeat indefinitely
			for (int i = 0; i < arrayofanimation.length; i++) { // cycle through animation sprites
				if (pvx != 0 || pvy != 0 || startmoving == false) { // if Pac-Man hasn't started moving or is moving without a collision
					pacman = arrayofanimation[direction][i]; // change pac-man sprite to appropriate direction and stage (no bite, half bite, full bite, etc)
				} else { // if Pac-Man has collided with a wall
					pacman = arrayofanimation[direction][1]; // pause the Pac-Man animation
				}
				try {
					Thread.sleep(50); // put thread to sleep
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/*
	 * Purpose: method runs b/c of Runnable implementation and the Thread object running. This method will call on the run() method.
	 * Pre: N/A
	 * Post: N/A
	 */
	public void run() {
		animate(); //call animate() method
	}
}