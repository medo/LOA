package eg.edu.guc.loa.ai;

import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.interfaces.BoardInterface;
import eg.edu.guc.loa.engine.interfaces.StandardBoard;

public interface MachinePlayer {

	void move(Point p1, Point p2);
	void init(StandardBoard board);
	void setColor(int color);
	Move getMove();
	
	
}
