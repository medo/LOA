package eg.edu.guc.loa.engine.interfaces;

import java.util.ArrayList;

import eg.edu.guc.loa.engine.Point;

public interface BoardInterface {
	boolean move(Point start, Point end);
	boolean isGameOver();
	int getWinner();
	int getColor(Point p);
	int getTurn();
	ArrayList<Point> getPossibleMoves(Point start);
}
