package eg.edu.guc.loa.controllers.exceptions;

public class ConnectionLostException extends RuntimeException {

	private static final long serialVersionUID = 4586008178044023229L;
	
	public ConnectionLostException(String msg) {
		super(msg);
	}

}
