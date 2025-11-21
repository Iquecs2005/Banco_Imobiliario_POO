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
	public final JButton saveGameButton = new JButton("Save Game");
	public final JButton rollDiceButton = new JButton("Roll Dice");
	public final JButton endTurnButton = new JButton("End Turn");
	public final JButton endGameButton = new JButton("End Game");
	
    private BufferedImage boardImg;
    private CardContainer cardContainer;

    private List<PlayerPin> activePlayerList = new ArrayList<PlayerPin>();
	private List<MoneyDisplay> moneyDisplays = new LinkedList<MoneyDisplay>();
    
    private int boardSize;

    public BoardPanel(int width, int height) 
    {
        super(width, height);
        
        cardContainer = new CardContainer();
        
        LoadImages();
        
        add(rollDiceButton);
        add(endTurnButton);
        add(saveGameButton);
        add(endGameButton);
        
        rollDiceButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.MovePlayer();
				ToggleEndTurnButton(true); 
				ToggleRollDiceButton(false); 
				repaint();
			}
		});
        endTurnButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.EndTurn();
				ToggleEndTurnButton(false); 
				ToggleRollDiceButton(true); 
				cardContainer.ClearCards();
				repaint();
			}
		});
        
        saveGameButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String path = showSaveFileChooser();
				if (path != null)
				{
					Controller.instance.SaveGame(path);
				}
			}
		});
        
        endGameButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.OnEndGame();
			}
		});
        
        
        ToggleEndTurnButton(false);
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
    
    public void AddPlayer(String color) 
    {
    	activePlayerList.add(new PlayerPin(color, this));
    	moneyDisplays.add(new MoneyDisplay(this, color));
    }
    
    public void AddPlayer(int nPlayers) 
    {
    	for (int i = 0; i < nPlayers; i++) 
    	{
    		System.out.println("Added player.");
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
        
        DiceContainer.instance.PaintComponent(g2d, boardX, boardSize, this);
        
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
        
        BaseFrame.PositionComponent(rollDiceButton, this.getWidth()/2, this.getHeight()/2 + 30);
        BaseFrame.PositionComponent(endTurnButton, this.getWidth()/2, this.getHeight()/2 + 30);
        BaseFrame.PositionComponent(saveGameButton, this.getWidth()/2, this.getHeight()/2 + 90);
        BaseFrame.PositionComponent(endGameButton, this.getWidth()/2, this.getHeight()/2 + 60);
    }
    
    public void ToggleRollDiceButton(boolean state) 
    {
    	rollDiceButton.setVisible(state);
    }
    
    public void ToggleEndTurnButton(boolean state) 
    {
    	endTurnButton.setVisible(state);
    }
    
    private String showSaveFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Game");

        int result = chooser.showSaveDialog(null);   // DIFFERENT FROM LOAD
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

}
