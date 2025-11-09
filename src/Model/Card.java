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
	
	public Player GetOwner()
	{
		return owner;
	}
	
	private boolean ReceiveAll(List<Player> players)
	{
		for (Player player: players)
		{
			if (this.owner != player)
			{
				player.TransferMoney(owner, 50);
			}
		}
		return true;
	}
	
	private boolean GoToBeginning(Board board)
	{
		Space startSpace = board.GetStartSpace();
		owner.SetCurrentSpace(startSpace);
		bank.TransferMoney(owner, 200);
		return true;
	}
	
	private boolean GoToJail(Jail jail)
	{
		jail.sendToJail(owner);
		return true;
	}
	
	private boolean LeaveJail()
	{
		return owner.AddJailCard(this);
	}
	
	public boolean UseCard(List<Player> players)
	{
		switch (this.type)
		{
		case Receive:
			bank.TransferMoney(owner, amount);
			return true;
		case Lose:
			owner.TransferMoney(bank, amount);
			return true;
		case ReceiveFromAll:
			return ReceiveAll(players);
		case GoToBeginning:
			return GoToBeginning(board);
		case GoToJail:
			return GoToJail(jail);
		case LeaveJail:
			return LeaveJail();
		default:
			return false;
		}
	}
}
