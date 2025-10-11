package Model;

import java.util.List;
import java.util.LinkedList;

class Player extends BankBalance 
{
	private boolean bankrupt;
	private String color;
	private Space currentSpace;
	
	private List<Space> ownedSpaces = new LinkedList<Space>();
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
		
		System.out.println(removeStatus);
		
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
	
	@Override
	public boolean TransferMoney(BankBalance receiver, float amount) 
	{
		if (receiver == null) return false;
		
		bankrupt = super.TransferMoney(receiver, amount);
		return bankrupt;
	}
	
	public boolean BuySpace(Bank bank)
	{
		/*if (TransferMoney(bank, currentSpace.price))
		{
			//currentSpace.SetOwner(this);
			ownedSpaces.add(currentSpace);
			return true;
		}*/
		
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
	
	public List<Space> GetOwnedSpaces() 
	{
		return ownedSpaces;
	}
	
	void SetCurrentSpace(Space currentSpace) 
	{
		this.currentSpace = currentSpace;
	}
}
