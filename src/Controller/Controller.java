package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFileChooser;

import Model.Model;
import Model.Observer;

public class Controller 
{
	public static final Controller instance = new Controller();

	private List<String> playerColors;
	private int currentPlayerIndex;
	public void CreateNewGame(List<String> playerColors) 
	{
		this.playerColors = playerColors;
		currentPlayerIndex = 0;
		Model.instance.NewGame(playerColors);
	}
	
	public void SaveGame(String path)
	{
		Model.instance.SaveGame(path);
	}
	
	public void LoadGame(String path)
	{
		Model.instance.LoadGame(path);
		this.playerColors = new ArrayList<> (Model.instance.GetPlayerColors());
	}
	
	public Vector<Integer> MovePlayer() 
	{
		Vector<Integer> diceResults = Model.instance.RollDice(2);
		
		int diceSum = 0;
		for (int diceResult : diceResults) 
		{
			diceSum += diceResult;
		}
		
		Model.instance.MovePlayer(playerColors.get(currentPlayerIndex), diceSum);
		
		return diceResults;
	}
	
	public void BuySpace() 
	{
		Model.instance.BuyProperty(playerColors.get(currentPlayerIndex));
	}
	
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
	
	public void EndTurn() 
	{
		currentPlayerIndex = (currentPlayerIndex + 1) % GetPlayerNumber();
	}
}
