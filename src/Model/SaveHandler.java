package Model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveHandler {
	private Map<String, Player> playerList;
	private Deck deck;
	
	
	public SaveHandler(Board b) {
		this.playerList = Model.instance.GetPlayersList();
		this.deck = Model.instance.getCurrentDeck();
	}
	
	public void writeToSaveFile(String filePath) {

        try (FileWriter writer = new FileWriter(filePath)) {

            for (Player p : playerList.values()) {

                writer.write("PLAYER " + p.GetColor() + "\n");

                writer.write("BALANCE " + p.GetMoney() + "\n");
                writer.write("IN_JAIL " + p.GetJailedStatus() + "\n");
                writer.write("HAS_JAIL_CARD " + p.HasJailedCard() + "\n");
                writer.write("CURRENT_SPACE " + p.GetCurrentSpace().getName() + "\n");

                writer.write("OWNED_SPACES:\n");

                List<Buyable> ownedSpaces = p.GetOwnedSpaces();
                for (Buyable b : ownedSpaces) {

                    if (b instanceof Property) {
                        Property prop = (Property) b;
                        writer.write("    " + prop.name +
                                " TYPE(Property) HOUSES " + prop.GetHouse().GetAmount() +
                                " HOTEL " + prop.GetHotel().GetAmount() + "\n");
                    } else {
                        writer.write("    " + b.name + " TYPE(Company)\n");
                    }
                }

                writer.write("END_PLAYER\n");
                writer.write("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void loadFromSaveFile(String filePath) {

	    Map<String, Player> loadedPlayers = new HashMap<>();
	    Board board = Model.instance.getCurrentBoard();  // IMPORTANT – board is already initialized

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

	        String line;
	        Player currentPlayer = null;
	        String currentColor = null;

	        while ((line = reader.readLine()) != null) {

	            if (line.startsWith("PLAYER ")) {
	                currentColor = line.substring(7).trim();
	                Space start = board.GetStartSpace();
	                currentPlayer = new Player(currentColor, 0f, start); // Money updated later
	                loadedPlayers.put(currentColor, currentPlayer);
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
	                Space space = findSpaceByName(board, line.substring(14).trim());
	                currentPlayer.SetCurrentSpace(space);
	                continue;
	            }

	            if (line.equals("OWNED_SPACES:")) {
	                continue; // next lines contain them
	            }

	            if (line.startsWith("END_PLAYER")) {
	                currentPlayer = null;
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
	    Model.instance.currentPlayers = loadedPlayers;
	}

	
}
