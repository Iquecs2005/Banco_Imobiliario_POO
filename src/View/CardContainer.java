package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		Controller.instance.SubscribeToCantAffordRent(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				DisplayPlayerCards(Controller.instance.GetCurrentPlayerColor());
			}
		});
		
		Controller.instance.SubscribeToOnBankrupt(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				ClearCards();
			}
		});
		
		Controller.instance.SubscribeToGameEnd(new Observer() 
		{
			@Override
			public void update(Event event)  
			{
				OnGameEnd();
			}
		});
	
		
	}
	
	public void DisplayPlayerCards(String playerColor) 
	{
		if (cardFrame!= null) 
		{
			cardFrame.ClearCards();
		}
		Map<String, String> playerCards = Controller.instance.GetPlayerOwnedSpaces(playerColor);
		Map<String, CardType> playerCardsWithType = playerCardsTyping(playerCards);
		for(Map.Entry<String, CardType> entry : playerCardsWithType.entrySet())
		{
			String cardName = entry.getKey();
			CardType cardType = entry.getValue();
			DisplayCard(cardName, cardType);
			cardFrame.AddSellButton(playerColor, cardName);
		}

	}
	
	private Map<String, CardType> playerCardsTyping(Map<String, String> playerCards)
	{
		Map<String, CardType> playerCardsWithType = new HashMap<>();

		for (Map.Entry<String, String> entry : playerCards.entrySet()) {
		    String cardName = entry.getKey();
		    String temp   = entry.getValue();
		    CardType type = null;
		    switch(temp)
		    {
		    case("Property"):
		    	type = CardType.Property;
		    	break;
		    case("Company"):
		    	type = CardType.Company;
		    	break;
		    }
		    playerCardsWithType.put(cardName, type);
		}
		
		return playerCardsWithType;
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
		cardFrame.ToggleRentValues(false, false);
	}
	
	public void OnPropertyBought() 
	{
		String cardName = Controller.instance.GetLandedBuyableName();
		CardType cardType = CardType.Company;
		boolean toggleRent = true;
		boolean toggleBuildings = false;
		
		if (Controller.instance.LandedSpaceIsProperty())
		{
			cardType = CardType.Property;
			toggleBuildings = true;
		}
		
		
		DisplayCard(cardName, cardType);
		cardFrame.UpdateRentValue();
		if (toggleBuildings) cardFrame.UpdateBuildingValues();
		cardFrame.ToggleRentValues(toggleRent, toggleBuildings);
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
	
	public void OnGameEnd()
	{
		Controller.instance.OnEndGame();
		if (cardFrame != null) cardFrame.dispose();
	}
}
