package View;

import javax.swing.*;

import Controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class BoardFrame extends BaseFrame 
{
	public final JButton b1 = new JButton("RollDice");
	private BufferedImage boardImg;
	private BoardPanel panel;
	
	public BoardFrame(int width,int height, int nPlayers) {
		super("Tabuleiro", width, height);
		
		panel = new BoardPanel(frameWidth,frameHeight);
		getContentPane().add(panel);
		panel.AddPlayer(nPlayers);
		panel.add(b1);
		PositionComponent(b1, width/2, height/2);
		b1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.MovePlayer();
				panel.repaint();
			}
		});
	}
	
	
}
