package Model;

public class Buyable extends Space {
	
	protected float price;
	protected float rent;
	protected Player owner;
	
	
	public Buyable (String name, float price, float rent) {
		super(name);
		this.price = price;
		this.owner = null;
	}
	
	public Codes onLand (Player p) {
		
		
		if (owner == null) {
			
			return Codes.CAN_BUY;
			
		}
		
		
		else if (owner != null && owner!= p) {
			
			p.TransferMoney(owner, rent);
			
			return Codes.GET_RENT;
			
		}
		
		
		return Codes.IS_MINE;
	}
	
	
	
}
