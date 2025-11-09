package Model;

public class LuckSpace extends Space {
	LuckSpace(){
		super("Luck Space");	
	}
	
	public Codes onLand(Player p, Deck d) {
		
		if (p == null || d == null || p.GetCurrentSpace() != this) {
			return Codes.NOTHING;
		}
		
		d.GetCard(p);
		
		return Codes.GET_CARD;
	}
	
}
