package eg.edu.guc.loa.net.parsers;

import eg.edu.guc.loa.net.Packet;
import eg.edu.guc.loa.net.PacketImpl;



public class SimpleMessageParser implements MessageParser {
	
	
	public SimpleMessageParser() {
		
	}
	
	@Override
	public Packet parse(String msg) {
		String[] str = msg.split(" ");
		String[] cmd = str[0].split(":");
		Packet pak = new PacketImpl(cmd[0]);
		pak.setFrom(cmd[1]);
		String[] params = new String[str.length - 1];
		for (int i = 1; i < str.length; i++)
			params[i - 1] = str[i];
		pak.setParameters(params);
		return pak;
	}

}
