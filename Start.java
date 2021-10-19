package game;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

/*
 * Name: Leon Xu
 * Date: January 8, 2021
 * Purpose: class that starts the game and main menu
 */
public class Start implements ActionListener {

	JFrame frame = new JFrame("Pac-Man Game"); // Home screen JFrame
	JPanel panel = new JPanel(); //Home screen JPanel
	JLabel title = new JLabel("PAC-MAN"); //Home screen title
	// Home screen buttons
	JButton btn1 = new JButton("PLAY");
	JButton btn2 = new JButton("INSTRUCTIONS");
	JLabel name = new JLabel("BY: LEON XU"); // Home screen name
	// Home screen high score labels
	JLabel highscoretxt = new JLabel("CURRENT HIGH SCORE: ");
	JLabel highscorev = new JLabel("");
	int max = 0; // variable stores the maximum score obtained on this machine
	Font gameFont; // gameFont stores font used for letters in this game
	Main startGame; // Main object is used to start the game
	FileReader rr; // File reader variable
	
	JFrame instructions = new JFrame("Pac-Man Game"); //Instructions screen JFrame
	JPanel instructionspanel = new JPanel(); //Instructions screen JPanel
	JLabel instructionslbl = new JLabel("INSTRUCTIONS"); //Instructions screen title
	//Instructions screen movement info JLabels + general rules of the game JLabels
	JLabel movementlbl = new JLabel("MOVEMENT INSTRUCTIONS");
	JLabel movementright = new JLabel("RIGHT ARROW KEY MOVES PAC-MAN TO THE RIGHT"); 
	JLabel movementleft = new JLabel("LEFT ARROW KEY MOVES PAC-MAN TO THE LEFT");
	JLabel movementup = new JLabel("UP ARROW KEY MOVES PAC-MAN UP");
	JLabel movementdown = new JLabel("DOWN ARROW KEY MOVES PAC-MAN DOWN");
	JLabel gameruleslbl = new JLabel("GAME INSTRUCTIONS");
	JLabel instructionstxt1 = new JLabel("1.  COLLECT ALL OF THE COINS WITHIN THE TIME LIMIT");
	JLabel instructionstxt2 = new JLabel("3.  DO NOT TOUCH ANY GHOSTS WITHOUT EATING A POWER PELLET");
	JLabel extrains = new JLabel        ("5.  EARN POINTS BY EATING GHOSTS, COINS, & BEING QUICK!");
	JLabel instructionstxt3 = new JLabel("2.  GHOSTS ALTERNATE BETWEEN SCATTER AND CHASE MODE");
	JLabel instructionstxt4 = new JLabel("4.  YOU CAN EAT GHOSTS FOR 5s AFTER EATING A POWER PELLET");
	JLabel instructionstxt5 = new JLabel("6.  GET THE HIGHGEST SCORE POSSIBLE & HAVE FUN!");
	JButton home = new JButton("Home"); //Home button for Instructions screen
	
	
	JFrame play = new JFrame("Pac-Man Game"); //Play screen JFrame
	JPanel playpanel = new JPanel(); //Play screen JPanel
	JLabel playheading = new JLabel("PLAY"); //title of play screen
	JLabel easytxt = new JLabel("3x GHOSTS & 1x MULTIPLIER"); //label: info for easy button
	JLabel normaltxt = new JLabel("4x GHOSTS & 2x MULTIPLIER"); //label: info for normal button
	JLabel hardtxt = new JLabel("6x GHOSTS & 3x MULTIPLIER"); //label: info for hard button 
	JButton easybtn = new JButton("EASY"); //easy button
	JButton normalbtn = new JButton("NORMAL"); //normal button
	JButton hardbtn = new JButton("HARD"); //hard button
	JButton home2 = new JButton("Home"); //home button
	
	
/*
 * Purpose: gets custom font, sets up frame icons, sets up home frame, instructions frame, and play frame. Creates all JLabels, buttons, etc used in these frames. 
 * Pre: N/A
 * Post: N/A
 */
	public Start() { 
		try { // get Pac-Man font
			gameFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.load("Joystix.TTF")).deriveFont(30f); //get Pac-Man font from Joystix.TTF file and save it to the gameFont vaariable
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.load("Joystix.TTF")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
		//Set home screen frame settings
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close on x
		frame.setSize(604, 690); //set frame size
		frame.setResizable(false); //set frame resizable false
		
		//set icon images of JFrames
		ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("pacicon.png"));
		frame.setIconImage(image.getImage());
		instructions.setIconImage(image.getImage()); 
		play.setIconImage(image.getImage());

		panel.setBackground(Color.BLACK); //make home screen panel black
		panel.setLayout(null); //make home screen panel layout null
		
		//For Home Screen
		gameFont = gameFont.deriveFont(50f); //change size of font
		title.setForeground(Color.WHITE); //make title text white 
		title.setBounds(169, 150, 400, 50); //set bounds
		title.setFont(gameFont); //change font to Pac-Man font
		gameFont = gameFont.deriveFont(20f); //change size of font
		btn1.setFont(gameFont); //set font of button 1 (PLAY)
		btn1.setBounds(245, 280, 100, 50); //set bounds of play button
		btn1.addActionListener(this); //add action listener
		btn1.setActionCommand("Play"); //set action command of play button 
		btn2.setFont(gameFont); //set font of button 2
		btn2.setBounds(170, 350, 250, 50); //set bounds
		btn2.addActionListener(this); //add action listener to this button
		btn2.setActionCommand("Instructions"); //set action command of button 2 
		name.setForeground(Color.WHITE); //set name label colour to white
		name.setFont(gameFont); //set font to Pac-Man custom font
		name.setBounds(445, 615, 200, 50); //set bounds
		
		//File reader code
//		ClassLoader classLoader = getClass().getClassLoader();

//		File myObj = new File(
//				classLoader.getResource("scores.txt").getFile()
//				);
//		Scanner myReader = null;
		//Assign Scanner myReader to the file path
//		try { 
//			myReader = new Scanner(myObj); 
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		while (myReader.hasNextLine()) { //while there are lines with values in it in the file scores.txt
//			String data = myReader.nextLine(); //get this value
//			if (Integer.parseInt(data) > max) { //if this value from the file is greater than the established max
//				max = Integer.parseInt(data); //update the max value detected
//			}
//		}
//		myReader.close(); //close Scanner
		highscoretxt.setForeground(Color.WHITE); //set highscore label to colour white
		highscoretxt.setFont(gameFont); //set font to Pac-Man custom font
		highscoretxt.setBounds(0, 615, 350, 50); //set bounds
		highscorev = new JLabel("N/A"); //put the value of the highest score detected into this highscorev JLabel
		highscorev.setForeground(Color.WHITE); //set colour of label to white
		highscorev.setFont(gameFont); //set font to custom font
		highscorev.setBounds(260, 615, 200, 50); //set bounds of this JLabel
		//Add buttons, title, high score, etc to the home screen panel
		panel.add(btn1);
		panel.add(btn2);
		panel.add(title);
		panel.add(name);
		panel.add(highscoretxt);
		panel.add(highscorev);
		//add home screen panel to home screen frame
		frame.add(panel);

		
		//Instructions screen coding
		instructions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set instructions JFrame to close on x
		instructions.setSize(604, 690); //adjust frame size
		instructions.setResizable(false); //adjust frame resizable to false
		instructionspanel.setBackground(Color.BLACK); //set instructions screen panel to black colour
		instructionspanel.setLayout(null); //set instructions screen panel layout to null

		gameFont = gameFont.deriveFont(50f); //change size of font
		instructionslbl.setFont(gameFont); //set font of instructions screen title to custom font
		instructionslbl.setForeground(Color.WHITE); //set colour of instructions screen title to white
		instructionslbl.setBounds(88, 20, 500, 50); //set bounds
		instructionspanel.add(instructionslbl); //add this label to instructions screen panel

		gameFont = gameFont.deriveFont(25f); //change size of font
		movementlbl.setFont(gameFont); //set font to custom font
		movementlbl.setForeground(Color.YELLOW); //set colour of movement instructions heading to yellow
		movementlbl.setBounds(40, 110, 500, 50); //set bounds
		instructionspanel.add(movementlbl); //add this label to instructions screen panel
		
		gameFont = gameFont.deriveFont(15f); //change size of font
		movementright.setFont(gameFont); //set font of movement label to custom font
		movementright.setForeground(Color.WHITE); //set label to colour white
		movementright.setBounds(100, 150, 500, 50); //set bounds
		instructionspanel.add(movementright); //add movement label to instructions screen panel

		movementleft.setFont(gameFont); //set font of movement label to custom font
		movementleft.setForeground(Color.WHITE); //set label colour to white
		movementleft.setBounds(103, 200, 500, 50); //set bounds
		instructionspanel.add(movementleft); //add movement label to instructions screen panel

		movementup.setFont(gameFont); //set font of movement label to custom font
		movementup.setForeground(Color.WHITE); //set label colour to white
		movementup.setBounds(103, 250, 500, 50); //set bounds
		instructionspanel.add(movementup); //add movement label to instructions screen panel

		movementdown.setFont(gameFont); //set font of movement label to custom font
		movementdown.setForeground(Color.WHITE); //set label colour to white
		movementdown.setBounds(103, 300, 500, 50); //set bounds
		instructionspanel.add(movementdown); //add movement label to instructions screen panel
		
		
		JLabel prlbl = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("p_r1.png")));//get image of Pac-Man facing right
		prlbl.setBounds(30, 150, 50, 50); //set bounds of image
		instructionspanel.add(prlbl); //add image to instructions screen panel

		JLabel pllbl = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("p_l1.png"))); //get image of Pac-Man facing left
		pllbl.setBounds(30, 200, 50, 50); //set bounds of image
		instructionspanel.add(pllbl); //add image to instructions screen panel

		JLabel pulbl = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("p_u1.png"))); //get image of Pac-Man facing up
		pulbl.setBounds(30, 250, 50, 50); //set bounds of image
		instructionspanel.add(pulbl); //add image to instructions screen panel

		JLabel pdlbl = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("p_d1.png"))); //get image of Pac-Man facing down
		pdlbl.setBounds(30, 300, 50, 50);  //set bounds of image
		instructionspanel.add(pdlbl); //add image to instructions screen panel

		gameFont = gameFont.deriveFont(25f); //change size of font
		gameruleslbl.setFont(gameFont); //set custom font
		gameruleslbl.setForeground(Color.YELLOW); //set colour of label to yellow for heading
		gameruleslbl.setBounds(40, 360, 500, 50); //set bounds
		instructionspanel.add(gameruleslbl); //add this label to instructions screen panel
		
		//Set the properties of the JLabels used for the instructions text. Make the text white, set the font to the custom Pac-Man font, set the bounds for each JLabel, and then add these labels to the instructions screen JPanel 
		gameFont = gameFont.deriveFont(15f); //change size of font
		instructionstxt1.setForeground(Color.WHITE);
		instructionstxt1.setFont(gameFont);
		instructionstxt1.setBounds(20, 390, 600, 50);
		instructions.add(instructionstxt1);
		instructionstxt2.setForeground(Color.WHITE);
		instructionstxt2.setFont(gameFont);
		instructionstxt2.setBounds(20, 450, 700, 50);
		instructions.add(instructionstxt2);
		instructionstxt3.setForeground(Color.WHITE);
		instructionstxt3.setFont(gameFont);
		instructionstxt3.setBounds(20, 420, 700, 50);
		instructions.add(instructionstxt3);
		instructionstxt4.setForeground(Color.WHITE);
		instructionstxt4.setFont(gameFont);
		instructionstxt4.setBounds(20, 480, 700, 50);
		instructions.add(instructionstxt4);
		instructionstxt5.setForeground(Color.WHITE);
		instructionstxt5.setFont(gameFont);
		instructionstxt5.setBounds(20, 540, 500, 50);
		instructions.add(instructionstxt5);
		extrains.setForeground(Color.WHITE);
		extrains.setFont(gameFont);
		extrains.setBounds(20, 510, 600, 50);
		instructions.add(extrains);

		gameFont = gameFont.deriveFont(10f); //change custom font size
		home.setFont(gameFont); //set the font for the home button to this custom font 
		home.setBounds(0, 600, 70, 50); //set bounds of button
		home.addActionListener(this); //add action listener
		home.setActionCommand("Home"); //set action command of this button to "Home"
		instructions.add(home); //add this button to the instructions panel

		instructions.add(instructionspanel); //add instructions panel to instructions JFrame
		instructions.setVisible(false); //set the instructions frame to invisible for now

		//Play screen coding
		play.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set close operation of play frame to close on x
		play.setSize(604, 690); //set play frame size
		play.setResizable(false); //set frame resizability to false
		playpanel.setBackground(Color.BLACK); //set play screen's panel to black
		playpanel.setLayout(null); //set play screen's panel layout to null

		gameFont = gameFont.deriveFont(50f);//change custom font size
		playheading.setFont(gameFont); //set font of title of play screen
		playheading.setForeground(Color.WHITE); //set colour to white
		playheading.setBounds(220, 150, 500, 50); //set bounds
		playpanel.add(playheading); //add title of play screen to play panel

		gameFont = gameFont.deriveFont(10f); //change size of font
		easybtn.setFont(gameFont); //set easy button to this custom font
		easybtn.setBounds(213, 230, 150, 50); //set bounds of button
		easybtn.addActionListener(this); //add action listener
		easybtn.setActionCommand("EasyButton"); //set action command of button to EasyButton
		playpanel.add(easybtn); //add easy button to play panel
		easytxt.setForeground(Color.WHITE); //set info text under button to white
		easytxt.setFont(gameFont); //set font to white
		easytxt.setBounds(220, 275, 500, 50); //set bounds 
		playpanel.add(easytxt); //add text under the button
		ImageIcon g1 = new ImageIcon(getClass().getClassLoader().getResource("g_b_r.png"));  //get picture of blue ghost
		Image g1img = g1.getImage(); //save image to Image variable
		g1img = g1img.getScaledInstance(15, 15, Image.SCALE_SMOOTH); //scale ghost to 15x15
		ImageIcon g1ii = new ImageIcon(g1img); //save scaled image as imageicon
		JLabel g1lbl = new JLabel(g1ii); //add imageicon to jlabel
		g1lbl.setBounds(195, 290, 20, 20); //set bounds of image jlabel
		playpanel.add(g1lbl); //add jlabel with image to the screen

		normalbtn.setFont(gameFont); //set font of normal button to custom font
		normalbtn.setBounds(213, 330, 150, 50); //set bounds of button
		normalbtn.addActionListener(this); //add action listener
		normalbtn.setActionCommand("NormalButton"); //set action command of normal button 
		playpanel.add(normalbtn); //add button to play panel
		normaltxt.setForeground(Color.WHITE); //set colour of text under normal button to white
		normaltxt.setFont(gameFont); //set text font to custom font
		normaltxt.setBounds(210, 375, 500, 50); //set bounds of text
		playpanel.add(normaltxt); //add text to play panel
		ImageIcon g2 = new ImageIcon(getClass().getClassLoader().getResource("g_o_r.png")); //get image of orange ghost
		Image g2img = g2.getImage(); //save to ghost Image variable
		g2img = g2img.getScaledInstance(15, 15, Image.SCALE_SMOOTH); //scale the orange ghost
		ImageIcon g2ii = new ImageIcon(g2img); //save scaled orange ghost to ImageIcon g2ii
		JLabel g2lbl = new JLabel(g2ii); //add ImageIcon scaled ghost to JLabel 
		g2lbl.setBounds(185, 390, 20, 20); //set bounds of this JLabel 
		playpanel.add(g2lbl);//add JLabel with ghost pic to play panel

		hardbtn.setFont(gameFont); //set font of hard button to custom font
		hardbtn.setBounds(213, 430, 150, 50); //set bounds of hard button
		hardbtn.addActionListener(this); //add action listener
		hardbtn.setActionCommand("HardButton"); //set action command to "HardButton"
		playpanel.add(hardbtn); //add hard button to play panel
		hardtxt.setForeground(Color.WHITE); //set colour of text underneath hard button to white
		hardtxt.setFont(gameFont); //set font of this text to white
		hardtxt.setBounds(215, 475, 500, 50); //set bounds of this text
		playpanel.add(hardtxt); //add text to the play panel
		ImageIcon g3 = new ImageIcon(getClass().getClassLoader().getResource("g_r_r.png")); //get red ghost ImageIcon
		Image g3img = g3.getImage(); //save this ghost image as an Image variable
		g3img = g3img.getScaledInstance(15, 15, Image.SCALE_SMOOTH); //get a 15x15 scaled image of this ghost
		ImageIcon g3ii = new ImageIcon(g3img); //save this scaled image of the ghost as an ImageIcon
		JLabel g3lbl = new JLabel(g3ii); //add ImageIcon of ghost to JLabel
		g3lbl.setBounds(190, 490, 20, 20); //set bounds of the ghost pic JLabel
		playpanel.add(g3lbl); //add JLabel to the play screen
		home2.setFont(gameFont); //set font of home button to custom font
		home2.setBounds(0, 600, 70, 50); //set bounds of the home button 
		home2.addActionListener(this);//add action listener
		home2.setActionCommand("Home"); //set action command of home button to "Home"
		playpanel.add(home2); //add home button to play panel

		play.add(playpanel); //add play panel to play frame
		play.setVisible(false); //make play frame invisible

		frame.setVisible(true); //set home screen frame to visible
	}
 
	public static void main(String[] args) {
		Start game = new Start(); //Create a class object which starts the main menu and consequently the game
	}

	/*
	 * Purpose: invoked when the User presses a button. Changes menu screens depending on button pressed.
	* Pre: ActionEvent e; represents action event that occurred
	* Post: N/A
	 */
	public void actionPerformed(ActionEvent e) {
		String txt = e.getActionCommand(); // set txt to actioncommand of event
		if (txt.contentEquals("Instructions")) { // if button pressed was the instructions button
			frame.setVisible(false); //close home screen
			instructions.setVisible(true); //open instructions screen
		}
		if (txt.contentEquals("Home")) { // if button pressed was home button
			instructions.setVisible(false); //close instructions screen
			play.setVisible(false); //close play screen
			frame.setVisible(true); //set home screen to visible
		}
		if (txt.contentEquals("Play")) { // if play button was pressed
			frame.setVisible(false); //close home screen
			play.setVisible(true); //open play screen
		}
		if (txt.contentEquals("EasyButton")) { // if easy button was pressed
			play.setVisible(false); //close play screen
			startGame = new Main(3); //start game with 3 ghosts
		}
		if (txt.contentEquals("NormalButton")) { // if normal button was pressed
			play.setVisible(false); //close play screen
			startGame = new Main(4); //start game with 4 ghosts
		}
		if (txt.contentEquals("HardButton")) { // if hard button was pressed
			play.setVisible(false); //close play screen
			startGame = new Main(6); //start game with 6 ghosts
		}

	}

}