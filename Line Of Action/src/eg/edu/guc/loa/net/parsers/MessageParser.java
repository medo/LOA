package eg.edu.guc.loa.net.parsers;

import eg.edu.guc.loa.net.Packet;



public interface MessageParser {

	Packet parse(String msg);
	
}
