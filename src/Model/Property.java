package Model;

class Property extends Buyable {
	
	public Property(String name, int price, int rent) {
		super(name,price,rent);
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}	
	
	public boolean onLand(Player p) {	
		return (p == owner);
	}
	
	public Codes onLand(Player p) {
		if (super.onLand(p) == Codes.IS_MINE) {
			//Offer to build house.
			
			return Codes.BUILT;
		}
		else 
			return Codes.NOTBUILT;
	}
	
	
	
}
