package View;

import javax.swing.*;

import Controller.Controller;

class MoneyDisplay 
{
	final JPanel panel;
	final JLabel colorField;
	final JLabel amountField;
	
	public MoneyDisplay(JPanel panel, String color) 
	{
		this.panel = panel;
		
		colorField = new JLabel(color + " Player");
		float amount = Controller.instance.GetMoney(color);
		amountField = new JLabel(Float.toString(amount));

		panel.add(colorField);
		panel.add(amountField);
	}
	
	public void DrawDisplay(int x, int y) 
	{
		BaseFrame.PositionComponent(colorField, x - 75, y);
		BaseFrame.PositionComponent(amountField, x, y);
	}
}
