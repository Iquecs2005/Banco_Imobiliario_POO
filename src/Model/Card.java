package Model;

import java.util.List;

class Card 
{
	public enum CardType
	{
		Receive, Lose, ReceiveFromAll, GoToBeginning, GoToJail, LeaveJail
	}
	
	private static Bank bank;
	private static Board board;
	private static Jail jail;
	private Player owner;
	private String id = "0";
	private int amount = 0;
	private CardType type;
	
	public Card(CardType type) 
	{
		this.type = type;
	}
	
	public Card(int amount, CardType type) 
	{
		this.amount = amount;
		this.type = type;
	}
	
	public static void SetStaticVariables(Bank bank, Board board, Jail jail)
	{
		Card.bank = bank;
		Card.board = board;
		Card.jail = jail;
	}
	
	public String GetCardId() 
	{
		return id;
	}
	
	public void SetOwner(Player owner)
	{
		this.owner = owner;
	}
	
	private void ReceiveAll(List<Player> players)
	{
		for (Player player: players)
		{
			if (this.owner != player)
			{
				player.TransferMoney(owner, 50);
			}
		}
	}
	
	private void GoToBeginning(Board board)
	{
		Space startSpace = board.GetStartSpace();
		owner.SetCurrentSpace(startSpace);
		bank.TransferMoney(owner, 200);
	}
	
	private void GoToJail(Jail jail)
	{
		jail.sendToJail(owner);
	}
	
	private void LeaveJail()
	{
		//player.hasJailCard = true;
	}
	
	public void UseCard(List<Player> players)
	{
		switch (this.type)
		{
		case Receive:
			bank.TransferMoney(owner, amount);
		case Lose:
			owner.TransferMoney(bank, amount);
		case ReceiveFromAll:
			ReceiveAll(players);
		case GoToBeginning:
			GoToBeginning(board);
		case GoToJail:
			GoToJail(jail);
		case LeaveJail:
			LeaveJail();
		default:
			break;
		}
	}
}
