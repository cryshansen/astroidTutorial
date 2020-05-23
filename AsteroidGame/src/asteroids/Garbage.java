/**
 * 
 */
package asteroids;

/**
 * @author hansenc
 * This is a Garbage class which will be the debris from the asteroids after they are destroyed. 
 * It contains similar methods to the missile class. 
 * It also will disappear after it has been in the game for a certain amount of time.
 *
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;




public class Garbage extends MovingSpaceObject implements Moveable,Drawable,Collider {

	/**
	 * 
	 */
	private int xWidth;//keep track of width of display
	private int yHeight;//keeps track of height of display
	//transform
	private AffineTransform myAT;
	private boolean collision;
	private int collisioncounter;
	Point finalPoint;
	private int base;
	
	
	public Garbage(int u, int r, int sp, Point xy, int base1, double heading2 ) {
		collision = false;
		collisioncounter = 0;
		setHeading(heading2);
		xWidth = u;
		yHeight=r;
		base=base1;
		setColor(new Color(150,255,64));
		if(sp<10)
			sp=10;
		setSpeed(sp);
		setLocation((int)xy.getX(),(int) xy.getY());
		
		myAT=new AffineTransform();
		finalPoint = new Point();
	
	}

	/**
	 * @param args
	 */
//draw Method
	public void draw(Graphics2D g)
	{
		int height1 = yHeight;//total height
		if(!(collision))
		{
			
			double heading2 = getHeading();
			heading2 +=2;
			if(heading2>359)
			{
				heading2 = heading2-359;
				AffineTransform saveAt = g.getTransform();
				g.setColor(getColor());
				Point location = getLocation();
				finalPoint.setLocation((double)0,(double)base);
				myAT.setToIdentity();
				myAT.translate((int)location.getX(), (int)location.getY());
				myAT.rotate(Math.toRadians(heading2));
				myAT.transform(finalPoint,finalPoint);
				
				g.drawLine((int)finalPoint.getX(),(int)finalPoint.getY(),(int)location.getX(),(int)location.getY());
				g.transform(this.myAT);
				g.setTransform(saveAt);
				
			}
			
		}
			
	}//end of draw method
// move method
	
	public void move(int elapsedMilliSecs)
	{
		
		if(!(collision))
		{
			
			double speed = (double)getSpeed();
			if(speed !=0)
			{
				double heading = getHeading();
				speed = (speed / ((double)elapsedMilliSecs));
				double heading2 = Math.toRadians(heading);//heading in radians
				Point p = getLocation();
				double x=p.getX();
				double y = p.getY();
				double x1 = Math.sin(heading2);
				double y1 = Math.cos(heading2);
				x1 +=x;
				y1+=y;
				
				if((heading>0)&&(heading <90))
				{
					x1+=speed;
					y1-=speed;
				}
				if((heading<270)&&(heading >180))
				{
					x1 -=speed;
					y1-=speed;
					
				}
				if((heading<360)&&(heading>270))
				{
					x1-=speed;
					y1+=speed;
					
				}
				
				
				if(heading==0)
				{
					y1 +=speed;
					
				}
				if(heading ==90)
				{
					x1 +=speed;
					
				}
				if(heading ==180)
				{
					
					y1 -=speed;

				}
				if(heading ==270)
				{
					x1 -=speed;
					
				}
				
				setLocation((int)x1,(int)y1);
				
			}//end if statement speed
		}
	}//end move method
	
	public void changeSpeed(int amount)
	{
		setSpeed(amount);
		
	}//end change speed
	public void changeHeading(int amount)
	{
		
		setHeading(amount);
	}
	
	public void collision()
	{
		
		collision = true;
	}
	public boolean removeCollision()
	{
		
		Point location = getLocation();
		if(location.getX()>3000)
			collision=true;
		else if(location.getX()<-3000)
			collision = true;
		else if (location.getY()>3000)
			collision =true;
		else if (location.getY()<-3000)
			collision=true;
		
		return collision;
	}
	
	public boolean bounds(Point p1,Point p2)
	{
		return true;
		
	}
	
	
}
