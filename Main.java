/*
 * Name: Leon Xu
 * Purpose: Main class is the hub of the main game after the User goes through the beginning menus (start class)
 * Date: January 8, 2021
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

public class Main extends JFrame implements Runnable {

	JFrame frame = new JFrame("Pac-Man Game"); //create game JFrame
	JFrame frameEnd = new JFrame("Pac-Man Loser"); //create lose JFrame
	JFrame frameWin = new JFrame("Pac-Man Winner"); //create win JFrame
	JPanel endPanel = new JPanel(); //create end JPanel
	MainPanel images; //this object will be where the game is drawn
	JPanel statsPanel = new JPanel(); //
	JPanel winnerpanel = new JPanel(); //If the user won, this is the panel that is put on the frameWin frame
	boolean endGameLose = false; //boolean endGameLose tells computer if User lost
	boolean endGameWin = false; //boolean endGameWin tells computer if the User won
	Font gameFont; //gameFont stores custom Pac-Man Font
	int livesv = 1; //lives value
	int coinseatenv = 0; //coins eaten value
	int time = 300; //time value
	int ghostseatenv = 0; //ghosts eaten value
	boolean test = false; //test is a boolean that makes a certain branch of code only run once
	JLabel livesvalue = new JLabel(""); //JLabel stores and displays # of lives
	JLabel coinseatenvalue = new JLabel(""); //JLabel stores and displays # of coins eaten
	JLabel timervalue = new JLabel(""); //JLabel stores and displays time remaining
	JLabel ghostseatenlbl = new JLabel(""); //JLabel stores and displays # of ghosts eaten
	JLabel title = new JLabel("Pac-Man"); //JLabel title with title of game
	JLabel lives = new JLabel("Lives"); //JLabel lives header
	JLabel coinseaten = new JLabel("Coins"); //JLabel coins eaten header 
	JLabel timelabel = new JLabel("Time"); //JLabel time header
	JLabel ghostseaten = new JLabel("Ghosts Eaten"); //JLabel ghosts eaten header
	int score = 0; //value of score 
	int multiplier = 0; //value of multiplier
	//PrintWriter and FileWriter objects
	PrintWriter pw; 
	FileWriter fw;
	TimerGame clock; //Clock object from TimerGame class
	JTextArea textArea; //text area to be animated with score that User got
	String text; //text that will be used to animate the score
	int i = 0; //index of character array

	/*
	 * Purpose: sets up custom font for game text, sets icons for frames, configures the main game frame and starts the game with the appropriate amount of ghosts
	 * Pre: int numGhosts; represents the number of ghosts to be added in the game
	 * Post: N/A
	 */
	public Main(int numGhosts) { //Class constructor
		try { // get Pac-Man font (from Youtube)
			gameFont = Font.createFont(Font.TRUETYPE_FONT,ResourceLoader.load("Joystix.TTF")).deriveFont(30f); //load and save Pac-Man custom font
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.load("Joystix.TTF")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

		//File file = new File("scores.txt"); //Access scores.txt file
//		ClassLoader classLoader = getClass().getClassLoader();
//		File file = new File(
//				classLoader.getResource("scores.txt").getFile()
//				);
//		try {
//			fw = new FileWriter(file, true); //direct filewriter to this file. true second argument makes it so that when we write to this file, we don't delete any previous text (from Youtube)
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		pw = new PrintWriter(fw); //make it so that we can print to this file
		
		//set icon images of JFrames
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("pacicon.png"));
		frame.setIconImage(image.getImage());
		frameEnd.setIconImage(image.getImage()); 
		frameWin.setIconImage(image.getImage());
		
		// set main game frame settings including size, resizability, close operation, and layout
		frame.setLayout(new GridLayout(1, 2)); //grid layout (1 row, 2 cols)
		frame.setSize(1176, 690); // default is 604 x 690
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		images = new MainPanel(numGhosts); //create the object where the game is going to be ran and mostly controlled.
		// second panel -> tells us the stats of what's going on in the game
		statsPanel.setBackground(Color.BLACK); //set colour of statsPanel to black
		statsPanel.setLayout(null); //set panel's layout to null

		// set images panel layout and boundaries
		images.setLayout(null);
		images.setBounds(0, 0, 588, 651);
		// add images object onto the main game frame
		frame.add(images);

		clock = new TimerGame(300); //clock object from TimerGame counts down from 300
		frame.setVisible(true); // make frame visible
		Thread runner = new Thread(this); //create a thread for this 
		runner.start(); //start thread 
	}

	/*
	 * Purpose: sets label's background and foreground colours
	 * Pre: JLabel label; label to be changed; Color c1, c2; colours of the background and foreground
	 * Post: N/A
	 */
	public void editLabel(JLabel label, Color c1, Color c2) {
		label.setBackground(c1); //change label background
		label.setForeground(c2); //change label foreground
	}

	/*
	 * Purpose: endGame() method constantly checks if the game has ended + sets up & updates the statsPanel labels. Once the game has ended, this method also calls onto win or lose methods to close out the game.
	 * Pre: N/A
	 * POst: N/A 
	 */
	public void endGame() {// endgame method
		while (endGameLose == false && endGameWin == false) { // loops if the user has not won or lost

			if (test == false) { //run once
				livesvalue = new JLabel(Integer.toString(livesv)); //put # of lives onto JLabel
				coinseatenvalue = new JLabel(Integer.toString(coinseatenv)); //put # of coins eaten onto JLabel
				timervalue = new JLabel(Integer.toString(time)); //put time remaining onto JLabel
				ghostseatenlbl = new JLabel(Integer.toString(ghostseatenv)); //put # of coins eaten onto JLabel

				// Title Label
				editLabel(title, Color.BLACK, Color.WHITE); //change title label colours
				gameFont = gameFont.deriveFont(50f); //change font size
				title.setFont(gameFont); //set title label to custom Font
				title.setBounds(160, 50, 300, 100); //set bounds

				// Lives Heading Label
				editLabel(lives, Color.BLACK, Color.WHITE); //change lives heading label colours
				gameFont = gameFont.deriveFont(20f); //change font size
				lives.setFont(gameFont); //set to custom font
				lives.setBounds(250, 200, 100, 30); //set bounds

				// Lives value counter label
				editLabel(livesvalue, Color.BLACK, Color.WHITE); //change colours of label
				livesvalue.setFont(gameFont); //set the label's font to custom font
				livesvalue.setBounds(275, 230, 100, 30); //set bounds

				//Coins eaten header label
				editLabel(coinseaten, Color.BLACK, Color.WHITE); //change colours
				coinseaten.setFont(gameFont); //set font to custom font
				coinseaten.setBounds(250, 300, 100, 30); //set bounds
				
				//Coins eaten actual value label
				editLabel(coinseatenvalue, Color.BLACK, Color.WHITE); //change colours
				coinseatenvalue.setFont(gameFont); //set font to custom font
				coinseatenvalue.setBounds(277, 328, 100, 30); //set bounds

				//Time header label
				editLabel(timelabel, Color.BLACK, Color.WHITE); //change colours
				timelabel.setFont(gameFont); //set font to custom font
				timelabel.setBounds(259, 395, 100, 30); //set bounds
				
				//Timer actual value label
				editLabel(timervalue, Color.BLACK, Color.WHITE); //change colours
				timervalue.setFont(gameFont); //set font to custom font
				timervalue.setBounds(266, 420, 100, 30); //set bounds

				//Ghosts eaten header label
				editLabel(ghostseaten, Color.BLACK, Color.WHITE); //change colours
				ghostseaten.setFont(gameFont); //set font to custom font
				ghostseaten.setBounds(210, 490, 200, 30); //set bounds

				//Ghosts eaten actual value label
				editLabel(ghostseatenlbl, Color.BLACK, Color.WHITE); //change colours
				ghostseatenlbl.setFont(gameFont); //set font to custom font
				ghostseatenlbl.setBounds(282, 515, 200, 30); //set bounds

				//Add all these JLabels to the statsPanel on the right of the frame 
				statsPanel.add(title);
				statsPanel.add(lives);
				statsPanel.add(livesvalue);
				statsPanel.add(coinseaten);
				statsPanel.add(coinseatenvalue);
				statsPanel.add(timelabel);
				statsPanel.add(timervalue);
				statsPanel.add(ghostseaten);
				statsPanel.add(ghostseatenlbl);
				//add statsPanel to the frame
				frame.add(statsPanel);
				test = true; //set variable to true; this code only runs through once
			}
			
			//if the lives value is updated in the images object
			if (livesv != images.lives) { 
				livesv = images.lives; //set the lives value here to the lives value that was changed in the images object
				livesvalue.setText(Integer.toString(livesv)); //change the lives value on the JLabel 
			}
			
			//if the coins eaten value is updated in the images object
			if (coinseatenv != images.coinseaten) { 
				coinseatenv = images.coinseaten; //set the coins eaten value here to the coins eaten value that was changed in the images object
				//Moves labels based on number of digits for a better looking presentation
				if (coinseatenv > 99) {
					coinseatenvalue.setBounds(267, 328, 100, 30);
				} else if (coinseatenv > 9) {
					coinseatenvalue.setBounds(274, 328, 100, 30);
				} else {
				}
				coinseatenvalue.setText(Integer.toString(coinseatenv)); //update the # of coins eaten JLabel
			}

			//if the time value is updated in the images object
			if (clock.timer != time) {
				time = clock.timer; //set local time value to updated time value
				//update label positioning for presentation
				if (time < 10) {
					timervalue.setBounds(278, 420, 100, 30);
				} else if (time < 100) {
					timervalue.setBounds(273, 420, 100, 30);
				} else {
				}
				timervalue.setText(Integer.toString(time)); //update remaining time JLabel
			}

			//if the ghosts eaten value is updated in the images object
			if (ghostseatenv != images.ghostseaten) {
				ghostseatenv = images.ghostseaten; //update the local ghosts eaten value
				//update label's positioning if necessary
				if (ghostseatenv > 9) {
					ghostseatenlbl.setBounds(276, 515, 200, 30);
				}
				ghostseatenlbl.setText(Integer.toString(ghostseatenv)); //update the ghosts eaten JLabel
			}

			endGameLose = images.endGameLose(); //update endGameLose variable. Constantly looks for if the User has lost.
			endGameWin = images.endGameWin(); //update the endGameWin variable. Constantly looks for if the User has won.

			if (time < 0) { //if user has run out of time
				endGameLose = true; //say that the User has lost the game
			}

			try { // put thread to sleep
				Thread.sleep(10); // put thread to sleep
			} catch (InterruptedException e) {
			}
		}

		//Depending on the difficulty, assign an appropriate multiplier
		if (images.numberOfGhosts >= 6) {
			multiplier = 3;
		} else if (images.numberOfGhosts == 4) {
			multiplier = 2;
		} else if (images.numberOfGhosts == 3) {
			multiplier = 1;
		}

		// if the game has ended in a loss
		if (endGameLose == true) {
			score = ((coinseatenv + (ghostseatenv * 30)) * 10) * multiplier; //assign user a score
//			pw.println(score); //print this score onto scores.txt file
//			pw.close(); //close printwriter
			endScreenLose(); //call endScreenLose() method
		}

		// if the game has ended in a win
		else if (endGameWin == true) { 
			score = ((coinseatenv + (ghostseatenv * 30) + (time * 5)) * 10) * multiplier; //assign user a score
//			pw.println(score); //print this score onto scores.txt file
//			pw.close(); //close printwriter
			endScreenWin(); //call endScreenWin() method
		}

	}

	/*
	 * Purpose: method displays the losing screen which includes the User's score
	 * Pre: N/A
	 * Post: N/A
	 */
	public void endScreenLose() {
		frame.setVisible(false); //turn off game frame
		frameEnd.setSize(604, 690); //set size of losing frame
		frameEnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameEnd.setResizable(false); 
		//Create labels for losing panel
		JLabel label1 = new JLabel("GAME OVER!"); 
		JLabel label2 = new JLabel("YOU LOSE");
		endPanel.setLayout(null); //set layout of the end panel to null
		endPanel.setBackground(Color.BLACK); //set background of end panel to black
		gameFont = gameFont.deriveFont(60f); //set size of custom font
		label1.setForeground(Color.WHITE); //set label text to white
		label1.setFont(gameFont); //set label font to custom font
		label1.setBounds(100, 120, 500, 30); //set bounds of label 1
		endPanel.add(label1); //add this label to the loser panel
		gameFont = gameFont.deriveFont(30f); //change size of custom font
		label2.setForeground(Color.WHITE); //change label colour to white
		label2.setFont(gameFont); //change label font to custom font
		label2.setBounds(210, 230, 500, 30); //set bounds of this label
		endPanel.add(label2); //add this label to the loser panel
		textArea = new JTextArea(); //create text area
		textArea.setBounds(145,310,400,50); //set bounds
		textArea.setBackground(Color.BLACK); //set background black
		textArea.setForeground(Color.WHITE); //set colour of text to white
		textArea.setFont(gameFont); //set the font to the game font
		endPanel.add(textArea); //add textArea to the loser screen
		text = "SCORE:        " + Integer.toString(score); //get text of what will be added to the text area
		frameEnd.add(endPanel); //add endPanel to the loser frame
		frameEnd.setVisible(true); //make this loser frame visible
		timer.start(); //start timer
	}

	/*
	 * Purpose: method displays the winning screen which includes the User's score
	 * Pre: N/A
	 * Post: N/A
	 */
	public void endScreenWin() {
		frame.setVisible(false); //turn off game frame 
		//set size, resizability, etc for winning frame
		frameWin.setSize(604, 690); 
		frameWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameWin.setResizable(false);
		//create JLabels for this winning scenario
		JLabel label1 = new JLabel("GAME OVER!");
		JLabel label2 = new JLabel("YOU WIN");
		//set panel layout to null and to black colour
		winnerpanel.setLayout(null);
		winnerpanel.setBackground(Color.BLACK); 
		gameFont = gameFont.deriveFont(60f); //adjust font size
		label1.setForeground(Color.WHITE); //set text to white
		label1.setFont(gameFont); //set font to custom font
		label1.setBounds(100, 120, 500, 30); //set bounds of text
		winnerpanel.add(label1); //add label to winner panel
		gameFont = gameFont.deriveFont(30f); //change font size
		label2.setForeground(Color.WHITE); //set text to white
		label2.setFont(gameFont); //set font to custom font
		label2.setBounds(225, 230, 500, 30); //set bounds
		winnerpanel.add(label2); //add second label to winner panel
		textArea = new JTextArea(); //make textArea
		textArea.setBounds(145,310,400,50); //set bounds 
		textArea.setBackground(Color.BLACK); //set background colour to black
		textArea.setForeground(Color.WHITE); //set text colour to white
		textArea.setFont(gameFont); //set font to custom font
		winnerpanel.add(textArea); //add text area to the winner screen
		text = "SCORE:        " + Integer.toString(score); //text that will be animated to the text area
		frameWin.add(winnerpanel); //add winner panel to the winner frame
		frameWin.setVisible(true); //make this winner frame visible
		timer.start(); //start timer

	}
/*
 * Purpose: method runs b/c of Runnable implementation and the Thread object running. This method will call on the endGame() method. 
 * Pre: N/A
 * Post: N/A
 */
	public void run() { 
		endGame(); // call endGame method
	}
	
	//create timer object that refreshes every 100ms
	Timer timer = new Timer(100, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) { //actionPerformed overrided method for new ActionListener()
			char character [] = text.toCharArray(); //get array of characters from the text that we want to animate
			int arrayNumber = character.length; //get length of the character array
			String addedCharacter = ""; //character that will be added
			
			addedCharacter= Character.toString(character[i]); //get a character from text 
			textArea.append(addedCharacter); //add character to textArea 1 by 1 to mimic animation
			i++; //increase index (go to the next character in the text)
			
			if(i==arrayNumber) { //if no more characters to print
				i=0; timer.stop(); //stop the timer
			}
		}} 

		);
	}