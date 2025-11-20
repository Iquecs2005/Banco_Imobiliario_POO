package Model;

import java.util.Vector;

class Company extends Buyable{
	
	private int multiplier;
	
	public Company(String name, float price, float rent, int multiplier) 
	{
		super(name,price,rent);
		this.multiplier = multiplier;
	}
	
	public float getRent() 
	{
		float baseRent = super.getRent();
		
		Vector<Integer> values = Model.instance.GetLastRoll();
		
		int diceSum = 0;
		for (int value : values)
			diceSum += value;
		
		return baseRent + (multiplier * diceSum);
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
