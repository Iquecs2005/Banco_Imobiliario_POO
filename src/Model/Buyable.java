package Model;

class Buyable extends Space {
	
	private float price;
	private float rent;
	private Player owner;
	
	
	public Buyable (String name, float price, float rent) {
		super(name);
		this.price = price;
		this.rent = rent;
		this.owner = null;
	}
	
	public Codes onLand (Player p) 
	{	
		if(p.GetCurrentSpace() != this) 
		{
			return Codes.NOTHING;
		}

		if (owner == null) 
		{
			return Codes.CAN_BUY;
		}
		else if (owner != null && owner!= p) 
		{
			float rentValue = this.getRent();
			
			if (!p.CanAfford(rentValue))
				return Codes.CANT_AFFORD;
			
			p.TransferMoney(this.getOwner(), this.getRent());
			
			return Codes.GET_RENT;
		}
		
		return Codes.IS_MINE;
	}
	
	public float getPrice() 
	{
		return price;
	}

	public void setPrice(float price) 
	{
		this.price = price;
	}

	public float getRent() 
	{
		return rent;
	}

	public void setRent(float rent) 
	{
		this.rent = rent;
	}
	
	public float getSellPrice() 
	{
		return rent * 0.9f;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
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
