package View;

import java.awt.event.*;

import javax.swing.*;

public class ViewController 
{
	BaseFrame currentFrame;
	
	ViewController()
	{
		//ActivateMainMenu();
		ActivateBoard();
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
	}
	
	public void ActivatePlayerSelect() 
	{
		if (currentFrame != null)
			currentFrame.setVisible(false);
		
		PlayerSelectFrame playerSelectFrame = new PlayerSelectFrame(500, 500);
		currentFrame = playerSelectFrame;
		playerSelectFrame.setVisible(true);
	}
	
	public void ActivateBoard() 
	{
		if (currentFrame != null)
			currentFrame.setVisible(false);
		
		BoardFrame boardFrame = new BoardFrame(500, 500);
		currentFrame = boardFrame;
		boardFrame.setVisible(true);
	}
	
}
