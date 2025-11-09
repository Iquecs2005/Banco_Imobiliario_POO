package Model;

import java.util.LinkedList;
import java.util.List;

class Board 
{
	private Bank bank;
	private List<Space> terrainsList = new LinkedList<Space>();
	private Jail jailSpace;
	
	public Board(Bank bank) 
	{
		//Sets terrainList
		this.bank = bank;
		House house = new House(10, 10);
		Hotel hotel = new Hotel(10, 10);
		jailSpace = new Jail();
		
		terrainsList.add(new Space("StartSpace"));
		terrainsList.add(new Property("Leblon", 100, 10, house, hotel));
		terrainsList.add(new Property("?", 100, 10, house, hotel));
		terrainsList.add(new Property("Av. Presidente Vargas", 100, 10, house, hotel));
		terrainsList.add(new Property("Av. Nossa Senhora de Copacabana", 100, 10, house, hotel));
		terrainsList.add(new Buyable("Metro", 100, 10));
		
		terrainsList.add(jailSpace);
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
	
	public Jail GetJail() 
	{
		return jailSpace;
	}
	
	public int GetSpaceIndex(String spaceName) 
	{
		int i = 0;
		for (Space space : terrainsList)
		{
			if (space.name == spaceName)
				return i;
			i++;
		}
		return -1;
	}
	
	Space GetSpace(int i) 
	{
		return terrainsList.get(i % terrainsList.size());
	}
}
