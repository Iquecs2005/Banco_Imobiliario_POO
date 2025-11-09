package View;

import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

public class BoardPanel extends BasePanel {

    private BufferedImage boardImg;
    private List<PlayerPin> activePlayerList = new ArrayList<PlayerPin>();
    private List<DiceUI> diceList = new ArrayList<DiceUI>();
    
    private int boardSize;

    public BoardPanel(int width, int height) 
    {
        super(width, height);
        
        LoadImages();
        LoadDice();
    }
    
    public void LoadImages()
    {
    	try 
    	{
            boardImg = ImageIO.read(getClass().getResource("/resources/tabuleiro.png"));
        } 
    	catch (IOException e) 
    	{
    		System.err.println("Image file not found");
            e.printStackTrace();
        }
    }
    
    public void LoadDice()
    {
    	for (int i = 0; i < 6; i++) 
    	{    		
    		diceList.add(new DiceUI("Red", i + 1, this));
    	}
    }
    
    public void AddPlayer(String color) 
    {
    	activePlayerList.add(new PlayerPin(color, this));
    }
    
    public void AddPlayer(int nPlayers) 
    {
    	for (int i = 0; i < nPlayers; i++) 
    	{    		
    		activePlayerList.add(new PlayerPin(PlayerPin.possibleColors[i], this));
    	}
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int boardX = 0;
        if (boardImg != null) 
        {
        	if (getWidth() > getHeight())
        		boardSize = getHeight();
        	else
        		boardSize = getWidth();
        	
        	boardX = getWidth() / 2 - boardSize / 2;
            g2d.drawImage(boardImg, boardX, 0, boardSize, boardSize, this);
        }
        
        for (PlayerPin playerPin : activePlayerList) 
        {
        	playerPin.PaintComponent(g2d, boardX, boardSize, this);
        }
        
        // for (DiceUI dice : diceList)
        // {
        // 	 dice.PaintComponent(g2d, boardX + 30, boardSize, this);
        //	 dice.PaintComponent(g2d, boardX - 30, boardSize, this);
        // }
    }
    
    
    
}
