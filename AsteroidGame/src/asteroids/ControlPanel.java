package asteroids;
/*
 * This class contains the control panel. 
 * This is a JPanel object that will be attached to your JFrame to allow the user to pause the game or start the game.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;


//part 16
public class ControlPanel extends JPanel {
	
	private JPanel westSubPanel;
	private myButtonListener listener;
	private Status newStatus;
	private AsteroidGame game;
	private Display display;
	private Random myRNG;


	public ControlPanel(Container container,Status statusBar,AsteroidGame gr,Display d)
	{
	//*********sets up pointers and instantiates objects

	westSubPanel=new JPanel();
	newStatus=statusBar;
	game=gr;
	display=d;
	myRNG=new Random();

	//*****JButtons set up***********
	JButton start=new JButton ("Start ");
	JButton pause=new JButton ("Pause ");

	start.setFocusable(false); //sets buttons to false, but
	pause.setFocusable(false); //are still sent to listeners

	//******Buttons and JCombo box added to panel*******

	westSubPanel.add(start);
	westSubPanel.add(pause);

	westSubPanel.setLayout(new BoxLayout(westSubPanel,BoxLayout.Y_AXIS));

	//*******adds Button's to Listeners***********************

	listener=new myButtonListener();
	start.addActionListener(listener);
	pause.addActionListener(listener);

	//******added Panel/Label to container********************

	container.add(westSubPanel,BorderLayout.WEST);


	}//end constructor


	private class myButtonListener implements ActionListener
	{

	public void actionPerformed(ActionEvent e)
	{


	Ships myShips;
	SpaceMines myMine;

	String temp="";

	if(e.getActionCommand().equals("Start "))
	{
		game.startGame();
	}
	if(e.getActionCommand().equals("Pause "))
	{
		game.stopGame();
	}


	}//end actionPerformed method

	}//end myButtonListener innerclass

}//end ControlPanel class
