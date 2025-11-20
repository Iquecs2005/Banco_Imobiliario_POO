package Model;

import java.util.List;
import java.awt.Color;
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
	private Vector<Integer> lastRoll;
	private Color currentColor;
	private Buyable lastLandedSpace;
	
	//Initialize events.
	private Event onPlayerPosAltered = new Event();
	private Event onMoneyPlayerAltered = new Event();
	private Event onDiceRoll = new Event();
	private Event onCardDrawn = new Event();
	private Event onBuyablePropertyLand = new Event();
	
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
		currentDeck = new Deck();
		currentBoard = new Board(currentBank, currentDeck);
		currentPlayers = new HashMap<String, Player>();
		currentDice = new Dice(6);
		
		Space startSpace = currentBoard.GetStartSpace();
		for (String color : playerColors)
		{
			currentPlayers.put(color, new Player(color, 4000.0f, startSpace));
		}
		List<Player> playerList = List.copyOf(currentPlayers.values());
		currentDeck.SetVariables(playerList, currentBank, currentBoard);
		
		return true;
	}
		
	public int RollDie()
	{
		return currentDice.roll();
	}
	
	public Vector<Integer> RollDice(int n)
	{
		Vector<Integer> res = currentDice.roll(n);
		this.lastRoll = res;
		onDiceRoll.notifyObservers();
		return res;
	}
	
	public Vector<Integer> GetLastRoll()
	{
		return this.lastRoll;
	}
	
	public void MovePlayer(String playerColor, int amount) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		DetermineCurrentPlayerColor(playerColor);
		Space landedSpace;
		
		landedSpace = currentBoard.MovePlayer(currentPlayer, amount);
		Codes landCode = landedSpace.onLand(currentPlayer);
		
		switch (landCode)
		{
			// Do something depending on the landCode (Ex: Trigger an event, call a method, etc)
			case Codes.GET_CARD:
				onCardDrawn.notifyObservers();
				break;
			case Codes.CAN_BUY:
				onBuyablePropertyLand.notifyObservers();
				break;
			default:
				break;
				
		}
		
		onPlayerPosAltered.notifyObservers();
		onMoneyPlayerAltered.notifyObservers();
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
	
	private void DetermineCurrentPlayerColor(String playerColor)
	{
		switch(playerColor)
		{
			case "Red":
				this.currentColor = Color.red;
				break;
			case "Blue":
				this.currentColor = Color.blue;
				break;
			case "Yellow":
				this.currentColor = Color.yellow;
				break;
			case "Orange":
				this.currentColor = Color.orange;
				break;
			case "Purple":
				this.currentColor = Color.magenta;
				break;
			case "Grey":
				this.currentColor = Color.gray;
				break;
			default:
				break;
		}
	}
	
	public Color getCurrentPlayerColor()
	{
		return this.currentColor;
	}
	
	public void SubscribeToPlayerPos(Observer newObserver) 
	{
		onPlayerPosAltered.addObserver(newObserver);
	}
	
	public void SubscribeToMoneyPlayerAltered(Observer newObserver) 
	{
		onMoneyPlayerAltered.addObserver(newObserver);
	}
	
	public void SubscribeToDiceRoll(Observer newObserver)
	{
		onDiceRoll.addObserver(newObserver);
	}
	
	public void SubscribeToCardDrawn(Observer newObserver)
	{
		onCardDrawn.addObserver(newObserver);
	}
	
	public void SubscribeToBuyablePropertyLand(Observer newObserver)
	{
		onBuyablePropertyLand.addObserver(newObserver);
	}
	
	public boolean BuyProperty(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		
		boolean status = currentPlayer.BuySpace(currentBank);
		
		if (status)
			lastLandedSpace = (Buyable) currentPlayer.GetCurrentSpace();
		
		return status;
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
	
	public float GetPlayerMoney(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		
		return currentPlayer.GetMoney();
	}
	
	public int GetNPlayers() 
	{
		return currentPlayers.size();
	}
	
	public Set<String> GetPlayerColors()
	{
		return currentPlayers.keySet();
	}
	
	public int getLastCardId() 
	{
		return currentDeck.GetLastCardID();
	}
	
	public boolean LandedSpaceIsProperty() 
	{
		return (lastLandedSpace instanceof Property); 
	}
}
