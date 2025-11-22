package Model;

import java.util.List;
import java.util.LinkedList;

class Player extends BankBalance 
{
	//private boolean bankrupt;
	private String color;
	private String name;
	private Space currentSpace;
	private boolean inJail = false;
	private boolean hasJailCard = false;
	
	private List<Buyable> ownedSpaces = new LinkedList<Buyable>();
	
	public Player(String color, String name, float money, Space currentSpace) 
	{
		super(money);
		this.color = color;
		this.name = name;
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
	
	public boolean SellSpace(Bank bank, Space soldSpace) 
	{
		int spaceIndex = ownedSpaces.indexOf(soldSpace);
		
		if (spaceIndex == -1) 
			return false;
		
		if (!(soldSpace instanceof Property) && !(soldSpace instanceof Company)) return false;
		
		Buyable space = (Buyable)soldSpace;
		bank.TransferMoney(this, (space.getRent() * 0.9f));
		space.setOwner(null);
		ownedSpaces.remove(space);
		
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
	
	public boolean HasJailedCard() 
	{
		return hasJailCard;
	}

	public void AddOwnedSpace(Buyable b) {
		this.ownedSpaces.add(b);
	}
	
	public String GetName()
	{
		return this.name;
	}
	
	public void SetName(String newName)
	{
		this.name = newName;
	}
}
