package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Model.Model;

class DiceUI
{
	public BufferedImage diceImage;
	
	private final String dicePath = "/resources/dice/";
	
	public DiceUI(int number) 
	{
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
		
		String colorName = Model.instance.getCurrentPlayerColorName();
		Color diceColor = StringToColor(colorName);
		
	    g2d.setColor(diceColor);
	    g2d.setStroke(new BasicStroke(10)); 
	    g2d.drawRect(pixelX, pixelY, width, height);
		g2d.drawImage(diceImage, pixelX, pixelY, width, height, panel);
	}
	
	private Color StringToColor(String playerColor)
	{
		switch(playerColor)
		{
			case "Red":
				return Color.red;
			case "Blue":
				return Color.blue;
			case "Yellow":
				return Color.yellow;
			case "Orange":
				return Color.orange;
			case "Purple":
				return Color.magenta;
			case "Grey":
				return Color.gray;
		}
		return null;
	}
}
