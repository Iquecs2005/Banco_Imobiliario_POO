package Model;

import java.util.List;
import java.util.LinkedList;

class Player extends BankBalance 
{
	//private boolean bankrupt;
	private String color;
	private Space currentSpace;
	private boolean inJail = false;
	private boolean hasJailCard = false;
	
	private List<Buyable> ownedSpaces = new LinkedList<Buyable>();
	
	public Player(String color, float money, Space currentSpace) 
	{
		super(money);
		this.color = color;
		this.currentSpace = currentSpace;
	}
	
	public boolean AddJailCard(Card card) 
	{
		if (card.GetOwner() != null) return false;
		
		hasJailCard = true;
		return true;
	}
	
	public boolean UseJailCard(Deck deck) 
	{
		if (!hasJailCard) return false;
		
		hasJailCard = false;
		deck.GetJailCard().SetOwner(null);
		return true;
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
	
	void SetInJail(boolean inJail) {
		this.inJail = inJail;
	}
	
	public boolean GetJailedStatus () {
		return inJail;
	}
	
	public boolean HasJailedCard() {
		return this.hasJailCard;
	}
}
