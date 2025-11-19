package Model;

import java.util.LinkedList;
import java.util.List;

class Board 
{
	private Bank bank;
	private List<Space> terrainsList = new LinkedList<Space>();
	private Jail jailSpace;
	
	public Board(Bank bank, Deck deck) 
	{
		//Sets terrainList
		this.bank = bank;
		
		this.jailSpace = new Jail();
		
		
		terrainsList.add(new Space("Ponto de Partida")); // Ponto de Partida (Start)
		terrainsList.add(new Property("Leblon", 100, 6, new House(50, 30), new Hotel(50, 400)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Av Presidente Vargas", 60, 2, new House(50, 10), new Hotel(50, 250)));
		terrainsList.add(new Property("Av Nossa S de Copacabana", 60, 4, new House(50, 20), new Hotel(50, 450)));
		terrainsList.add(new Company("Trem", 200, 0, 25)); // Companhia Ferroviária
		terrainsList.add(new Property("Av Brig Faria Lima", 240, 20, new House(150, 100), new Hotel(150, 1100)));
		terrainsList.add(new Company("Onibus", 150, 0, 40)); // Companhia de Viação
		terrainsList.add(new Property("Av Reboucas", 220, 15, new House(150, 75), new Hotel(150, 900)));
		terrainsList.add(new Property("Av 9 de Julho", 220, 18, new House(150, 90), new Hotel(150, 1050)));
		terrainsList.add(jailSpace); // Prisão
		terrainsList.add(new Property("Av Europa", 200, 16, new House(100, 80), new Hotel(100, 1000)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Rua Augusta", 180, 14, new House(100, 70), new Hotel(100, 950)));
		terrainsList.add(new Property("Av Pacaembu", 180, 14, new House(100, 70), new Hotel(100, 950)));
		terrainsList.add(new Company("Taxi", 150, 0, 30)); // Companhia de Navegação
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Interlagos", 350, 35, new House(200, 175), new Hotel(200, 1500)));
		terrainsList.add(new GiveMoneySpace(200)); // Ganha Dinheiro
		terrainsList.add(new Property("Morumbi", 400, 50, new House(200, 200), new Hotel(200, 2000)));
		terrainsList.add(new Space("Parada Livre")); // Parada Livre
		terrainsList.add(new Property("Flamengo", 120, 8, new House(50, 40), new Hotel(50, 600)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Botafogo", 100, 8, new House(50, 40), new Hotel(50, 600)));
		terrainsList.add(new TakeMoneySpace(200)); // Perde Dinheiro (Income Tax)
		terrainsList.add(new Company("Barco", 200, 0, 50)); // Companhia de Aviação
		terrainsList.add(new Property("Av Brasil", 160, 12, new House(100, 60), new Hotel(100, 900)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Av Paulista", 140, 10, new House(100, 50), new Hotel(100, 750)));
		terrainsList.add(new Property("Jardim Europa", 140, 10, new House(100, 50), new Hotel(100, 750)));
		terrainsList.add(new GoToJailSpace("Goto Jail", jailSpace)); // Vá para a Prisão
		terrainsList.add(new Property("Copacabana", 260, 22, new House(150, 110), new Hotel(150, 1150)));
		terrainsList.add(new Company("Avião", 200, 0, 50)); // Companhia de Táxi Aéreo
		terrainsList.add(new Property("Av Vieira Souto", 320, 25, new House(200, 125), new Hotel(200, 1200)));
		terrainsList.add(new Property("Av Atlantica", 300, 26, new House(200, 130), new Hotel(200, 1275)));
		terrainsList.add(new Company("Helicoptero", 200, 0, 50)); // Companhia de Táxi Aéreo
		terrainsList.add(new Property("Ipanema", 300, 26, new House(200, 130), new Hotel(200, 1275)));
		terrainsList.add(new LuckSpace(deck)); // Sorte
		terrainsList.add(new Property("Jardim Paulista", 280, 24, new House(150, 120), new Hotel(150, 1200)));
		terrainsList.add(new Property("Brooklin", 260, 22, new House(150, 110), new Hotel(150, 1150)));
		
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
	
	Space GetSpace(int i) 
	{
		return terrainsList.get(i % terrainsList.size());
	}
}
