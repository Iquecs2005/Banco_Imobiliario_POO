package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class BoardFrame extends BaseFrame {
	private BufferedImage boardImg;
	private JPanel panel;
	
	public BoardFrame(int width,int height) {
		super("Tabuleiro", width, height);
		
		panel = new BoardPanel(frameWidth,frameHeight);
		getContentPane().add(panel);
		
	}
	
	
}
