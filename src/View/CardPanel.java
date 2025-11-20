package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;

import Controller.Controller;
import View.CardUI.CardType;

public class CardPanel extends BasePanel
{
	private List<CardUI> cardList = new LinkedList<CardUI>();
	private JButton buyButton = new JButton("Buy Space");

	public CardPanel(int width, int height) 
	{
		super(width, height);
		
		add(buyButton);
		buyButton.setVisible(false);
		buyButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.BuySpace();
				ToggleBuyButton(false);
			}
		});
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        int frameSize = getWidth();
        int cardSize = 0;
        if (cardList.size() != 0)
        	cardSize = frameSize / cardList.size();
        
        int i = 0;
		for (CardUI card : cardList) 
		{
			card.SetPos(i * cardSize, 0);
			card.PaintComponent(g2d, 0, cardSize, this);
			i++;
		}
		
		BaseFrame.PositionComponent(buyButton, frameSize / 2, getHeight() - 30);
	}
	
	public void AddCard(String cardName, CardType cardType) 
	{
		cardList.add(new CardUI(cardName, cardType, 0, 0));
		repaint();
	}
	
	public void ClearCards() 
	{
		cardList.clear();
	}
	
	public void ToggleBuyButton(boolean state) 
	{
		buyButton.setVisible(state);
		repaint();
	}
}
