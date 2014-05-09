package eg.edu.guc.loa.engine;

public class Point {
	private int x, y;
	public Point() {
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRow() {
		return y;
	}
	
	public int getCol() {
		return x;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public boolean equals(Object x) {
		Point b = (Point) x;
		return (getX() == b.getX()) && (getY() == b.getY());
	}
	
}
