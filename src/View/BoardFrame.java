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
	private BoardPanel panel;
	
	public BoardFrame(int width,int height, int nPlayers) {
		super("Tabuleiro", width, height);
		
		panel = new BoardPanel(frameWidth,frameHeight);
		getContentPane().add(panel);
		panel.AddPlayer(nPlayers);
	}
}
