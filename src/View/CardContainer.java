package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import Controller.Controller;
import Model.Event;
import Model.Observer;
import View.CardUI.CardType;

class CardContainer
{
	private CardFrame cardFrame;
	
	public CardContainer() 
	{
		Controller.instance.SubscribeToCardDrawn(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				OnLuckCardDrawn();
			}
		});
		
		Controller.instance.SubscribeToBuyablePropertyLand(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				OnPropertyBought();
			}
		});
	}
	
	public void DisplayPlayerCards() 
	{
		
	}
	
	public void DisplayCard(String cardName, CardType cardType) 
	{
		if (cardFrame == null)
			cardFrame = new CardFrame("Cards Window", 200, 100);
		else
			cardFrame.ClearCards();
		cardFrame.setVisible(true);
		
		cardFrame.AddCard(cardName, cardType);
	}
	
	public void OnLuckCardDrawn() 
	{
		String cardName = "chance";
		cardName += String.valueOf(Controller.instance.getLastCardId());
		DisplayCard(cardName, CardType.Luck);
	}
	
	public void OnPropertyBought() 
	{
		String cardName;
		if (Controller.instance.LandedSpaceIsProperty())
			cardName = "";
		else
			cardName = "";
		cardName += String.valueOf(Controller.instance.getLastCardId());
		DisplayCard(cardName, CardType.Luck);
	}
}
