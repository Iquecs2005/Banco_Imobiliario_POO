package Model;

abstract class Building {
	private int cost;
	private int amount;
	
	public Building(int cost)
	{
		this.cost = cost;
		this.amount = 0;
	}
	
	public void AddBuilding()
	{
		this.amount += 1;
	}
	
	public int GetCost()
	{
		return this.cost;
	}
	
	public int GetAmount()
	{
		return this.amount;
	}
	
	public void SetAmount(int i)
	{
		this.amount = i;
	}
}
