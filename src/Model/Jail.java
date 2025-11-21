package Model;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class Jail extends Space{
	
    private Map<Player, Integer> prisoners;
    private static final int MAX_TURNS = 4;
    private Board board;
    private Deck deck;
	
    public Jail(Board board, Deck deck) 
    {
    	super("Jail");
    	prisoners = new HashMap<>();
    	this.board = board;
    	this.deck = deck;
    }
    
    public void sendToJail(Player p) 
    {
        prisoners.put(p, 0);
        p.SetInJail(true);
        board.MovePlayerToJail(p);
    }

    private void releasePlayer(Player p) 
    {
        p.SetInJail(false);
        prisoners.remove(p);
    }
    
    
    public int tryToLeaveJail(Player p, Vector<Integer> diceRes) 
    {
        if (!prisoners.containsKey(p)) return -1; // player not in jail

        int turns = prisoners.get(p) + 1;
        prisoners.put(p, turns);
        
        if (p.HasJailedCard())
        {
        	p.UseJailCard(deck);
	        releasePlayer(p);
	        return 1;
        }
        
        boolean hasLeave = (diceRes.get(0).equals(diceRes.get(1))) || turns >= MAX_TURNS;

        // Check leave conditions
        if (hasLeave) 
        {
            releasePlayer(p);
            return 1;
        }
        return 0;
    }

	public Codes onLand(Player p) {
		return Codes.NOTHING;
	}
	
}
