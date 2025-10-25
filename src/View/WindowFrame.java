package View;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class WindowFrame extends BaseFrame
{	
	public final JButton b1 = new JButton("New Game");
	public final JButton b2 = new JButton("Load Game");
	
	private JPanel panel;
	
	public WindowFrame(int startWidth, int startHeight) 
	{
		super("Banco Imobiliario", startWidth, startHeight);
		
		panel = new InitialPanel(frameWidth, frameHeight);
		getContentPane().add(panel);
		
		PositionButton(b1, frameWidth / 2 - 5, frameHeight / 2 - 25);
		panel.add(b1);
		PositionButton(b2, frameWidth / 2 - 5, frameHeight / 2 + 25);
		panel.add(b2);
	}
}
