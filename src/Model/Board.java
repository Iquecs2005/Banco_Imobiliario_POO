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
	}
	
	public void CreateSpaces(Jail jail, Deck deck) 
	{
		this.jailSpace = jail;
		
		terrainsList.add(new Space("Ponto de Partida")); // Ponto de Partida (Start)
		terrainsList.add(new Property("Leblon", 100, 0, new House(50), new Hotel(50)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Av Presidente Vargas", 60, 0, new House(30), new Hotel(30)));
		terrainsList.add(new Property("Av Nossa S de Copacabana", 60, 0, new House(30), new Hotel(30)));
		terrainsList.add(new Company("Trem", 200, 0, 25)); // Companhia Ferroviária
		terrainsList.add(new Property("Av Brig Faria Lima", 240, 20, new House(120), new Hotel(120)));
		terrainsList.add(new Company("Onibus", 150, 0, 40)); // Companhia de Viação
		terrainsList.add(new Property("Av Reboucas", 220, 0, new House(110), new Hotel(110)));
		terrainsList.add(new Property("Av 9 de Julho", 220, 0, new House(110), new Hotel(110)));
		terrainsList.add(jailSpace); // Prisão
		terrainsList.add(new Property("Av Europa", 200, 0, new House(100), new Hotel(100)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Rua Augusta", 180, 0, new House(90), new Hotel(90)));
		terrainsList.add(new Property("Av Pacaembu", 180, 0, new House(90), new Hotel(90)));
		terrainsList.add(new Company("Taxi", 150, 0, 30)); // Companhia de Navegação
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Interlagos", 350, 0, new House(175), new Hotel(175)));
		terrainsList.add(new GiveMoneySpace(200)); // Ganha Dinheiro
		terrainsList.add(new Property("Morumbi", 400, 0, new House(200), new Hotel(200)));
		terrainsList.add(new Space("Parada Livre")); // Parada Livre
		terrainsList.add(new Property("Flamengo", 120, 0, new House(60), new Hotel(60)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Botafogo", 100, 0, new House(50), new Hotel(50)));
		terrainsList.add(new TakeMoneySpace(200)); // Perde Dinheiro (Income Tax)
		terrainsList.add(new Company("Barco", 200, 0, 50)); // Companhia de Aviação
		terrainsList.add(new Property("Av Brasil", 160, 0, new House(80), new Hotel(80)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Av Paulista", 140, 0, new House(70), new Hotel(70)));
		terrainsList.add(new Property("Jardim Europa", 140, 0, new House(70), new Hotel(70)));
		terrainsList.add(new GoToJailSpace("Goto Jail", jailSpace)); // Vá para a Prisão
		terrainsList.add(new Property("Copacabana", 260, 0, new House(130), new Hotel(130)));
		terrainsList.add(new Company("Avião", 200, 0, 50)); // Companhia de Táxi Aéreo
		terrainsList.add(new Property("Av Vieira Souto", 320, 0, new House(160), new Hotel(160)));
		terrainsList.add(new Property("Av Atlantica", 300, 0, new House(150), new Hotel(150)));
		terrainsList.add(new Company("Helicoptero", 200, 0, 50)); // Companhia de Táxi Aéreo
		terrainsList.add(new Property("Ipanema", 300, 0, new House(150), new Hotel(150)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Jardim Paulista", 280, 0, new House(140), new Hotel(140)));
		terrainsList.add(new Property("Brooklin", 260, 0, new House(130), new Hotel(130)));
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
	
	public boolean MovePlayerToJail(Player player) 
	{
		if (player == null)
			return false;
		
		player.SetCurrentSpace(jailSpace);
		return true;
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
	
	public int GetSpaceIndex(Space desiredSpace) 
	{
		int i = 0;
		for (Space space : terrainsList)
		{
			if (space == desiredSpace)
				return i;
			i++;
		}
		return -1;
	}
	
	public Space getSpaceByName(String name)
	{
		for (Space s : this.terrainsList)
		{
			if (s.getName().equals(name)) 
				return s;
		}
		return null;
	}
	
	Space GetSpace(int i) 
	{
		return terrainsList.get(i % terrainsList.size());
	}
}
