package Model;

class Bank {
	private float money;
	
	public Bank(float money)
	{
		this.money = money;
	}
	
	public void GiveMoney(float amount, Player player)
	{
		this.money -= amount;
		float playerMoney = player.GetMoney();
		player.SetMoney(playerMoney + amount);
	}
	
	public boolean TakeMoney(float amount, Player player)
	{
		this.money += amount;
		float playerMoney = player.GetMoney();
		player.SetMoney(playerMoney - amount);
		if (player.GetMoney() <= 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean TransferMoney(float amount, Player playerGet, Player playerGive)
	{
		float getMoney = playerGet.GetMoney();
		float giveMoney = playerGive.GetMoney();
		playerGet.SetMoney(getMoney + amount);
		playerGive.SetMoney(giveMoney - amount);
		if (playerGive.GetMoney() <= 0)
		{
			return true;
		}
		return false;
	}
}
