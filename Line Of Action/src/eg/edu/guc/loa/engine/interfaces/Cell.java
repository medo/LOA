package eg.edu.guc.loa.engine.interfaces;

import eg.edu.guc.loa.engine.Point;

public interface Cell {
	
	Checker getPiece();
	void setPiece(Checker piece);
	void setPiece(int color);
	boolean hasPiece();
	Point getPosition();
	int getPieceColor();
	void removePiece();
}
