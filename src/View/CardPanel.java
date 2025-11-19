package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import View.CardUI.CardType;

public class CardPanel extends BasePanel
{
	private List<CardUI> cardList = new LinkedList<CardUI>();

	public CardPanel(int width, int height) 
	{
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        int frameSize = getWidth();
        int cardSize = frameSize / cardList.size();
        
        int i = 0;
		for (CardUI card : cardList) 
		{
			System.out.println(i);
			card.SetPos(i * cardSize, 0);
			card.PaintComponent(g2d, 0, cardSize, this);
			i++;
		}
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
}
