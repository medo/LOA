package eg.edu.guc.loa.ui.impl;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartWindowPanel extends JLayeredPane {
	private JButton singlePlayer, multiPlayer, network;
	private JPanel background;
	private static final int BUTTONWIDTH = 200;
	private static final int BUTTONHEIGHT = 50;
	private Image startBackground;
	
	public StartWindowPanel() {
		singlePlayer = new JButton("Single Player");
		multiPlayer = new JButton("Multiplayer");
		network = new JButton("Network");
		
		try {
			startBackground = ImageIO.read(new File("images/startimage.jpg"));
		} catch (IOException e) {
            e.printStackTrace();
        }
		
		singlePlayer.setSize(BUTTONWIDTH, BUTTONHEIGHT);
		multiPlayer.setSize(BUTTONWIDTH, BUTTONHEIGHT);
		network.setSize(BUTTONWIDTH, BUTTONHEIGHT);
		
		singlePlayer.setLocation(0, 0);
		multiPlayer.setLocation(0, BUTTONHEIGHT);
		network.setLocation(0, 2 * BUTTONHEIGHT);
		
		singlePlayer.setBackground(Color.CYAN);
		multiPlayer.setBackground(Color.CYAN);
		network.setBackground(Color.CYAN);
		
		this.setSize(500, 500);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.add(singlePlayer);
		this.add(multiPlayer);
		this.add(network);
		
		JLabel gg = new JLabel();
		gg.setSize(500, 500);
		gg.setIcon(new ImageIcon(startBackground));
		
		this.add(gg, new Integer(0));
		
	}
	
	JButton getSinglePlayerButton() {
		return singlePlayer;
	}
	
	JButton getMultiPlayerButton() {
		return multiPlayer;
	}
	
	JButton getNetworkButton() {
		return network;
	}
	
	JPanel getBackgroundPanel() {
		return background;
	}
	
	public static void main(String[] args) {
		JFrame x = new JFrame();
		x.setSize(500, 500);
		x.setLayout(null);
		x.add(new StartWindowPanel());
		x.setVisible(true);
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
