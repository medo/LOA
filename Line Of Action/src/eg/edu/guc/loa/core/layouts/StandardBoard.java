package eg.edu.guc.loa.core.layouts;

import java.util.List;

import eg.edu.guc.loa.core.pieces.StandardPiece;
import eg.edu.guc.loa.core.players.Player;
import eg.edu.guc.loa.exceptions.InvalidMoveException;

public interface StandardBoard {

		
	/*
	 * initializes the Board, set up the pieces etc
	 */
	public void init(List<Player> players) throws UnsupportedOperationException;
	public StandardLayout getLayout();
	public void makeMove(Player player, StandardLayoutPosition move) throws InvalidMoveException;
	public boolean isWinner(Player player);
	public int getNumberOfSeparatedGroups(Player player);
	public StandardLayoutPosition[] getValidMoves(StandardPiece piece);

	
}
