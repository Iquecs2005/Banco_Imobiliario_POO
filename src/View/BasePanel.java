package View;

import java.awt.*;

import javax.swing.*;

abstract class BasePanel extends JPanel
{
	Color backgroundColor;
	
	protected int width;
	protected int height;
	
	public BasePanel(int width, int height) 
	{
		super();
		
		this.width = width;
		this.height = height;
		
		setLayout(null);
		setBounds(0, 0, width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    
	    width = getWidth();
	    height = getHeight();
	}
	
	public void SetBackgroundColor(int r, int g, int b) 
	{
		float[] backgroundHSV = Color.RGBtoHSB(r, g, b, null);
		backgroundColor = Color.getHSBColor(backgroundHSV[0], backgroundHSV[1], backgroundHSV[2]);
		setBackground(backgroundColor);
	}
}
