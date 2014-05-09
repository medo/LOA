package eg.edu.guc.loa.net;

public class PacketImpl implements Packet {

	private String command;
	private String[] params;
	private String from;
	
	public PacketImpl(String command) {
		this.command = command;
	}
	
	@Override
	public String getCommand() {
		return this.command;
	}

	public void setParameters(String[] params) {
		this.params = params;
	}
	
	@Override
	public String[] getParameters() {
		return this.params;
	}

	@Override
	public String getFrom() {
		return from;
	}

	@Override
	public void setFrom(String from) {
		this.from = from;
	}


	
}
