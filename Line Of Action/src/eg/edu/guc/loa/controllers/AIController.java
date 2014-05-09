package eg.edu.guc.loa.controllers;

import java.util.List;
import java.util.Random;

import eg.edu.guc.loa.ai.MachinePlayer;
import eg.edu.guc.loa.ai.MachinePlayerFactory;
import eg.edu.guc.loa.ai.Move;
import eg.edu.guc.loa.controllers.exceptions.ConnectionLostException;
import eg.edu.guc.loa.controllers.exceptions.InvalidMoveException;
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.StandardBoardImpl;

public class AIController implements Controller {

	private static final int AICOLOR = 1;
	private UserInterface ui;
	private int color;
	private MachinePlayer ai;
	private int level;
	private final String[] messages = 	{"El3ab w enta saket !"
										, "Khaly Azazy yel3ab"
										, "Hatly Farghal akalemo"
										, "Dana 7amarmatak"
										, ":*"};
	
	
	
	public AIController(int level, UserInterface ui) {
		this.level = level;
		this.ui = ui;
	}
	
	@Override
	public void run() {
		ai		= MachinePlayerFactory.getInstance(level);
		ai.init(new StandardBoardImpl());
		//the AI is always 1
		ai.setColor(AICOLOR);
		Random r = new Random();
		if (r.nextBoolean()) {
			color = 1;
		} else { 
			color = 2;
		}
		if (color == 1)
			play();
		else 
			ui.play(color);
	}

	private void play() {
		Move m = ai.getMove();
		ui.move(m.getStart(), m.getEnd());
		changeTurn();
		ui.play(color);
	}
	
	private void changeTurn() {
		if (color == 1) {
			color = 2;
		} else {
			color = 1;
		}
	}

	@Override
	public void move(Point p1, Point p2) throws InvalidMoveException {
		ai.move(p1, p2);
		changeTurn();
		play();
	}
	
	@Override
	public void register(String room) throws FullRoomException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void message(String msg) throws ConnectionLostException {
		Random r = new Random(System.currentTimeMillis());
		ui.message(messages[Math.abs(r.nextInt()) % messages.length], "Computer");
	}

	@Override
	public List<Room> getRooms() {
		// TODO Auto-generated method stub
		return null;
	}


}
