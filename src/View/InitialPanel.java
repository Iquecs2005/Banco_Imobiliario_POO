package View;

import java.awt.*;

import javax.swing.*;

class InitialPanel extends BasePanel
{
	public InitialPanel(int width, int height) 
	{
		super(width, height);
		
		SetBackgroundColor(10, 100, 140);
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		g.setFont(new Font("Arial", Font.BOLD, 24)); // Set font
        g.setColor(Color.white);
		g.drawString("Banco Imobiliario", width / 2 - 105, 100);
	}
}
