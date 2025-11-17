package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Controller.Controller;
import Model.Event;
import Model.Observer;

class DiceUI
{
	public BufferedImage diceImage;
	
	private final String color;
	private final int number;
	private final String dicePath = "/resources/dice/";
	
	public DiceUI(String color, int number) 
	{
		this.color = color;
		this.number = number;
		
		try 
		{			
			diceImage = ImageIO.read(getClass().getResource(dicePath + "die_face_" + number + ".png"));
		}
		catch (IOException e) 
    	{
    		System.err.println("Image file not found");
            e.printStackTrace();
        }
	}
	
	public void PaintComponent(Graphics2D g2d, int boardX, int boardSize, BasePanel panel) 
	{
		int width = (int)(0.1 * boardSize);
		int height = (int)(0.1 * boardSize);
		int pixelX = (int)(boardX + boardSize / 2 - width / 2 + 5);
		int pixelY = (int)(boardSize / 2 - height / 2 - 30);
	    // g2d.setColor(Color.RED);
	    // g2d.setStroke(new BasicStroke(10)); 
	    // g2d.drawRect(pixelX, pixelY, width, height);
		g2d.drawImage(diceImage, pixelX, pixelY, width, height, panel);
	}
}
