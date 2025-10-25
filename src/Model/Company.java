package Model;

class Company extends Buyable{
	
	private int multiplier;
	
	public Company(String name, float price, float rent, int multiplier) {
		super(name,price,rent);
		this.multiplier = multiplier;
	}
	
	public Codes onLand(Player p) {
		Codes parentResult = super.onLand(p);
		if (parentResult == Codes.GET_RENT) {
			p.TransferMoney(this.owner, this.rent * multiplier);
		}
		return parentResult;	
	}
}
