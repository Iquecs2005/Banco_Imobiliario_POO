package View;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class MainMenuFrame extends BaseFrame
{	
	public final JButton b1 = new JButton("New Game");
	public final JButton b2 = new JButton("Load Game");
	
	private JPanel panel;
	
	public MainMenuFrame(int startWidth, int startHeight) 
	{
		super("Banco Imobiliario", startWidth, startHeight);
		
		panel = new MainMenuPanel(frameWidth, frameHeight);
		getContentPane().add(panel);
		
		PositionComponent(b1, frameWidth / 2 - 5, frameHeight / 2 - 25);
		panel.add(b1);
		PositionComponent(b2, frameWidth / 2 - 5, frameHeight / 2 + 25);
		panel.add(b2);
	}
}
