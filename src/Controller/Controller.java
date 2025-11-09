package Controller;

import java.util.List;
import java.util.Set;

import Model.Model;
import Model.Observer;

public class Controller 
{
	public static final Controller instance = new Controller();
	
	public void CreateNewGame(List<String> playerColors) 
	{
		Model.instance.NewGame(playerColors);
	}
	
	public void MovePlayer(String playerColor) 
	{
		Model.instance.MovePlayer(playerColor, 1);
	}
	
	public int GetPlayerNumber() 
	{
		return Model.instance.GetNPlayers();
	}
	
	public Set<String> GetPlayerColors()
	{
		return Model.instance.GetPlayerColors();
	}
	
	public void SubscribeToPlayerPos(Observer newObserver) 
	{
		Model.instance.SubscribeToPlayerPos(newObserver);
	}
	
	public int GetPlayerSpaceIndex(String playerColor) 
	{
		String spaceName = Model.instance.GetPlayerSpaceName(playerColor);
		return Model.instance.GetSpaceIndex(spaceName);
	}
}
