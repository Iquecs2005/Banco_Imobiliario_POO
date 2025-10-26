package View;

import java.awt.event.*;

public class ExitButtonListener implements ActionListener
{
	final PlayerSelectFrame parent;
	final PlayerInfoForm id;
	
	public ExitButtonListener(PlayerSelectFrame parent, PlayerInfoForm id)
	{
		this.parent = parent;
		this.id = id; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		parent.RemovePlayer(id);
	}
}
