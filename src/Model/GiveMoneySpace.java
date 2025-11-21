package Model;

public class GiveMoneySpace extends Space {
	
	private float value;
	
	public GiveMoneySpace(float value){
		super("GiveMoneySpace");
		this.value = value;
	}
	
	public Codes onLand(Player p, Bank b) {
		
		if (p == null || p.GetCurrentSpace() != this) {
			return Codes.NOTHING;
		}
		
		b.TransferMoney(p, value);
		
		return Codes.GIVE_MONEY;
	}
}