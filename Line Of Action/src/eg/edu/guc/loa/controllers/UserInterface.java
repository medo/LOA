package eg.edu.guc.loa.controllers;
 
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.interfaces.StandardBoard;
 
public interface UserInterface {
    
 
        String getNickName();
        void move(Point p1, Point p2, String from);
        void move(Point p1, Point p2);
        void message(String msg, String from);
        void play(int color);
        void notifyDisconnection(String errorMsg);
        void notifyError(String msg);
        void notifyOpponentInfo(Info info); // up till now this is never called
        void notifyServerVersion(String version);
        void setBoard(StandardBoard board);
        void startGame(String player1, String player2);
        void startNewGame();
}