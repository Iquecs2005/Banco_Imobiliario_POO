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
}
