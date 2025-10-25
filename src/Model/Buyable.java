package Model;

class Buyable extends Space {
	
	protected float price;
	protected float rent;
	protected Player owner;
	
	
	public Buyable (String name, float price, float rent) {
		super(name);
		this.price = price;
		this.rent = rent;
		this.owner = null;
	}
	
	public Codes onLand (Player p) {
		
		if(p.GetCurrentSpace() != this) {
			return Codes.NOTHING;
		}
		
		if (owner == null) {
			
			return Codes.CAN_BUY;
			
		}
		
		
		else if (owner != null && owner!= p) {
			return Codes.GET_RENT;
		}
		
		
		return Codes.IS_MINE;
	}
	
	public boolean purchaseBuyable(Player p, Bank b) {
		
		if (p == null || b == null || owner != null) {
			return false;
		}
		
		if(p.CanAfford(price)) {
			p.TransferMoney(b, price);
			this.owner = p;
			return true;
		}
		
		return false;
		
	}
	
	
	
}
