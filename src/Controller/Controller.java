package Controller;

import java.util.Set;

import Model.Model;

public class Controller 
{
	public static final Controller instance = new Controller();
	
	public void CreateNewGame(int nPlayers) 
	{
		Model.instance.NewGame(nPlayers);
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
