package Model;

public class TakeMoneySpace extends Space {
	
	private float value;
	
	public TakeMoneySpace(float value){
		super("TakeMoneySpace");
		this.value = value;
	}
	
	public Codes onLand(Player p, Bank b) {
		
		if (p == null || p.GetCurrentSpace() != this) {
			return Codes.NOTHING;
		}
		
		p.TransferMoney(b, value);
		
		return Codes.TAKE_MONEY;
	}
}
