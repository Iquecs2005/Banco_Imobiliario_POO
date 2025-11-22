package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import Controller.Controller;
import View.CardUI.CardType;

public class CardPanel extends BasePanel
{
	private List<CardUI> cardList = new LinkedList<CardUI>();
	private JButton buyButton = new JButton("Buy Space");
	private JButton houseButton = new JButton("Buy House");
	private JButton hotelButton = new JButton("Buy Hotel");
	private JLabel totalRent = new JLabel("Rent: ");
	private JLabel totalHouses = new JLabel("Houses: ");
	private JLabel totalHotel = new JLabel("Hotel: ");
	private List<JButton> sellButtons = new ArrayList<>();
	private JLabel debtLabel;

	public CardPanel(int width, int height) 
	{
		super(width, height);
		
		add(buyButton);
		add(houseButton);
		add(hotelButton);
		add(totalRent);
		add(totalHouses);
		add(totalHotel);
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
		BaseFrame.PositionComponent(totalRent, frameSize / 2 - 100, getHeight() - 60);
		BaseFrame.PositionComponent(totalHouses, frameSize / 2 - 100, getHeight() - 40);
		BaseFrame.PositionComponent(totalHotel, frameSize / 2 - 100, getHeight() - 20);
		if (debtLabel != null)
			BaseFrame.PositionComponent(debtLabel, frameSize / 2, getHeight() - 75);
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
	
	public void ToggleRentValues(boolean stateRent, boolean stateBuildings)
	{
		totalRent.setVisible(stateRent);
		totalHouses.setVisible(stateBuildings);
		totalHotel.setVisible(stateBuildings);
	}
	
	public void UpdateRentValue()
	{
		float rent = Controller.instance.GetLandedSpaceRent();
		totalRent.setText("Rent: " + Float.toString(rent));
	}
	
	public void UpdateBuildingValues()
	{
		int houses = Controller.instance.GetLandedPropertyHouses();
		int hotel = Controller.instance.GetLandedPropertyHotel();
		totalHouses.setText("Houses: " + Integer.toString(houses));
		totalHotel.setText("Hotel: " + Integer.toString(hotel));
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
				sellButton.setVisible(false);
				repaint();
			}
		});
		
		sellButtons.add(sellButton);
		add(sellButton);
		
	}
	
	public void AddDebtLabel()
	{
		float value = Controller.instance.GetDebtValue();
		debtLabel = new JLabel("Debt: R$ " + Float.toString(value));
		debtLabel.setVisible(true);
		add(debtLabel);
	}
}
