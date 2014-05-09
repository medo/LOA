package eg.edu.guc.loa.engine;

import eg.edu.guc.loa.engine.interfaces.Checker;
import eg.edu.guc.loa.engine.interfaces.Player;

public class PlayerImpl implements Player {

	private int color, id, team;
	private String name;
	
	
	public PlayerImpl() {
	}
	
	public PlayerImpl(int color) {
		this.color = Checker.COLORS[color];
		team = color;
	}
	
	public PlayerImpl(String name, int id, int color) {
		this.color = color;
		this.name = name;
		this.id = id;
		team = color;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public int getColor() {
		return color;
	}

	public int getTeam() {
		return team;
	}

}
