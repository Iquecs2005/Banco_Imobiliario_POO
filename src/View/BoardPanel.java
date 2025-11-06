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
    private Map<String, BufferedImage> playerPinsImg = new HashMap<String, BufferedImage>();
    private List<String> activePlayerList = new ArrayList<String>();

    public BoardPanel(int width, int height) 
    {
        super(width, height);
        
        LoadImages();
    }
    
    public void LoadImages()
    {
    	try 
    	{
            boardImg = ImageIO.read(getClass().getResource("/resources/tabuleiro.png"));
            
            BufferedImage currentPlayerPin;
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin0.png"));
            playerPinsImg.put("Red", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin1.png"));
            playerPinsImg.put("Blue", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin2.png"));
            playerPinsImg.put("Orange", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin3.png"));
            playerPinsImg.put("Yellow", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin4.png"));
            playerPinsImg.put("Purple", currentPlayerPin);
            
            currentPlayerPin = ImageIO.read(getClass().getResource("/resources/pin5.png"));
            playerPinsImg.put("Grey", currentPlayerPin);
        } 
    	catch (IOException e) 
    	{
    		System.err.println("Image file not found");
            e.printStackTrace();
        }
    }
    
    public void AddPlayer(String color) 
    {
    	activePlayerList.add(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (boardImg != null) 
        {
            g2d.drawImage(boardImg, 0, 0, getWidth(), getHeight(), this);
        }
        int i = 0;
        for (String playerColor : activePlayerList) 
        {
        	g2d.drawImage(playerPinsImg.get(playerColor), i * 50, 0, 25, 40, this);
        	i++;
        }
    }
}
