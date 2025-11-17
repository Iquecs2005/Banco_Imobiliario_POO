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
		int startpos = -30;
		//iterate dicelist and paint.
		for (DiceUI diceUI : diceList)
		{
			diceUI.PaintComponent(g2d, boardX + startpos, boardSize, panel);
			startpos += 60;
		}
	}
	
	public void update(Event event) 
	{
		diceList.clear();
		Vector<Integer> LastRoll = Model.instance.GetLastRoll();
		for(Integer i : LastRoll) 
		{
			diceList.add(new DiceUI("Red", i));
		}
	}


}
