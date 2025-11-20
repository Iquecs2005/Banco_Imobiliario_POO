package View;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

import Model.Event;
import Model.Model;
import Model.Observer;
import Controller.Controller;

public class DiceContainer implements Observer 
{
	public static final DiceContainer instance = new DiceContainer();
	List<DiceUI> diceList = new ArrayList<DiceUI>();
	
	public DiceContainer() {
		Controller.instance.SubscribeToRollDice(this);
	}
	
	public void PaintComponent(Graphics2D g2d, int boardX, int boardSize, BasePanel panel)
	{
	    int spacing = (int)(boardSize * 0.14);  // instead of +60 pixels
	    int startX  = boardX - (int)(boardSize * 0.07); 
		//iterate dicelist and paint.
		for (DiceUI diceUI : diceList)
		{
			diceUI.PaintComponent(g2d, startX, boardSize, panel);
			startX += spacing;
		}
	}
	
	public void update(Event event) 
	{
		diceList.clear();
		Vector<Integer> LastRoll = Model.instance.GetLastRoll();
		for(Integer i : LastRoll) 
		{
			diceList.add(new DiceUI(i));
		}
	}


}
