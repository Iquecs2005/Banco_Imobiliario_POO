package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controller.Controller;

class MainMenuFrame extends BaseFrame
{	
	private final JButton b1 = new JButton("New Game");
	private final JButton b2 = new JButton("Load Game");
	private final JButton b3 = new JButton("Debug Mode");
	
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
		PositionComponent(b3, frameWidth - 60, frameHeight - 60);
		panel.add(b3);
		
		b1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.OnNewGameButton();
			}
		});
		
		b2.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	Controller.instance.OnLoadGameButton();
		    }
		});
		
		System.out.println(Controller.instance);
		
		b3.addActionListener(new ActionListener() 
		{
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
				OnDebugButtonPress();
		    }

		});
	}
	
	private void OnDebugButtonPress() 
	{
		Controller.instance.ToggleDebugMode();
		
		Boolean active = Controller.instance.GetDebugModeActive();
		if (active)
			b3.setBackground(Color.blue);
		else
			b3.setBackground(Color.white);
	}
}
