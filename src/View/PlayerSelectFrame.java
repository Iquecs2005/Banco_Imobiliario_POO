package View;

import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import Controller.Controller;

class PlayerSelectFrame extends BaseFrame
{
	public final JButton b1 = new JButton("New Player");
	public final JButton b2 = new JButton("Start Game");
	
	int nPlayers = 0;
	
	private JPanel panel;
	private boolean b2Active = false;
	private boolean b1Active = true;
	private List<PlayerInfoForm> playerInformation = new LinkedList<PlayerInfoForm>();
	
	public PlayerSelectFrame(int startWidth, int startHeight, ViewController vc) 
	{
		super("Player Select", startWidth, startHeight);
		
		panel = new PlayerSelectPanel(frameWidth, frameHeight);
		getContentPane().add(panel);
		
		PositionComponent(b1, 100, 100);
		panel.add(b1);
		PositionComponent(b2, frameWidth / 2, frameHeight - 100);
		b1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				NewPlayer();
			}
		});
		
		b2.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.CreateNewGame(nPlayers);
				vc.ActivateBoard();
			}
		});	
		
		System.out.println(getContentPane().getClass());
		System.out.println(Arrays.toString(getContentPane().getComponents()));
	}
	
	public void NewPlayer() 
	{
		if (nPlayers >= 6)
			return;
		
		PlayerInfoForm newForm = new PlayerInfoForm(panel, nPlayers);
		
		newForm.exitButton.addActionListener(new ExitButtonListener(this, newForm));
		playerInformation.add(newForm);
		nPlayers++;
		
		if (nPlayers >= 6) 
		{
			b1.setVisible(false);
			panel.remove(b1);
			b1Active = false;
		}
		else if (nPlayers >= 3) 
		{
			if (!b2Active) 
			{
				panel.add(b2);
				b2.setVisible(true);
				b2Active = true;
			}
		}

		DrawFields();
	}
	
	public void RemovePlayer(PlayerInfoForm id) 
	{
		playerInformation.get(playerInformation.indexOf(id)).Destructor();
		playerInformation.remove(id);
		nPlayers--;
		
		if (nPlayers < 3) 
		{
			if (b2Active) 
			{
				panel.remove(b2);
				b2.setVisible(false);
				b2Active = false;
			}
		}
		else if (!b1Active) 
		{
			b1.setVisible(true);
			panel.add(b1);
			b1Active = true;
		}
		
		DrawFields();
	}
	
	public void DrawFields() 
	{
		for (int i = 0; i < nPlayers; i++) 
		{
			PlayerInfoForm playerInfo = playerInformation.get(i);
			
			int x = 100 + 150 * (i % 3);
			int y = 100 + 100 * (i / 3);
			
			playerInfo.Move(x, y, i);
		}
		
		PositionComponent(b1, 100 + 150 * (nPlayers % 3), 100 + 100 * (nPlayers / 3));
		
		panel.revalidate();
		panel.repaint();
	}
}

class PlayerInfoForm 
{
	final JPanel panel;
	final JTextField textField;
	final JLabel playerlabel;
	final JButton exitButton = new JButton("X");
	
	public PlayerInfoForm(JPanel panel, int id) 
	{
		this.panel = panel;
		textField = new JTextField("Player Name");
		playerlabel = new JLabel("Player " + Integer.toString(id + 1));
		
		playerlabel.setForeground(Color.white);
		textField.setEditable(true);
		
		panel.add(textField);
		panel.add(playerlabel);
		panel.add(exitButton);
	}
	
	public void Destructor() 
	{
		panel.remove(textField);
		panel.remove(playerlabel);
		panel.remove(exitButton);
	}
	
	public void Move(int x, int y, int id) 
	{
		playerlabel.setText("Player " + Integer.toString(id + 1));
		
		BaseFrame.PositionComponent(playerlabel, x, y - 20);
		BaseFrame.PositionComponent(textField, x, y + 10);
		BaseFrame.PositionComponent(exitButton, x, y + 40);
	}
}
