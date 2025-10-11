package Model;

enum TransferMoneyResult
{
	NullReceiver, NotEnoughMoney, Ok
}

abstract class BankBalance 
{
	private float money;
	
	public BankBalance(float money) 
	{
		this.money = money;
	}
	
	public TransferMoneyResult TransferMoney(BankBalance receiver, float amount)		
	{
		if (receiver == null) return TransferMoneyResult.NullReceiver;
		
		if (money < amount) return TransferMoneyResult.NotEnoughMoney;
		
		money -= amount;
		receiver.money += amount;
		
		return TransferMoneyResult.Ok;
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
