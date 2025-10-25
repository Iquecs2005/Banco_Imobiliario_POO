package View;

import javax.swing.*;

class PlayerSelectFrame extends BaseFrame
{
	public final JButton b1 = new JButton("New Player");
	public final JButton b2 = new JButton("Start Game");
	
	int nPlayers = 0;
	
	private JPanel panel;
	
	public PlayerSelectFrame(int startWidth, int startHeight) 
	{
		super("Player Select", startWidth, startHeight);
		
		panel = new PlayerSelectPanel(frameWidth, frameHeight);
		getContentPane().add(panel);
		
		PositionButton(b1, 100, 100);
		panel.add(b1);
	}
}
