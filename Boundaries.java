package game;
/*
 * Name: Leon Xu
 * Date: January 8, 2021
 * Purpose: Boundaries class for Pac-Man game
 */
public class Boundaries {
	 int x, y, width, height;

	/*
	 * Purpose: class constructor saves parameters to local variables. These unique local variables will be used in the HitBoundary() method
	*  Pre: int x, int y, int width, int height; represents the x, y, width, and height of the entity in question (ghost or Pac-Man)
	*	Post: N/A
	 */
	public Boundaries(int x, int y, int width, int height) {
		this.x = x; //set object x to x
		this.y = y; //set object y to y
		this.width = width; //set object width to width 
		this.height = height; //set object height to height
	}

	/*
	 * Purpose: Returns true if the entity hits a wall, false if the entity doesn't
	 * Pre: N/A
	 * Post: boolean true or false
	 */
	public boolean HitBoundary() {

		// stop pac-man from going off screen to the left w/ exception of tunnel
		if (x < 11 && !(y >= 283 && y <= 325 - height)) {
			return true;
		}
		// stops pac-man from going off screen to the right w/ exception of tunnel
		else if (x > 577 - width && !(y >= 283 && y <= 325 - height)) {
			return true;
		}
		// stops pac-man from going off screen from the bottom
		else if (y > 640 - height) {
			return true;
		}
		// stop pac-man from going off screen from the top
		else if (y < 11) {
			return true;
		}

		// top level collision blocks (4x rectangles and 1x pillar)
		else if (x > 52 - width && x < 116 && y > 52 - height && y < 94) {
			return true;
		} else if (x > 157 - width && x < 241 && y > 52 - height && y < 94) {
			return true;
		} else if (x > 282 - width && x < 304 && y >= 11 && y < 94) {
			return true;
		} else if (x > 347 - width && x < 430 && y > 52 - height && y < 94) {
			return true;
		} else if (x > 473 - width && x < 535 && y > 52 - height && y < 94) {
			return true;
		}

		// top medium level collision blocks
		else if (x > 52 - width && x < 116 && y > 136 - height && y < 157) {
			return true;
		} else if (x > 157 - width && x < 179 && y > 136 - height && y < 283) {
			return true;
		} else if (x > 179 - width && x < 241 && y > 199 - height && y < 220) {
			return true;
		} else if (x > 220 - width && x < 367 && y > 136 - height && y < 157) {
			return true;
		} else if (x > 283 - width && x < 305 && y > 157 - height && y < 220) {
			return true;
		} else if (x > 409 - width && x < 430 && y > 136 - height && y < 283) {
			return true;
		} else if (x > 347 - width && x < 409 && y > 199 - height && y < 220) {
			return true;
		} else if (x > 473 - width && x < 535 && y > 136 - height && y < 157) {
			return true;
		} else if (x < 116 && y > 199 - height && y < 283) {
			return true;
		} else if (x > 473 - width && y > 199 - height && y < 283) {
			return true;
		}

		// bottom medium level collision blocks
		else if (x < 116 && y > 325 - height && y < 409) {
			return true;
		} else if (x > 157 - width && x < 179 && y > 325 - height && y < 409) {
			return true;
		} else if (x > 221 - width && x < 368 && y > 263 - height && y < 347) {
			return true;
		} else if (x > 221 - width && x < 368 && y > 388 - width && y < 409) {
			return true;
		} else if (x > 283 - width && x < 305 && y > 409 - height && y < 473) {
			return true;
		} else if (x > 409 - width && x < 430 && y > 325 - height && y < 409) {
			return true;
		} else if (x > 473 - width && y > 325 - height && y < 409) {
			return true;
		}

		// bottom 2 levels collision blocks
		else if (x > 52 - width && x < 116 && y > 451 - height && y < 473) {
			return true;
		} else if (x > 94 - width && x < 116 && y > 473 - height && y < 536) {
			return true;
		} else if (x < 52 && y > 515 - height && y < 536) {
			return true;
		} else if (x > 157 - width && x < 241 && y > 451 - height && y < 473) {
			return true;
		} else if (x > 52 - width && x < 241 && y > 577 - height && y < 599) {
			return true;
		} else if (x > 157 - width && x < 179 && y > 515 - height && y < 577) {
			return true;
		} else if (x > 220 - width && x < 368 && y > 515 - height && y < 536) {
			return true;
		} else if (x > 283 - width && x < 305 && y > 536 - height && y < 599) {
			return true;
		} else if (x > 347 - width && x < 430 && y > 451 - height && y < 473) {
			return true;
		} else if (x > 473 - width && x < 493 && y > 451 - height && y < 536) {
			return true;
		} else if (x > 473 - width && x < 535 && y > 451 - height && y < 473) {
			return true;
		} else if (x > 535 - width && y > 515 - height && y < 536) {
			return true;
		} else if (x > 347 - width && x < 535 && y > 577 - height && y < 599) {
			return true;
		} else if (x > 409 - width && x < 430 && y > 515 - height && y < 577) {
			return true;
		}

		//if no collision detected, return false
		return false;
	}

}