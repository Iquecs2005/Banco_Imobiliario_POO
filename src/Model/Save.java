package Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Save {
	private Map<String, Player> playerList;
	private Board board;
	public Save(Board b) {
		this.board = b;
		
	}
	
	public void writeToSaveFile() {
		
		
		
		for (Player p : playerList.values()) {
			float bal = p.GetMoney();
			boolean inJail = p.GetJailedStatus();
			boolean hasJailCard = p.HasJailedCard();
			Space currSpace = p.GetCurrentSpace();
			Color currTurn = Model.instance.getCurrentPlayerColor();
			
			
			List<Buyable> ownedSpaces = p.GetOwnedSpaces();
			for(Buyable b : ownedSpaces)
			{
				String name = b.name;
				if (b instanceof Property)
				{
					Property prop = (Property) b;
					prop.GetHouse().GetAmount();
					prop.GetHotel().GetAmount();
				}
			}
			
			
		}
		
		return;
	}
}
