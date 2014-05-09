package eg.edu.guc.loa.controllers;

import eg.edu.guc.loa.ai.SimpleMachinePlayer;

public class ControllerFactory {

	public static Controller getAIControllerInstance(int level, UserInterface ui) {
		return new AIController(level, ui);
	}
	
	public static Controller getAIControllerInstance(UserInterface ui) {
		return getAIControllerInstance(2, ui);
	}
	
	public static Controller getSingleUserControllerInstance(UserInterface ui) {
		return new SingleUserController(ui);
	}
	
	public static Controller getNetworkController(UserInterface ui) {
		return new NetworkController(ui);
	}
	
}
