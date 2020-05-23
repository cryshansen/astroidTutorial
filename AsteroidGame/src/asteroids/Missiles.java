package asteroids;
/*
 * http://www.theprogrammersweblog.com/2008/12/this-query-will-check-if-you-have.html
 * 
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;




public class Missiles extends MovingSpaceObject implements Moveable,Drawable {

	private int power;
	private AffineTransform myAT;
	
	private int xWidth;
	private int yHeight;
	
	private Point top;
	private Point bottom;
	
	private int height;
	private int base;
	
	public Missiles(int x, int y, double heading, int speed, int u, int r)
	{
		
		base =15;
		height = 25;
		
		xWidth = u;
		yHeight =r;
		power = 80;
		setLocation(x,y);
		
		setHeading(heading);
		setColor(new Color(190,240,190));
		int w;
		float t;
		if(speed ==0)
			speed = 2;// calculates speed 20% faster then ship
		
		t=(float)(speed*2);
		t+=(float)(speed);
		t+=Math.ceil(t);
		setSpeed((int) t);
		
		myAT = new AffineTransform();
		top = new Point();
		bottom = new Point();
		
	}//end constructor
	
	//********************************* Return Power Method ******************
	/* 
	 * purpose to return the amount of power this missle has
	 * preconditions none
	 * postconditions: return int
	 * 
	 */
	 
	public int returnPower()
	{
		return power;	
	}
	
	//********************************* decrement Power Method ******************
	/*
	 * purpose subtracts one from power 
	 * preconditions none 
	 * post conditions none
	 *  
	 */
	public void decrementPower()
	{
		
		power -=1;
	}
	 
	//********************************* getBounds Power Method ******************
		/*
		 * purpose to return the location of this bounding box 
		 * preconditions none 
		 * post conditions returns Point
		 *  
		 */
	public Point getBounds1()
	{
		
		Point top=getLocation();
		double x=top.getX();
		double y1=top.getY();
		double y=(yHeight-y1);
		Point p2 = new Point();
		p2.setLocation(x,y);
		return p2;
	}
	
	public Point getBounds2()
	{
		
		Point p5 = new Point();
		p5.setLocation(2,2);
		return p5;
	}
	
	
	/*
	 * draw Method
	 * 
	 * 
	 */
	public void draw(Graphics2D g)
	{
		g.setColor(getColor());
		AffineTransform saveAT = g.getTransform();
		int height1 = yHeight; //total height
		Point location = getLocation();
		
		top.setLocation(0,height/2);
		double heading2 = Math.toRadians((getHeading()));//heading in radians
		double x = Math.sin(heading2);
		double y = Math.cos(heading2);
		
		x+=top.getX();
		y+=top.getY();
		double heading = getHeading();
		if((heading>0)&&(heading <90))
		{
			bottom.setLocation((x-2),(y+2));
		}
		if((heading<270)&&(heading >180))
		{
			bottom.setLocation((x-2),(y-2));
		}
		if((heading<360)&&(heading>270))
		{
			
			bottom.setLocation((x+2),(y+2));
		}
		
		if(heading==0)
		{
			bottom.setLocation((x),(y+2));
			
		}
		if(heading ==90)
		{
			bottom.setLocation((x-2),(y));
			
		}
		if(heading ==180)
		{
			
			bottom.setLocation((x),(y-2));
		}
		if(heading ==270)
		{
			
			bottom.setLocation((x+2),(y));
		}
		
		myAT.setToIdentity();
		myAT.translate((int)location.getX(),(int)location.getY());
		myAT.rotate(Math.toRadians(getHeading()));
		myAT.transform(top, top);
		myAT.transform(bottom, bottom);
		
		g.drawLine((int)top.getX(),(height1-((int)top.getY())), (int)bottom.getX(),(height1-(int)bottom.getY()));
		g.setTransform(saveAT);
		
	}//end draw Method
	 
	
	/*
	 * 
	 * 
	 * 
	 */
	public void move(int elapsedMilliSecs)
	{
		double speed = (double) getSpeed();
		speed = Math.abs(speed);
		if(speed<80)
			speed=80;
		speed = (speed/((double)elapsedMilliSecs));
		double heading = getHeading();
		double heading2 = Math.toRadians(heading);//heading in radians
		Point p = getLocation();
		double x = p.getX();//original location
		double y=p.getY();
		double x1 = Math.sin(heading2);
		double y1 = Math.cos(heading2);
		x1 +=x;
		y1 +=y;
		
		if((heading>0)&&(heading <90))
		{
			
			x1-=speed;
			y1+=speed;
		}
		
		if((heading>90)&&(heading <180))
		{
			x1-=speed;
			y1 -=speed;
		}
		if((heading>90)&&(heading <180))
		{
			x1-= speed;
			y1-= speed;
		}
		if((heading>180)&&(heading <270))
		{
			x1+=speed;
			
			y1 -=speed;
		}
		if((heading>180)&&(heading <360))
		{
			x1+=speed;
			y1+=speed;
		}
		
		if(heading==0)
		{
			y1 +=speed;
		}
		if(heading==90)
		{
			x1-=speed;
		}
		if(heading==180)
		{
			y1-=speed;
		}

		if(heading==270)
		{
			x1 +=speed;
		}


		setLocation((int)x1,(int)y1);
		
	}//end move Method
	
	
	
}//end Missile Class
