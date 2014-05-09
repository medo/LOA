package eg.edu.guc.loa.ui.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import eg.edu.guc.loa.engine.Point;

@SuppressWarnings("serial")
public class CheckerPanel extends JPanel {

	private BufferedImage background;
	private static final int SIZE = 85;
	private Color color;
	private int type;
	private Point position;
	
	public CheckerPanel() {
		this.setLayout(null);
		this.setSize(SIZE, SIZE);
		/// TODO set background to a suitable transparent photo
		try {
			background = ImageIO.read(new File("images/transparent.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public CheckerPanel(Color color) {
		this();
		this.color = color;
		if (color == Color.WHITE) {
			type = 1;
		} else {
			type = 2;
		}
	}
	
	public CheckerPanel(Point position) {
		this();
		this.position = position;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
		if (color == Color.WHITE) {
			type = 1;
		} else {
			type = 2;
		}
		repaint();
	}
	
	public void removeChecker() {
		this.color = null;
		type = 0;
		repaint();
	}
	
	public int getType() {
		return type;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Point getPosition() {
		return position;
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
	/*	g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		
		if (color != null) {
			Ellipse2D circle = new Ellipse2D.Double(0, 0, SIZE, SIZE);
			g2.setColor(getColor());
			g2.fill(circle);
		}*/
	}
	
	public static void main(String[] args) {
		JFrame x = new JFrame();
		x.setSize(100, 100);
		x.setLayout(null);
		x.add(new CheckerPanel(Color.WHITE));
		x.setVisible(true);
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
