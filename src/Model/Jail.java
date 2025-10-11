package Model;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class Jail extends Space{
	
    private Map<Player, Integer> prisoners;
    private static final int MAX_TURNS = 4;
	
    public Jail() {
    	super("Jail");
    	prisoners = new HashMap<>();
    }
    
    public void sendToJail(Player p) {
        prisoners.put(p, 0);
        p.SetInJail(true);
    }

    private void releasePlayer(Player p) {
        p.SetInJail(false);
        prisoners.remove(p);
    }
    
    
    public void tryToLeaveJail(Player p, Vector<Integer> diceRes, boolean hasLeaveCard) {
        if (!prisoners.containsKey(p)) return; // player not in jail

        int turns = prisoners.get(p) + 1;
        prisoners.put(p, turns);
        
        boolean hasLeave = hasLeaveCard || (diceRes.get(0).equals(diceRes.get(1))) || turns >= MAX_TURNS;

        // Check leave conditions
        if (hasLeave) {
            releasePlayer(p);
        }
    }

	public Codes onLand(Player p) {
		return Codes.NOTHING;
	}
	
}
