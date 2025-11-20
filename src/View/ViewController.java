package View;

import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

import Controller.Controller;

public class ViewController 
{
	BaseFrame currentFrame;
	
	int nPlayers;
	
	ViewController()
	{
		ActivateMainMenu();
		//ActivateBoard();
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new ViewController();
	}

	void ActivateMainMenu() 
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
				ActivatePlayerSelect();
			}
		});
		
		mainMenuFrame.b2.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e)
		    {
		        String path = showFileChooser();
		        if (path != null) {
		            Controller.instance.LoadGame(path);
		            ActivateBoard();
		        }
		    }
		});
	}


	private String showFileChooser() {
	    JFileChooser chooser = new JFileChooser();
	    chooser.setDialogTitle("Choose Save File");

	    int result = chooser.showOpenDialog(null);  
	    if (result == JFileChooser.APPROVE_OPTION) {
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
		
		BoardFrame boardFrame = new BoardFrame(500, 500, nPlayers);
		currentFrame = boardFrame;
		boardFrame.setVisible(true);
	}
	
	public void SetNPlayers(int nPlayers) 
	{
		this.nPlayers = nPlayers;
	}
}
