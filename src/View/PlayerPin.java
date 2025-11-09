package View;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Controller.Controller;
import Model.Event;
import Model.Observer;

class PlayerPin implements Observer 
{
	public static final String[] possibleColors = 
		{"Red", "Blue", "Yellow", "Orange", "Purple", "Grey"};
	
	public BufferedImage pinImage;
	private float x;
	private float y;
	
	private final String color;
	private final String pinsPath = "/resources/pins/";
	private final BasePanel panel;
	
	public PlayerPin(String color, BasePanel panel) 
	{
		this.color = color;
		this.panel = panel;
		
		try 
		{			
			pinImage = ImageIO.read(panel.getClass().getResource(pinsPath + color + ".png"));
		}
		catch (IOException e) 
    	{
    		System.err.println("Image file not found");
            e.printStackTrace();
        }
		
		Controller.instance.SubscribeToPlayerPos(this);
		Controller.instance.GetPlayerSpaceIndex(color);
		
		x = 0;
		y = 0;
	}
	
	public void PaintComponent(Graphics2D g2d, int boardX, int boardSize, BasePanel panel) 
	{
		int width = (int)(0.05 * boardSize);
		int height = (int)(0.075 * boardSize);
		int pixelX = (int)(boardX + x * boardSize / 13 - width / 2);
		int pixelY = (int)(y * boardSize / 13 - height / 2);
		
		g2d.drawImage(pinImage, pixelX, pixelY, width, height, panel);
	}
	
	private void CalculatePos(int spaceIndex)
	{
		x = 0;
		y = 0;
		
		if (spaceIndex == 0) 
		{
			x = 12f;
			y = 12f;
		}
		else if (spaceIndex < 10) 
		{
			x = 11.5f - spaceIndex;
			y = 12;
		}
		else if (spaceIndex == 10) 
		{
			x = 1f;
			y = 12f;
		}
		else if (spaceIndex < 20) 
		{
			x = 1;
			y = 10.25f - (spaceIndex - 11);
		}
		else if (spaceIndex == 20) 
		{
			x = 1f;
			y = 1f;
		}
		else if (spaceIndex < 30)
		{
			x = 2.5f + (spaceIndex - 21);
			y = 1;
		}
		else if (spaceIndex == 30) 
		{
			x = 12f;
			y = 1f;
		}
		else 
		{
			x = 12f;
			y = 2 + (spaceIndex - 31);
		}
	} 
	
	public void update(Event event) 
	{
		CalculatePos(Controller.instance.GetPlayerSpaceIndex(color));
	}
}
