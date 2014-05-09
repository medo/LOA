package eg.edu.guc.loa.ui.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.naming.ldap.ControlFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import eg.edu.guc.loa.controllers.Controller;
import eg.edu.guc.loa.controllers.ControllerFactory;
import eg.edu.guc.loa.controllers.FullRoomException;
import eg.edu.guc.loa.controllers.Info;
import eg.edu.guc.loa.controllers.NetworkController;
import eg.edu.guc.loa.controllers.SingleUserController;
import eg.edu.guc.loa.controllers.UserInterface;
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.StandardBoardImpl;
import eg.edu.guc.loa.engine.interfaces.StandardBoard;

@SuppressWarnings("serial")
public class GUI extends JFrame implements UserInterface, MouseListener, ActionListener, MouseMotionListener, KeyListener {
	
	private StartWindowPanel startWindowPanel;
	private BoardPanel boardPanel;
	private Controller controller;
	
	/// Chat Window Variables
	private JButton sendButton;
	private JTextField chatInputField;
	private JScrollPane chatField;
	private JTextArea chatOutputField;
	
	/// Network Type Variables
	private JButton findRoomButton;
	private JButton joinRoomButton;
	
	/// Join Room Variables
	private JTextField nickNameInputField;
	private JTextField roomNameInputField;
	
	private JTextArea spareText1, spareText2;
	private JButton continueButton;
	
	private String nickName = "Player";
	
	private int gameType = -1;
	
	public GUI() {
		this.addMouseListener(this);
		
		startWindowPanel = new StartWindowPanel();
		startWindowPanel.getSinglePlayerButton().addActionListener(this);
		startWindowPanel.getMultiPlayerButton().addActionListener(this);
		startWindowPanel.getNetworkButton().addActionListener(this);
		
		initStartWindow();
	}

	public void setSize(int x, int y) {
		this.setResizable(true);
		super.setSize(x, y);
		this.setResizable(false);
	}
	
	private void initStartWindow() {
		gameType = -1;
		
		this.getContentPane().removeAll();
		this.setTitle("Line Of Action");
		
		this.setSize(500, 500);
		this.setLocation(100, 100);
		
		this.setLayout(null);
		this.add(startWindowPanel);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initGameWindow() {
		this.getContentPane().removeAll();
		
		this.setTitle("Line Of Action");
		this.setSize(980, 700);
		this.setLocation(0, 0);
		this.addKeyListener(this);
		
		/// Adding Board
		boardPanel = new BoardPanel(this);
		boardPanel.setController(controller);
		boardPanel.setLocation(0, 0);
		this.add(boardPanel);
		
		/// Adding Chat Window
		sendButton = new JButton("Send");
		sendButton.setLocation(900, 640);
		sendButton.setSize(80, 40);
		sendButton.addActionListener(this);
		this.add(sendButton);
		
		chatInputField = new JTextField();
		chatInputField.setSize(220, 40);
		chatInputField.setLocation(680, 640);
		chatInputField.addKeyListener(this);
		
		if (gameType == 1) {
			chatInputField.setEnabled(false);
		}
		
		this.add(chatInputField);
		
		chatField = new JScrollPane();
		chatField.setSize(300, 640);
		chatField.setLocation(680, 0);
		
		chatOutputField = new JTextArea();
		chatOutputField.setLineWrap(true);
		chatOutputField.setEditable(false);
		chatField.getViewport().add(chatOutputField);
		this.add(chatField);
		
		
		
		this.repaint();
	}

	@Override
	public void play(int color) {
		boardPanel.setTurn(color);
	}
	
	public void move(Point p1, Point p2, String from) {
		boardPanel.moveNoTurn(p1, p2);
	}

	@Override
	public void notifyDisconnection(String errorMsg) {
		viewError(errorMsg + " is disconnected");
		initStartWindow();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void initGetNetworkTypeWindow() {
		this.getContentPane().removeAll();
		
		this.setTitle("Line Of Action");
		this.setSize(550, 150);
		
		findRoomButton = new JButton("Find Room");
		joinRoomButton = new JButton("Join Room");
		
		findRoomButton.addActionListener(this);
		joinRoomButton.addActionListener(this);
		
		findRoomButton.setSize(200, 35);
		joinRoomButton.setSize(200, 35);
		
		findRoomButton.setLocation(50, 37);
		joinRoomButton.setLocation(300, 37);
		
		this.add(findRoomButton);
		this.add(joinRoomButton);
		
		this.repaint();
	}

	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getSource() == startWindowPanel.getSinglePlayerButton()) {
			gameType = 0;
			controller = ControllerFactory.getAIControllerInstance(this);
			initGameWindow();
		}
		else if (arg0.getSource() == startWindowPanel.getMultiPlayerButton()) {
			gameType = 1;
			controller = ControllerFactory.getSingleUserControllerInstance(this);
			initGameWindow();
		}
		else if (arg0.getSource() == startWindowPanel.getNetworkButton()) {
			gameType = 2;
			try {
				controller = ControllerFactory.getNetworkController(this);
			} catch (Exception e) {
				viewError("Connection Error!");
				startNewGame();
			}
			initGetNetworkTypeWindow();
		}
		else if (arg0.getSource() == sendButton) {
			String msg = chatInputField.getText().trim();
			if (msg.length() > 0) {
				controller.message(msg);
				message(msg, nickName);
				chatInputField.setText("");
			}
		}
		else if (arg0.getSource() == joinRoomButton) {
			initJoinRoomWindow();
		}
		else if (arg0.getSource() == findRoomButton) {
			initFindRoomWindow();
		}
		else if (arg0.getSource() == continueButton) {
			if (spareText2 == null) {
				String nickname = nickNameInputField.getText().trim();
				if (nickname.length() == 0) {
					viewError("Please enter a valid nick name");
				} else {
					nickName = nickname;
					System.out.println("NICK" + getNickName());
					((NetworkController) controller).match();
					controller.run();
					initLoadingWindow();
				}
			} else {
				String nickname = nickNameInputField.getText().trim();
				String roomname = roomNameInputField.getText().trim();
				if (nickname.length() == 0) {
					viewError("Please enter a valid nick name");
				}
				if (roomname.length() == 0) {
					viewError("Please enter a valid room name");
				}
				nickName = nickname;
				try {
					((NetworkController) controller).register(roomname);
					controller.run();
				} catch (FullRoomException e) {
					e.printStackTrace();
				}
				initLoadingWindow();
			}
		}
	}

	public void initJoinRoomWindow() {
		this.getContentPane().removeAll();
		
		this.setTitle("Line Of Action");
		this.setSize(325, 120);
		
		nickNameInputField = new JTextField("Enter your nickname");
		roomNameInputField = new JTextField("Enter the room name");
		
		nickNameInputField.setSize(200, 20);
		roomNameInputField.setSize(200, 20);
		
		nickNameInputField.setLocation(115, 10);
		roomNameInputField.setLocation(115, 35);
		
		this.setLayout(null);
		this.add(nickNameInputField);
		this.add(roomNameInputField);
		
		spareText1 = new JTextArea("Nickname ");
		spareText2 = new JTextArea("Room Name");
		
		spareText1.setSize(100, 15);
		spareText2.setSize(100, 20);
		
		spareText1.setLocation(10, 10);
		spareText2.setLocation(10, 30);
		
		spareText1.setEditable(false);
		spareText2.setEditable(false);
		
		this.add(spareText1);
		this.add(spareText2);
		
		continueButton = new JButton("Continue");
		continueButton.setSize(162, 20);
		continueButton.setLocation(81, 65);
		continueButton.addActionListener(this);
		
		this.add(continueButton);
		
		this.repaint();
	}
	
	public void initFindRoomWindow() {
		this.getContentPane().removeAll();
		
		this.setTitle("Line Of Action");
		this.setSize(325, 95);
		
		nickNameInputField = new JTextField("Enter your nickname");
		
		nickNameInputField.setSize(200, 20);
		
		nickNameInputField.setLocation(115, 10);
		
		this.setLayout(null);
		this.add(nickNameInputField);
		
		spareText1 = new JTextArea("Nickname ");
		
		spareText1.setSize(100, 15);
		
		spareText1.setLocation(10, 10);
		spareText1.setEditable(false);
		
		this.add(spareText1);
		
		continueButton = new JButton("Continue");
		continueButton.setSize(162, 20);
		continueButton.setLocation(81, 35);
		continueButton.addActionListener(this);
		
		this.add(continueButton);
		
		this.repaint();
	}
	
	public void initLoadingWindow() {
		this.getContentPane().removeAll();
		
		this.setTitle("Line Of Action");
		this.setSize(400, 100);
		this.setLayout(null);
		
		spareText1 = new JTextArea("Please wait till you get matched");
		spareText1.setSize(200, 25);
		spareText1.setLocation(50, 25);
		spareText1.setEditable(false);
		
		this.add(spareText1);
		
		this.repaint();
	}
	
	public void viewError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public String getNickName() {
		return nickName;
	}

	@Override
	public void message(String msg, String from) {
		chatOutputField.append(from + "> " + msg + "\r\n");
	}


	@Override
	public void notifyError(String msg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyOpponentInfo(Info info) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void notifyServerVersion(String version) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void move(Point p1, Point p2) {
		move(p1, p2, nickName);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			String msg = chatInputField.getText().trim();
			if (msg.length() > 0) {
				controller.message(msg);
				message(msg, nickName);
				chatInputField.setText("");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	@Override
	public void setBoard(StandardBoard board) {
		// TODO Auto-generated method stub
		
	}

	public void startNewGame() {
		initStartWindow();
	}
	
	@Override
	public void startGame(String player1, String player2) {
		
		initGameWindow();
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
	}	
}
