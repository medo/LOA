package eg.edu.guc.loa.engine.interfaces;

import java.util.List;

import eg.edu.guc.loa.engine.Point;

public interface StandardBoard {
	boolean move(Point start, Point end);
	boolean isGameOver();
	Player getWinner();
	int getColor(Point p);
	Player getTurn();
	List<Cell> getPossibleMoves(Point start);
	Layout getLayout();
}
