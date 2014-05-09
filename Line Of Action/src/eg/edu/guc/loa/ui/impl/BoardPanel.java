package eg.edu.guc.loa.ui.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eg.edu.guc.loa.controllers.Controller;
import eg.edu.guc.loa.controllers.UserInterface;
import eg.edu.guc.loa.engine.Point;
import eg.edu.guc.loa.engine.StandardBoardImpl;
import eg.edu.guc.loa.engine.interfaces.Cell;

@SuppressWarnings("serial")
public class BoardPanel extends JLayeredPane implements MouseMotionListener, MouseListener {
	
	private Image backgroundImage, blackPieceImage, whitePieceImage;
	
	private JLabel[][] checkers = new JLabel[8][8];
	private JPanel[][] greenPanel = new JPanel[8][8]; 
	private JPanel[][] redPanel = new JPanel[8][8];
	private JLabel chessboardLabel = new JLabel();
	private static final int CHECKERLAYERINDEX = 3;
	private static final int CHECKERSIZE = 85;
	private Controller controller;
	private UserInterface ourGUI;
	
	private StandardBoardImpl board = new StandardBoardImpl();
	
	private Point startOfPress;
	private int colorToPlay;
	
	private int dragLevel = 0, draggedX = -1, draggedY = -1;
	
	public BoardPanel(UserInterface GUI) {
		
		ourGUI = GUI;
		
		colorToPlay = -1;
		
		this.setSize(680, 680);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setLayout(null);
		
		try {
			backgroundImage = ImageIO.read(new File("images/chessboard.jpg"));
			blackPieceImage = ImageIO.read(new File("images/blackpiece.png"));
			whitePieceImage = ImageIO.read(new File("images/whitePiece.png"));
		} catch (IOException e) {
            e.printStackTrace();
        }
		
		chessboardLabel.setSize(680, 680);
		chessboardLabel.setIcon(new ImageIcon(backgroundImage));
		this.add(chessboardLabel, new Integer(0));
		
		for (int q = 1; q < 7; q++) {
			viewChecker(0, q, whitePieceImage);
			viewChecker(7, q, whitePieceImage);
			viewChecker(q, 0, blackPieceImage);
			viewChecker(q, 7, blackPieceImage);
		}
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
		this.controller.run();
	}
	
	public void viewChecker(int x, int y, Image image) {
		checkers[x][y] = new JLabel();
		checkers[x][y].setSize(CHECKERSIZE, CHECKERSIZE);
		checkers[x][y].setLocation(CHECKERSIZE * y, CHECKERSIZE * x);
		checkers[x][y].setIcon(new ImageIcon(image));
		this.add(checkers[x][y], new Integer(CHECKERLAYERINDEX));
		paintImmediately(CHECKERSIZE * y, CHECKERSIZE * x
				, CHECKERSIZE * (y + 1), CHECKERSIZE * (x + 1));
	}
	
	public void hideChecker(int x, int y) {
		if (checkers[x][y] != null) {
			checkers[x][y].setVisible(false);
		}
		checkers[x][y] = null;
		paintImmediately(CHECKERSIZE * y, CHECKERSIZE * x
				, CHECKERSIZE * (y + 1), CHECKERSIZE * (x + 1));
	}
	
	public void viewGreenBackground(int x, int y) {
		greenPanel[x][y] = new JPanel();
		greenPanel[x][y].setLayout(null);
		greenPanel[x][y].setSize(CHECKERSIZE, CHECKERSIZE);
		greenPanel[x][y].setLocation(CHECKERSIZE * y, CHECKERSIZE * x);
		greenPanel[x][y].setBackground(new Color(227, 209, 132));//(255, 227, 100, 200));
		this.add(greenPanel[x][y], new Integer(1));
		paintImmediately(CHECKERSIZE * y, CHECKERSIZE * x
				, CHECKERSIZE * (y + 1), CHECKERSIZE * (x + 1));
	}
	
	public void removeGreenBackground(int x, int y) {
		if (greenPanel[x][y] != null) {
			greenPanel[x][y].setVisible(false);
		}
		greenPanel[x][y] = null;
		paintImmediately(CHECKERSIZE * y, CHECKERSIZE * x
				, CHECKERSIZE * (y + 1), CHECKERSIZE * (x + 1));
	}
	
	public void viewRedBackground(int x, int y) {
		redPanel[x][y] = new JPanel();
		redPanel[x][y].setLayout(null);
		redPanel[x][y].setSize(CHECKERSIZE, CHECKERSIZE);
		redPanel[x][y].setLocation(CHECKERSIZE * y, CHECKERSIZE * x);
		redPanel[x][y].setBackground(Color.RED);
		this.add(redPanel[x][y], new Integer(2));
		paintImmediately(CHECKERSIZE * y, CHECKERSIZE * x
				, CHECKERSIZE * (y + 1), CHECKERSIZE * (x + 1));
	}

	public void removeRedBackground(int x, int y) {
		if (redPanel[x][y] != null) {
			redPanel[x][y].setVisible(false);
		}
		redPanel[x][y] = null;
		paintImmediately(CHECKERSIZE * y, CHECKERSIZE * x
				, CHECKERSIZE * (y + 1), CHECKERSIZE * (x + 1));
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
	public Point getMouseLocation(MouseEvent arg0) {
		int x = arg0.getY() / CHECKERSIZE;
		int y = arg0.getX() / CHECKERSIZE;
		return new Point(y, x);
	}

	public void viewPossibleMoves() {
		List<Cell> moves = board.getPossibleMoves(startOfPress);
		
		for (Cell cell : moves) {
			viewGreenBackground(cell.getPosition().getRow(), cell.getPosition().getCol());
		}
	}
	
	public void hidePossibleMoves() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				removeGreenBackground(x, y);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		Point mousePosition = getMouseLocation(arg0);
		if (board.isValidPoint(mousePosition) 
				&& board.getPieceColor(mousePosition) == getColorToPlay()) {
			startOfPress = mousePosition;
			viewPossibleMoves();
		}
	}

	public boolean move(Point start, Point end) {
		boolean canMove = board.move(start, end);
		if (canMove) {
			setTurn(-1);
			if (board.isGameOver()) {
				hidePossibleMoves();
				int winner = board.getWinner().getColor();
				String winnerName = "White";
				if (winner == 2) {
					winnerName = "Black";
				}
				JOptionPane.showMessageDialog(null, winnerName 
						+ " Player won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return canMove;
	}
	
	public void moveNoTurn(Point start, Point end) {
		setTurn(board.getPieceColor(start));
		boolean canMove = board.move(start, end);
		if (canMove) {
			int sx = start.getRow();
			int sy = start.getCol();
			int ex = end.getRow();
			int ey = end.getCol();
			
			hideChecker(ex, ey);
			if (board.getPieceColor(end) == 1) {
				viewChecker(ex, ey, whitePieceImage);
			} else {
				viewChecker(ex, ey, blackPieceImage);
			}
			
			hideChecker(sx, sy);
			
			if (board.isGameOver()) {
				int winner = board.getWinner().getColor();
				String winnerName = "White";
				if (winner == 2) {
					winnerName = "Black";
				}
				JOptionPane.showMessageDialog(null, winnerName 
						+ " won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				ourGUI.startNewGame();
			}
		}
		setTurn(-1);
	}
	
	public int getColorToPlay() {
		return colorToPlay;
	}
	public void setTurn(int color) {
		colorToPlay = color;
		
		board.setTurn(color);
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point mousePosition = getMouseLocation(arg0);
		if (startOfPress != null && board.isValidPoint(mousePosition)) {
			int oldColor = board.getPieceColor(startOfPress);
			boolean canMove = move(startOfPress, mousePosition);
			hidePossibleMoves();
			int sx = startOfPress.getRow();
			int sy = startOfPress.getCol();
			int ex = mousePosition.getRow();
			int ey = mousePosition.getCol();
			if (canMove) {
				hideChecker(ex, ey);
				hideChecker(sx, sy);
				if (oldColor == 1) {
					viewChecker(ex, ey, whitePieceImage);
				} else {
					viewChecker(ex, ey, blackPieceImage);
				}
				hidePossibleMoves();
				controller.move(startOfPress, mousePosition);
			} else {
				hideChecker(sx, sy);
				if (oldColor == 1) {
					viewChecker(sx, sy, whitePieceImage);
				} else {
					viewChecker(sx, sy, blackPieceImage);
				}
				hidePossibleMoves();
			}
			dragLevel = 0;
			startOfPress = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point mousePosition = getMouseLocation(arg0);
		
		if (dragLevel == 0 && startOfPress != null) {
			draggedX = mousePosition.getRow();
			draggedY = mousePosition.getCol();
			if (checkers[draggedX][draggedY] == null) {
				draggedX = -1;
				draggedY = -1;
				return;
			}
			dragLevel++;
			this.setLayer((Component) checkers[draggedX][draggedY], JLayeredPane.DRAG_LAYER);
		}
		else if (draggedX != -1 && checkers[draggedX][draggedY] != null) {
			checkers[draggedX][draggedY].setLocation(arg0.getX() - CHECKERSIZE / 2
					, arg0.getY() - CHECKERSIZE / 2);
			paintImmediately(arg0.getY() - CHECKERSIZE / 2, arg0.getX() - CHECKERSIZE / 2
					, arg0.getY() + CHECKERSIZE / 2, arg0.getX() + CHECKERSIZE / 2);
			dragLevel++;
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	
	public static void main(String[] args) {
		JFrame x = new JFrame();
		x.setSize(800, 800);
		x.setLayout(null);
		x.add(new BoardPanel(new GUI()));
		x.setVisible(true);
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
