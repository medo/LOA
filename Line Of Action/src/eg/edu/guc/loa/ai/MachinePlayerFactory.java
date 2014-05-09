package eg.edu.guc.loa.ai;

public class MachinePlayerFactory {

	public static MachinePlayer getInstance(int level) {
		return new SimpleMachinePlayer(level);
	}
	
}
