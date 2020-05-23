/*
 * This class contains the game logic. It also contains the timer which will keep the game constantly updating. 
 * It will add the objects to the world which is contained in a Vector class. 
 * I didn't complete everything in this game so I will leave that for you to complete.
 */

package asteroids;

import java.util.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.Random;



public class AsteroidGame implements ActionListener
{

	private Vector world;
	private static int[] total; //keeps track of the total
	private int difficulty; //0-missiles, 1-asteroids
	//2-ships,3-space mines
	
	
	private boolean sound;
	private int time; //increments the timer
	private Timer myTimer; //timer for the game clock
	private GFrame menu; //creates GUI Frame
	private int score; //keeps track of score
	
	private boolean start; //if the game is on start/pause
	private int timerDelay;
	
	private final int DELAY_IN_MSEC=20; //clock Delay
	
	private boolean gameStarted; //keeps track if game has started or not
	private int gameCount;
	private boolean newGame;
	private int difficultyCount;
	private int newgameCount;
	private int SpaceID;
	private int shipamount;
	
	private boolean continueg;
	private int tempDifficulty;
	
	private int highscore;
	private String highname;


	//************** STATIC VARIABLES ***********************
	static final String FILE_NAME = "C:\\Users\\owner\\GregJava\\workspace\\Asteroids\\src\\score.txt";
	//*********************Constructor***********************

public AsteroidGame()
{
		highscore=0;
		highname="";
		tempDifficulty=0;
		continueg=false;
		shipamount=3;
		SpaceID=50;
		newgameCount=0;
		newGame=false;
		gameCount=0;
		timerDelay=0;
		total=new int[4];
		total[0]=0;
		total[1]=0;
		total[2]=0;
		total[3]=0;
		world=new Vector();
		difficulty=1;
		sound=true;
		start=true;
		difficultyCount=10;
		
		
		//****Timer set up***************************
		myTimer=new Timer(DELAY_IN_MSEC,this);
		
		time=0;
		score=0;
		

}//end constructor

//************************createGame Method****************
//purpose: To create the Asteroid Game
//preconditions: none
//postconditions: Instantiates the GFrame to play the
// asteroid game
//********************************************************
//keeps track of the total

public void createGame()
{
	menu=new GFrame(this);
	gameStarted=false;
	
	
	try
	{
		BufferedReader inFileStream =
		new BufferedReader(new FileReader(FILE_NAME));
		String temp=inFileStream.readLine();
		highscore=Integer.parseInt(temp);
		highname=inFileStream.readLine();
		
		inFileStream.close();
	}
	catch(Exception e)
	{
		System.out.println("HighScore not found");
	}
	
	myTimer.start();
}

public boolean returngameStarted()
{
	return gameStarted;
}

public void setBegin()
{
	gameStarted=true;
}

//************************returnTotal Method**************
//purpose: to return the totals 0-missiles, 1-asteroids,
// and 2-ships
//preconditions: none
//postconditions: return int array
//********************************************************
public int[] returnTotal()
{
	return total;
}

//************************returnSound Method**************
//purpose: to return the sound counter
//preconditions: none
//postconditions: return boolean for sound
//********************************************************
public boolean returnSound()
{
	return sound;
}

//************************updateDifficulty Method***********
//purpose: to update the difficulty counter
//preconditions: passes in new difficulty level
//postconditions: updates difficulty counter
//*********************************************************
public void updateDifficulty(int d)
{
	difficulty=d;
	difficultyCount=(difficulty*10);
}

//************************returnDifficulty Method***********
//purpose: to return the Difficulty counter
//preconditions: none
//postconditions: returns int, the difficulty counter
//*********************************************************
public int returnDifficulty()
{
	return difficulty;
}

//************************decrementTotal Method************
//purpose: to decrement either 0-missiles, 1-asteroids,
// or 2-ships
//preconditions: array item to decrement
//postconditions: decrements missile, asteroid, or ship
// counter
//********************************************************
public void decrementTotal(int d)
{
	total[d]-=1;
}

//************************incrementTotal Method************
//purpose: to increment an item 0-missiles, 1-asteroids,
// 2-ships
//preconditions: int to increment
//postconditions: none
//*********************************************************
public void incrementTotal(int i)
{
	total[i] +=1;
	if(i==2)
	shipamount++ ;
}

//************************newGame Method*****************
//purpose: to reset the total counts
//preconditions: none
//postconditions: resets all the counts for missiles, ships,
// and asteroids
//**********************************************************&^&^&^&^&^&^&
public void newGame()
{
	total[0]=0;
	total[1]=0;
	total[2]=0;
	world.clear();
	time=0;
	score=0;
	SpaceID=50;
	shipamount=3;
	gameCount=0;
	newGame=true;
	newgameCount=0;
	tempDifficulty=(difficulty*2);
	difficultyCount=(difficulty*20);
}

public void continueGame()
{
	total[1]=0;
	newGame=true;
	newgameCount=0;
	continueg=true;
	tempDifficulty ++ ;
	if(tempDifficulty>10)
	tempDifficulty=10;
	difficultyCount=(tempDifficulty*20);

}

//************************addScore Method*****************
//purpose: to add scores to the game
//preconditions: amount to be added, int
//postconditions: none
//**********************************************************

public void addScore(int z)
{
	score =z;
}

//************************startGame Method*****************
//purpose:to start the game
//preconditions: none
//postconditions: returns void
//**********************************************************

public void startGame()
{
	start=true;
}

//************************stopGame Method*****************
//purpose: to pause the game
//preconditions: none
//postconditions: none, pauses the game
//**********************************************************

public void stopGame()
{
	start=false;
}

//************************returnStart Method*****************
//purpose: to return if game is on or off
//preconditions: none
//postconditions: returns boolean
//**********************************************************

public boolean returnStart()
{
	return start;
}

//************************updateSound Method*****************
//purpose: to update the sound counter, if sound is on or off
//preconditions: none
//postconditions: updates the sound counter, pass in boolean
//**********************************************************

public void updateSound(boolean s)
{
	sound=s;
}

//************************addElement Method*****************
//purpose: to add an object to the world
//preconditions: send in a valid object to add to the world
//postconditions: to add an object to the world
//***********************************************************

public void addElement(Object add)
{
	world.addElement(add);
}

//************************isEmpty Method*******************
//purpose: to check if world is empty
//preconditions: none
//postconditions: returns boolean if world is empty or not
//*********************************************************

public boolean isEmpty()
{
	return world.isEmpty();
}

//************************size Method**********************
//purpose: to returns the world's size
//preconditions: none
//postconditions: returns int, which is worlds size
//**********************************************************

public int size()
{
	return world.size();
}

//************************elementAt Method*******************
//purpose: to return object at the specified location
//preconditions: sends in int to specify the location
//postconditions: returns Object at that location
//**********************************************************

public Object elementAt(int x)
{
	return world.elementAt(x);
}

//************************removeElement Method**************
//purpose: to remove element from world
//preconditions: Object to be removed
//postconditions: none
//********************************************************** 

public void removeElement(Object q)
{

	world.removeElement(q);

}

public String returnhighName()
{
	return highname;
}
public int returnhighScore()
{
	return highscore;
}

//************************tick Method*********************
//purpose: to subtract one from each missle object and remove
// it when it is at zero
//preconditions: sends in current amount to be ticked
//postconditions: none
//***********************************************************

public void tick()
{
	if(!(world.isEmpty()))
	{
		Missiles m;
		
		int z=world.size();
		for(int y=0;y<z;y++ )
		{
		
			if(world.elementAt(y) instanceof Missiles)
			{
			
				m=(Missiles)(world.elementAt(y));
				m.decrementPower(); //subtracting one
				//from the power
				
				if(m.returnPower()==0)
				{
					total[0]--;
					world.removeElement(m);
					z--;
					y--;
				
				}
			}
	
		}//end for statement

	}

//return totalm; 
}

	//************************timerListener method********************
	//purpose: to listen for the timer events
	//preconditions: ActionEvent
	//postconditions: will update status panel according to the time
	//****************************************************************
	
	public void actionPerformed(ActionEvent e)
	{
		//******HIGH SCORE********
		if((total[2]==0)&&(!newGame))
		{
			if(shipamount==1) //game over when ship amount uses last ship
			{ 
				gameStarted=false;
				shipamount--;
				if(score>highscore)
				{
					highscore=score;
					highname=JOptionPane.showInputDialog("Enter name for new Highscore");
					try
					{
						PrintWriter outFileStream =
						new PrintWriter(new FileOutputStream(FILE_NAME));
						outFileStream.println(highscore);
						outFileStream.println(highname);
						outFileStream.close();
					}
					catch(Exception exception)
					{
						System.out.println("HighScore file not found");
					}
				}//end highscore if
	
			}	
			else if(gameStarted)
			{ 
				total[2]++ ;
				Ships ship8=new Ships(menu.getWidth(),menu.getHeight());
				ship8.setSelected(true);
				this.addElement(ship8);
				shipamount--;
			
			}
		}//end of shipamount==1 game over
	
		if((total[1]==0)&&(!newGame))
		{
		
			this.continueGame();
		}
	
		//asteroids for the game when it isn't started
		if(!gameStarted)
		{
			if(gameCount<40)
			{
				Asteroids gs=new Asteroids(menu.getWidth(),menu.getHeight());
				this.addElement(gs);
				gameCount ++ ;
				total[1]++ ;
			}
		}
	
		if(newGame)
		{
		
			if(newgameCount<difficultyCount) //adds asteroids to game according to
			{ //difficulty level
				Asteroids as=new Asteroids(menu.getWidth(),menu.getHeight());
				this.addElement(as);
				total[1] ++ ;
			}
			if(newgameCount<tempDifficulty) //adds spacemines to game
			{
				SpaceMines sp=new SpaceMines(menu.getWidth(),menu.getHeight());
				SpaceID++ ;
				sp.setID(SpaceID);
				this.addElement(sp);
			}
			if(newgameCount==difficultyCount)
			{
				newGame=false;
				if(!continueg)
				{
					total[2]++ ;
					Ships ship=new Ships(menu.getWidth(),menu.getHeight());
					ship.setSelected(true);
					this.addElement(ship);
				}
				continueg=false;
			}
		newgameCount++ ;
	
	}//end if newgame
	
	if(start)
	{
	
		String temp1=new String();
		
		if(timerDelay==20)
		{
			time++ ; //clock timer for seconds
			timerDelay=0;
		}
	
	
		if (sound==true)
		{
			temp1=("Sound: On Missiles " + total[0] + " Asteroids " + total[1] + " Ships " + shipamount + " Difficulty Level " + difficulty + " Score " + score + " Time: " +  time);
		}
		else
		{
			temp1=("Sound: Off Missiles " + total[0] + " Asteroids " + total[1] + " Ships " + shipamount + " Difficulty Level " + difficulty + " Score " + score + " Time: " +  time);
		}
		
	
		//adding Enemy Ship
		if(gameStarted)
		{
			int yui=50;
			yui=(yui-(difficulty*3));
		if(time==yui)
		{
			EnemyShip enemy=new EnemyShip(menu.getWidth(),menu.getHeight(),world);
			this.addElement(enemy);
		}
	
	
	}
	
			
			menu.setStatus(temp1);
			
			Missiles m;
			Asteroids a;
			Ships s;
			SpaceMines sm;
			Garbage gb;
			EnemyShip enemyShip5;
			Point po;
			
			int speed=0;
			Point location1=new Point();
			double heading=0.0;
			int height5=0;
			
			boolean add=false;
			int base5=0;
			
			int z=world.size();
			
			//********************MOVING OBJECTS********************
			
			for(int y=0;y<z;y++ )
			{
			
				if(world.elementAt(y) instanceof Missiles)
				{
					m=(Missiles)(world.elementAt(y));
					m.move(DELAY_IN_MSEC);
				}
				else if(world.elementAt(y) instanceof Garbage)
				{
					gb=(Garbage)(world.elementAt(y));
					gb.move(DELAY_IN_MSEC);
				
				if(gb.removeCollision())
				{ 
					world.removeElement(gb);
					y--;
					z--;
				}
			
			}
			
			else if(world.elementAt(y) instanceof Asteroids)
			{
				a=(Asteroids)(world.elementAt(y));
				a.move(DELAY_IN_MSEC);
				
				if(a.removeCollision())
				{ 
					world.removeElement(a);
					total[1]--;
					y--;
					z--;
				}
			
			}
			else if(world.elementAt(y) instanceof Ships)
			{
				s=(Ships)(world.elementAt(y));
				s.move(DELAY_IN_MSEC);
				
				if(s.removeCollision())
				{ 
						world.removeElement(s);
						y--;
						z--;
						total[2]--;
				}
			}
			else if(world.elementAt(y) instanceof EnemyShip)
			{
				enemyShip5=(EnemyShip)(world.elementAt(y));
				enemyShip5.move(DELAY_IN_MSEC);
				
				if(enemyShip5.removeCollision())
				{
					world.removeElement(enemyShip5);
					y--;
					z--;
				}
			
			}
		
		
		}//ends for statement
	
		//*************************CHECKING FOR COLLISIONS*************
		int wy=0;
		
		for(int y=0;y<z;y++ )
		{
		
				if(world.elementAt(y) instanceof Missiles)
				{
					m=(Missiles)(world.elementAt(y));
					
					for(wy=0;wy<z;wy++ )
					{
						if(world.elementAt(wy) instanceof Asteroids)
						{
							a=(Asteroids)(world.elementAt(wy));
							
							if(a.bounds(m.getBounds1(),m.getBounds2()))
							{ 
								a.collision();
								score =a.returnValue();
								add=true;
								speed=a.getSpeed();
								location1.setLocation(a.getLocation());
								base5=a.getBase();
								heading=a.getHeading();
					
							}
						}//end if statement for asteroids
			
						if(world.elementAt(wy) instanceof EnemyShip)
						{
							enemyShip5=(EnemyShip)(world.elementAt(wy));
							if(enemyShip5.bounds(m.getBounds1(),m.getBounds2()))
							{
								enemyShip5.collision();
								score =100;
							}
						}
			
					}
			}//end if statement
			
			else if(world.elementAt(y) instanceof Asteroids)
			{
				a=(Asteroids)(world.elementAt(y));
				
				for(wy=0;wy<z;wy ++ )
				{
					if(world.elementAt(wy) instanceof Ships)
					{
						s=(Ships)(world.elementAt(wy));
						if(a.bounds(s.getBounds(),s.getBounds()))
						{
							s.collision();
							a.collision();
							score-=25; //deduct score for ship being killed
							add=true;
							
							speed=a.getSpeed();
							location1.setLocation(a.getLocation());
							base5=a.getBase();
							height5=a.getHeight();
					
					
						}
					}
				}
			
			}//end if statement
			else if(world.elementAt(y) instanceof SpaceMines)
			{
				sm=(SpaceMines)(world.elementAt(y));
				
				for(wy=0;wy<z;wy++ )
				{
					if(world.elementAt(wy) instanceof Ships)
					{
						s=(Ships)(world.elementAt(wy));
						
						if((s.bounds(sm.getBounds(),sm.returnHW()))&&(!sm.remove()))
						{
							sm.setRemove(true);
							s.collision();
						
						}
					}
				}
			
			}
			else if(world.elementAt(y) instanceof EnemyShip)
			{
			
				enemyShip5=(EnemyShip)(world.elementAt(y));
				
				for(wy=0;wy<z;wy++ )
				{
					if(world.elementAt(wy) instanceof Ships)
					{
						s=(Ships)(world.elementAt(wy));
						
						if(enemyShip5.bounds(s.getLocation(),s.getLocation()))///was getBounds1 - didnt resolve so need to find something to return used getLocation
						{
							enemyShip5.collision();
							s.collision();
							score =75;
						
						}
					}
				}//for statement
			}//end else if for enemy ship
		
		}//ends for statement
		
			//*****************ADDING GARBAGE AND SMALLER ASTEROIDS******************
			if(add)
			{
				Random myRNG=new Random();
				gb=new Garbage(menu.getWidth(),menu.getHeight(),speed,location1,base5,myRNG.nextInt(359));
				this.addElement(gb);
				gb=new Garbage(menu.getWidth(),menu.getHeight(),speed,location1,base5,myRNG.nextInt(359));
				this.addElement(gb);
				gb=new Garbage(menu.getWidth(),menu.getHeight(),speed,location1,base5,myRNG.nextInt(359));
				this.addElement(gb);
				gb=new Garbage(menu.getWidth(),menu.getHeight(),speed,location1,base5,myRNG.nextInt(359));
				
				height5/=2;
				base5/=2;
				
				if(!(height5<6||base5<6))
				{ 
					a=new Asteroids(menu.getWidth(),menu.getHeight(),myRNG.nextInt(359),speed,(int)location1.getX(),(int)location1.getY(),base5,height5);
					total[1]++ ;
					this.addElement(a);
				}
			}
				
				tick();
				
				timerDelay++ ;
		}//end start or stop if statement
	
		menu.repaintDisplay();
	
	}//end actionperformed method


}//end AsteroidGame class