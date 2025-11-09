package Controller;

import java.util.List;
import java.util.Set;
import java.util.Vector;

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
	
	public Vector<Integer> MovePlayer() 
	{
		Vector<Integer> diceResults = Model.instance.RollDice(2);
		
		int diceSum = 0;
		for (int diceResult : diceResults) 
		{
			diceSum += diceResult;
		}
		
		Model.instance.MovePlayer(playerColors.get(currentPlayerIndex), diceSum);
		PassTurn();
		
		return diceResults;
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
	
	public int GetPlayerSpaceIndex(String playerColor) 
	{
		return Model.instance.GetSpaceIndex(playerColor);
	}
	
	private void PassTurn() 
	{
		currentPlayerIndex = (currentPlayerIndex + 1) % GetPlayerNumber();
	}
}
