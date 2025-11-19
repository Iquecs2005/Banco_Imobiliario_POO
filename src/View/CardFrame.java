package View;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

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
	}
}
