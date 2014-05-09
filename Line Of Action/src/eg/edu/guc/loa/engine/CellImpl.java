package eg.edu.guc.loa.engine;


import eg.edu.guc.loa.engine.interfaces.Cell;
import eg.edu.guc.loa.engine.interfaces.Checker;

public class CellImpl implements Cell {
	
	private Checker piece;
	private Point position;
	
	public CellImpl() {
		piece = new CheckerImpl();
		position = new Point();
	}
	
	public CellImpl(Checker piece) {
		this();
		this.piece = piece;
	}
	
	public CellImpl(int color) {
		this();
		piece = new CheckerImpl(color);
	}

	public CellImpl(Point p, int color) {
		this();
		position = p;
		piece = new CheckerImpl(color);
	}
	
	public CellImpl(Point point) {
		this();
		position = point;
	}

	public Checker getPiece() {
		return piece;
	}

	public void setPiece(Checker piece) {
		this.piece = piece;
	}
	
	public void setPiece(int color) {
		piece = new CheckerImpl(color);
	}

	public boolean hasPiece() {
		return piece.getColor() != Checker.EMPTYCELL;
	}

	public Point getPosition() {
		return position;
	}

	public int getPieceColor() {
		return piece.getColor();
	}

	public void removePiece() {
		piece = new CheckerImpl();
	}
	
	public boolean equals(Object x) {
		Cell b = (Cell) x;
		return getPosition().equals(b.getPosition());
	}
}
