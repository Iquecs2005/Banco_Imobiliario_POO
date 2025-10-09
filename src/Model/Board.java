package Model;

import java.util.LinkedList;
import java.util.List;

class Board 
{
	private List<Space> terrainsList = new LinkedList<Space>();
	
	public Board() 
	{
		//Sets terrainList
		terrainsList.add(new Space());
	}
	
	public Space MovePlayer(Player player, int amount) 
	{
		Space playerCurrentSpace = player.GetCurrentSpace();
		int playerLocation = terrainsList.indexOf(playerCurrentSpace);
		
		if (playerLocation == -1) 
			return null;
		
		int spaceIndex = playerLocation + amount;
		if (spaceIndex > terrainsList.size()) 
		{
			//Bank.Transfer(player, 200)
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
}
