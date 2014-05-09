package eg.edu.guc.loa.engine.interfaces;

import java.util.List;

import eg.edu.guc.loa.engine.Point;

public interface Layout {
	
	int VERTICAL = 0, HORIZONTAL = 1, MAINDIAGONAL = 2, DIAGONAL = 3;
	int[] DIRECTION = {VERTICAL, HORIZONTAL, MAINDIAGONAL, DIAGONAL};
	int[][] FORMULA = {{0, 1, 0}, {1, 0, 0}, {1, -1, 1}, {1, 1, 0}};
	int[] COLORS = {Checker.WHITE, Checker.BLACK};
	int[][] DELTA = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
	
	Cell getCell(Point position);
	void init();
	void move(Point start, Point end);
	Cell[][] getGrid();
	int getHeight();
	int getWidth();
	boolean sameColor(Point point, int color);
	int getCount(Point start, int direction);
	boolean validPoint(Point point);
	boolean inBetween(Point start, Point point, int direction);
	int getColor(Point point);
	boolean possibleKill(Point start, Point end);
	List<Cell> getCells(int currentColor);
	int getPieceColor(Point p);
	void printBoard();
}
