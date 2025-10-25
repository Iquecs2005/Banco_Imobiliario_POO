package Model;

import java.util.LinkedList;
import java.util.List;

public class Event {
	private List<Observer> observerList = new LinkedList<Observer>();
	
	public boolean addObserver(Observer observer) {
		if (observer == null || observerList.contains(observer)) {
			return false;
		}
		
		else 
			observerList.add(observer);
		
		return true;
	}
	
	public boolean removeObserver(Observer observer) {
		if (observer == null || !(observerList.contains(observer))) {
			return false;
		}
		else
			observerList.remove(observer);
		return true;
	}
	
	public void notifyObservers() {
		for(Observer o : observerList) {
			o.update(this);
		}
	}
	
	
}
