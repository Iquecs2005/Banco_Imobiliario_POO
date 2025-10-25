package View;

import java.awt.*;

import javax.swing.*;

class InitialPanel extends JPanel
{
	Color backgroundColor;
	
	int width;
	int height;
	
	public InitialPanel(int width, int height) 
	{
		super();
		
		this.width = width;
		this.height = height;
		
		float[] backgroundHSV = Color.RGBtoHSB(10, 100, 140, null);
		backgroundColor = Color.getHSBColor(backgroundHSV[0], backgroundHSV[1], backgroundHSV[2]);
		setBackground(backgroundColor);
		
		setLayout(null);
		setBounds(0, 0, width, height);
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		g.setFont(new Font("Arial", Font.BOLD, 24)); // Set font
        g.setColor(Color.white);
		g.drawString("Banco Imobiliario", width / 2 - 105, 100);
	}
}
