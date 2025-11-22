package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Controller.Controller;

class WinPanel extends BasePanel
{
	JLabel congratulationsLabel = new JLabel("Congratulations!!!");
	JLabel winnerNameLabel = new JLabel();
	JLabel winnerColorLabel = new JLabel();
	JLabel winnerNetworthLabel = new JLabel();
	JButton closeButton = new JButton("Main Menu");
	
	public WinPanel(int width, int height) 
	{
		super(width, height);
		
		SetBackgroundColor(10, 100, 140);
		
		congratulationsLabel.setForeground(Color.white);
		
		String winnerName = Controller.instance.GetWinnerName();
		winnerNameLabel.setText(winnerName);
		winnerNameLabel.setForeground(Color.white);
		
		String winnerColor = Controller.instance.GetWinnerColor();
		winnerColorLabel.setText(winnerColor + " Player");
		winnerColorLabel.setForeground(Color.white);
		
		float winnerNetworth = Controller.instance.GetWinnerNetworth();
		winnerNetworthLabel.setText("With R$: " + Float.toString(winnerNetworth));
		winnerNetworthLabel.setForeground(Color.white);
		
		closeButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.ActivateMainMenu();
			}
		}
		);
		
		PositionButtons();
		
		add(congratulationsLabel);
		add(winnerNameLabel);
		add(winnerColorLabel);
		add(winnerNetworthLabel);
		add(closeButton);
	}
	
	@Override
    protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		PositionButtons();
	}
	
	private void PositionButtons() 
	{
		BaseFrame.PositionComponent(congratulationsLabel, width / 2, 50);
		BaseFrame.PositionComponent(winnerNameLabel, width / 2, height / 2);
		BaseFrame.PositionComponent(winnerColorLabel, width / 2, height / 2 - 50);
		BaseFrame.PositionComponent(winnerNetworthLabel, width / 2, height / 2 + 50);
		BaseFrame.PositionComponent(closeButton, width / 2, height - 50);
		
		Font newFont = new Font("Arial", Font.PLAIN, 50);
		congratulationsLabel.setFont(newFont);
	}
}
