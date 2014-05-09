package eg.edu.guc.loa.engine;


import eg.edu.guc.loa.engine.interfaces.Checker;

public class CheckerImpl implements Checker {

	private int color;
	
	public CheckerImpl() {
		color = EMPTYCELL;
	}

	public CheckerImpl(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public int getTeam() {
		if (color == WHITE) {
			return WHITE;
		} else if (color == BLACK) {
			return BLACK;
		}
		return EMPTYCELL;
	}

}
