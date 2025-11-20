package Model;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SaveHandler {
	private Map<String, Player> playerList;
	public static final SaveHandler instance = new SaveHandler();
	
	public SaveHandler(Board b) {
		this.playerList = Model.instance.GetPlayersList();
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
		
	}
	
}
