package Model;

abstract class Building {
	private int cost;
	private int rent;
	private int amount;
	
	public Building(int cost, int rent)
	{
		this.cost = cost;
		this.rent = rent;
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
	
	public int GetRent()
	{
		return this.rent;
	}
	
	public int GetAmount()
	{
		return this.amount;
	}
}
