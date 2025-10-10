package Model;

class Dice {
	
	private int sides;
	
	public Dice (int sides) {
		this.sides = sides;
	}
	
	
	
	
	public int roll () {
		
		return (int)(Math.random()*(this.sides) + 1);
		
	}
	
	public Vector<Integer> roll (int nrolls) {
		
		Vector<Integer> result = new Vector<>();
		
		for (int i = 0; i < nrolls; i++) {
			
			result.add(roll());
			
		}
		
		return result;
	}
	
}
