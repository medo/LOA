package eg.edu.guc.loa.net;
 
public interface Packet {
 
    String SERVER       = "Server";
        String CLOSE    = "close";
        String MOVE     = "move";
        String REGISTER = "join";
        String MESSAGE  = "msg";
        String ERROR    = "Error";
        String NICK             = "nick";
        String RETRIEVE = "show";
        String RETRIEVEROOM = "showroom";
        String DISCONNECTED     = "Diconnected";
        String ROOMID   = "id";
        String MATCH    = "match";
        String START    = "start";
        
        String getCommand();
        String[] getParameters();
        void setParameters(String[] params);
        String getFrom();
        void setFrom(String from);
}