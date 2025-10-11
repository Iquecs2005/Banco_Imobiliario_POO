package Model;

public class GoToJailSpace extends Space {
	
	private Jail jail;
	
    public GoToJailSpace(String name, Jail jail) {
        super(name);
        this.jail = jail;
    }
    
    
    public Codes onLand(Player player) {
    	
    	if (player == null || jail == null) {
    		return Codes.NOTHING;
    	}
    	
    	jail.sendToJail(player);
    	
        return Codes.SENT_TO_JAIL;
    }
    
    public Jail getJail() {
    	return jail;
    }
}