package Model;

import java.util.List;
import java.util.LinkedList;

class Player extends BankBalance 
{
	private boolean bankrupt;
	private String color;
	private Space currentSpace;
	private boolean inJail = false;
	
	private List<Buyable> ownedSpaces = new LinkedList<Buyable>();
	private List<Card> heldCards = new LinkedList<Card>();
	
	public Player(String color, float money, Space currentSpace) 
	{
		super(money);
		this.color = color;
		this.currentSpace = currentSpace;
	}
	
	public boolean AddCard(Card card) 
	{
		if (card == null) return false;
		
		heldCards.add(card);
		return true;
	}
	
	public boolean RemoveCard(Card card) 
	{
		if (card == null) return false;
		
		boolean removeStatus = heldCards.remove(card);
		
		return removeStatus;
	}
	
	public Card FindCard(String cardId) 
	{
		for (Card card : heldCards) 
		{
			if (card.GetCardId() == cardId)
				return card;
		}
		
		return null;
	}
	
	public boolean BuySpace(Bank bank)
	{
		if (!(currentSpace instanceof Buyable)) return false;
		
		Buyable space = (Buyable)currentSpace;
		boolean purchaseres = space.purchaseBuyable(this, bank);
		
		if (purchaseres == true) {
			ownedSpaces.add(space);
			return true;
		}
		
		
		return false;
	}
	
	public boolean SellSpace(Space soldSpace) 
	{
		int spaceIndex = ownedSpaces.indexOf(soldSpace);
		
		if (spaceIndex == -1) 
			return false;
		
		//Bank.Transfer(this, currentSpace.sellValue);
		//currentSpace.SetOwner(null);
		
		return true;
	}
	
	public String GetColor() 
	{
		return color;
	}
	
	public Space GetCurrentSpace() 
	{
		return currentSpace;
	}
	
	public List<Buyable> GetOwnedSpaces() 
	{
		return ownedSpaces;
	}
	
	void SetCurrentSpace(Space currentSpace) 
	{
		this.currentSpace = currentSpace;
	}
	
	void SetInJail(boolean bool) {
		this.inJail = bool;
	}
	
	public boolean GetJailedStatus () {
		return this.inJail;
	}
}
