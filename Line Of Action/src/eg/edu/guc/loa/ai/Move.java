package eg.edu.guc.loa.ai;

import eg.edu.guc.loa.engine.Point;

public class Move {
	
	private Point start;
	private Point end;
	
	public Move(Point start, Point end) {
		this.start 	= start;
		this.end 	= end;
	}
	
	public Point getStart() {
		return this.start;
	}
	
	public Point getEnd() {
		return this.end;
	}
}
