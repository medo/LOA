package eg.edu.guc.loa.engine.interfaces;

public interface Checker {
	
	int EMPTYCELL = 0, WHITE = 1, BLACK = 2, WHITETURN = 0, BLACKTURN = 1;
	int[] COLORS = {EMPTYCELL, WHITE, BLACK};
	int[] TURNCOLOR = {WHITE, BLACK};
	
	int getColor();
	int getTeam();
}
