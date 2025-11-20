package Model;

class Space {
	protected String name;
	
	public enum Codes 
	{
		CAN_BUY, GET_RENT, IS_MINE, CAN_BUILD_HOUSE, CAN_BUILD_HOTEL, CANT_AFFORD,
		CAN_BUILD_BOTH, CANT_BUILD, BUILT, NOT_BUILT, GET_CARD, NOTHING, SENT_TO_JAIL, TAKE_MONEY, GIVE_MONEY
	}
	
	public Space(String name) 
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Codes onLand (Player p) 
	{
		return Codes.NOTHING;
	}
}
