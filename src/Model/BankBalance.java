package Model;

enum TransferMoneyResult
{
	NullReceiver, NegativeAmount, SenderReceiverEquals, Ok
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
		if (receiver == this) return TransferMoneyResult.SenderReceiverEquals;
		if (receiver == null) return TransferMoneyResult.NullReceiver;
		if (amount < 0) return TransferMoneyResult.NegativeAmount;
		
		amount = Math.min(money, amount);
		
		money -= amount;
		receiver.money += amount;
		
		return TransferMoneyResult.Ok;
	}
	
	public boolean IsBankrupt() 
	{
		return money <= 0;
	}
	
	public boolean CanAfford(float price) 
	{
		return money > price;
	}
	
	public void SetMoney(float moneyval)
	{
		this.money = moneyval;
	}

	public float GetMoney() 
	{
		return money;
	}
}
