package eg.edu.guc.loa.net.parsers;

public interface Command {

	void exec(String[] params, String from) throws InvalidCommandParametersExcetpion;
	
}
