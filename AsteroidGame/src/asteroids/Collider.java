package asteroids;
import java.awt.Point;

public interface Collider {
	
	public boolean bounds(Point p1, Point p2);
	public void collision();
	
	public boolean removeCollision();

}
