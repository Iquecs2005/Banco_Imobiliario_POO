package Model;

import java.util.LinkedList;
import java.util.List;

class Property extends Space {
	
	private int price;
	private int rent;
	private int baseRent;
	private Player owner;
	private Hotel hotel;
	private House house;
	
	public Property(String name, int price, int rent, Hotel hotel, House house) {
		super(name);
		this.price = price;
		this.rent = rent;
		this.baseRent = rent;
		this.owner = null;
		this.hotel = hotel;
		this.house = house;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}	
	
	public boolean onLand(Player p) {	
		return (p == owner);
	}
	
	private void UpdateRent()
	{
		int hotelValue = this.hotel.GetAmount() * this.hotel.GetRent();
		int houseValue = this.house.GetAmount() * this.house.GetRent();
		this.rent = this.baseRent + hotelValue + houseValue;
	}
	
	
	
}
