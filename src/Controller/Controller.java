package Controller;

import java.util.List;
import java.util.Set;

import Model.Model;

public class Controller 
{
	public static final Controller instance = new Controller();
	
	public void CreateNewGame(List<String> playerColors) 
	{
		Model.instance.NewGame(playerColors);
	}
	
	public int GetPlayerNumber() 
	{
		return Model.instance.GetNPlayers();
	}
	
	public Set<String> GetPlayerColors()
	{
		return Model.instance.GetPlayerColors();
	}
}
