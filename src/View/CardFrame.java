package View;

import View.CardUI.CardType;

public class CardFrame extends BaseFrame 
{
	private CardPanel panel;
	
	public CardFrame(String windowName, int startWidth, int startHeight) 
	{
		super(windowName, startWidth, startHeight);
		
		panel = new CardPanel(startWidth, startHeight);
		getContentPane().add(panel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void AddCard(String cardName, CardType cardType) 
	{
		panel.AddCard(cardName, cardType);
	}
	
	public void ClearCards() 
	{
		panel.ClearCards();
		panel.ToggleBuyButton(false);
		panel.ToggleHouseButton(false);
		panel.ToggleHotelButton(false);
		panel.ToggleRentValues(false, false);
	}
	
	public void ToggleBuyButton(boolean state) 
	{
		panel.ToggleBuyButton(state);
	}
	
	public void ToggleHouseButton(boolean state) 
	{
		panel.ToggleHouseButton(state);
	}
	
	public void ToggleHotelButton(boolean state) 
	{
		panel.ToggleHotelButton(state);
	}
	
	public void ToggleRentValues(boolean stateRent, boolean stateBuildings)
	{
		panel.ToggleRentValues(stateRent, stateBuildings);
	}
	
	public void UpdateRentValue()
	{
		panel.UpdateRentValue();
	}
	
	public void UpdateBuildingValues()
	{
		panel.UpdateBuildingValues();
	}
	
	public void AddSellButton(String color, String propName)
	{
		panel.AddSellButton(color, propName);
	}
	
	public void RemoveSellButtons()
	{
		panel.RemoveSellButtons();
	}
	
}
