package game;
/*
 * Name: Leon Xu
 * Date: January 8, 2021
 * Purpose: Coins class
 */

public class Coins{ 
	int cheight = 6; // height of a coin
	int cwidth = 5; // width of a coin
	int pacwidth = 30; // width of Pac-Man
	int pacheight = 30; // height of Pac-Man
	int px, py; // x and y values of Pac-Man
	int[] coinsx = { 26, 47, 69, 90, 111, 132, 153, 174, 195, 216, 237, 258, 279, 301, 322, 343, 364, 385, 406, 427, 448, 469, 490, 512, 533, 554 }; // x values of coins
	int[] coinsxforpac = { 26, 47, 69, 90, 111, 132, 153, 174, 195, 216, 237, 249, 279, 301, 322, 343, 364, 385, 406, 427, 448, 469, 490, 512, 533, 545 }; // x values of coins that Pac-Man can actually reach (because of the walls and the width of pac-man, the x values of some coins are beyond Pac-Man's x coordinate reach)
	int[] coinsy = { 26, 47, 69, 90, 111, 132, 153, 174, 195, 216, 237, 258, 279, 300, 322, 343, 364, 385, 406, 427, 448, 469, 490, 511, 532, 553, 575, 596, 617 }; // y values of coins
	int[][] CoinGrid = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 2D array coin grid. 1 = coin is there, 0 = coin is not there. 
			{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 2, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 2 },
			{ 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 },
			{ 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
	int[] yvalues = new int[30]; // y value ranges for row #. Basically if Pac-Man's y value is between a certain top number and a certain bottom number, then he is in row x (where x is a row int).
	int coinx; // x value of coin eaten to be returned
	int coiny; // y value of coin eaten to be returned
	
	/*
	 * Purpose:  adds values to yvalues array which represents the different y values of different rows
	 * Pre: N/A
	 * Post: N/A
	 */
	public Coins() { // Class constructor
		yvalues[0] = 7; // top y value of the map
		for (int i = 1; i < yvalues.length - 1; i++) { // loop
			yvalues[i] = (40 + (21 * (i - 1))); // y levels differentiate by ~21 px. These lines are drawn horizontally between coins.
		}
		yvalues[29] = 643; // bottom y value of the map
	}

	/*
	 * Purpose: getX method returns x value of coin that was eaten
	 * Pre: N/A
	 * Post: int coinx; x value of coin eaten
	 */
	public int getX() {
		return coinx;
	}

	/*
	 * Purpose: getY method returns Y value of coin that was eaten
	 * Pre: N/A
	 * Post: int coiny; y value of coin eaten
	 */
	public int getY() {
		return coiny;
	}

	/*
	 * Purpose: check method returns an integer based on if a coin/power pellet/no coin was eaten
	 * Pre: int px and py, represents the current x and y value of Pac-Man
	 * Post: an integer 0, 1, or 2. 0 = no coin eaten, 1 = coin eaten, 2 = power pellet eaten
	 */
	public int check(int px, int py) {
		this.px = (px); // set local px value to Pac-Man's current x value
		this.py = (py); // set local py value to Pac-Man's current y value
		
		// make px and py equal to the center of pac-man instead of the top left
		px = px + 15;
		py = py + 15;

		int i = 0; // i value tells program the row the user is in

		for (int p = 0; p < yvalues.length - 1; p++) { // loop for yvalues.length - 1 times
			if (py >= (yvalues[p]) && py < (yvalues[p + 1])) { // if the user is within certain y values, for example between yvalues[0] and yvalues[1], the user is in row 0. Check which row the User is in.
				i = p; // set row # to i
			}
		}

		for (int p = 0; p < coinsxforpac.length; p++) { // loop for coinsxforpac.length
			if (px > (coinsxforpac[p] - 15) && px < (coinsxforpac[p] + cwidth + 15)) { // checks if the user is within a coin's x value range. If this is true...
				if (CoinGrid[i][p] == 1) { // Check if a coin is still there. 1 on the CoinGrid means that a coin is there.
					coinx = coinsx[p]; // set value of coinx to the x value of the coin that was eaten
					coiny = coinsy[i]; // set value of coiny to the y value of the coin that was eaten
					CoinGrid[i][p] = 0;// set this coin status to eaten
					return 1; // return 1; coin was eaten
				} else if (CoinGrid[i][p] == 2) { //Check if the coin eaten was a power pellet. If it was...
					coinx = coinsx[p]; // set value of coinx to the x value of the coin that was eaten
					coiny = coinsy[i]; // set value of coiny to the y value of the coin that was eaten
					CoinGrid[i][p] = 0; // set this coin status to eaten
					return 2; // return 2; power pellet/coin was eaten
				}
			}
		}

		return 0; // return 0; no coin was eaten

	}

}