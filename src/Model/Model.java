package Model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import Model.Space.Codes;

public class Model 
{
	//References
	public static final Model instance = new Model();

	private Bank currentBank;
	private Board currentBoard;
	private Dice currentDice;
	private Deck currentDeck;
	private Jail currentJail;
	private SaveHandler saveHandler;
	
	//Data Structures
	private Map<String, Player> currentPlayers;
	private List<String> playerColors;
	private Player currentPlayer;
	private Player debtToPlayer;
	private float debtValue;
	private int currentPlayerIndex;
	
	private Vector<Integer> lastRoll;
	private Buyable lastLandedSpace;
	
	//Events.
	private Event onPlayerPosAltered = new Event();
	private Event onMoneyPlayerAltered = new Event();
	private Event onDiceRoll = new Event();
	private Event onCardDrawn = new Event();
	private Event onBuyablePropertyLand = new Event();
	private Event onBuyableHotelHouse = new Event();
	private Event onBuyableHotel = new Event();
	private Event onBuyableHouse = new Event();
	private Event onCantAffordRent = new Event();
	private Event onBankrupt = new Event();
	private Event onTurnStart = new Event();
	private Event onTurnEnd = new Event();
	private Event onGameEnd = new Event();
	
	//Constructor
	
	private Model() 
	{
		playerColors = new LinkedList<String>();
	}
	
	//Functions
	
	public boolean NewGame(List<String> playerColors, List<String> playerNames) 
	{
		int nPlayers = playerColors.size();
		
		if (nPlayers < 3 || nPlayers > 6)
		{
			return false;
		}
		
		this.playerColors = playerColors;
		
		currentBank = new Bank(200000);
		currentDeck = new Deck();
		currentBoard = new Board(currentBank);
		currentJail = new Jail(currentBoard, currentDeck);
		currentPlayers = new HashMap<String, Player>();
		currentDice = new Dice(6);
		
		currentBoard.CreateSpaces(currentJail, currentDeck);
		Space startSpace = getCurrentBoard().GetStartSpace();
		int i = 0;
		for (String color : playerColors)
		{
			currentPlayers.put(color, new Player(color, playerNames.get(i), 4000.0f, startSpace));
			i++;
		}
		
		currentPlayerIndex = 0;
		String currentColor = this.playerColors.get(currentPlayerIndex);
		currentPlayer = currentPlayers.get(currentColor);
		
		List<Player> playerList = List.copyOf(currentPlayers.values());
		currentDeck.SetVariables(playerList, currentBank, getCurrentBoard());
		this.saveHandler = new SaveHandler();
		
		return true;
	}
	
	public boolean SaveGame(String filepath)
	{
		saveHandler.writeToSaveFile(filepath);
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
	
	public void MoveCurrentPlayer(int amount) 
	{
		MovePlayer(currentPlayer.GetColor(), amount);
	} 
	
	public void MovePlayer(String playerColor, int amount) 
	{
		Player currentPlayer = currentPlayers.get(playerColor);
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
				Buyable lastSpace = (Buyable) currentPlayer.GetCurrentSpace();
				if (currentPlayer.GetOwnedSpaces().size() == 0)
				{
					BankruptPlayer(lastSpace.getOwner());
				}
				else
				{
					debtToPlayer = lastSpace.getOwner();
					debtValue = lastSpace.getRent();
					onCantAffordRent.notifyObservers();					
				}
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
	
	public void RemovePlayer(String playerColor)
	{
		currentPlayers.remove(playerColor);
		playerColors.remove(playerColor);
		
		if (currentPlayers.size() == 1)
		{
			onGameEnd.notifyObservers();
		}
	}
	
	public boolean BuyProperty() 
	{
		boolean status = currentPlayer.BuySpace(currentBank);
		onMoneyPlayerAltered.notifyObservers();
		
		return status;
	}
	
	public boolean BuyHouse() 
	{
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
	
	public boolean BuyHotel() 
	{
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
	
	private void BankruptPlayer(BankBalance receiver) 
	{
		currentPlayer.TransferMoney(receiver, currentPlayer.GetMoney());
		currentPlayer.SetMoney(-1);
		RemovePlayer(currentPlayer.GetColor());
		onBankrupt.notifyObservers();
		currentPlayerIndex--;
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
		
		if (currentPlayer.GetMoney() > debtValue)
			currentPlayer.TransferMoney(debtToPlayer, debtValue);
		else if (currentPlayer.GetOwnedSpaces().size() == 0)
		{
			System.out.println("abc");
			BankruptPlayer(debtToPlayer);
		}
		
		onMoneyPlayerAltered.notifyObservers();

		return status;
	}
	
	public void PassTurn() 
	{
		currentPlayerIndex = (currentPlayerIndex + 1) % playerColors.size();
		String currentColor = this.playerColors.get(currentPlayerIndex);
		currentPlayer = currentPlayers.get(currentColor);
		
		onTurnStart.notifyObservers();
	}
	
	public void EndGame()
	{
	    onPlayerPosAltered.clearObservers();
	    onMoneyPlayerAltered.clearObservers();
	    onCardDrawn.clearObservers();
	    onBuyablePropertyLand.clearObservers();
	    onBuyableHotelHouse.clearObservers();
	    onBuyableHotel.clearObservers();
	    onBuyableHouse.clearObservers();
	    onCantAffordRent.clearObservers();
	    onTurnStart.clearObservers();
	    onTurnEnd.clearObservers();
	    onGameEnd.clearObservers();
	}
	
	public void NotifyEndGame()
	{
		onGameEnd.notifyObservers();
	}
	
	//Getters and Setters
	
	public Vector<Integer> GetLastRoll()
	{
		return this.lastRoll;
	}
	
	public int GetLastRollSum()
	{
		int diceSum = 0;
		for (int diceResult : lastRoll) 
		{
			diceSum += diceResult;
		}
		
		return diceSum;
	}
	
	public void SetLastRoll(int firstDice, int secondDice)
	{
		Vector<Integer> roll = new Vector<>(2);
		roll.add(firstDice);
		roll.add(secondDice);
		this.lastRoll = roll;
		onDiceRoll.notifyObservers();
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
	
	public String getCurrentPlayerColorName()
	{
		return currentPlayer.GetColor();
	}
	
	public void SubscribeToPlayerPos(Observer newObserver) 
	{
		onPlayerPosAltered.addObserver(newObserver);
	}
	
	public void SubscribeToTurnEnd(Observer newObserver)
	{
		onTurnEnd.addObserver(newObserver);
	}
	
	public void SubscribeToTurnStart(Observer newObserver)
	{
		onTurnStart.addObserver(newObserver);  
	}
	
	public void UnsubscribeToPlayerPos(Observer newObserver) 
	{
		onPlayerPosAltered.removeObserver(newObserver);
	}
	
	public void SubscribeToMoneyPlayerAltered(Observer newObserver) 
	{
		onMoneyPlayerAltered.addObserver(newObserver);
	}
	
	public void UnsubscribeToMoneyPlayerAltered(Observer newObserver) 
	{
		onMoneyPlayerAltered.removeObserver(newObserver);
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
	
	public void SubscribeToCantAffordRent(Observer newObserver)
	{
		onCantAffordRent.addObserver(newObserver);
	}
	
	public void SubscribeToGameEnd(Observer newObserver)
	{
		onGameEnd.addObserver(newObserver);
	}
	
	public void SubscribeToOnBankrupt(Observer newObserver)
	{
		onBankrupt.addObserver(newObserver);
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
	
	public float GetLandedSpaceRent()
	{
		return lastLandedSpace.getRent();
	}
	
	public int GetLandedPropertyHouses()
	{
		if (LandedSpaceIsProperty())
		{
			Property property = (Property)lastLandedSpace;
			return property.GetHouse().GetAmount();
		}
		return 0;
	}
	
	public int GetLandedPropertyHotel()
	{
		if (LandedSpaceIsProperty())
		{
			Property property = (Property)lastLandedSpace;
			return property.GetHotel().GetAmount();
		}
		return 0;
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
		this.playerColors = new ArrayList<String>(newPlayers.keySet());
	}
	
	public String GetPlayerColorByIndex(int index)
	{
		return playerColors.get(index);
	}
	
	public int GetPlayerIndex(String color)
	{
		for(int i = 0; i < playerColors.size(); i++) 
		{
			if (playerColors.get(i).equals(color)) 
			{
				return i;
			}
		}
		return -1;
	}
	
	public int GetCurrentPlayerIndex()
	{
		return this.currentPlayerIndex;
	}
	
	public String GetCurrentPlayerColor()
	{
		return currentPlayer.GetColor();
	}
	
	public void SetCurrentPlayerByIndex(int index)
	{
		this.currentPlayerIndex = index;
		String currentColor = this.playerColors.get(currentPlayerIndex);
		currentPlayer = currentPlayers.get(currentColor);
	}
	
	public Player GetPlayerByColor(String Color)
	{
		return this.currentPlayers.get(Color);
	}
	
	public String GetPlayerNameByColor(String color)
	{
		Player p = currentPlayers.get(color);
		return p.GetName();
	}
	
	public Map<String, String> GetPlayerOwnedSpaces(Player p)
	{
		List<Buyable> ownedList = p.GetOwnedSpaces();
		Map<String, String> ownedSpaces = new HashMap<>();
		String type = null;
		for (Buyable b : ownedList)
		{
			if(b instanceof Property)
			{
				type = "Property";
			}
			
			if(b instanceof Company)
			{
				type = "Company";
			}
		ownedSpaces.put(b.getName(), type);
		}
		return ownedSpaces;
	}
	
}
