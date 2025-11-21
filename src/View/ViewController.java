package View;

import java.awt.event.*;

import javax.swing.*;

import Controller.Controller;

public class ViewController 
{
	BaseFrame currentFrame;

	public void ActivateMainMenu() 
	{
		if (currentFrame != null)
			currentFrame.setVisible(false);
		
		MainMenuFrame mainMenuFrame = new MainMenuFrame(500, 500);
		currentFrame = mainMenuFrame;
		mainMenuFrame.setVisible(true);
		
		mainMenuFrame.b1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.OnNewGameButton();
			}
		});
		
		mainMenuFrame.b2.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	Controller.instance.OnLoadGameButton();
		    }
		});
	}


	public String showFileChooser() 
	{
	    JFileChooser chooser = new JFileChooser();
	    chooser.setDialogTitle("Choose Save File");

	    int result = chooser.showOpenDialog(null);  
	    if (result == JFileChooser.APPROVE_OPTION) 
	    {
	        return chooser.getSelectedFile().getAbsolutePath();
	    }
	    
	    return null;
	}

	
	public void ActivatePlayerSelect() 
	{
		if (currentFrame != null)
			currentFrame.setVisible(false);
		
		PlayerSelectFrame playerSelectFrame = new PlayerSelectFrame(500, 500, this);
		currentFrame = playerSelectFrame;
		playerSelectFrame.setVisible(true);
	}
	
	public void ActivateBoard() 
	{
		if (currentFrame != null)
			currentFrame.setVisible(false);
		
		BoardFrame boardFrame = new BoardFrame(500, 500, Controller.instance.GetPlayerNumber());
		currentFrame = boardFrame;
		boardFrame.setVisible(true);
	}
	
	public void DestroyCurrentBoard()
	{
		if (currentFrame != null)
			currentFrame.dispose();
			currentFrame = null;
	}
}
