package View;

import java.awt.Color;

import javax.swing.*;

class InitialPanel extends JPanel
{
	public InitialPanel() 
	{
		float[] backgroundHSV = Color.RGBtoHSB(10, 100, 140, null);
		
		setBackground(Color.getHSBColor(backgroundHSV[0], backgroundHSV[1], backgroundHSV[2]));
	}
}
