package View;

import java.awt.*;

import javax.swing.*;

abstract class BaseFrame extends JFrame
{
	protected int frameWidth;
	protected int frameHeight;
	
	public BaseFrame(String windowName, int startWidth, int startHeight) 
	{
		super();
		
		setTitle("Banco Imobiliario");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		
		frameWidth = startWidth;
		frameHeight = startHeight;
		
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int x = screenWidth / 2 - frameWidth / 2;
		int y = screenHeight / 2 - frameHeight / 2;
		
		setBounds(x, y, frameWidth, frameHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	static public void PositionComponent(JComponent component, int x, int y) 
	{
		Dimension buttonSize = component.getPreferredSize();
		
		int buttonX;
		int buttonY;
		int sizeX;
		int sizeY;
		
		sizeX = buttonSize.width;
		sizeY = buttonSize.height;
		buttonX = sizeX / 2;
		buttonY = sizeY / 2;
		
		component.setBounds(new Rectangle(x - buttonX, y - buttonY, sizeX, sizeY));
	}
	
	static public void PositionComponent(JButton button, int x, int y) 
	{
		Dimension buttonSize = button.getPreferredSize();
		
		int buttonX;
		int buttonY;
		int sizeX;
		int sizeY;
		
		sizeX = buttonSize.width;
		sizeY = buttonSize.height;
		buttonX = sizeX / 2;
		buttonY = sizeY / 2;
		
		button.setFocusable(false);
		button.setBounds(new Rectangle(x - buttonX, y - buttonY, sizeX, sizeY));
	}
}
