package eg.edu.guc.loa.engine;

import eg.edu.guc.loa.engine.interfaces.Move;

public class MoveImpl implements Move {
	
	private Point start = null, end = null;

	public MoveImpl() {
	}
	
	public MoveImpl(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

}
