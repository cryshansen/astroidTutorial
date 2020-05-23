package asteroids;
/*
 * This class contains an enemy ship in the game.  
 * It contains all of the same methods as the other ship.java class.  
 * The difference is that the ship is shaped differently, and that it has some type of intelligence.
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.Vector;



public class EnemyShip extends MovingSpaceObject implements Collider, Moveable, Drawable{

	private boolean selected;
	private Random myRNG;
	private int xWidth;
	private int yHeight;
	
	private int base;
	private int height;
	
	private boolean incSpeed;
	
	private AffineTransform myAT;
	private AffineTransform inVerse;
	
	private boolean collision;
	private int collisioncounter;
	private boolean start;
	
	private Point line1;
	private Point line2;
	private Point line3;
	private Point line4;
	private Point line5;
	private Point line6;
	private Point line7;
	private Point line8;

	private int n=8;
	private int[] x8=new int[n]; //points for polygon
	private int[] y8=new int[n];
	private Polygon po;

	private Vector world;

	private boolean lockedOn=false;
	private int headingcount=0;

	public EnemyShip(int u,int r, Vector wor)
	{
	world=wor;
	incSpeed=false;
	collision=false;
	collisioncounter=0;
	start=false;
	xWidth=u;
	yHeight=r;
	selected=false; 
	myRNG=new Random();
	int x=myRNG.nextInt(3000);
	int y=myRNG.nextInt(3000);
	setColor(new Color(255,0,175));
	setSpeed(50);
	setLocation(x,y);
	x=myRNG.nextInt(359);
	if(x==0)
	x=1;
	setHeading(x); 

	base=20;
	height=35;

	myAT=new AffineTransform();
	inVerse=new AffineTransform();

	line1=new Point();
	line2=new Point();
	line3=new Point();
	line4=new Point();
	line5=new Point();
	line6=new Point();
	line7=new Point();
	line8=new Point();


	po=new Polygon();
	}//end constructor




	//******draw Method*********************************
	public void draw(Graphics2D g)
	{

	int height1=yHeight; //total height
	if(!(collision))
	{


	AffineTransform saveAt=g.getTransform();


	Point location=getLocation();

	line1.setLocation((double)(-base/2),(double)((-height/2)-5) );
	line2.setLocation((double)(0),(double)(height));
	line3.setLocation((double)(base/2),(double)((-height/2)-5) );
	line4.setLocation((double)(3),(double)((-height/2) +3) );
	line5.setLocation((double)(double)(0),(double)(height));
	line6.setLocation((double)(-3),(double)((-height/2) +3) );
	line7.setLocation((double)(0),(double)((-height/2)-6) );
	line8.setLocation((double)(3),(double)((-height/2) +3) );

	myAT.setToIdentity();

	myAT.translate((int)location.getX(),(int)location.getY());

	myAT.rotate(Math.toRadians(getHeading()));

	myAT.transform(line1,line1);
	myAT.transform(line2,line2);
	myAT.transform(line3,line3);
	myAT.transform(line4,line4);
	myAT.transform(line5,line5);
	myAT.transform(line6,line6);
	myAT.transform(line7,line7);
	myAT.transform(line8,line8);

	g.setColor(getColor());


	x8[0]=(int)line1.getX();
	y8[0]=(int)line1.getY();
	x8[1]=(int)line2.getX();
	y8[1]=(int)line2.getY();
	x8[2]=(int)line3.getX();
	y8[2]=(int)line3.getY();
	x8[3]=(int)line4.getX();
	y8[3]=(int)line4.getY();
	x8[4]=(int)line5.getX();
	y8[4]=(int)line5.getY();
	x8[5]=(int)line6.getX();
	y8[5]=(int)line6.getY();
	x8[6]=(int)line7.getX();
	y8[6]=(int)line7.getY();
	x8[7]=(int)line8.getX();
	y8[7]=(int)line8.getY();

	po=new Polygon(x8,y8,n);
	g.fillPolygon(po);

	g.transform(this.myAT);

	g.setTransform(saveAt);

	}
	else
	{
	Point location=getLocation();
	g.setColor(getColor());
	g.fillArc((int)location.getX(),(int)location.getY(),collisioncounter,collisioncounter,30,60);
	if(!start)
	collisioncounter++ ;
	}

	}//end draw Method


	//********move Method*******************************
	public void move(int elapsedMilliSecs)
	{

	if(!(collision))
	{
	Point p=getLocation();
	double x=p.getX(); //original location
	double y=p.getY();

	Ships s=null;
	double heading9=getHeading();
	double tempHeading=0.0;
	//***************INTELLIGENCE*********************
	int z9=world.size();
	Point location9=null;
	try
	{
	for(int y9=0;y<z9;y9++ )
	{
	if(world.elementAt(y9) instanceof Ships)
	{

	s=(Ships)(world.elementAt(y9));
	location9=s.getLocation();

	if((heading9>0)&&(heading9<90)&&((location9.getX()>x))&&((location9.getY()>y)))
	{ increaseSpeed();
	if(this.getSpeed()>125)
	decreaseSpeed();


	}
	else{
	tempHeading=(heading9+ 10);
	if(tempHeading>359)
	tempHeading=0;
	setHeading(tempHeading);
	headingcount++ ;
	if(headingcount==6)
	{headingcount=0;
	increaseSpeed();}
	if(this.getSpeed()>125)
	decreaseSpeed();


	}

	if((heading9<180)&&(heading9>89)&&((location9.getX()>x))&&(!(location9.getY()>y)))
	{
	increaseSpeed();
	if(this.getSpeed()>125)
	decreaseSpeed();



	}
	else{
	tempHeading=(heading9 +10);
	if(tempHeading>359)
	tempHeading=0;
	setHeading(tempHeading);
	headingcount++ ;
	if(headingcount==6)
	{headingcount=0;
	increaseSpeed();}
	if(this.getSpeed()>125)
	decreaseSpeed();



	}


	if((heading9<270)&&(heading9>179)&&(!(location9.getX()>x))&&(!(location9.getY()>y)))
	{
	increaseSpeed();
	if(this.getSpeed()>125)
	decreaseSpeed();



	}
	else{
	tempHeading=(heading9+ 10);
	if(tempHeading>359)
	tempHeading=0;
	setHeading(tempHeading);
	headingcount++ ;
	if(headingcount==6)
	{headingcount=0;
	increaseSpeed();}
	if(this.getSpeed()>125)
	decreaseSpeed();



	}


	if((heading9<360)&&(heading9>270)&&(!(location9.getX()>x))&&((location9.getY()>y)))
	{
	increaseSpeed();
	if(this.getSpeed()>125)
	decreaseSpeed();



	}
	else{
	tempHeading=(heading9 +10);
	if(tempHeading>359)
	tempHeading=0;
	setHeading(tempHeading);
	headingcount++ ;
	if(headingcount==6)
	{headingcount=0;
	increaseSpeed();}
	if(this.getSpeed()>125)
	decreaseSpeed();


	}


	}//end if instance of ship
	}//end for loop
	}//end try block
	catch(Exception exception)
	{
	}
	//************************************

	double speed=(double)getSpeed();


	if(speed!=0)
	{

	double heading=getHeading();

	if(speed<0)
	{
	heading +=180;
	if(heading>359)
	heading-=359;
	}
	speed=Math.abs(speed);
	speed=(speed/((double)elapsedMilliSecs));
	double heading2=Math.toRadians(heading); //heading in radians



	double x1=Math.sin(heading2);
	double y1=Math.cos(heading2);

	if((heading>0)&&(heading<90))
	{
	x1 +=x;
	y1 +=y;
	x1-=speed;
	y1 =speed;
	}
	if((heading<180)&&(heading>90))
	{
	x1 =x;
	y1 =y;
	x1-=speed;
	y1-=speed;
	}
	if((heading<270)&&(heading>180))
	{
	x1 +=x;
	y1 +=y;
	x1 +=speed;
	y1-=speed;
	}
	if((heading<360)&&(heading>270))
	{
	x1 +=x;
	y1 +=y;
	x1 +=speed;
	y1 +=speed;
	}
	if(heading==0)
	{
	x1 +=x;
	y1 +=y;
	y1 +=speed;
	}
	if(heading==90)
	{
	x1 +=x;
	y1 +=y;
	x1 -=speed;
	}
	if(heading==180)
	{
	x1 +=x;
	y1 +=y;
	y1-=speed;
	}

	if(heading==270)
	{
	x1 +=x;
	y1 +=y;
	x1 +=speed;
	}

	if(x1>3000)
	{
	x1=-3000;
	}
	if(x1<-3000)
	{
	x1=3000;
	}

	if(y1>3000)
	{
	y1=-3000;
	}
	if(y1<-3000)
	{
	y1=3000;
	}
	setLocation((int)x1,(int)y1);


	}//end if statement if speed==0

	}
	}//end move method

	//********changeSpeed Method************************
	public void changeSpeed(int amount)
	{
	setSpeed(amount);

	}//end changeSpeed method

	//********changeHeading Method***********************
	public void changeHeading(int amount)
	{
	setHeading(amount);

	}//end changeHeading method

	//******************collision Method***********************
	public void collision()
	{

		collision=true;

	}

	public Point getTop()
	{
		return line2;
	}


	public boolean returnCollision()
	{
	return collision;
	}

	//*****************removeCollision Method***************
	public boolean removeCollision()
	{
		if(collisioncounter>40)
		{
			return true;
		}
		else
			return false;
	}


	public void setStart(boolean a)
	{
		start=a;
	}

	//************************bounds Method********************
	public boolean bounds(Point p5,Point p6)
	{

		if(po.intersects(p5.getX(),p5.getY(),p6.getX(),p6.getY()))
			return true;
		else return false;

	}



}//end Ships class

	

