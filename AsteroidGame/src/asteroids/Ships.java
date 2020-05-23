/*
 * The next class is the Ship.java object that will be the ship the user controls in the game.
 * The object isn't a complicated object. 
 * I will explain the methods in my next post.
 *http://www.theprogrammersweblog.com/2008/12/creating-asteroids-in-java-2d-part-8.html
 */

package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Ships extends MovingSpaceObject implements Collider,Steerable,Moveable,Drawable,Selectable {
/**/
	

	@Override
	public void setSelected(boolean newVal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(int elapsedMilliSecs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeSpeed(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeHeading(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean bounds(Point p1, Point p2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void collision() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeCollision() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
/**/
	
	private boolean selected;
	private Random myRNG;
	private int xWidth;
	private int yHeight;
	private int base;
	private int height;
	private Point top;
	private Point lowerLeft;
	private Point lowerRight;
	private boolean incSpeed;
	private AffineTransform myAT;
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
	private Point line9;
	private Point line10;
	private Point line11;
	private Point line12;
	
	private Rectangle rect;
	private double WB;
	private double WT;
	private boolean left = false;
	private boolean right =false;
	private boolean up=false;
	private boolean down=false;
	

	public Ships(int u, int r)
	{
		
		incSpeed = false;
		collision = false;
		collisioncounter = 0;
		start = false;
		xWidth = u;
		yHeight=r;
		selected = false;
		myRNG = new Random();
		int x = myRNG.nextInt(3000);
		int y = myRNG.nextInt(3000);
		setColor(new Color(0,0,255));//sets color
		setSpeed(0);
		setLocation(x,y);
		
		base = 15;//sets the initial base and height of the ship
		height = 25;
		
		myAT = new AffineTransform();
		top = new Point();
		lowerLeft = new Point();
		lowerRight = new Point();
		
		line1 = new Point();
		line2=new Point();
		line3=new Point();
		line4 =new Point();
		line5=new Point();
		line6=new Point();
		line7=new Point();
		line8=new Point();
		line9=new Point();
		line10=new Point();
		line11=new Point();
		line12=new Point();

		rect = new Rectangle();
		
		
	}//end constructor
	

	public Ships(int u, int r, int locationx, int locationy)
	{
		
		collision = false; 
		collisioncounter = 0;
		myRNG = new Random();
		xWidth = u;
		yHeight = r;
		selected= false;
		setColor(new Color(0,0,255));
		setSpeed(0);
		setLocation(locationx,locationy);
		
		base = 15;
		height = 25;
		
		myAT = new AffineTransform();
		top = new Point();
		lowerLeft = new Point();
		lowerRight = new Point();
		
		line1 = new Point();
		line2 = new Point();
		line3=new Point();
		line4=new Point();
		line5=new Point();
		line6=new Point();
		line7=new Point();
		line8=new Point();
		line9=new Point();
		line10=new Point();
		line11=new Point();
		line12=new Point();
		
		rect = new Rectangle();
		

	}
	
	public void setincSpeed(boolean sr)
	{
		
		incSpeed=sr;
	}
	public boolean contains(int x, int y)
	{
		Point ptp = getLocation();
		double x1 = ptp.getX();
		double y1 = ptp.getY();
		
		if( (x>(x1+(-base/2))) && (x <(x1+(base/2))) && (y>(y1+(-height/2))) && (y<(y1 + (height/2))) )
		{
			
			return true;
			
		}else{
			return false;
		}
	}//end contains method

	public Point getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Point getBounds()
	{
		
		return this.getLocation();
	}
	/*
	 * The ship object keeps track of its own AffineTransform.  
	 * An AffineTransform is any transformation that preserves collinearity 
	 * (all points lying on a line initially still lie on a line after transformation). 
	 * The Affine Transformation is congruent and  preserves proportions , but it does not necessarily preserve angles or lengths.   
	 * The contains method still checks for collisions within the bounds of the object.  The highlight method is still used to know if the object is selected.  
	 * We use the Affine Transformation to transform the lines according to the new location.  
	 * The setWBWT are the methods that will see the world location of this object. 
	 * The move method will of course move the object using speed, and the heading to calculate the movement of the ship.   
	 * 
	 * 
	*/

	public Point getTop() {
		// TODO Auto-generated method stub
		//this would be base of ship plus height and where it is on the display pane...
		
		return top;
	}
	public void	setStart(boolean value)
	{
		start = value;
	}
	public void setWBWT(double inWB, double inWT)
	{
		WB = inWB;
		WT = inWT;
	}

}
