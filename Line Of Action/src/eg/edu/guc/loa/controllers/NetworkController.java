package eg.edu.guc.loa.controllers;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
 
import eg.edu.guc.loa.controllers.exceptions.ConnectionLostException;
import eg.edu.guc.loa.controllers.exceptions.InvalidMoveException;
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.net.Packet;
import eg.edu.guc.loa.net.PacketImpl;
import eg.edu.guc.loa.net.parsers.Command;
import eg.edu.guc.loa.net.parsers.InvalidCommandParametersExcetpion;
import eg.edu.guc.loa.net.parsers.MessageParser;
import eg.edu.guc.loa.net.parsers.ParserFactory;
 
public class NetworkController implements Controller {
 
    private UserInterface ui;
        private Connection connection;
        
        
        private int color;
        
        private class Connection extends Thread {
                
                public static final String SEPARATOR = "^!";
                
                private final String address = "localhost";
                private final int port = 7070;
                private Socket socket;
                private BufferedReader br;
                private PrintWriter pw;
                private MessageParser parser;
                private HashMap<String, Command> cmds;
                private boolean timeToEnd = false;
                public boolean registered = false;
                
                
                public Connection() throws UnknownHostException, IOException {
                        initCommands();
                        parser  = ParserFactory.getInstance();
                        socket  = new Socket(address, port);
                        pw              = new PrintWriter(socket.getOutputStream());
                        br              = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String serverVersion = br.readLine();
                        ui.notifyServerVersion(serverVersion);  
                       
                }
                
                public void initCommands() {
                        cmds = new HashMap<String, Command>();
                        cmds.put(Packet.MESSAGE, new Command() {
                                
                                @Override
                                public void exec(String[] params, String from ) throws InvalidCommandParametersExcetpion {
                                        ui.message(concat(params), from);
                                }
                        });
                        
                        cmds.put(Packet.MOVE, new Command() {
                                @Override
                                public void exec(String[] params, String from) throws InvalidCommandParametersExcetpion {
                                        Point p1 = new Point(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                                        Point p2 = new Point(Integer.parseInt(params[2]), Integer.parseInt(params[3]));
                                        ui.move(p1, p2, from);
                                        if (color <= 2) {
                                            ui.play(color);
                                        }
                                }
                        });
                        
                        cmds.put(Packet.ERROR, new Command() {
                                @Override
                                public void exec(String[] params, String from) throws InvalidCommandParametersExcetpion {
                                        ui.notifyError(concat(params));
                                }       
                        });
                        
                        cmds.put(Packet.ROOMID, new Command() {
                                
                                @Override
                                public void exec(String[] params, String from)
                                                throws InvalidCommandParametersExcetpion {
                                        color = Integer.parseInt(params[0]) + 1;
                                        
                                }
                        });
                        
                        cmds.put(Packet.START, new Command() {
                                
                                @Override
                                public void exec(String[] params, String from)
                                                throws InvalidCommandParametersExcetpion {
                                        ui.startGame(params[0], params[1]);
                                        if (color == 1) {
                                                ui.play(color);
                                        }     
                                }
                        });
                        
                        cmds.put(Packet.DISCONNECTED, new Command() {
                            
                            @Override
                            public void exec(String[] params, String from)
                                            throws InvalidCommandParametersExcetpion {
                                        	ui.notifyDisconnection(from);
                            }
                    });
                        
                }
                
                public Packet recv() {
                        try {
                                return parser.parse(br.readLine());
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        return null;
                }
                
                @Override
                public void run() {
                        while (!timeToEnd && registered) {
                                try {
                                        Packet recv = parser.parse(br.readLine());
                                        cmds.get(recv.getCommand()).exec(recv.getParameters(), recv.getFrom());
                                } catch (IOException e) {
                                        ui.notifyError(e.getMessage());
                                }
                        }
                        
                }
                
                public void send(Packet msg) {
                        String res = String.format(msg.getCommand());
                        if (msg.getParameters() != null) {
                                for (String s : msg.getParameters()) {
                                        res += " " + s;
                                }
                        }
                        send(res);
                }
                
                public void send(String msg) {
                        pw.println(msg);
                        pw.flush();
                }       
        }
        
        @Override
        public void message(String msg) throws ConnectionLostException {
                Packet pak = new PacketImpl(Packet.MESSAGE);
                String[] params = {msg};
                pak.setParameters(params);
                connection.send(pak);
        }
 
        private String concat(String[] s) {
                StringBuffer buf = new StringBuffer(s[0]);
                for (int i = 1; i < s.length; i++) {
                        buf.append(" ");
                        buf.append(s[i]);
                }
                return buf.toString();
        }
        
        @Override
        public void move(Point p1, Point p2) throws InvalidMoveException {
                Packet pak = new PacketImpl(Packet.MOVE);
                String[] params = {     "" + p1.getX(), 
                                                        "" + p1.getY(),
                                                        "" + p2.getX(),
                                                        "" + p2.getY()};
                pak.setParameters(params);
                connection.send(pak);
        }
 
        public NetworkController(UserInterface ui) {
                this.ui = ui;
                try {
                        connection = new Connection();
                } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                this.ui = ui;
        }
        
        @Override
        public void run() throws NotRegisteredException {
                try {
                        if (!connection.registered) {
                                throw new NotRegisteredException("You must be registered in a room");
                        }
                        connection.start();
                } catch (Exception e) {
                        ui.notifyError(e.getMessage());
                }       
        }
 
        
        private void initNickName() {
        	 Packet pak = new PacketImpl(Packet.NICK);
             String[] param = {ui.getNickName()};
             pak.setParameters(param);
             connection.send(pak);
        }
 
        public void register(String room) throws FullRoomException {
                initNickName();
                Packet pak = new PacketImpl(Packet.REGISTER);
                String[] param = {room};
                pak.setParameters(param);
                connection.send(pak);
                connection.registered = true;
                
        }
 
        public List<Room> getRooms() {
                Packet pak = new PacketImpl(Packet.RETRIEVE);
                connection.send(pak);
                Packet rec = connection.recv();
                List<Room> res = new LinkedList<Room>();
                for (String s : rec.getParameters()) {
                        String[] d = s.split(Connection.SEPARATOR);
                        Room r = new Room(d[0]);
                        r.setNumberOfSubscribers(Integer.parseInt(d[1]));
                        res.add(r);
                }
                return res;
        }
        
        private Room getRoom(String room) {
                Packet pak = new PacketImpl(Packet.RETRIEVE);
                connection.send(pak);
                Packet rec = connection.recv();
                String[] d = rec.getParameters()[0].split(Connection.SEPARATOR);
                Room r = new Room(d[0]);
                r.setNumberOfSubscribers(Integer.parseInt(d[1]));
                return r;
                
        }
 
        public void match() {
        		initNickName();
                Packet pak = new PacketImpl(Packet.MATCH);
                connection.send(pak);
                connection.registered = true;
        }
 
}