package Model;

class Bank extends BankBalance 
{
	public Bank(float money)
	{
		super(money);
	}
	
	@Override
	public TransferMoneyResult TransferMoney(BankBalance receiver, float amount) 
	{
		TransferMoneyResult transferResult = super.TransferMoney(receiver, amount);
		
		if (transferResult == TransferMoneyResult.NotEnoughMoney) 
		{
			return super.TransferMoney(receiver, GetMoney()); 
		}
		
		return transferResult;
	}
}
