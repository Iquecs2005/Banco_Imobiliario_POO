package View;

import javax.swing.*;

import Controller.Controller;
import Model.Event;
import Model.Observer;

class MoneyDisplay implements Observer
{
	final JPanel panel;
	final JLabel colorField;
	final JLabel amountField;
	
	final String color;
	
	public MoneyDisplay(JPanel panel, String color) 
	{
		this.panel = panel;
		this.color = color;
		
		colorField = new JLabel(color + " Player");
		float amount = Controller.instance.GetMoney(color);
		amountField = new JLabel(Float.toString(amount));

		panel.add(colorField);
		panel.add(amountField);
		
		Controller.instance.SubscribeToMoneyAltered(this);
	}
	
	public void DrawDisplay(int x, int y) 
	{
		BaseFrame.PositionComponent(colorField, x - 75, y);
		BaseFrame.PositionComponent(amountField, x, y);
	}
	
	public void update(Event event) 
	{
		float newAmount = Controller.instance.GetMoney(color);
		amountField.setText(Float.toString(newAmount));
	}
}
