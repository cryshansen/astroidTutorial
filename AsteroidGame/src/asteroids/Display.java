package asteroids;
/*
 * This class controls the graphics in the game.  It contains the paintComponent method that paints the objects on the screen.  
 * It also checks if the space mine is activated, and if it is, it should draw a Bezier curve on the screen.
 * http://www.theprogrammersweblog.com/2009/01/asteroids-in-java-2d-part-21-conclusion.html
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.awt.Point.*;
import java.awt.geom.AffineTransform;
import java.awt.Rectangle;


public class Display extends JPanel implements MouseListener, MouseMotionListener 
{
	private AsteroidGame game;
	private Container container;

	private static Point startPoint;
	private static Point currentPoint;

	private ControlPanel panel;

	private int maxLevel; 

	private Random myRNG; 

	private int tempID; 
	private double epsilon; 
	private int[][] stars;
	private int starCounter=0;

	private AffineTransform Viewport;
	private AffineTransform inVerse; 
	private boolean inV; 

	private double WL; 
	private double WR;
	private double WB;
	private double WT;


	public Display(Container c,AsteroidGame g)
	{
		//*****WORLD INFORMATION*****
		Viewport=new AffineTransform();
		inVerse=new AffineTransform();
		inV=true;
		WL=0;
		WR=1000;//sets witdth and height of asteroids
		WT=800;
		WB=0;
		//*****************************
	
		stars=new int[100][100];
		maxLevel=6;
		tempID=0;
		game=g;
		epsilon=.001;
		myRNG=new Random();
		startPoint=null;
		currentPoint=null;
		container=c;
	
		this.setBackground(new Color(0,0,0));
	
		container.add(this,BorderLayout.CENTER); //adds display to container
	
	
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}//ends constructor


	public JPanel returnDisplay()
	{ 
		return this;

	}//end JPanel panel

	//PANNING WORLD COORDINATES, PANLEFT1 IS WITH SMALLER INCREMENTS
	public void panLeft()
	{
		WT-=200;
		WB-=200;
		inV=true;
	}

	public void panRight()
	{
		WT +=200;
		WB +=200;
		inV=true;
	}
	public void panUp()
	{
		WL +=200;
		WR +=200;
		inV=true;

	}
	public void panDown()
	{
		WL-=200;
		WR-=200;
		inV=true;
	}

	public void panLeft1()
	{
		WT-=20;
		WB-=20;
		inV=true;
	}
	public void panRight1()
	{
		WT +=20;
		WB =20;
		inV=true;
	}
	public void panUp1()
	{
		WL +=20;
		WR+=20;
		inV=true;

	}
	public void panDown1()
	{
		WL-=20;
		WR-=20;
		inV=true;
	}
	//****get distance with world coordinates*****

	public int getLengthsX()
	{
		return (int)(WR-WL);
	}
	public int getLengthsY()
	{
		return (int)(WT-WB);
	}

	//************************addControlPanel Method********
	//purpose: to add reference to control panel
	//preconditions: ControlPanel
	//postconditions: none
	//*****************************************************

	public void addControlPanel(ControlPanel p)
	{
		panel=p;
	}

	//*****************MOUSE METHODS*******************

	//************************mousePressed Method*******
	//purpose: keeps track of plasma field while mouse is pressed
	//preconditions: MouseEvent
	//postconditions: none
	//***************************************************
	public void mousePressed(MouseEvent e)
	{

		Point ptp2=new Point();
		ptp2.setLocation(e.getX(),e.getY()); 
		inVerse.transform(ptp2,ptp2);
		Point e3=new Point();
		e3.setLocation(ptp2.getX(),this.getLengthsX()-ptp2.getY());
	
		if(game.returnStart())
		{
		double[][] point=new double[4][2];
		int direction5=0;
		SpaceMines mine=new SpaceMines(this.getLengthsX(),this.getLengthsY());
		Ships ship=new Ships(this.getLengthsX(),this.getLengthsY());
		int z=game.size();
	
		for(int y=0;y<z;y++ )
		{
		if(game.elementAt(y) instanceof SpaceMines)
		{
		mine=(SpaceMines)(game.elementAt(y));
		if(mine.contains((int)e3.getX(),(int)e3.getY()))
		{
		mine.setSelected(true);
		startPoint=new Point();
		startPoint.setLocation(ptp2.getX(),ptp2.getY());
		tempID=mine.getID();
	
		//Plasma field
		point[0][0]=ptp2.getX();
		point[0][1]=ptp2.getY();
		int tempso=0;
	
		for(int o=1;o<4;o++ )
		{
	
		for(int r=0;r<2;r++ )
		{
	
		tempso=myRNG.nextInt(4);
		if(tempso<2)
		point[o][r]=(double)(myRNG.nextInt((int)(300)) +point[0][r]);
		else
		point[o][r]=(double)(-myRNG.nextInt((int)(300))+ point[0][r]);
	
	
		}
		}
	
		mine.savePoint(point);
		mine.setField(true);
	
		//********end plasma field********** 
		}
	
		else
		{
		mine.setSelected(false);
	
		}
	
		if(game.elementAt(y) instanceof Ships)
		{
		ship=(Ships)(game.elementAt(y));
		if(ship.contains((int)e3.getX(),(int)e3.getY()))
		{
		ship.setSelected(true);
	
		}
		else
		{
		ship.setSelected(false);
	
		}
	
		}
		}
	
		}
		}//if start is on or off
	
		this.repaint(); 

	}//end mousepressed method


	//************************drawBezierCurve Method*************
	//purpose: to draw a curve from plasma field
	//preconditions: Array with 4 points, and the level of depth
	//postconditions: none, recursive
	//**********************************************************

	private void drawBezierCurve (double[][] CPV,int Level,Graphics g)
	{

		g.setColor(new Color(128,128,128));
		double[][] LeftSubVector=new double[4][2];
		double[][] RightSubVector=new double[4][2];
	
		if(straightEnough(CPV)||(Level>maxLevel))
		{
			g.drawLine((int)CPV[0][0],(int)CPV[0][1],(int)CPV[3][0],(int)CPV[3][1]);
			Point p=new Point();
			Point p1=new Point();
			Ships s;
			p.setLocation(CPV[0][0],CPV[0][1]);
			p1.setLocation(0,0);
			int z=game.size();
		
			//****Checks for collisions with the curve********
			for(int y=0;y<z;y++ )
			{
				if(game.elementAt(y) instanceof Ships)
				{
					s=(Ships)(game.elementAt(y));
					if(s.bounds(p,p1))
					{
						s.collision();
					}
				}
			}
		
		} 
	
		else
		{
			LeftSubVector[0][0]=CPV[0][0];
			LeftSubVector[0][1]=CPV[0][1];
			RightSubVector[3][0]=CPV[3][0];
			RightSubVector[3][1]=CPV[3][1];
		
			LeftSubVector[1][0]=((CPV[0][0] +CPV[1][0])/2);
			LeftSubVector[1][1]=((CPV[0][1]+ CPV[1][1])/2);
			RightSubVector[2][0]=((CPV[3][0] +CPV[2][0])/2);
			RightSubVector[2][1]=((CPV[3][1]+ CPV[2][1])/2);
		
			LeftSubVector[2][0]=((LeftSubVector[1][0]/2) +((CPV[1][0]+ CPV[2][0])/4));
			LeftSubVector[2][1]=((LeftSubVector[1][1]/2) +((CPV[1][1] +CPV[2][1])/4));
			RightSubVector[1][0]=((RightSubVector[2][0]/2)+ ((CPV[2][0]+ CPV[1][0])/4));
			RightSubVector[1][1]=((RightSubVector[2][1]/2)+ ((CPV[2][1]+ CPV[1][1])/4));
		
			LeftSubVector[3][0]=((LeftSubVector[2][0] +RightSubVector[1][0])/2);
			LeftSubVector[3][1]=((LeftSubVector[2][1] +RightSubVector[1][1])/2);
			RightSubVector[0][0]=((RightSubVector[1][0]+ LeftSubVector[2][0])/2);
			RightSubVector[0][1]=((RightSubVector[1][1] +LeftSubVector[2][1])/2);
		
		
			subdivideCurve(CPV,LeftSubVector,RightSubVector);
		
			Level ++ ;
			drawBezierCurve(LeftSubVector,Level,g);
			drawBezierCurve(RightSubVector,Level,g);
	
		}

	}

	//************************straightEnough Method*************
	//purpose: to determine if line is straight enough for curve
	//preconditions: double array with 4 points
	//postconditions: returns boolean
	//**********************************************************

	private boolean straightEnough(double[][] CPV)
	{
		double d1=0.0;
		double d2=0.0;
		double p1=0.0;
		double p2=0.0;
	
		d1 +=(Math.sqrt(((CPV[1][0]-CPV[0][0])*(CPV[1][0]-CPV[0][0]))+ ((CPV[1][1]-CPV[0][1])*(CPV[1][1]-CPV[0][1])))+
		Math.sqrt(((CPV[2][0]-CPV[1][0])*(CPV[2][0]-CPV[1][0]))+ ((CPV[2][1]-CPV[1][1])*(CPV[2][1]-CPV[1][1]))) +
		Math.sqrt(((CPV[3][0]-CPV[2][0])*(CPV[3][0]-CPV[2][0]))+ ((CPV[3][1]-CPV[2][1])*(CPV[3][1]-CPV[2][1]))));
	
		p1 +=((CPV[3][0]-CPV[0][0])*(CPV[3][0]-CPV[0][0]));
		p2 +=((CPV[3][1]-CPV[0][1])*(CPV[3][1]-CPV[0][1]));
	
		d2=Math.sqrt((p1 +p2));
	
		if(Math.abs(d1-d2)<epsilon)
			return true;
		else
			return false;

	}

	//************************subdivideCurve Method************
	//purpose: to subdivide curve
	//preconditions: sends in 3 valid arrays containing 4 points
	//postconditions: none
	//**********************************************************

	private void subdivideCurve(double[][] Q, double[][] R, double[][] S)
	{
		R[0][0]=Q[0][0];
		R[0][1]=Q[0][1];
		R[1][0]=((Q[0][0]/2.0)+ (Q[1][0])/2.0);//may just be and addition of the two Qs
		R[1][1]=((Q[0][1]/2.0) +(Q[1][1])/2.0);//may just be and addition of the two Qs
		R[2][0]=((R[1][0]/2.0)+((Q[1][0] + Q[2][0])/4.0));
		R[2][1]=((R[1][1]/2.0)+((Q[1][1] + Q[2][1])/4.0));
	
		S[3][0]=Q[3][0];
		S[3][1]=Q[3][1];
		S[2][0]=((Q[2][0] +Q[3][0])/2.0);
		S[2][1]=((Q[2][1] +Q[3][1])/2.0);
		S[1][0]=(((Q[1][0] +Q[2][0])/4.0)+ (S[2][0]/2.0));
		S[1][1]=(((Q[1][1]+ Q[2][1])/4.0)+ (S[2][1]/2.0));
	
		R[3][0]=((R[2][0]+ S[1][0])/2.0);
		R[3][1]=((R[2][1] +S[1][1])/2.0);
	
		S[0][0]=R[3][0];
		S[0][1]=R[3][1];
	}

	//************************mouseDragged Method*****************
	//purpose: to keep track of the mouse being dragged
	//preconditions: mouse event
	//postconditions: none
	//**********************************************************

	public void mouseDragged(MouseEvent e)
	{

		Point ptp2=new Point();
		ptp2.setLocation(e.getX(),e.getY()); 
		inVerse.transform(ptp2,ptp2);
		Point e3=new Point();
		e3.setLocation(ptp2.getX(),this.getLengthsY()-ptp2.getY());
	
		if(game.returnStart())
		{
			currentPoint=new Point();
			currentPoint.setLocation(ptp2.getX(),ptp2.getY());
			Ships ship=new Ships(this.getLengthsX(),this.getLengthsY());
			int z=game.size();
			for(int y=0;y<z;y++ )
			{
				if(game.elementAt(y) instanceof Ships)
				{
			
				ship=(Ships)(game.elementAt(y));
				ship.setSelected(false);
			
				}
			}
		}
		this.repaint();
	}

	//************************createStars Method*********
	//purpose: to create Background stars
	//preconditions: pass in Graphics
	//postconditions: none
	//*****************************************************
	private void createStars(Graphics g)
	{

		g.setColor(new Color(255,255,255));
	
		if(starCounter==0)
		{
			for(int q9=0;q9<100;q9++ )
			{
				stars[q9][0]=myRNG.nextInt(this.getWidth()); 
				stars[q9][1]=myRNG.nextInt(this.getHeight());
				g.drawLine(stars[q9][0],stars[q9][1],(stars[q9][0] +1),(stars[q9][1] +1));
			}
		}
		else
		{
			for(int q9=0;q9<100;q9++ )
			{
				g.drawLine(stars[q9][0],stars[q9][1],(stars[q9][0]),(stars[q9][1]));
		
			}
	
		}
		starCounter ++ ;
		if(starCounter==30)
			starCounter=0;

	}//end starCounter method

	//************************mouseClicked Method*********
	//*****************************************************

	public void mouseClicked(MouseEvent e)
	{


		Point ptp2=new Point();
		ptp2.setLocation(e.getX(),e.getY()); 
		inVerse.transform(ptp2,ptp2);
		Point e3=new Point();
		e3.setLocation(ptp2.getX(),this.getLengthsY()-ptp2.getY());
	
		Ships ship=new Ships(this.getX(),this.getY());
		SpaceMines mine=new SpaceMines(getX(),getY());
		int curMouseX=(int)e3.getX();
		int curMouseY=(int)e3.getY();
	
		int z=game.size();
		for(int y=0;y<z;y++ )
		{
	
			if(game.elementAt(y) instanceof SpaceMines)
			{
				mine=(SpaceMines)(game.elementAt(y));
				if(mine.contains(curMouseX,curMouseY))
				{
				mine.setSelected(true);
				Graphics g=this.getGraphics();
			
				}
				else
				{
				mine.setSelected(false);
			
				}
			}
		
			if(game.elementAt(y) instanceof Ships)
			{
				ship=(Ships)(game.elementAt(y));
				if(ship.contains(curMouseX,curMouseY))
				{
				ship.setSelected(true);
			
				}
				else
				{
				ship.setSelected(false);
			
				}
		
			}
	
		}

	}//end mouse clicked method

	//************empty methods required for interface******************
	public void mouseExited(MouseEvent e){}//empty, required for interface
	public void mouseMoved(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	//******************************************************************

	//***************MouseReleased Method********************
	//releases rubberbandline, removes spacemines if release
	//is located on a ship.

	public void mouseReleased(MouseEvent e)
	{
		Point ptp2=new Point();
		ptp2.setLocation(e.getX(),e.getY()); 
		inVerse.transform(ptp2,ptp2);
		Point e3=new Point();
		e3.setLocation(ptp2.getX(),this.getLengthsY()-ptp2.getY());
	
		if(game.returnStart())
		{
			Graphics g;
			Ships ship;
			int curMouseX=(int)e3.getX();
			int curMouseY=(int)e3.getY();
			SpaceMines mine=new SpaceMines(this.getLengthsX(),this.getLengthsY());
			int z=game.size();
			int d=z;
			int i;
			Point pointLocation;
		
			String temp2;
			for(int y=0;y<z;y++ )
			{
		
				if(game.elementAt(y) instanceof SpaceMines)
				{
				mine=(SpaceMines)(game.elementAt(y));
				mine.setField(false);
				}
		
				if(game.elementAt(y) instanceof Ships)
				{
			
				ship=(Ships)(game.elementAt(y));
				if((ship.contains(curMouseX,curMouseY))&&(!(startPoint==null))&&(!(currentPoint==null)))
				{
			
				for(int q=0;q < d; q++ )
				{
					if(game.elementAt(q) instanceof SpaceMines)
					{
				
					mine=(SpaceMines)(game.elementAt(q));
				
					if( (mine.getID()==tempID) && (!(mine.remove()) ))
					{
					i=mine.getID();
					temp2=Integer.toString(i);
					game.addScore(mine.returnValue());
					mine.setRemove(true);
				
					pointLocation=mine.getLocation();
					ship=new Ships(this.getLengthsX(),this.getLengthsY(),(int)pointLocation.getX(),(int)pointLocation.getY());
					ship.collision();
					game.addElement(ship);
					game.incrementTotal(2);
				
				
				
					}
					}
				}
			}
		}
	}

	startPoint=null;
	currentPoint=null;
	} 
	this.repaint();
	} //end mousepressed method



//*********************paintComponent method**************
//overiding paintcomponent

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	
	
		if(!game.returngameStarted())
		{
			g.setColor(Color.orange);
			String newString=new String("Created by Gregory A. Dias");
			g.drawString(newString,(this.getWidth()/2)-(newString.length()),(this.getHeight()/2)-16);
			newString=new String("Press Enter to Begin the Game");
			g.drawString(newString,(this.getWidth()/2)-(newString.length()),this.getHeight()/2);
			newString= new String("Current High Score "+ game.returnhighName() +" with "+ game.returnhighScore()+ " points");
			g.drawString(newString,((this.getWidth()/2))-(newString.length()),(this.getHeight()/2) + 16);
			newString=new String("Game Over");
			g.drawString(newString,((this.getWidth()/2))-(newString.length()),(this.getHeight()/2) +40);
		}
	
	
		createStars(g);
		g.setColor(Color.green);
		int z=game.size();
		Missiles m;
		Asteroids a; 
		Ships s;
		SpaceMines mine;
		Garbage gb;
		EnemyShip tuv;
	
		Graphics2D g2d=(Graphics2D)g;
		Viewport.setToIdentity();
		Viewport.scale(this.getWidth(),-this.getHeight());
	
		Viewport.scale((1/(WR-WL)),(1/(WT-WB)));
		Viewport.translate(-WB,-WL);
	
		g2d.transform(Viewport);
	
		if(inV)
		{

			try
			{
				inVerse=Viewport.createInverse();
			}
			catch(Exception e5)
			{
			}
		}
	
		if(game.returnStart())
		{
			//*******for rubberband******
			if(startPoint!=null && currentPoint !=null)
				g.drawLine((int)startPoint.getX(),(int)startPoint.getY(),(int)currentPoint.getX(),(int)currentPoint.getY());
		}
		//****end rubberbandline information****
	
		Point e4=new Point();
		Point ptp5=new Point();
		Rectangle test=new Rectangle();
	
	
	
		//************DRAWING OBJECTS IN THE WORLD*****************
		for(int y=0;y<z;y++ )
		{
			//draws all the objects 
			if(game.elementAt(y) instanceof Missiles)
			{
				m=(Missiles)(game.elementAt(y));
				m.draw(g2d);
			}
		
			if(game.elementAt(y) instanceof Ships)
			{
				s=(Ships)(game.elementAt(y));
				
				test=g2d.getClipBounds();
				ptp5=s.getLocation();
				e4.setLocation(ptp5.getX(),ptp5.getY());
				
				if(game.returnStart())
				{ 
					s.setStart(false);
					s.setWBWT(WB,WT);
					s.draw(g2d);
			
				//for ships to pan the world
				if(s.getSpeed()==0)
				{
						
					if((600-e4.getY())>((test.getY()+ test.getHeight()-50)))
					this.panUp1();
					if((600-e4.getY())<(test.getY()+ 50))
					this.panDown1();
					if(ptp5.getX()>(WT-50))
					this.panRight1();
					if(ptp5.getX()<(WB +50))
					this.panLeft1();
				}
			
				
				if(ptp5.getX()<(WB +50))                      //(s.getHeading()==0)///getLeft
					this.panLeft();
				if(ptp5.getX()<(WT -50)) //(s.getHeading()==359)//getRight
					this.panRight();
				if((600-e4.getY())>((test.getY()+ test.getHeight()-50)))//(s.getTop()==0)//changed fromgetUP to getTOp
					this.panUp();
				if((600-e4.getY())<(test.getY()+ 50))//(s.getBottom()==800)
					this.panDown();
				}
				else
				{
					s.setStart(true);
					s.draw(g2d);
				}
		
			}
	
			if(game.elementAt(y) instanceof Asteroids)
			{
				a=(Asteroids)(game.elementAt(y));
				a.draw(g2d);
			}
			if(game.elementAt(y) instanceof EnemyShip)
			{
				tuv=(EnemyShip)(game.elementAt(y));
		
				if(game.returnStart())
				{ 
					tuv.setStart(false);
					tuv.draw(g2d);
				}
				else
				{
					tuv.setStart(true);
					tuv.draw(g2d);
				}
			}
			if(game.elementAt(y) instanceof Garbage)
			{
				gb=(Garbage)(game.elementAt(y));
				gb.draw(g2d);
			}
	
	
	
			if(game.elementAt(y) instanceof SpaceMines)
			{
				mine=(SpaceMines)(game.elementAt(y));
				if(mine.remove())
				{
					if(mine.blink(g2d))
					{
						y--;
						z--;
						game.removeElement(mine);
			
					}
				}
				else
				{
					mine.draw(g2d);
			
					//**********draws BezierCurve***********************
				
					if(mine.returnField())
					{
					drawBezierCurve(mine.returnPoint(),0,g);
					//draws curve if still active
					}
				}
			}
	
		}

	}//end paintComponent


	}//ends display class
