package Model;

import java.util.LinkedList;
import java.util.List;

class Board 
{
	private Bank bank;
	private List<Space> terrainsList = new LinkedList<Space>();
	
	public Board(Bank bank) 
	{
		//Sets terrainList
		this.bank = bank;
		
		terrainsList.add(new Property("StartSpace", 100, 10));
		terrainsList.add(new Property("Leblon", 100, 10));
		terrainsList.add(new Property("?", 100, 10));
		terrainsList.add(new Property("Av. Presidente Vargas", 100, 10));
		terrainsList.add(new Property("Av. Nossa Senhora de Copacabana", 100, 10));
		terrainsList.add(new Property("Metro", 100, 10));
	}
	
	public Space MovePlayer(Player player, int amount) 
	{
		if (player == null)
			return null;
		
		Space playerCurrentSpace = player.GetCurrentSpace();
		if (playerCurrentSpace == null)
			return null;
		
		int playerLocation = terrainsList.indexOf(playerCurrentSpace);
		
		if (playerLocation == -1) 
			return null;
		
		int spaceIndex = playerLocation + amount;
		if (spaceIndex >= terrainsList.size()) 
		{
			for (int i = 0; i < spaceIndex / terrainsList.size(); i++) 
			{
				bank.TransferMoney(player, 200);
			}
			spaceIndex = spaceIndex % terrainsList.size();
		}
		Space newSpace = terrainsList.get(spaceIndex);
		
		player.SetCurrentSpace(newSpace);
		
		return newSpace;
	}
	
	public Space GetStartSpace() 
	{
		return terrainsList.get(0);
	}
	
	public int GetBoardSize() 
	{
		return terrainsList.size();
	}
	
	Space GetSpace(int i) 
	{
		return terrainsList.get(i % terrainsList.size());
	}
}
