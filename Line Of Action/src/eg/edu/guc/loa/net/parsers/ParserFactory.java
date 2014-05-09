package eg.edu.guc.loa.net.parsers;

public class ParserFactory {

	public static MessageParser getInstance() {
		return new SimpleMessageParser();
	}
	
}
