package Model;


class Property extends Buyable {
	
	House house;
	Hotel hotel;
	private float baseRent = this.getRent();
	
	
	public Property(String name, int price, int rent, House house, Hotel hotel) {
		super(name,price,rent);
		
		this.house = house;
		this.hotel = hotel;
	}
	
	public Codes buildHouse (Player p, Bank b) {
		
		
		if (p.GetMoney() < house.GetCost() || this.getOwner() != p) {
			return Codes.NOT_BUILT;
		}
		
		else {
			
			p.TransferMoney(b, house.GetCost());
		
			house.AddBuilding();
			this.UpdateRent();
			return Codes.BUILT;
		}
		
	}
	
	public Codes buildHotel (Player p, Bank b) {
		
		if(p.GetMoney() < hotel.GetCost() || this.getOwner() != p) {
			
			return Codes.NOT_BUILT;
			
		}
		
		else {
			
			p.TransferMoney(b, hotel.GetCost());
			
			hotel.AddBuilding();
			this.UpdateRent();
			return Codes.BUILT;
			
		}
	}
	
	
	
	public Codes onLand(Player p) {
		
		Codes parentResult = super.onLand(p);
		
		if (parentResult == Codes.GET_RENT) {
			p.TransferMoney(this.getOwner(), this.getRent());
			return parentResult;
		}
		
		if (parentResult == Codes.IS_MINE) {
			
			int houseNum = house.GetAmount();
			
			int hotelNum = hotel.GetAmount();
			
			boolean canBuildHouse = houseNum < 4;
			boolean canBuildHotel = hotelNum < 1 && houseNum >= 1;
			boolean canBuildBoth = canBuildHouse && canBuildHotel;
			
			if (canBuildBoth) {
				return Codes.CAN_BUILD_BOTH;
			}
			else if (canBuildHouse) {
				return Codes.CAN_BUILD_HOUSE;
			}
			else if (canBuildHotel) {
				return Codes.CAN_BUILD_HOTEL;
			}
			else return Codes.CANT_BUILD;
		}
		
		else return parentResult;
	}
	
	public House GetHouse() 
	{
		return this.house;
	}
	
	public Hotel GetHotel()
	{
		return this.hotel;
	}
	
	private void UpdateRent() {
        float hotelValue = this.hotel.GetAmount() * (this.baseRent * 0.3f);
        float houseValue = this.house.GetAmount() * (this.baseRent * 0.15f);
        this.setRent(this.baseRent + hotelValue + houseValue);
    }
	
	
}


