package eg.edu.guc.loa.controllers;

import java.util.List;

import eg.edu.guc.loa.controllers.exceptions.ConnectionLostException;
import eg.edu.guc.loa.controllers.exceptions.InvalidMoveException;
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.interfaces.Player;

public interface Controller {

	void run();
	void register(String room) throws FullRoomException; 
	void message(String msg) throws ConnectionLostException;
	void move(Point p1, Point p2) throws InvalidMoveException;
	List<Room> getRooms();
	
}
