package View;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

class PlayerPin 
{
	public static final String[] possibleColors = 
		{"Red", "Blue", "Yellow", "Orange", "Purple", "Grey"};
	
	public BufferedImage pinImage;
	public float x;
	public int y;
	
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
		
		x = 2.5f;
		y = 0;
	}
	
	public void PaintComponent(Graphics2D g2d, int boardX, int boardSize, BasePanel panel) 
	{
		int width = (int)(0.05 * boardSize);
		int height = (int)(0.075 * boardSize);
		int pixelX = (int)(boardX + x * boardSize / 13 - width / 2);
		g2d.drawImage(pinImage, pixelX, y, width, height, panel);
	}
}
