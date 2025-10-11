package Model;

class Property extends Space {
	
	private int price;
	private int rent;
	private Player owner;
	
	public Property(String name, int price, int rent) {
		super(name);
		this.price = price;
		this.rent = rent;
		this.owner = null;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public boolean onLand(Player p) {
		
		
		
	}
	
	
	
}
