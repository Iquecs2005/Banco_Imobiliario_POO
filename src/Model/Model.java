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
	private Jail currentJail;
	
	private Vector<Integer> lastRoll;
	private Color currentColor;
	private Buyable lastLandedSpace;
	private String currentColorName;
	private SaveHandler saveHandler;
	
	//Initialize events.
	private Event onPlayerPosAltered = new Event();
	private Event onMoneyPlayerAltered = new Event();
	private Event onDiceRoll = new Event();
	private Event onCardDrawn = new Event();
	private Event onBuyablePropertyLand = new Event();
	private Event onBuyableHotelHouse = new Event();
	private Event onBuyableHotel = new Event();
	private Event onBuyableHouse = new Event();
	private Event onCantAffordRent = new Event();
	private Event onTurnStart = new Event();
	private Event onTurnEnd = new Event();
	
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
		currentBoard = new Board(currentBank);
		currentJail = new Jail(currentBoard, currentDeck);
		currentPlayers = new HashMap<String, Player>();
		currentDice = new Dice(6);
		
		currentBoard.CreateSpaces(currentJail, currentDeck);
		Space startSpace = getCurrentBoard().GetStartSpace();
		for (String color : playerColors)
		{
			currentPlayers.put(color, new Player(color, 4000.0f, startSpace));
		}
		List<Player> playerList = List.copyOf(currentPlayers.values());
		currentDeck.SetVariables(playerList, currentBank, getCurrentBoard());
		this.saveHandler = new SaveHandler();
		
		return true;
	}
	
	public void LoadGame(String filepath)
	{
		currentBank = new Bank(200000);
		currentDeck = new Deck();
		currentBoard = new Board(currentBank);
		currentJail = new Jail(currentBoard, currentDeck);
		currentPlayers = new HashMap<String, Player>();
		currentDice = new Dice(6);
	
		currentBoard.CreateSpaces(currentJail, currentDeck);
		saveHandler = new SaveHandler();
		
		saveHandler.loadFromSaveFile(filepath);
		
		List<Player> playerList = List.copyOf(currentPlayers.values());
		
		currentDeck.SetVariables(playerList, currentBank, currentBoard);
	}
	
	public boolean SaveGame(String filepath)
	{
		saveHandler.writeToSaveFile(filepath);
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
		
		if (currentPlayer.GetJailedStatus())
			if (currentJail.tryToLeaveJail(currentPlayer, lastRoll) == 0)
				return;
		
		landedSpace = getCurrentBoard().MovePlayer(currentPlayer, amount);
		
		Codes landCode = landedSpace.onLand(currentPlayer);
		
		switch (landCode)
		{
			// Do something depending on the landCode (Ex: Trigger an event, call a method, etc)
			case Codes.GET_CARD:
				onCardDrawn.notifyObservers();
				break;
			case Codes.CAN_BUY:
				lastLandedSpace = (Buyable) currentPlayer.GetCurrentSpace();
				onBuyablePropertyLand.notifyObservers();
				break;
			case Codes.CANT_AFFORD:
				onCantAffordRent.notifyObservers();
				break;
			case Codes.CAN_BUILD_BOTH:
				lastLandedSpace = (Buyable) currentPlayer.GetCurrentSpace();
				onBuyableHotelHouse.notifyObservers();
				break;
			case Codes.CAN_BUILD_HOUSE:
				lastLandedSpace = (Buyable) currentPlayer.GetCurrentSpace();
				onBuyableHouse.notifyObservers();
				break;
			case Codes.CAN_BUILD_HOTEL:
				lastLandedSpace = (Buyable) currentPlayer.GetCurrentSpace();
				onBuyableHotel.notifyObservers();
				break;
			default:
				break;
				
		}
		
		onPlayerPosAltered.notifyObservers();
		onMoneyPlayerAltered.notifyObservers();
		onTurnEnd.notifyObservers();
	} 
	
	public void PassTurn() 
	{
		onTurnStart.notifyObservers();
	}
	
	public String GetPlayerSpaceName(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		return currentPlayer.GetCurrentSpace().name;
	}
	
	public int GetSpaceIndex(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		return getCurrentBoard().GetSpaceIndex(currentPlayer.GetCurrentSpace());
	}
	
	private void DetermineCurrentPlayerColor(String playerColor)
	{
		this.currentColorName = playerColor;
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
	
	public String getCurrentPlayerColorName()
	{
		return this.currentColorName;
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
	
	public void SubscribeToBuyableHotelHouse(Observer newObserver)
	{
		onBuyableHotelHouse.addObserver(newObserver);
	}
	
	public void SubscribeToBuyableHouse(Observer newObserver)
	{
		onBuyableHouse.addObserver(newObserver);
	}
	
	public void SubscribeToBuyableHotel(Observer newObserver)
	{
		onBuyableHotel.addObserver(newObserver);
	}
	
	public boolean BuyProperty(String playerColor) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
		
		boolean status = currentPlayer.BuySpace(currentBank);
		onMoneyPlayerAltered.notifyObservers();
		
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
		
		onMoneyPlayerAltered.notifyObservers();
		
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
		
		onMoneyPlayerAltered.notifyObservers();
		
		return true;
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
		
		boolean status = currentPlayer.SellSpace(currentBank, foundSpace);
		onMoneyPlayerAltered.notifyObservers();

		return status;
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
	
	public Map<String, Player> GetPlayersList()
	{
		return this.currentPlayers;
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
	
	public String GetLandedBuyableName() 
	{
		return lastLandedSpace.name; 
	}

	public Board getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
	}
	
	public Deck getCurrentDeck()
	{
		return this.currentDeck;
	}
	
	public void SetCurrentPlayers(Map<String, Player> newPlayers)
	{
		this.currentPlayers = newPlayers;
	}
}
