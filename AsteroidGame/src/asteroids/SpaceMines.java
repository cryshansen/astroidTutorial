/*
 * 
*This post is to explain the SpaceMines.java class I posted in my last post.  
*This class holds information about the plasmaField.  It also randomly sets the location in the world.  
*It has a 'contains' method which will be used to to check the bounds of the object.  
*The draw method will draw the object on the screen.   
*The blink method will change the object when it has been selected.
*
*/
package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;


public class SpaceMines extends StationarySpaceObject implements Selectable, Drawable
{

	private boolean selected;
	private int ID;
	private int pointValue;
	private Random myRNG;
	private double [][] curvePoint;
	private boolean plasmaField;
	private int xWidth;
	private int yHeight;
	
	private int blinkcounter;
	private boolean remove;
	
	
	
	public SpaceMines (int u, int r)
	{
		remove = false;
		blinkcounter = 0;
		xWidth=u;
		yHeight = r;
		plasmaField=false;
		curvePoint = new double [4][2];
		selected = false;
		myRNG = new Random();
		int x5 = myRNG.nextInt(3000);
		int y5 = myRNG.nextInt(3000);
		setLocation(x5,y5);
		setColor(new Color(100,100,255));
		pointValue=10;
		
		
	}
	
	public void setLocation(int x5, int y5) {
		// TODO Auto-generated method stub
		
	}

	public boolean contains(int x, int y)
	{
		
		//affine transform inverse
		Point p= getLocation();
		int x1 = (int)p.getX();
		int y1 = (int)p.getY();
		
		if(x>1 &&x<(x1+25)&&(y+25)>y1&&(y+25)<(y1+25))
			return true;
		else
			return false;
	}
	
	public void savePoint(double[][] point)
	{
		
		curvePoint = point;
		plasmaField = true;
		
		
	}
	
	public double [][] returnPoint()
	{
		return curvePoint;
		
	}
	
	public boolean returnField()
	{
		
		return plasmaField;
	}
	
	public void setField(boolean a)
	{
		plasmaField = a;
		
	}
	
	public boolean isSelected()
	{
		
		return selected;
			
	}
	//isselectedMetod
	public void highlight()
	{
		
		
		setColor(new Color(255,100, 0));
		
	}
	
	
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}

	public Point getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public void draw(Graphics2D g)
	{
		
		if(selected)
			highlight();
		
		Point pointx = getLocation();
		int e=(int)pointx.getX();
		int y=(int) pointx.getY();
		int temp=(yHeight-y);
		int w = temp;
		
		g.setColor(new Color(25,25,25));
		if(selected)
			g.fill3DRect(e, w, 25, 25, true);
		else 
			g.draw3DRect(e, w, 25, 25, true);
		
		int n=12;
		int[] x8 = new int[n];
		int[]y8 = new int[n];
		
		x8[0]=(e-5);
		y8[0]=(w);
		x8[1]=(e);
		y8[1]=(w-5);
		x8[2]=(e+ 25);
		y8[2]=y8[1];
		x8[3]=(e+ 30);
		y8[3]=(w);
		x8[4]=(e+ 25);
		y8[4]=(w+ 14);
		x8[5]=(e+ 30);
		y8[5]=(w+ 25);
		x8[6]=x8[3];
		y8[6]=(w +25);
		x8[7]=(e+ 25);
		y8[7]=(w+ 30);
		x8[8]=(e);
		y8[8]=(w+ 30);
		x8[9]=(e-5);
		y8[9]=(w +25);
		x8[10]=(e-5);
		y8[10]=(w);
		x8[11]=(e-5);
		y8[11]=(w);
	
		g.drawPolyline(x8, y8, n);
		setColor(new Color(100,100,255));
		
	}//end draw method

	public boolean remove()
	{
		return remove;
	}
	
	public void setRemove(boolean r)
	{
		remove = r;
	}
	public boolean blink(Graphics g)
	{
		Point pointx = getLocation();
		int e=(int)pointx.getX();
		int y = (int)pointx.getY();
		
		int temp = (yHeight - y);
		int w = temp;
		
		int x5 = myRNG.nextInt(255);
		int x6 = myRNG.nextInt(255);
		int x7 = myRNG.nextInt(255);
		
		g.setColor(new Color(x5,x6,x7));

		g.fillRoundRect(e, w, 25,25,5,5);
		
		blinkcounter++;
		
		if(blinkcounter ==10)
			return true;
		else
			return false;
		
	}
	
	public void setID(int x)
	{
		
		ID = x;
		
	}
	public int returnValue()
	{
		
		return pointValue;
	}
	
	public int getID()
	{
		
		return ID;
	}
	
	public Point getBounds()
	{
		
		Point pt = new Point();
		
		pt = getLocation();
		double x = pt.getX();
		double y = pt.getY();
		y = (yHeight -y);
		Point pt1 = new Point();
		pt1.setLocation(x,y);
		return pt1;
		
	}
	
	public Point returnHW()
	{
		Point pt = new Point();
		pt.setLocation(30,30);
		return pt;
	}

	@Override
	public void setSelected(boolean newVal) {
		// TODO Auto-generated method stub
		
	}
	
	
}

