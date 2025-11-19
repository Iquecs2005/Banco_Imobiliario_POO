package Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Deck 
{
	private HashMap<Integer, Card> deck = new HashMap<Integer, Card>();
	private List<Player> players = new LinkedList<Player>();
	
	private int lastCardId;
	
	public Deck()
	{
		CreateDeck();
	}
	
	public void SetVariables(List<Player> players, Bank bank, Board board) 
	{
		this.players = players;
		Card.SetStaticVariables(bank, board, board.GetJail());
	}
	
	private void CreateDeck()
	{
		deck.put(1, new Card(25, Card.CardType.Receive));
		deck.put(2, new Card(150, Card.CardType.Receive));
		deck.put(3, new Card(80, Card.CardType.Receive));
		deck.put(4, new Card(200, Card.CardType.Receive));
		deck.put(5, new Card(50, Card.CardType.Receive));
		deck.put(6, new Card(50, Card.CardType.Receive));
		deck.put(7, new Card(100, Card.CardType.Receive));
		deck.put(8, new Card(100, Card.CardType.Receive));
		deck.put(9, new Card(Card.CardType.LeaveJail));
		deck.put(10, new Card(Card.CardType.GoToBeginning));
		deck.put(11, new Card(Card.CardType.ReceiveFromAll));
		deck.put(12, new Card(45, Card.CardType.Receive));
		deck.put(13, new Card(100, Card.CardType.Receive));
		deck.put(14, new Card(100, Card.CardType.Receive));
		deck.put(15, new Card(20, Card.CardType.Receive));
		deck.put(16, new Card(15, Card.CardType.Lose));
		deck.put(17, new Card(25, Card.CardType.Lose));
		deck.put(18, new Card(45, Card.CardType.Lose));
		deck.put(19, new Card(30, Card.CardType.Lose));
		deck.put(20, new Card(100, Card.CardType.Lose));
		deck.put(21, new Card(100, Card.CardType.Lose));
		deck.put(22, new Card(40, Card.CardType.Lose));
		deck.put(23, new Card(Card.CardType.GoToJail));
		deck.put(24, new Card(30, Card.CardType.Lose));
		deck.put(25, new Card(50, Card.CardType.Lose));
		deck.put(26, new Card(25, Card.CardType.Lose));
		deck.put(27, new Card(30, Card.CardType.Lose));
		deck.put(28, new Card(45, Card.CardType.Lose));
		deck.put(29, new Card(50, Card.CardType.Lose));
		deck.put(30, new Card(50, Card.CardType.Lose));
	}
	
	public Card GetJailCard()
	{
		return deck.get(9);
	}
	
	public void GetCard(Player player)
	{
		boolean gotCard = false;
		while(!gotCard)
		{
			lastCardId = (int)(Math.random()*(30) + 1);
			Card card = deck.get(lastCardId);
			if (card == GetJailCard() && card.GetOwner() != null) continue;
			card.SetOwner(player);
			gotCard = card.UseCard(players);
		}
	}
	
	public int GetLastCardID() 
	{
		return lastCardId;
	}
}
