package Model;

public class LuckSpace extends Space 
{
	private Deck deck;
	
	LuckSpace(Deck d)
	{
		super("Luck Space");	
		deck = d;
	}
	
	public Codes onLand(Player p) 
	{	
		if (p == null || p.GetCurrentSpace() != this) {
			return Codes.NOTHING;
		}
		
		deck.GetCard(p);
		
		return Codes.GET_CARD;
	}
	
}
