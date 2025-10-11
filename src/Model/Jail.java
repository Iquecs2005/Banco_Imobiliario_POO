package Model;
import java.util.Vector;

class Jail extends Space{
	
	
	
	
	public boolean tryToLeaveJail (Player p, Vector<Integer> diceRes, boolean hasLeaveCard) {
		
		if(diceRes.get(0).equals(diceRes.get(1)) || hasLeaveCard) {
			return true;
		}
		
		return false;
	}
	
	public Codes onLand(Player p) {
		return Codes.NOTHING;
	}
	
}
