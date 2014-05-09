package eg.edu.guc.loa.controllers;

import java.util.List;
import java.util.Random;

import eg.edu.guc.loa.controllers.exceptions.ConnectionLostException;
import eg.edu.guc.loa.controllers.exceptions.InvalidMoveException;
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.interfaces.Player;

public class SingleUserController implements Controller {

	private UserInterface ui;
	private int turn;
	
	public SingleUserController(UserInterface ui) {
		this.ui = ui;
	}
	
	@Override
	public void message(String msg) throws ConnectionLostException {
		// does nothing
	}

	@Override
	public void move(Point p1, Point p2) throws InvalidMoveException {
		ui.play(turn);
		switchTurn();
	}

	@Override
	public void run() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
			turn = 1;
		} else {
			turn = 2;
		}
		ui.play(turn);
		switchTurn();
		
	}

	private void switchTurn() {
		if (turn == 1) {
			turn = 2;
		} else {
			turn = 1;
		}
		
	}

	@Override
	public void register(String room) throws FullRoomException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Room> getRooms() {
		// TODO Auto-generated method stub
		return null;
	}

}
