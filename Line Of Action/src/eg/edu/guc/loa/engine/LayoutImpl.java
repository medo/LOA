package eg.edu.guc.loa.engine;

import java.util.ArrayList;
import java.util.List;

import eg.edu.guc.loa.engine.interfaces.Cell;
import eg.edu.guc.loa.engine.interfaces.Layout;
import eg.edu.guc.loa.engine.interfaces.Checker;

public class LayoutImpl implements Layout {
	
	private Cell[][] grid;
	private int[][][] gridCount;
	
	public LayoutImpl() {
		init();
	}
	
	public LayoutImpl(int[][] f) {
		grid = new Cell[8][8];
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				grid[x][y] = new CellImpl(new Point(y, x), f[x][y]);
			}
		}
		
		
		gridCount = new int[4][3][16];
		for (int q = 0; q < 4; q++) {
			for (int w = 0; w < 3; w++) {
				for (int e = 0; e < 16; e++) {
					gridCount[q][w][e] = 0;
				}
			}
		}


		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				for (int direction = 0; direction < DIRECTION.length; direction++) {
					int d = DIRECTION[direction];
					int color = getCell(new Point(y, x)).getPieceColor();
					int index = getIndex(new Point(y, x), d);
					gridCount[d][color][index]++;
				}
			}
		}
	}
	
	public void init() {
		grid = new Cell[8][8];
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				grid[x][y] = new CellImpl(new Point(y, x));
			}
		}
		
		for (int q = 0; q < 6; q++) {
			grid[0][q + 1].setPiece(Checker.WHITE);
			grid[7][q + 1].setPiece(Checker.WHITE);
			grid[q + 1][0].setPiece(Checker.BLACK);
			grid[q + 1][7].setPiece(Checker.BLACK);
		}
		
		gridCount = new int[4][3][16];
		for (int q = 0; q < 4; q++) {
			for (int w = 0; w < 3; w++) {
				for (int e = 0; e < 16; e++) {
					gridCount[q][w][e] = 0;
				}
			}
		}


		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				for (int direction = 0; direction < DIRECTION.length; direction++) {
					int d = DIRECTION[direction];
					int color = getCell(new Point(y, x)).getPieceColor();
					int index = getIndex(new Point(y, x), d);
					gridCount[d][color][index]++;
				}
			}
		}
	}
	
	private int getIndex(Point point, int d) {
		int x = point.getRow();
		int y = point.getCol();
		int z = getWidth();
		
		return x * FORMULA[d][0] + y * FORMULA[d][1] + z * FORMULA[d][2];
	}

	public Cell getCell(Point position) {
		if (validPoint(position)) {
			return grid[position.getRow()][position.getCol()];
		}
		return null;
	}

	public void move(Point start, Point end) {
		Checker piece = getPiece(start);
		
		removePiece(start);
		removePiece(end);
		addPiece(end, piece);
	}

	private void addPiece(Point position, Checker piece) {
		int x = position.getRow();
		int y = position.getCol();
		
		addCount(position, 1, piece.getColor());
		
		grid[x][y].setPiece(piece);
	}

	private void removePiece(Point position) {
		int x = position.getRow();
		int y = position.getCol();
		
		addCount(position, -1, getPieceColor(position));
		
		grid[x][y].removePiece();
	}

	private void addCount(Point position, int value, int color) {
		for (int direction = 0; direction < DIRECTION.length; direction++) {
			int d = DIRECTION[direction];
			int index = getIndex(position, d);
			gridCount[d][color][index] += value;
		}
	}

	private Checker getPiece(Point position) {
		int x = position.getRow();
		int y = position.getCol();
		
		return grid[x][y].getPiece();
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public int getHeight() {
		return grid.length;
	}

	public int getWidth() {
		return grid[0].length;
	}

	public boolean sameColor(Point position, int color) {
		
		if (!validPoint(position)) {
			return false;
		}
		
		int x = position.getRow();
		int y = position.getCol();
		
		return grid[x][y].getPieceColor() == color;
	}

	public int getCount(Point position, int direction) {
		int d = DIRECTION[direction];
		int index = getIndex(position, d);
		int result = 0;
		for (int colori = 0; colori < COLORS.length; colori++) {
			int color = COLORS[colori];
			if (color != Checker.EMPTYCELL) {
				result += gridCount[d][color][index];
			}
		}
		return result;
	}

	public boolean inBetween(Point start, Point end, int direction) {
		int startX = start.getRow();
		int startY = start.getCol();
		
		int endX = end.getRow();
		int endY = end.getCol();
		
		int d = DIRECTION[direction];
		
		int dx = LayoutImpl.DELTA[d][0];
		int dy = LayoutImpl.DELTA[d][1];
		
		int jumpLength;
		
		if (dx != 0) {
			jumpLength = (endX - startX) / dx;
		} else {
			jumpLength = (endY - startY) / dy;
		}
		
		if (jumpLength < 0) {
			dx *= -1;
			dy *= -1;
			jumpLength *= -1;
		}
		
		int color = getPieceColor(start), badcolor;
		
		if (color == Checker.WHITE) {
			badcolor = Checker.BLACK;
		} else {
			badcolor = Checker.WHITE;
		}
		
		for (int q = 1; q < jumpLength; q++) {
			int newX = startX + dx * q;
			int newY = startY + dy * q;
			if (sameColor(new Point(newY, newX), badcolor)) {
				return false;
			}
		}
		
		return true;
	}

	public boolean validPoint(Point point) {
		int x = point.getRow();
		int y = point.getCol();
		
		if (x >= 0 && x < getHeight() && y >= 0 && y < getWidth()) {
			return true;
		}
		
		return false;
	}

	public int getColor(Point point) {
		int x = point.getRow();
		int y = point.getCol();
		
		return grid[x][y].getPieceColor();
	}

	public boolean possibleKill(Point start, Point end) {
		int color = getPieceColor(end);
		return !sameColor(start, color);
	}

	public List<Cell> getCells(int color) {
		List<Cell> cells = new ArrayList<Cell>();
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				if (sameColor(new Point(y, x), color)) {
					cells.add(grid[x][y]);
				}
			}
		}
		return cells;
	}

	public int getPieceColor(Point p) {
		int x = p.getRow();
		int y = p.getCol();
		if (grid[x][y] == null) {
			return -5;
		}
		
		return grid[x][y].getPieceColor();
	}

	public void printBoard() {
		for (int x = 0; x < getHeight(); x++) {
			for (int y = 0; y < getWidth(); y++) {
				System.out.print(getPieceColor(new Point(y, x)));
			}
			System.out.println();
		}
	}
	
}
