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
	final String name;
	
	public MoneyDisplay(JPanel panel, String color) 
	{
		this.panel = panel;
		this.color = color;
		this.name = Controller.instance.GetPlayerNameByColor(color);
		
		colorField = new JLabel(name + " | " + color + " Player");
		float amount = Controller.instance.GetMoney(color);
		amountField = new JLabel(Float.toString(amount));

		panel.add(colorField);
		panel.add(amountField);
		
		Controller.instance.SubscribeToMoneyAltered(this);
	}
	
	public void Unsubscribe() 
	{
		Controller.instance.UnsubscribeToMoneyAltered(this);
		amountField.setText(Float.toString(-1));
	}
	
	public void DrawDisplay(int x, int y) 
	{
		BaseFrame.PositionComponent(colorField, x - (75 + name.length() * 5) , y);
		BaseFrame.PositionComponent(amountField, x, y);
	}
	
	public void update(Event event) 
	{
		float newAmount = Controller.instance.GetMoney(color);
		amountField.setText(Float.toString(newAmount));
	}
	
	public String GetColor() 
	{
		return color;
	}
}
