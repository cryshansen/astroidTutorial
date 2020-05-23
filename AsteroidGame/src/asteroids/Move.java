package asteroids;

public interface Move {
	
	public void move();
	public void Prepare(float e);
	public double getPositionX();
	public double getPositionZ();
	public void rotate();
	public double getFuturePosX();
	public double getFuturePosZ();
	
}
