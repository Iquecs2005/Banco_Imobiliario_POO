package Model;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import Model.Space.Codes;

public class Model 
{
	public static final Model instance = new Model();
	
	private Map<String, Player> currentPlayers;
	private Bank currentBank;
	private Board currentBoard;
	private Dice currentDice;
	private Deck currentDeck;
	
	//Initialize events.
	private Event onPlayerPosAltered = new Event();
	
	private Model() 
	{
		
	}
	
	public boolean NewGame(List<String> playerColors) 
	{
		int nPlayers = playerColors.size();
		
		if (nPlayers < 3 || nPlayers > 6) 
		{
			return false;
		}
		
		currentBank = new Bank(200000);
		currentBoard = new Board(currentBank);
		currentPlayers = new HashMap<String, Player>();
		currentDice = new Dice(6);
		List<Player> playerList = List.copyOf(currentPlayers.values());
		currentDeck = new Deck(playerList, currentBank, currentBoard);
		
		Space startSpace = currentBoard.GetStartSpace();
		for (String color : playerColors)
		{
			currentPlayers.put(color, new Player(color, 4000.0f, startSpace));
		}
		
		return true;
	}
		
	public int RollDie()
	{
		return currentDice.roll();
	}
	
	public Vector<Integer> RollDice(int n)
	{
		return currentDice.roll(n);
	}
	
	public void MovePlayer(String playerColor, int amount) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		Space landedSpace;
		
		landedSpace = currentBoard.MovePlayer(currentPlayer, amount);
		Codes landCode = landedSpace.onLand(currentPlayer);
		
		switch (landCode)
		{
			// Do something depending on the landCode (Ex: Trigger an event, call a method, etc)
			default:
				break;
				
		}
		
		onPlayerPosAltered.notifyObservers();
	} 
	
	public String GetPlayerSpaceName(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		return currentPlayer.GetCurrentSpace().name;
	}
	
	public int GetSpaceIndex(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		return currentBoard.GetSpaceIndex(currentPlayer.GetCurrentSpace());
	}
	
	public void SubscribeToPlayerPos(Observer newObserver) 
	{
		onPlayerPosAltered.addObserver(newObserver);
	}
	
	public boolean BuyProperty(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		
		return currentPlayer.BuySpace(currentBank);
	}
	
	public boolean BuyHouse(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		Space currentSpace = currentPlayer.GetCurrentSpace();
		
		if (!(currentSpace instanceof Property))
			return false;
		
		Property currentProperty = (Property) currentSpace;
		
		Codes codes = currentProperty.buildHouse(currentPlayer, currentBank);
		
		if (codes == Codes.NOT_BUILT)
			return false;
		
		return true;
	}
	
	public boolean BuyHotel(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		Space currentSpace = currentPlayer.GetCurrentSpace();
		
		if (!(currentSpace instanceof Property))
			return false;
		
		Property currentProperty = (Property) currentSpace;
		
		Codes codes = currentProperty.buildHotel(currentPlayer, currentBank);
		
		if (codes == Codes.NOT_BUILT)
			return false;
		
		return true;
	}
	
	public boolean GetOutOfJailWithCard(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		boolean hasCard = currentPlayer.UseJailCard(currentDeck);
		
		Jail jailSpace = currentBoard.GetJail();
		jailSpace.tryToLeaveJail(currentPlayer, null, hasCard);
		
		return !currentPlayer.GetJailedStatus();
	}
	
	public boolean GetOutOfJailWithDice(String playerColor, Vector<Integer> dice) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		
		Jail jailSpace = currentBoard.GetJail();
		jailSpace.tryToLeaveJail(currentPlayer, dice, false);
		
		return !currentPlayer.GetJailedStatus();
	}
	
	public boolean SellProperty(String playerColor, String propertyName) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		List<Buyable> ownedSpaces = currentPlayer.GetOwnedSpaces();
		
		Space foundSpace = null;
		for (Space space : ownedSpaces) 
		{
			if (space.name == propertyName) 
			{
				foundSpace = space;
				break;
			}
		}
		
		if (foundSpace == null)
			return false;
		
		return currentPlayer.SellSpace(foundSpace);
	}
	
	public int GetNPlayers() 
	{
		return currentPlayers.size();
	}
	
	public Set<String> GetPlayerColors()
	{
		return currentPlayers.keySet();
	}
}
