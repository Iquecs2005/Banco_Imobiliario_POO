package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Controller.Controller;

public class SaveHandler {

	private Board board;
	private Deck deck;
	
	
	public SaveHandler() {
		this.board = Model.instance.getCurrentBoard();
		this.deck = Model.instance.getCurrentDeck();
	}
	
	public void writeToSaveFile(String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {
        	Map<String, Player> playerList = Model.instance.GetPlayersList();
        	
            for (Player p : playerList.values()) {

                writer.write("PLAYER " + p.GetColor() + "\n");
                writer.write("NAME " + p.GetName() + "\n");

                writer.write("BALANCE " + p.GetMoney() + "\n");
                writer.write("IN_JAIL " + p.GetJailedStatus() + "\n");
                writer.write("HAS_JAIL_CARD " + p.HasJailedCard() + "\n");
                writer.write("CURRENT_SPACE " + p.GetCurrentSpace().getName() + "\n");

                writer.write("OWNED_SPACES:\n");

                List<Buyable> ownedSpaces = p.GetOwnedSpaces();
                for (Buyable b : ownedSpaces) {

                    if (b instanceof Property) {
                        Property prop = (Property) b;
                        writer.write("    " + prop.name + ":" + "HOUSES:" + 
                        prop.GetHouse().GetAmount() + ":" + "HOTELS:" + prop.GetHotel().GetAmount() + "\n");
                    } else {
                        writer.write("    " + b.name + ":TYPE(Company)\n");
                    }
                }
                
                writer.write("END_PLAYER\n");
                writer.write("\n");
            }
            writer.write("CURRENT TURN:" + Model.instance.getCurrentPlayerColorName() +"\n");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void loadFromSaveFile(String filePath) {

	    Map<String, Player> loadedPlayers = new HashMap<>();
	    String currTurnColor = null;
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

	        String line;
	        Player currentPlayer = null;
	        String currentColor = null;

	        while ((line = reader.readLine()) != null) {

	            if (line.startsWith("PLAYER ")) {
	                currentColor = line.substring(7).trim();
	                Space start = board.GetStartSpace();
	                currentPlayer = new Player(currentColor,"tempname", 0f, start);
	                loadedPlayers.put(currentColor, currentPlayer);
	                continue;
	            }
	            
	            if(line.startsWith("NAME ")) {
	            	String currName = line.substring(5).trim();
	            	currentPlayer.SetName(currName);
	            	continue;
	            }

	            if (line.startsWith("BALANCE ")) {
	                currentPlayer.SetMoney(Float.parseFloat(line.substring(8).trim()));
	                continue;
	            }

	            if (line.startsWith("IN_JAIL ")) {
	                currentPlayer.SetInJail(Boolean.parseBoolean(line.substring(8).trim()));
	                continue;
	            }

	            if (line.startsWith("HAS_JAIL_CARD ")) {
	            	
	            	boolean hasCard = Boolean.parseBoolean(line.substring(14).trim());
	            	if(hasCard)
	            	{
	            		currentPlayer.AddJailCard(this.deck.GetJailCard());
	            	}
	                continue;
	            }

	            if (line.startsWith("CURRENT_SPACE ")) {
	                Space space = board.getSpaceByName(line.substring(14).trim());
	                currentPlayer.SetCurrentSpace(space);
	                continue;
	            }

	            if (line.equals("OWNED_SPACES:")) {
	                continue;
	            }

	            if (line.startsWith("END_PLAYER")) {
	                currentPlayer = null;
	                continue;
	            }
	            
	            if (line.startsWith("CURRENT TURN:")) {
	            	currTurnColor = line.substring(13).trim();
	            	continue;
	            }

	            // owned space line:
	            if (line.startsWith("    ")) {
	                parseOwnedSpace(line.trim(), currentPlayer, board);
	                
	            }
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Assign players back to model
	    
	    Model.instance.SetCurrentPlayers(loadedPlayers);
    	int index = Model.instance.GetPlayerIndex(currTurnColor);
    	Model.instance.SetCurrentPlayerByIndex(index);
	}
	
	private void parseOwnedSpace(String line, Player player, Board board) {

	    // Example:
	    // Av Paulista TYPE(Property) HOUSES 2 HOTEL 1

	    String[] parts = line.split(":");
	    String spaceName = parts[0].trim();

	    Space s = board.getSpaceByName(spaceName);
	    if (!(s instanceof Buyable)) return;
	    Buyable b = (Buyable) s;

	    // Set owner in the existing board space
	    b.setOwner(player);
	    player.AddOwnedSpace(b);

	    if (b instanceof Property) {
	        Property prop = (Property) b;

	        for (int i = 0; i < parts.length; i++) {
	            if (parts[i].equals("HOUSES")) {
	                prop.GetHouse().SetAmount(Integer.parseInt(parts[i + 1]));
	            }
	            if (parts[i].equals("HOTEL")) {
	                prop.GetHotel().SetAmount(Integer.parseInt(parts[i + 1]));
	            }
	        }
	    }
	}

	
}
