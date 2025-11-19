package View;

import java.util.ArrayList;
import java.util.List;

import Controller.Controller;
import Model.Event;
import Model.Observer;
import View.CardUI.CardType;

class CardContainer implements Observer
{
	private CardFrame cardFrame;
	
	public CardContainer() 
	{
		Controller.instance.SubscribeToCardDrawn(this);
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
	
	@Override
	public void update(Event event) 
	{
		String cardName = "chance";
		cardName += String.valueOf(Controller.instance.getLastCardId());
		DisplayCard(cardName, CardType.Luck);
	}
}
