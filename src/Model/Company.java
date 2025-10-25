package Model;

class Company extends Buyable{
	
	private int multiplier;
	
	public Company(String name, float price, float rent, int multiplier) {
		super(name,price,rent);
		this.multiplier = multiplier;
	}
	
	public Codes onLand(Player p, int diceSum) {
		Codes parentResult = super.onLand(p);
		if (parentResult == Codes.GET_RENT) {
			p.TransferMoney(this.owner, this.rent + (multiplier * diceSum));
		}
		return parentResult;	
	}
	
	public int getMultiplier(){
		return this.multiplier;
	}
	
	public boolean setMultiplier(int mult) {
		if (mult <= 0) {
			return false;
		}
		this.multiplier = mult;
		return true;
	}
	
}
