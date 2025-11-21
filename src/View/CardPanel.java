package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;

import Controller.Controller;
import View.CardUI.CardType;

public class CardPanel extends BasePanel
{
	private List<CardUI> cardList = new LinkedList<CardUI>();
	private JButton buyButton = new JButton("Buy Space");
	private JButton houseButton = new JButton("Buy House");
	private JButton hotelButton = new JButton("Buy Hotel");
	private List<JButton> sellButtons = new ArrayList<>();

	public CardPanel(int width, int height) 
	{
		super(width, height);
		
		add(buyButton);
		add(houseButton);
		add(hotelButton);
		buyButton.setVisible(false);
		houseButton.setVisible(false);
		hotelButton.setVisible(false);
		buyButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.BuySpace();
				ToggleBuyButton(false);
			}
		});
		houseButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.BuyHouse();
				ToggleHouseButton(false);
				ToggleHotelButton(false);
			}
		});
		hotelButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.BuyHotel();
				ToggleHouseButton(false);
				ToggleHotelButton(false);
			}
		});
		
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
		
		i = 0;
		for(JButton sellButton : sellButtons)
		{
			BaseFrame.PositionComponent(sellButton, (i*cardSize) + cardSize/2, getHeight() - 30);
			i++;
		}
		
		BaseFrame.PositionComponent(buyButton, frameSize / 2, getHeight() - 30);
		BaseFrame.PositionComponent(houseButton, frameSize / 2, getHeight() - 30);
		BaseFrame.PositionComponent(hotelButton, frameSize / 2, getHeight() - 60);
		
	}
	
	public void AddCard(String cardName, CardType cardType) 
	{
		cardList.add(new CardUI(cardName, cardType, 0, 0));
		repaint();
	}
	
	public void ClearCards() 
	{
		cardList.clear();
		sellButtons.clear();
	}
	
	public void ToggleBuyButton(boolean state) 
	{
		buyButton.setVisible(state);
		repaint();
	}
	
	public void ToggleHouseButton(boolean state) 
	{
		houseButton.setVisible(state);
		repaint();
	}
	
	public void ToggleHotelButton(boolean state) 
	{
		hotelButton.setVisible(state);
		repaint();
	}
	
	public void AddSellButton(String color, String propName)
	{
		JButton sellButton = new JButton("Sell space");
		sellButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Controller.instance.SellProperty(color, propName);
			}
		});
		
		sellButtons.add(sellButton);
		add(sellButton);
		
	}
	
	
}
