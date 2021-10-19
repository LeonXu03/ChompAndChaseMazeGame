package game;
/*
 * Name: Leon Xu
 * Purpose: TimerGame class
 * Date: January 8, 2021
 */
public class TimerGame implements Runnable {

	int timer; //timer value

	/*
	 * Purpose: saves startTime value to local instance variable int timer. This is the number that we will count down from. Starts runner thread.
	 * Pre: int startTime; represents time to start counting down from
	 * Post: N/A 
	 */
	public TimerGame(int startTime) {
		timer = startTime; //set object timer value to startTime. Counts from this number to 0. 
		Thread runner = new Thread(this); //create thread runner
		runner.start(); //run runner thread
	}

	/*
	 * Purpose: method runs b/c of Runnable implementation and the Thread object running. This method will wait 1s between decreasing the timer by 1. This is where the stopwatch will “count down”.
	 * Pre: N/A
	 * Post: N/A
	 */
	public void run() { 
		while (timer >= 0) { //repeats only if timer is >=0
			try { //wait 1s
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timer--; //decrease timer by 1. 
		}
	}
}