package game;
/*
 * Name: Leon Xu
 * Purpose: Ghost class for Pac-Man game
 * Date: January 8, 2021
 */
import java.awt.Image;
import java.awt.Toolkit;

public class Ghost implements Runnable {
	private Image Ghost; // variable for Ghost image
	private int gx = 280; // x value of Ghost
	private int gy = 290; // y value of Ghost
	private int gvx = 0; // x velocity of Ghost
	private int gvy = 0; // y velocity of Ghost
	private final int gwidth = 40; // width of Ghost + extra 10 for collision detection. Actual value is 30.
	private final int gheight = 40; // height of Ghost + extra 10 for collision detection. Actual value is 30.
	private int direction; // direction variable. 0 = right, 1 = down, 2 = left, 3 = up.
	boolean leavegate = false; // variable tells program if the ghost has left its origin gate
	// String 2D array houses the image locations for all 4 ghosts
	String[][] imagelocation = { { "g_r_r.png", "g_r_d.png", "g_r_l.png", "g_r_u.png" },
			{ "g_b_r.png", "g_b_d.png", "g_b_l.png", "g_b_u.png" },
			{ "g_o_r.png", "g_o_d.png", "g_o_l.png", "g_o_u.png" },
			{ "g_p_r.png", "g_p_d.png", "g_p_l.png", "g_p_u.png" } };
	Image[] arrayofanimation = new Image[4]; // array used to store images of the appropriate ghost
	int[] directionsright = { 0, 3, 1 }; // movement options if ghost is moving right
	int[] directionsdown = { 1, 0, 2 };// movement options if ghost is moving down
	int[] directionsleft = { 2, 1, 3 };// movement options if ghost is moving left
	int[] directionsup = { 3, 2, 0 };// movement options if ghost is moving up
	int[] check = { 0, 0, 0 }; // Basically this array is set to 0 and 1 for all 3 directions that the ghost can go in. 0 = can't go, 1 = can go.
	Boundaries[] ghost = new Boundaries[3]; // 3 Boundaries objects is used by the ghost to check which direction is valid to move in and which directions leads to a collision.
	boolean atleastone;// atleastone variable is true if there is at least one path the ghost can move in, and false if there aren't any paths the ghost can move in.
	TimerGame timer = new TimerGame(15); //create 15s countdown timer
	/*
	 * Purpose: depending on the ghostnum number, a specific ghost colour and associated sprites are added to the arrayofanimation array. Thread runner is created and started. 
	 * Pre: int ghostnum; represents colour of ghost. 0 = red, 1 = blue, 2 = orange, 3 = pink.
	 * Post: N/A
	 */
	public Ghost(int ghostnum) {// class constructor
		// int ghostnum represents which ghost colour this object is referring to. (i.e. ghostnum = 0 is red, ghostnum = 3 is pink)
		Toolkit kit = Toolkit.getDefaultToolkit(); // use Toolkit to get images easier
		for (int i = 0; i < arrayofanimation.length; i++) { // loop 4 times
			arrayofanimation[i] = kit.getImage(getClass().getClassLoader().getResource(imagelocation[ghostnum][i])); // put images into arrayofanimation array
		}
		Ghost = arrayofanimation[3]; // set Ghost image to facing up by default
		Thread runner = new Thread(this); // create a thread for current object
		runner.start(); // start thread
	}

	/*
	 * Purpose: Move method updates the x and y values of the ghost
	 * Pre: N/A
	 * Post: N/A
	 */
	public void move() {
		gx = gx + gvx; // update x
		gy = gy + gvy; // update y
	}

	/*
	 * Purpose: moveUp method sets the ghost moving up
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveUp() {
		gvx = 0; // set x velocity to 0
		gvy = -1; // change y velocity to move up
		direction = 3; // set direction to 3
	}

	/*
	 * Purpose: moveLeft method sets the ghost moving left
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveLeft() {
		gvx = -1; // change x velocity to move left
		gvy = 0; // set y velocity to 0
		direction = 2; // set direction to 2
	}

	/*
	 * Purpose: moveDown method sets the ghost moving down
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveDown() {
		gvx = 0; // set x velocity to 0
		gvy = 1; // set y velocity to move down
		direction = 1; // set direction to 1
	}

	/*
	 * Purpose: moveRight method sets the ghost moving right
	 * Pre: N/A
	 * Post: N/A
	 */
	public void moveRight() {
		gvx = 1; // set x velocity to move right
		gvy = 0; // set y velocity to 0
		direction = 0; // set direction to 0
	}

	/*
	 * Purpose: method returns the X value of Ghost + 5. +5 just centers the ghost better.
	 * Pre: N/A
	 * Post: returns x value of ghost + 5
	 */
	public int getDrawingX() {
		return gx + 5;
	}

	/*
	 * Purpose: method returns the Y value of Ghost + 5. +5 just centers the ghost better.
	 * Pre: N/A
	 * Post: returns y value of ghost + 5.
	 */
	public int getDrawingY() {
		return gy + 5;
	}

	/*
	 * Purpose: resets the Ghost back to the gate.
	 * Pre: N/A
	 * Post: N/A
	 */
	public void reset() {
		gx = 280; //reset Ghost x value to beginning
		gy = 290; //reset Ghost y value to beginning
		leavegate = false; //set leavegate boolean to false
		moveUp(); //start moving the ghost up again
		timer = new TimerGame(15); //reset the timer to scatter the ghost
	}

	/*
	 * Purpose: returns the current image of the Ghost.
	 * Pre: N/A
	 * Post: Image Ghost; represents the current Ghost's image (direction & colour)
	 */
	public Image getImage() {
		return Ghost;
	}

	/*
	 * Purpose: ghostmovement method controls all of the Ghost's movement & changes sprite to match movement direction
	 * Pre: N/A
	 * Post: N/A
	 */
	boolean chase = true; //tells program if Ghosts should be scattering or chasing the palyer
	int [] distancetopac = new int[3]; //array stores distances to the player given different paths (ex: distance to user if ghost moves left, if ghost moves up, etc)
	public void ghostmovement() {

		// stage 1 of ghost movement: leaving the gate

		while (leavegate == false) { // while the ghost hasn't left the gate
			if (gy > 235) { // if y value is greater than 235
				this.moveUp();
			} else {// if y value is <=235
				leavegate = true; // tell program that the ghost has left the gate
			}
			try {
				Thread.sleep(10); // put thread to sleep for 10ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// stage 2 of ghost movement: outside of the gate

		while (true) { // loop indefinitely
			int max = 0; //max distance to pac-man
			int min = 1000;  //min distance to pac-man
			int [] priority = {2,2,2}; //which path should be given priority array (lowest distance = highest priority = 3)
			boolean maxtaken = false; //tells program if one of the priorities is already the highest (3)
			boolean mintaken = false; //tells program if one of the priorities is already the lowest (1)
			int [] prioritydirections = new int [3]; //array that uses the priority array and puts which direction pac-man should travel in to best chase the player
			atleastone = false; // reset atleastone to false
			if(timer.timer==-1) {timer = new TimerGame(15);} //reset timer if we get to -1
			if(timer.timer>=5) {chase=false;} //if 10s is up of the 15s, for the next 5s chase the User
			else {chase=true;}; //if 10s of 15s is happening, make the Ghosts "scatter" or move in random directions
			if (direction == 3) { // if the ghost is moving up
				ghost[0] = new Boundaries(gx, gy - 5, gwidth, gheight); // check up path
				ghost[1] = new Boundaries(gx - 5, gy, gwidth, gheight); // check left path
				ghost[2] = new Boundaries(gx + 5, gy, gwidth, gheight); // check right path
				for (int i = 0; i < 3; i++) { // loop 3 times
					if (ghost[i].HitBoundary() == false) { // if path doesn't lead to collision
						check[i] = 1; // set check for this direction to 1
					} else { // if path leads to collision
						check[i] = 0; // set check for this direction to 0
					}
					//get distance to pac-man for all 3 directions
					distancetopac[i]=(int)(Math.sqrt((((ghost[i].x-PacMan.px)*(ghost[i].x-PacMan.px))+((ghost[i].y-PacMan.py)*(ghost[i].y-PacMan.py)))));
				}
				for (int i = 0; i < 3; i++) { // repeat 3 times
					if (check[i] == 1) { // if a path is available
						atleastone = true; // set atleastone variable to true
					}
					if(distancetopac[i]>max) {max = distancetopac[i];} //get max distance 
					if(distancetopac[i]<min) {min = distancetopac[i];} //get min distance
 				}
				for(int i = 0; i<3; i++) { //loop for each direction
					if(distancetopac[i]==max && maxtaken==false) {priority[i]=1; maxtaken=true;} //if the path is the furthest away, set priority to 1
					if(distancetopac[i]==min && mintaken==false) {priority[i]=3; mintaken=true;} //if path is the closest to Pac-Man, set priority to the highest 3
				}
				for(int i = 0; i<3; i++) { //loop for each direction
					if(priority[i]==3) { prioritydirections[0]=i;} //set direction closest to pac-man to first index of prioritydirections array
					if(priority[i]==2) { prioritydirections[1]=i;} //set direction 2nd closest to pac-man to second index of prioritydirections array
					if(priority[i]==1) { prioritydirections[2]=i;} //set furthest direction to pac-man to last index of prioritydirections array
				}
//				System.out.println("Distance to pac: " + distancetopac[0] + " " + distancetopac[1] + " " + distancetopac[2]);
//				System.out.println("Max & Min: " + max + " " + min);
//				System.out.println("Priorities: " + priority[0] + " " + priority[1] + " " + priority[2]);
//				System.out.println("Priorities Direction: " + prioritydirections[0] + " " + prioritydirections[1] + " " + prioritydirections[2]);
				if (atleastone == true) { // if path is available
					if(chase==true) { //if Ghost are in chase mode
						int p = 0; //index counter
						while(check[prioritydirections[p]]==0) {p++;}; //while the closest path Pac-Man can go in is blocked by a wall, update index to go to take the next closest path
						direction = directionsup[prioritydirections[p]]; //set direction to the best available direction to chase Pac-Man
					}
					else { //if in random scatter mode
					int randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0); // get randomnumber between 0 and 2
					while (check[randomnumber] == 0) { // repeat if the path selected by randomnumber leads to a collision
						randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0);
					}
					direction = directionsup[randomnumber]; // set direction to the random number
					}
					if (direction == 3) { // if direction is up
						moveUp(); // move up
					} else if (direction == 0) { // if direction is right
						moveRight(); // move right
					} else { // if direction is left
						moveLeft(); // move left
					}
					try {
						Thread.sleep(35); // put thread to sleep
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (direction == 0) {// if ghost is moving right
				ghost[0] = new Boundaries(gx + 5, gy, gwidth, gheight); // check right path
				ghost[1] = new Boundaries(gx, gy - 5, gwidth, gheight); // check up path
				ghost[2] = new Boundaries(gx, gy + 5, gwidth, gheight); // check down path
				for (int i = 0; i < 3; i++) { // loop 3 times
					if (ghost[i].HitBoundary() == false) { // if path doesn't lead to a collision
						check[i] = 1; // set check for this direction to 1
					} else { // if path leads to collision
						check[i] = 0; // set check for this direction to 0
					}
					//get distance to pac-man for all 3 directions
					distancetopac[i]=(int)(Math.sqrt((((ghost[i].x-PacMan.px)*(ghost[i].x-PacMan.px))+((ghost[i].y-PacMan.py)*(ghost[i].y-PacMan.py)))));
				}
				for (int i = 0; i < 3; i++) { // repeat 3 times
					if (check[i] == 1) { // if a path is available
						atleastone = true; // set atleastone variable to true
					}
					if(distancetopac[i]>max) {max = distancetopac[i];} //get max distance
					if(distancetopac[i]<min) {min = distancetopac[i];} //get min distance
				}
				for(int i = 0; i<3; i++) { //loop all directions
					if(distancetopac[i]==max && maxtaken==false) {priority[i]=1; maxtaken=true;} //if the path is the furthest away, set priority to 1
					if(distancetopac[i]==min && mintaken==false) {priority[i]=3; mintaken=true;} //if path is the closest to Pac-Man, set priority to the highest 3
				}
				for(int i = 0; i<3; i++) { //loop all directions
					if(priority[i]==3) { prioritydirections[0]=i;} //set direction closest to pac-man to first index of prioritydirections array
					if(priority[i]==2) { prioritydirections[1]=i;} //set direction 2nd closest to pac-man to second index of prioritydirections array
					if(priority[i]==1) { prioritydirections[2]=i;} //set furthest direction to pac-man to last index of prioritydirections array
				}
				if (atleastone == true) {// if there is a path available
					if(chase==true) { //if Ghost are in chase mode
						int p = 0;  //index counter
						while(check[prioritydirections[p]]==0) {p++;}; //while the closest path Pac-Man can go in is blocked by a wall, update index to go to take the next closest path
						direction = directionsright[prioritydirections[p]]; //set direction to the best available direction to chase Pac-Man
					}
					else { //if in random scatter mode
					int randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0); // get random num between 0 and 2
					while (check[randomnumber] == 0) { // while the path selected by random number leads to a collision
						randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0);
					}
					direction = directionsright[randomnumber]; // set direction to the random number
					}
					if (direction == 3) { // if direction is up
						moveUp(); // set ghost moving up
					} else if (direction == 0) { // if direction is right
						moveRight(); // set ghost moving right
					} else { // if direction is down
						moveDown(); // set ghost moving down
					}
					try {
						Thread.sleep(35); // put thread to sleep
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (direction == 1) { // if direction is down
				ghost[0] = new Boundaries(gx, gy + 5, gwidth, gheight); // check down path
				ghost[1] = new Boundaries(gx + 5, gy, gwidth, gheight); // check right path
				ghost[2] = new Boundaries(gx - 5, gy, gwidth, gheight); // check left path
				for (int i = 0; i < 3; i++) { // repeat 3 times
					if (ghost[i].HitBoundary() == false) { // if path doesn't lead to a collision
						check[i] = 1; // set check for this direction to 1
					} else {// if path leads to a collision
						check[i] = 0; // set check for this direction to 0
					}
					//get distance for all 3 directions
					distancetopac[i]=(int)(Math.sqrt((((ghost[i].x-PacMan.px)*(ghost[i].x-PacMan.px))+((ghost[i].y-PacMan.py)*(ghost[i].y-PacMan.py)))));
				}
				for (int i = 0; i < 3; i++) {// repeat 3 times
					if (check[i] == 1) { // if path is available
						atleastone = true; // set atleastone variable to true
					}
					if(distancetopac[i]>max) {max = distancetopac[i];} //get max distance
					if(distancetopac[i]<min) {min = distancetopac[i];} //get min distance
				}
				for(int i = 0; i<3; i++) { //loop all 3 directions
					if(distancetopac[i]==max && maxtaken==false) {priority[i]=1; maxtaken=true;}  //if the path is the furthest away, set priority to 1
					if(distancetopac[i]==min && mintaken==false) {priority[i]=3; mintaken=true;} //if path is the closest to Pac-Man, set priority to the highest 3
				}
				for(int i = 0; i<3; i++) { //loop all 3 directions
					if(priority[i]==3) { prioritydirections[0]=i;} //set direction closest to pac-man to first index of prioritydirections array
					if(priority[i]==2) { prioritydirections[1]=i;} //set direction 2nd closest to pac-man to second index of prioritydirections array
					if(priority[i]==1) { prioritydirections[2]=i;} //set furthest direction to pac-man to last index of prioritydirections array
				}
				if (atleastone == true) {// if path is available
					if(chase==true) { //if Ghosts are in chase mode
						int p = 0; //index counter
						while(check[prioritydirections[p]]==0) {p++;}; //while the closest path Pac-Man can go in is blocked by a wall, update index to go to take the next closest path
						direction = directionsdown[prioritydirections[p]]; //set direction to the best available direction to chase Pac-Man
					}
					else { //if Ghosts are in scatter mode
					int randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0); // get random number between 0 and 2
					while (check[randomnumber] == 0) { // repeat if path selected by randomnumber leads to a collision
						randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0);
					}
					direction = directionsdown[randomnumber]; // set new direction
					}
					if (direction == 1) { // if direction is down
						moveDown(); // set ghost moving down
					} else if (direction == 0) { // if direction is right
						moveRight(); // set ghost moving right
					} else { // if direction is left
						moveLeft(); // set ghost moving to the left
					}
					try {
						Thread.sleep(35);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (direction == 2) { // if direction is to the left
				ghost[0] = new Boundaries(gx - 5, gy, gwidth, gheight); // check left path
				ghost[1] = new Boundaries(gx, gy + 5, gwidth, gheight); // check down path
				ghost[2] = new Boundaries(gx, gy - 5, gwidth, gheight); // check up path
				for (int i = 0; i < 3; i++) { // loop 3 times
					if (ghost[i].HitBoundary() == false) { // if path doesn't lead to a collision
						check[i] = 1;// set check for this direction to 1
					} else { // if path leads to a collision
						check[i] = 0; // set check for this direction to 0
					}
					//get distance for all 3 directions
					distancetopac[i]=(int)(Math.sqrt((((ghost[i].x-PacMan.px)*(ghost[i].x-PacMan.px))+((ghost[i].y-PacMan.py)*(ghost[i].y-PacMan.py)))));
				}
				for (int i = 0; i < 3; i++) {// loop 3 times
					if (check[i] == 1) {// if there is a path available
						atleastone = true; // set atleastone to true
					}
					if(distancetopac[i]>max) {max = distancetopac[i];} //get max distance
					if(distancetopac[i]<min) {min = distancetopac[i];} //get min distance
				}
				for(int i = 0; i<3; i++) { //loop for all directions 
					if(distancetopac[i]==max && maxtaken==false) {priority[i]=1; maxtaken=true;}  //if the path is the furthest away, set priority to 1
					if(distancetopac[i]==min && mintaken==false) {priority[i]=3; mintaken=true;} //if path is the closest to Pac-Man, set priority to the highest 3
				}
				for(int i = 0; i<3; i++) { //loop all 3 directions
					if(priority[i]==3) { prioritydirections[0]=i;} //set direction closest to pac-man to first index of prioritydirections array
					if(priority[i]==2) { prioritydirections[1]=i;} //set direction 2nd closest to pac-man to second index of prioritydirections array
					if(priority[i]==1) { prioritydirections[2]=i;} //set furthest direction to pac-man to last index of prioritydirections array
				}
				if (atleastone == true) { // if there is a path
					if(chase==true) { //if Ghost chase mode is true 
						int p = 0; //index counter
						while(check[prioritydirections[p]]==0) {p++;}; //while the closest path Pac-Man can go in is blocked by a wall, update index to go to take the next closest path
						direction = directionsleft[prioritydirections[p]]; //set direction to the best available direction to chase Pac-Man
					}
					else { //if Ghost scatter mode is on
					int randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0);// get a random number
					while (check[randomnumber] == 0) { // repeat if the path selected by randomnumber leads to a
														// collision
						randomnumber = (int) (Math.random() * (2 - 0 + 1) + 0);
					}
					direction = directionsleft[randomnumber];// set new direction
					}
					if (direction == 1) {// if direction is down
						moveDown(); // set ghost moving down
					} else if (direction == 2) { // if direction is left
						moveLeft(); // set ghost moving left
					} else { // if direction is up
						moveUp(); // set ghost moving up
					}
					try {
						Thread.sleep(35); // put thread to sleep
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (gx > 588 + gwidth)
				gx = -gwidth; // if ghost goes off screen to the right, put the ghost in the tunnel to the left
			if (gx < -gwidth)
				gx = 588 + gwidth; // if the ghost goes off screen to the left, put the ghost in the tunnel to the right
			Ghost = arrayofanimation[direction]; //depending on which way the ghost is moving, update the Ghot image
			try {
				Thread.sleep(10); // put thread to sleep
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
/*
 * Purpose: method runs b/c of Runnable implementation and the Thread object running. This method will call on the ghostmovement() method. 
 * Pre: N/A
 * Post: N/A
 */
	public void run() { // run method executes b/c of runnable implementation and thread runner starting
		ghostmovement(); // call method ghostmovement
	}
}