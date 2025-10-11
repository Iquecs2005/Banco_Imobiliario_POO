package Model;

abstract class BankBalance 
{
	protected float money;
	
	boolean TransferMoney(BankBalance receiver, float amount)		
	{
		float transferAmount = Math.min(money, amount);
		money -= amount;
		receiver.money += transferAmount;
		
		return money <= 0;
	}
	
	boolean CanAfford(float price) 
	{
		return money > price;
	}
}
