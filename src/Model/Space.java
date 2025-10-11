package Model;

abstract class Space {
	protected String name;
	
	public enum Codes {
		CAN_BUY,GET_RENT,IS_MINE,BUILT,NOTBUILT,GET_CARD
	}
	
	
	public Space(String name) {
		this.name = name;
	}
	
	public abstract Codes onLand (Player p);
	
}
