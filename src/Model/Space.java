package Model;

abstract class Space {
	private String name;
	
	public Space(String name) {
		this.name = name;
	}
	
	public abstract boolean onLand (Player p);
	
}
