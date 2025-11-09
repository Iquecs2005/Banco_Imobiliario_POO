package View;

import javax.swing.*;

import Controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;

public class BoardPanel extends BasePanel 
{
	public final JButton b1 = new JButton("Roll Dice");
	
    private BufferedImage boardImg;
    private List<PlayerPin> activePlayerList = new ArrayList<PlayerPin>();
    private List<DiceUI> diceList = new ArrayList<DiceUI>();
	private List<MoneyDisplay> moneyDisplays = new LinkedList<MoneyDisplay>(); 
    private Vector<Integer> diceResults;
    
    private int boardSize;

    public BoardPanel(int width, int height) 
    {
        super(width, height);
        
        LoadImages();
        LoadDice();
        add(b1);
        b1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.MovePlayer();
				repaint();
			}
		});
        BaseFrame.PositionComponent(b1, width/2, height/2);
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
    
    public void SetDiceResults(Vector<Integer> diceResults)
    {
    	this.diceResults = diceResults;
    }
    
    public void AddPlayer(String color) 
    {
    	activePlayerList.add(new PlayerPin(color, this));
    	moneyDisplays.add(new MoneyDisplay(this, color));
    }
    
    public void AddPlayer(int nPlayers) 
    {
    	for (int i = 0; i < nPlayers; i++) 
    	{    		
    		activePlayerList.add(new PlayerPin(PlayerPin.possibleColors[i], this));
    		moneyDisplays.add(new MoneyDisplay(this, PlayerPin.possibleColors[i]));
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
        
        System.out.println(boardX);
        for (PlayerPin playerPin : activePlayerList) 
        {
        	playerPin.PaintComponent(g2d, boardX, boardSize, this);
        }
        
        int offset = 50;
        int initialOffset = -offset / (moneyDisplays.size() - 1);
        for (MoneyDisplay moneyDisplay : moneyDisplays) 
        {
        	moneyDisplay.DrawDisplay(this.getWidth() - 50, boardSize / 2 + initialOffset);
        	initialOffset += offset;
        }
        
        BaseFrame.PositionComponent(b1, this.getWidth()/2, this.getHeight()/2);

        if (diceResults != null)
        {
            diceList.get(diceResults.get(0) - 1).PaintComponent(g2d, boardX + 30, boardSize, this);
            diceList.get(diceResults.get(1) - 1).PaintComponent(g2d, boardX - 30, boardSize, this);
        }
    }
    
    
    
}
