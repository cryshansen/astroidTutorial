package asteroids;

import java.awt.Color;
import java.awt.Point;

public class SpaceObjects {

private Point point1;
private Color color;

	public SpaceObjects()
	{
		point1 = new Point();
	}
	
	public void setLocation(int x1,int y1)
	{
		
		point1.setLocation(x1,y1);

	}
	
	public Point getLocation()
	{
		
		return point1;
	}
	
	public void setColor(Color c)
	{
		
		color = c;
		
	}
	
	public Color getColor()
	{
		
		return color;
	}
	
}
