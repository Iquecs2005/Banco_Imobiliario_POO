package Model;

import java.util.List;
import java.util.LinkedList;

class Player extends BankBalance 
{
	private boolean bankrupt;
	private String color;
	private Space currentSpace;
	private List<Space> ownedSpaces = new LinkedList<Space>();
	
	public Player(String color, float money, Space currentSpace) 
	{
		super(money);
		this.color = color;
		this.currentSpace = currentSpace;
	}
	
	@Override
	public boolean TransferMoney(BankBalance receiver, float amount) 
	{
		bankrupt = super.TransferMoney(receiver, amount);
		return bankrupt;
	}
	
	public boolean BuySpace(Bank bank, Buyable space)
	{
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

	public void SetColor(String color) 
	{
		this.color = color;
	}
	
	public Space GetCurrentSpace() 
	{
		return currentSpace;
	}
	
	public void SetCurrentSpace(Space currentSpace) 
	{
		this.currentSpace = currentSpace;
	}
	
	public List<Space> GetOwnedSpaces() 
	{
		return ownedSpaces;
	}
}
