package View;

import Model.Observer;

class WinFrame extends BaseFrame
{
	WinPanel panel;
	
	public WinFrame(int startWidth, int startHeight) 
	{
		super("Win Screen", startWidth, startHeight);
		
		panel = new WinPanel(startWidth, startHeight);
		getContentPane().add(panel);
	}
}
