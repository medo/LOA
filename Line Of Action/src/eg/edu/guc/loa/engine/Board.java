package eg.edu.guc.loa.engine;

import java.util.ArrayList;
import java.util.List;

import eg.edu.guc.loa.engine.interfaces.BoardInterface;
import eg.edu.guc.loa.engine.interfaces.Cell;

public class Board implements BoardInterface {
	private StandardBoardImpl board;
	
	public Board() {
		board = new StandardBoardImpl();
	}

	public boolean move(Point start, Point end) {
		boolean x = board.move(start, end);
		return x;
	}

	public boolean isGameOver() {
		return board.isGameOver();
	}

	public int getWinner() {
		return board.getWinner().getTeam();
	}

	public int getColor(Point p) {
		return board.getColor(p);
	}

	public int getTurn() {
		return board.getTurn().getTeam();
	}

	public ArrayList<Point> getPossibleMoves(Point start) {
		List<Cell> cells = board.getPossibleMoves(start);
		ArrayList<Point> points = new ArrayList<Point>();
		for (Cell cell : cells) {
			points.add(cell.getPosition());
		}
		
		return points;
	}
	
	public void printBoard() {
		board.getLayout().printBoard();
	}
	
}
