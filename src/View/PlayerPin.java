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
	public float x;
	public float y;
	
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
		CalculatePos(Controller.instance.GetPlayerSpaceIndex(color));
		int pixelX = (int)(boardX + x * boardSize / 13 - width / 2);
		int pixelY = (int)(y * boardSize / 13 - height / 2);
		g2d.drawImage(pinImage, pixelX, pixelY, width, height, panel);
	}
	
	private void CalculatePos(int spaceIndex)
	{
		float transformedSpaceIndex = spaceIndex * 1.2f;
		//System.out.println(transformedSpaceIndex);
		
		if (spaceIndex < 11) 
		{
			System.out.println("a");
			x = 12.5f - transformedSpaceIndex;
			y = 12;
		}
		else if (spaceIndex < 21) 
		{
			x = 1;
			y = 12.5f - transformedSpaceIndex;
		}
		else if (spaceIndex < 31)
		{
			x = 1 + transformedSpaceIndex;
			y = 1;
		}
		else 
		{
			x = 1;
			y = 1 + transformedSpaceIndex;
		}
	} 
	
	public void update(Event event) 
	{
		
	}
}
