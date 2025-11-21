package Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import Model.Model;
import Model.Observer;
import View.ViewController;

public class Controller 
{
	public static final Controller instance = new Controller();

	private ViewController vc;
	
	private boolean debugModeActive;
	
	public static void main(String[] args) 
	{
		instance.ActivateView();
	}
	
	private void ActivateView() 
	{
		vc = new ViewController();
		vc.ActivateMainMenu();
	}
	
	public void OnNewGameButton() 
	{
		vc.ActivatePlayerSelect();
	}
	
	public void OnLoadGameButton() 
	{
		String filePath = vc.showFileChooser();
		
		if (filePath == null) 
		{
			return;
        }
		
		Model.instance.LoadGame(filePath);
		vc.ActivateBoard();
	}
	
	public void OnEndGameButton()
	{
		Model.instance.NotifyEndGame();
	}
	
	public void OnEndGame()
	{
		Model.instance.EndGame();
		vc.DestroyCurrentBoard();
		vc.ActivateMainMenu();
	}
	
	public void CreateNewGame(List<String> playerColors, List<String> playerNames) 
	{
		Model.instance.NewGame(playerColors, playerNames);
		vc.ActivateBoard();
	}
	
	public void SaveGame(String path)
	{
		Model.instance.SaveGame(path);
	}
	
	public Vector<Integer> RollDice(int n)
	{
		return Model.instance.RollDice(n);
	}
	
	public Vector<Integer> MovePlayer() 
	{
		//TODO: Create a button to roll dice and a different button to move player in view;
		Vector<Integer> diceResults = Model.instance.RollDice(2);
		
		int diceSum = Model.instance.GetLastRollSum();
		Model.instance.MoveCurrentPlayer(diceSum);
		
		return diceResults;
	}
	
	public void MovePlayer(int amount) 
	{
		int dice1 = amount / 2 + amount % 2;
		int dice2 = amount / 2;
		
		Model.instance.SetLastRoll(dice1, dice2);
		Model.instance.MoveCurrentPlayer(amount);
	}

	public void BuySpace() 
	{
		Model.instance.BuyProperty();
	}
	
	public void BuyHouse() 
	{
		Model.instance.BuyHouse();
	}
	
	public void BuyHotel() 
	{
		Model.instance.BuyHotel();
	}

	public void EndTurn() 
	{
		Model.instance.PassTurn();
	}
	
	//Getters and Setters
	
	public int GetPlayerNumber() 
	{
		return Model.instance.GetNPlayers();
	}
	
	public Set<String> GetPlayerColors()
	{
		return Model.instance.GetPlayerColors();
	}
	
	public float GetMoney(String playerColor) 
	{
		return Model.instance.GetPlayerMoney(playerColor);
	}
	
	public void SubscribeToPlayerPos(Observer newObserver) 
	{
		Model.instance.SubscribeToPlayerPos(newObserver);
	}
	
	public void SubscribeToMoneyAltered(Observer newObserver)
	{
		Model.instance.SubscribeToMoneyPlayerAltered(newObserver);
	}
	
	public void SubscribeToRollDice(Observer newObserver)
	{
		Model.instance.SubscribeToDiceRoll(newObserver);
	}
	
	public void SubscribeToCardDrawn(Observer newObserver)
	{
		Model.instance.SubscribeToCardDrawn(newObserver);
	}
	
	public void SubscribeToBuyablePropertyLand(Observer newObserver)
	{
		Model.instance.SubscribeToBuyablePropertyLand(newObserver);
	}
	
	public void SubscribeToBuyableHotelHouse(Observer newObserver)
	{
		Model.instance.SubscribeToBuyableHotelHouse(newObserver);
	}
	
	public void SubscribeToBuyableHouse(Observer newObserver)
	{
		Model.instance.SubscribeToBuyableHouse(newObserver);
	}
	
	public void SubscribeToBuyableHotel(Observer newObserver)
	{
		Model.instance.SubscribeToBuyableHotel(newObserver);
	}
	
	public void SubscribeToCantAffordRent(Observer newObserver)
	{
		Model.instance.SubscribeToCantAffordRent(newObserver);
	}
	
	public void SubscribeToGameEnd(Observer newObserver)
	{
		Model.instance.SubscribeToGameEnd(newObserver);
	}
	
	public int getLastCardId() 
	{
		return Model.instance.getLastCardId();
	}
	
	public boolean LandedSpaceIsProperty()
	{
		return Model.instance.LandedSpaceIsProperty();
	}
	
	public String GetLandedBuyableName() 
	{
		return Model.instance.GetLandedBuyableName(); 
	}
	
	public int GetPlayerSpaceIndex(String playerColor) 
	{
		return Model.instance.GetSpaceIndex(playerColor);
	}
	
	public void SellProperty(String playerColor, String propertyName)
	{
		Model.instance.SellProperty(playerColor, propertyName);
	}
	
	public String GetCurrentPlayerColor() {
		return Model.instance.GetCurrentPlayerColor();
	}
	
	public String GetPlayerNameByColor(String color)
	{
		return Model.instance.GetPlayerNameByColor(color);
	}
	
	public Map<String, String> GetPlayerOwnedSpaces(String color)
	{
		return Model.instance.GetPlayerOwnedSpaces(Model.instance.GetPlayerByColor(color));
	}
	
	public boolean GetDebugModeActive() 
	{
		return debugModeActive;
	}
	
	public void ToggleDebugMode() 
	{
		debugModeActive = !debugModeActive;
	}
}
