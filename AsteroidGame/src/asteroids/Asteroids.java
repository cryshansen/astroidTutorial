/**
 * The Asteroid class randomly creates a polygon shape so each Asteroid looks different.  
 * It also contains an Affine Transform object and world locations where the object is located in the world.  
 * It has a move method to move the object, and the object will rotate as it moves.    
 * 
 */
package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Random;



public class Asteroids  extends MovingSpaceObject implements Collider,Moveable,Drawable
{

	private Random myRNG;
	private AffineTransform myAT;
	private int rotation;
	
	private int xWidth;
	private int yHeight;
	private int pointValue;
	private Point line1;
	private Point line2;
	private Point line3;
	private Point line4;
	private Point line5;
	private Point line6;
	private Point line7;
	private Point line8;

	private int base; //base of polygon
	private int height; //height of polygon
	private int div1; //random numbers
	private int div2;
	private int div3;
	private int div4;

	private boolean collision; //if asteroid has been in a collision

	int n=8;
	int[] x8=new int[n]; //points for polygon
	int[] y8=new int[n];
	Polygon po;

	private int getRandome(int i)
	{
		
		int r= myRNG.nextInt(i);
		return r;
	}
	public Asteroids(int u,int r)
	{
		myRNG = new Random();
		int x5 = getRandome(3000);//sets random locations
		int y5 = getRandome(3000);
		rotation = 90; //asteroids rotation
		setLocation(x5,y5);
		xWidth = u;
		yHeight = r;
		int x=1;
		
		x=getRandome(359);//heading
		if(x==0)
			x=1;
		setHeading(x);
		x=getRandome(200);//speed

		if(x<1)
			x=1;
		setSpeed(x);
		setColor(new Color(150,255,64));
		myAT = new AffineTransform();
		
		//points
		line1=new Point();
		line2=new Point();
		line3=new Point();
		line4=new Point();
		line5=new Point();
		line6=new Point();
		line7=new Point();
		line8=new Point();

		po=new Polygon();
		
		int ty = getRandome(40);
		if (ty <15)
			ty=15;
		base = ty;
		
		ty = getRandome(40);//random height of asteroid
		if(ty<15)
			ty = 15;
			height = ty;
		ty = getRandome(8);
		if(ty<2)
			ty=2;
		div1=ty;
		ty =getRandome(8);
		if(ty<2)
			ty=2;
			div2=ty;

			ty=getRandome(8);
			if(ty<2)
			ty=2;
			div3=ty;

			ty=getRandome(8);
			if(ty<2)
			ty=2;
			div4=ty;
			pointValue=5*height;
			//setting lines
	}//end constructor
	
	public Asteroids(int u,int r,double h1,int speed5,int xg,int yg,int base5,int height5)
	{
	myRNG=new Random();
	base=base5;
	height=height5;
	rotation=90; //asteroids rotation
	setLocation(xg,yg);

	xWidth=u;
	yHeight=r;
	//giving Asteroid random speed and heading
	int x=1;


	//setHeading(h1);

	setSpeed(speed5);
	setColor(new Color(150,255,64));
	myAT=new AffineTransform();

	//points
	line1=new Point();
	line2=new Point();
	line3=new Point();
	line4=new Point();
	line5=new Point();
	line6=new Point();
	line7=new Point();
	line8=new Point();

	po=new Polygon();



	int ty=getRandome(8);
	if(ty<2)
	ty=2;
	div1=ty;

	ty=getRandome(8);
	if(ty<2)
	ty=2;
	div2=ty;

	ty=getRandome(8);
	if(ty<2)
	ty=2;
	div3=ty;

	ty=getRandome(8);
	if(ty<2)
	ty=2;
	div4=ty;

	//setting lines

	}//end constructor

	public int getHeight()
	{
	return height;
	}

	//********draw Method********************
	public void draw(Graphics2D g)
	{
	if(!(collision))
	{

	g.setColor(getColor());

	AffineTransform saveAt=g.getTransform();

	int height1=yHeight; //total height
	Point location=getLocation();

	line1.setLocation((double)0,(double)height);
	line2.setLocation((double)base,(double)(height));
	line3.setLocation((double)(base/div4) ,(double)(height/div3));
	line4.setLocation((double)(base/div2) ,(double)(height/div1));
	line5.setLocation((double)(-base/div3),(double)(height/div2) );
	line6.setLocation((double)(-base/div3),(double)(height/div4));
	line7.setLocation((double)(-base),(double)(height/div2));
	line8.setLocation((double)0,(double)height);


	myAT.setToIdentity();
	myAT.translate((int)location.getX(),(int)location.getY());
	myAT.rotate(Math.toRadians(rotation));

	myAT.transform(line1,line1);
	myAT.transform(line2,line2);
	myAT.transform(line3,line3);
	myAT.transform(line4,line4);
	myAT.transform(line5,line5);
	myAT.transform(line6,line6);
	myAT.transform(line7,line7);
	myAT.transform(line8,line8);


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


	}//end draw Method

	//*********move Method******************
	public void move(int elapsedMilliSecs) 
	{
	//rotation
	if(!(collision))
	{
	rotation +=1;

	if(rotation==359)
	rotation=0;

	double speed=(double)getSpeed();

	double heading=getHeading();
	speed=(speed/((double)elapsedMilliSecs));
	double heading2=Math.toRadians(heading); //heading in radians
	Point p=getLocation();
	double x=p.getX(); //original location
	double y=p.getY();
	double x1=Math.sin(heading2); 
	double y1=Math.cos(heading2);
	x1 +=x;
	y1 +=y;

	if((heading>0)&&(heading<90))
	{
	x1 +=speed;
	y1 +=speed;
	} 
	if((heading<180)&&(heading>90)) 
	{ x1 +=speed;
	y1-=speed;
	}
	if((heading<270)&&(heading>180))
	{ x1-=speed;
	y1-=speed;
	}
	if((heading<360)&&(heading>270))
	{
	x1-=speed;
	y1 +=speed; 
	} 
	if(heading==0)
	{
	y1 +=speed;
	}
	if(heading==90)
	{
	x1 +=speed;
	}
	if(heading==180)
	{
	y1-=speed;
	}

	if(heading==270)
	{
	x1-=speed;
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
	}

	}//end move Method

	public int getBase()
	{
	 return base;
	}

	//*********************bounds Method******************
	public boolean bounds(Point p5,Point p6)
	{

		if(po.intersects(p5.getX(),p5.getY(),p6.getX(),p6.getY()))
		return true;
		else return false;

	}
	//********************collision Method*****************
	public void collision()
	{
		collision=true;
	}

	//*******************removeCollision Method************
	public boolean removeCollision()
	{
		return collision;
	}

	public int returnValue()
	{
		return pointValue;
	}

}//end Asteroids Class

