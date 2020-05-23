package asteroids;
/*
 * This class is the JFrame class used to hold the frame of the game.
 *  All of the components will get attached to this class. 
 * I will explain what this class does in my next post. 
 * This post is to explain the GFrame.java class from my last post. 
 * This class creates all of the menus that the user will use in the game. 
 * It creates a list of radio buttons for the difficulty level. 
 * It will also create some menu bars that you can use in the game to either quit the game or create a new game. 
 * The KeyboardFocusManager is the keystroke manager and will hold each key that the user presses. 
 * When a key is pressed, the dispatchKeyEvent method is called, and you can check which key the user pressed. 
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.event.*;
import java.util.Random;



public class GFrame extends JFrame implements KeyEventDispatcher
{

private JMenuBar bar; 

private JMenu menu; 
private JMenu menu1; 
private JMenu sound; 

private JMenuItem newGame; 
private JMenuItem quit; 
private JMenuItem difficulty;

private JRadioButtonMenuItem on; 
private JRadioButtonMenuItem off; 
private ButtonGroup soundGroup; 
private Status statusBar; 
private Display displayPanel; 

private ControlPanel panel; 

private Container container; 

private FlowLayout layout; 

private menuListener xlistener;

private JPopupMenu difficultMenu; 
private ButtonGroup difficultGroup; 
private JRadioButtonMenuItem items[];
private String levels[]={"1-Wimpy","2-Loser","3-Moron","4-Pee Wee","5-Sissy","6-Girly","7-Rocky","8-Mr. Muscles","9-Godzilla","10-You, the Man!"};
private ItemHandler handler; 

private MyWindowListener myWinListener; 

private AsteroidGame game; 


public GFrame(AsteroidGame g)
{
super("ASTEROIDS THE PINK VIPER");
game=g;

//***********instantiate window listener*******

myWinListener=new MyWindowListener();
this.addWindowListener(myWinListener);

//**********sets up difficulty menu*************

difficultGroup=new ButtonGroup();
difficultMenu=new JPopupMenu();
items=new JRadioButtonMenuItem[10];
handler=new ItemHandler();

for(int count=0;count<items.length;count++ )
{
items[count]=new JRadioButtonMenuItem(levels[count]);
difficultMenu.add(items[count]);
difficultGroup.add(items[count]);
items[count].addActionListener(handler);
}

//****************sets up menu bar***************
bar=new JMenuBar();
setJMenuBar(bar);

//****************sets up the menu items*********
menu=new JMenu("File");
menu1=new JMenu("Options");
sound=new JMenu("Sound");

//*********sets up the menu items****************
newGame=new JMenuItem("New Game");
quit=new JMenuItem("Quit");
difficulty=new JMenuItem("Difficulty");

//*********sets up the submenu under sound******
on=new JRadioButtonMenuItem("On");
off=new JRadioButtonMenuItem("Off");

//*********sets up the soundGroup***************
soundGroup=new ButtonGroup();

//********sets up the sound submenu************* 
sound.add(on);
sound.add(off);
soundGroup.add(on);
soundGroup.add(off);
on.setSelected(true); //sets the sound to be initially on

//*******adds menuItems and submenus to menus**
menu.add(newGame);
menu.addSeparator();
menu.add(quit);
menu1.add(difficulty);
menu1.add(sound);

//******adds menus to the menu bar************
bar.add(menu);
bar.add(menu1);

//********set up FlowLayout*******************
layout=new FlowLayout();
layout.setAlignment(FlowLayout.LEFT);


//******gets the ContentPane for the Frame*

container=getContentPane();

//***Instantiates the ControlPanel and
//***Display panel and status panel to be added to the GUI*

statusBar=new Status(container,game);
displayPanel=new Display(container,game);
panel=new ControlPanel(container,statusBar,game,displayPanel);
displayPanel.addControlPanel(panel);
//*************sets up listeners for the menu bar*********

xlistener = new menuListener();
newGame.addActionListener(xlistener);
quit.addActionListener(xlistener);
difficulty.addActionListener(xlistener);
on.addActionListener(xlistener);
off.addActionListener(xlistener);

//*******Sets up Frame to listen for keystrokes**********
KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

//sets up window's location and sets size****

setLocation(0,0); //the default location
setSize(1000,800);
setVisible(true);


}//end constructor 

public void setStatus(String temp)
{
statusBar.setStatus(temp);
}

public void repaintDisplay()
{
displayPanel.repaint();
}

public int getWidth()
{
return displayPanel.getLengthsX();
}

public int getHeight()
{
return displayPanel.getLengthsY();

}

//***********LISTENS FOR KEYSTROKES*******************
public boolean dispatchKeyEvent(KeyEvent event)
{
Ships ship=new Ships(displayPanel.getX(),displayPanel.getY());

if(game.returnStart())
{ 
if(event.getID()==KeyEvent.KEY_PRESSED)
{

//***********Starting game by pressing Enter********************
if(event.getKeyCode()==KeyEvent.VK_ENTER)
{ 
if(!game.returngameStarted())
{
game.setBegin();
game.newGame();
}
} 



int z=game.size();
for(int y=0;y<z;y++ )
{

if(game.elementAt(y) instanceof Ships)
{
ship=(Ships)(game.elementAt(y));
if(ship.isSelected())
{

switch(event.getKeyCode())
{
case KeyEvent.VK_LEFT:

//***************Ship moving left******************************
ship.leftHeading();

break;
case KeyEvent.VK_SPACE:

// ****************adding a Missile******************************

game.incrementTotal(0);
Point po=ship.getTop();
Missiles myMissile=new Missiles((int)po.getX(),(int)po.getY(),ship.getHeading(),ship.getSpeed(),this.getWidth(),this.getHeight());
game.addElement((Object)myMissile);//panel

break;

case KeyEvent.VK_RIGHT:

//*********************ship moving right******************************

ship.rightHeading();

break;

case KeyEvent.VK_UP:

//********************Ship increasing speed***********************

ship.increaseSpeed();
ship.setincSpeed(true);

break;
case KeyEvent.VK_DOWN:

//*************Ship decreasing speed*****************************

ship.decreaseSpeed();
ship.setincSpeed(true);
break;
case KeyEvent.VK_H:

//*********HyperJump*********************************************
Random myRNG=new Random();
ship.setLocation(myRNG.nextInt(3000),myRNG.nextInt(3000));

break;

default:
}//end switch statement

}//end inner if statement
}//end if statement

displayPanel.repaint();

}//end for statement

}//end if statement

} 

return false; //doesn't pass key pressed on to other listeners if true

}//end dispatchKeyEvent method



private class menuListener implements ActionListener
{


public void actionPerformed(ActionEvent e)
{
int x=1;

//*****checks which button was pressed
//and activates the action sequence


//***************NEW GAME ACTION*******************
if(e.getActionCommand().equals("New Game"))
{
game.newGame();
displayPanel.repaint();


}
//************QUIT ACTION**************************
if(e.getActionCommand().equals("Quit"))
{

x=JOptionPane.showConfirmDialog(null,
"Are you sure you want to quit?","Please Don't Quit", JOptionPane.YES_NO_OPTION);

if(x==0) //exits if zero, otherwise do nothing
{ 
	System.exit(0);
}
}

//***********SETS DIFFICULTY LEVEL****************
if(e.getActionCommand().equals("Difficulty"))
{
	difficultMenu.show(displayPanel.returnDisplay(),10,10);
}

//****************SETS SOUND ON AND OFF************
if(e.getActionCommand().equals("On"))
{
	game.updateSound(true);

}
if(e.getActionCommand().equals("Off"))
{

	game.updateSound(false);

}

}//end action Performend method


}//end ActionListener inner class

private class ItemHandler implements ActionListener
{
public void actionPerformed(ActionEvent event)
{

	int y=game.returnDifficulty();
	
	int[] totalg=new int[3];
	totalg=game.returnTotal();
	
	for(int i=0;i<items.length;i ++ )
	{
		if(event.getSource()==items[i])
		{
			game.updateDifficulty(i +1);
		}
	
	}

}//end actionperformed method

}//end ItemHandler class

private class MyWindowListener implements WindowListener
{
int x=1;

public void windowClosing(WindowEvent e)
{
x=JOptionPane.showConfirmDialog(null,
"Are you sure you want to quit?","Please Don't Quit", JOptionPane.YES_NO_OPTION);

if(x==0) //exits if zero, otherwise do nothing
{
System.exit(0);}
}

//****non used methods*******************
public void windowClosed(WindowEvent e){}
public void windowOpened(WindowEvent e){}
public void windowIconified(WindowEvent e){}
public void windowDeiconified(WindowEvent e){}
public void windowActivated(WindowEvent e){}
public void windowDeactivated(WindowEvent e){}



}//end myWinListener inner class



}//end GFrame class