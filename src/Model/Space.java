package Model;

abstract class Space {
	protected String name;
	
	public enum Codes {
		CAN_BUY,GET_RENT,IS_MINE,CAN_BUILD_HOUSE, CAN_BUILD_HOTEL, CAN_BUILD_BOTH, CANT_BUILD, BUILT,NOT_BUILT,GET_CARD,NOTHING,SENT_TO_JAIL
	}
	
	
	public Space(String name) {
		this.name = name;
	}
	
	public abstract Codes onLand (Player p);
	
}
