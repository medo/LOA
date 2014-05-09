package eg.edu.guc.loa.controllers.exceptions;

public class InvalidMoveException extends RuntimeException {

	private static final long serialVersionUID = 332217749069422248L;
	
	public InvalidMoveException(String msg) {
		super(msg);
	}
	
}
