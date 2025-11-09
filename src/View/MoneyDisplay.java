package View;

import javax.swing.*;

import Controller.Controller;

class MoneyDisplay 
{
	final JPanel panel;
	final JTextField colorField;
	final JTextField amountField;
	
	public MoneyDisplay(JPanel panel, String color) 
	{
		this.panel = panel;
		colorField = new JTextField(color + "Player");
		float amount = Controller.instance.GetMoney(color);
		amountField = new JTextField(Float.toString(amount));
	}
}
