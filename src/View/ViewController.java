package View;

import javax.swing.*;

public class ViewController 
{
	static JButton b1 = new JButton("New Game");
	static JButton b2 = new JButton("Load Game");
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		WindowFrame window = new WindowFrame(500, 500);
		window.setTitle("Banco Imobiliario");
		window.setVisible(true);
	}

}
