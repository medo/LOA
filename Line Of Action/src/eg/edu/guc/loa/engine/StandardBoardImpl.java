package eg.edu.guc.loa.engine;

import java.util.ArrayList;
import java.util.List;

import eg.edu.guc.loa.ai.Move;
import eg.edu.guc.loa.engine.interfaces.Cell;
import eg.edu.guc.loa.engine.interfaces.Layout;
import eg.edu.guc.loa.engine.interfaces.Checker;
import eg.edu.guc.loa.engine.interfaces.Player;
import eg.edu.guc.loa.engine.interfaces.StandardBoard;

public class StandardBoardImpl implements StandardBoard {
	
	private Layout board;
	private Player white, black;
	private int turn;
	
	public void init() {
		board = new LayoutImpl();
		white = new PlayerImpl(Checker.WHITE);
		black = new PlayerImpl(Checker.BLACK);
		turn = Checker.WHITETURN;
	}
	
	public StandardBoardImpl() {
		init();
	}
	
	public StandardBoardImpl(int[][] board) {
		this();
		setBoard(board);
	}
	
	public void setBoard(int[][] board) {
		this.board = new LayoutImpl(board);
	}
	
	public StandardBoardImpl(Layout board, Player white, Player black) {
		this.board = board;
		this.white = white;
		this.black = black;
		turn = Checker.WHITETURN;
	}
	
	public boolean move(Point start, Point end) {
		
		if (getPieceColor(start) != getTurn().getColor()) {
			return false;
		}
		
		List<Cell> possibleMoves = getPossibleMoves(start);
		Cell endCell = new CellImpl(end);
		for (Cell cell : possibleMoves) {
			if (endCell.equals(cell)) {
				board.move(start, end);
				if (turn == Checker.WHITETURN) {
					turn = Checker.BLACKTURN;
				} else {
					turn = Checker.WHITETURN;
				}
				return true;
			}
		}
		return false;
	}

	public boolean isGameOver() {
		return getWinner() != null;
	}

	private void floodfill(Point source, boolean[][] visited, int color) {
		Point[] queue = new Point[board.getHeight() * board.getWidth()];
		int queueStart = 0, queueEnd = 0;
		
		if (!board.sameColor(source, color)) {
			return;
		}
		
		queue[queueEnd++] = source;
		visited[source.getRow()][source.getCol()] = true;
		
		while (queueStart < queueEnd) {
			Point cur = queue[queueStart++];
			
			for (int direction = 0; direction < Layout.DIRECTION.length; direction++) {
				for (int k = -1; k < 2; k += 2) {
					int d = Layout.DIRECTION[direction];
					int dx = Layout.DELTA[d][0] * k;
					int dy = Layout.DELTA[d][1] * k;
					
					int newX = cur.getRow() + dx;
					int newY = cur.getCol() + dy;
					if (board.sameColor(new Point(newY, newX), color) && !visited[newX][newY]) {
						visited[newX][newY] = true;
						queue[queueEnd++] = new Point(newY, newX);
					}
				}
			}
		}
	}

	public Player getWinner() {
		int height = board.getHeight();
		int width = board.getWidth();
		boolean[][] visited = new boolean[height][width];
		if (!canMove()) {
			if (turn == Checker.WHITETURN) {
				return black;
			} else {
				return white;
			}
		}
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				visited[x][y] = false;
			}
		}
		Player winner = null;
		for (int colori = 0; colori < Checker.COLORS.length; colori++) {
			int color = Checker.COLORS[colori];
			if (color == Checker.EMPTYCELL) {
				continue;
			}
			for (int x = 0; x < height; x++) {
				for (int y = 0; y < width; y++) {
					if (board.sameColor(new Point(y, x), color)) {
						floodfill(new Point(y, x), visited, color);
						x = height;
						break;
					}
				}
			}
			boolean won = true;
			for (int x = 0; x < height; x++) {
				for (int y = 0; y < width; y++) {
					if (board.sameColor(new Point(y, x), color) && !visited[x][y]) {
						won = false;
					}
				}
			}
			if (won && !(winner != null && winner.getColor() != getTurn().getColor())) {
				if (color == Checker.WHITE) {
					winner = white;
				} else {
					winner = black;
				}
			}
		}
		return winner;
	}

	private boolean canMove() {
		int currentColor;
		
		if (turn == Checker.WHITETURN) {
			currentColor = Checker.WHITE;
		} else {
			currentColor = Checker.BLACK;
		}
		
		List<Cell> cells = board.getCells(currentColor);
		for (Cell cell : cells) {
			if (getPossibleMoves(cell.getPosition()).size() > 0) {
				return true;
			}
		}
		return false;
	}

	public int getPieceColor(Point p) {
		return board.getPieceColor(p);
	}
	
	public int getColor(Point p) {
		return board.getColor(p);
	}

	public Player getTurn() {
		if (turn == Checker.WHITETURN) {
			return white;
		} else {
			return black;
		}
	}
	
	public List<Cell> getPossibleMoves(Point start) {
		List<Cell> possibleCells = new ArrayList<Cell>();
		
		if (getTurn().getColor() != getPieceColor(start)) {
			return possibleCells;
		}
		
		for (int direction = 0; direction < Layout.DIRECTION.length; direction++) {
			int jumpLength = board.getCount(start, direction);
			for (int k = -1; k < 2; k += 2) {
				int x = start.getRow();
				int y = start.getCol();
				int d = Layout.DIRECTION[direction];
				int dx = LayoutImpl.DELTA[d][0] * jumpLength * k;
				int dy = LayoutImpl.DELTA[d][1] * jumpLength * k;
				
				int newX = x + dx;
				int newY = y + dy;
				Point end = new Point(newY, newX);
				if (!board.validPoint(end)) {
					continue;
				}
				
				if (board.inBetween(start, end, d)) {
					if (board.possibleKill(start, end)) {
						possibleCells.add(board.getCell(end));
					}
				}
			}
		}
		return possibleCells;
	}

	public Layout getLayout() {
		return board;
	}
	
	public boolean isValidPoint(Point p) {
		return p.getRow() >= 0 && p.getRow() < 8 && p.getCol() >= 0 && p.getCol() < 8;
	}
	
	public void setTurn(int color) {
		if (getTurn().getColor() != color) {
			if (turn == Checker.WHITETURN) {
				turn = Checker.BLACKTURN;
			} else {
				turn = Checker.WHITETURN;
			}
		}
	}
	
	public static List<Move> getAllPossibleMoves(int[][] b, int color) {
		ArrayList<Move> result = new ArrayList<Move>();
		StandardBoardImpl board = new StandardBoardImpl(b);
		board.setTurn(color);
		
		for (int x = 0; x < b.length; x++) {
			for (int y = 0; y < b[x].length; y++) {
				if (b[x][y] == color) {
					Point start = new Point(y, x);
					List<Cell> moves = board.getPossibleMoves(start);
					for (Cell cell : moves) {
						result.add(new Move(start, cell.getPosition()));
					}
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		StandardBoardImpl x = new StandardBoardImpl();
		x.getLayout().printBoard();
		x.move(new Point(3, 0), new Point(3, 2));
		x.getLayout().printBoard();
		x.move(new Point(0, 2), new Point(3, 2));
		System.out.println(x.getTurn().getColor());
		x.getLayout().printBoard();
	}
	
}
