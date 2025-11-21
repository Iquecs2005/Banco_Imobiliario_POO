package View;

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
		
		Controller.instance.SubscribeToBuyableHotelHouse(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				OnPropertyOwned(true, true);
			}
		});
		
		Controller.instance.SubscribeToBuyableHouse(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				OnPropertyOwned(true, false);
			}
		});
		
		Controller.instance.SubscribeToBuyableHotel(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				OnPropertyOwned(false, true);
			}
		});
	}
	
	public void DisplayPlayerCards() 
	{
		
	}
	
	public void ClearCards() 
	{
		if (cardFrame != null)
			cardFrame.ClearCards();
	}
	
	public void DisplayCard(String cardName, CardType cardType) 
	{
		if (cardFrame == null)
			cardFrame = new CardFrame("Cards Window", 300, 400);
		else
			ClearCards();
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
		String cardName = Controller.instance.GetLandedBuyableName();
		CardType cardType = CardType.Company;
		
		if (Controller.instance.LandedSpaceIsProperty())
			cardType = CardType.Property;
		
		
		DisplayCard(cardName, cardType);
		cardFrame.ToggleBuyButton(true);
	}
	
	public void OnPropertyOwned(boolean houseState, boolean hotelState) 
	{
		String cardName = Controller.instance.GetLandedBuyableName();
		CardType cardType = CardType.Property;	
		
		DisplayCard(cardName, cardType);
		cardFrame.ToggleHouseButton(houseState);
		cardFrame.ToggleHotelButton(hotelState);
	}
}
