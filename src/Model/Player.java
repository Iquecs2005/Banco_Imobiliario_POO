package Model;

import java.util.List;
import java.util.LinkedList;

class Player 
{
	private String color;
	private float money;
	private Space currentSpace;
	private List<Space> ownedSpaces = new LinkedList<Space>();
	
	public Player(String color, float money, Space currentSpace) 
	{
		this.color = color;
		this.money = money;
		this.currentSpace = currentSpace;
	}
	
	public boolean BuySpace()
	{
		//if (Bank.Pay(this, currentSpace.price))
		//{
		//  currentSpace.SetOwner(this);
		//	ownedSpaces.add(currentSpace);
		//	return true;
		//}
		
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
	
	public float GetMoney() 
	{
		return money;
	}
	
	public void SetMoney(float money) 
	{
		this.money = money;
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
