package View;

import javax.swing.*;

class PlayerSelectFrame extends BaseFrame
{
	private JPanel panel;
	
	public PlayerSelectFrame(int startWidth, int startHeight) 
	{
		super("Player Select", startWidth, startHeight);
		
		panel = new MainMenuPanel(frameWidth, frameHeight);
		getContentPane().add(panel);
	}
}
