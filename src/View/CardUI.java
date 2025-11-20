package View;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Controller.Controller;
import Model.Event;
import Model.Observer;

public class CardUI{
	
	public enum CardType {
		Company,
		Property,
		Luck
	}
	
	private String name;
	private BufferedImage image;
	private CardType type;
	private String path;
	
	private float x;
	private float y;
	
	public CardUI (String name, CardType type, float x, float y) {
		this.name = name;
		this.type = type;
		this.x = x;
		this.y = y;
		
		path = "/resources/cards";
		
		switch(type) 
		{
			case Company:
				path += "/companies/" + name + ".png";
				break;
			case Property:
				path += "/properties/" + name + ".png";
				break;
			case Luck:
				path += "/luck/" + name + ".png";
				break;
		}
		
		try 
		{
			System.out.println(path);
			this.image = ImageIO.read(getClass().getResource(path));
		}
		catch (IOException e) {
    		System.err.println("Image file not found");
            e.printStackTrace();
		}
	}
	
	public void PaintComponent(Graphics2D g2d, int boardX, int boardSize, BasePanel panel) {
		int width = (int)(boardSize);
		int height = (int)(boardSize);
		
		g2d.drawImage(image, (int)x, (int)y, width, height, panel);
	}
	
	public void SetPos(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

}
