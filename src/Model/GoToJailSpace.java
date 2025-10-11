package Model;

public class GoToJailSpace extends Space {
    public GoToJailSpace(String name) {
        super(name);
    }
    
    
    public Codes onLand(Player player, Jail jail) {
    	
    	if (player == null || jail == null) {
    		return Codes.NOTHING;
    	}
    	
    	jail.sendToJail(player);
    	
        return Codes.SENT_TO_JAIL;
    }
}