package Model;

abstract class BankBalance 
{
	private float money;
	
	public BankBalance(float money) 
	{
		this.money = money;
	}
	
	public boolean TransferMoney(BankBalance receiver, float amount)		
	{
		float transferAmount = Math.min(money, amount);
		money -= amount;
		receiver.money += transferAmount;
		
		return money <= 0;
	}
	
	public boolean CanAfford(float price) 
	{
		return money > price;
	}

	public float GetMoney() 
	{
		return money;
	}
}
