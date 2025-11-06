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
	
	//Initialize events.
	
	private Model() 
	{
		
	}
	
	public boolean NewGame(int nPlayers) 
	{
		if (nPlayers < 3 || nPlayers > 6) 
		{
			return false;
		}
		
		currentBank = new Bank(200000);
		currentBoard = new Board(currentBank);
		currentPlayers = new HashMap<String, Player>();
		currentDice = new Dice(6);
		
		String[] possibleColors = {"Red", "Green", "Blue", "Yellow", "Purple", "Pink"};
		
		String currentColor;
		Space startSpace = currentBoard.GetStartSpace();
		for (int i = 0; i < nPlayers; i++) 
		{
			currentColor = possibleColors[i];
			currentPlayers.put(currentColor, new Player(currentColor, 4000.0f, startSpace));
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
				return;
				
		}
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
		Card jailCard = currentPlayer.FindCard("GetOutOfJail");
		
		if (jailCard == null)
			return false;
		
		Jail jailSpace = currentBoard.GetJail();
		jailSpace.tryToLeaveJail(currentPlayer, null, true);
		currentPlayer.RemoveCard(jailCard);
		
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
