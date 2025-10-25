package View;

import java.awt.*;
import javax.swing.*;

class WindowFrame extends JFrame
{
	JPanel panel;
	
	static JButton b1 = new JButton("New Game");
	static JButton b2 = new JButton("Load Game");
	
	public WindowFrame(int startWidth, int startHeight) 
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int x = screenWidth / 2 - startWidth / 2;
		int y = screenHeight / 2 - startHeight / 2;
		
		setBounds(x, y, startWidth, startHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new InitialPanel(startWidth, startHeight);
		getContentPane().add(panel);
		
		PositionButton(b1, startWidth / 2 - 5, startHeight / 2 - 25);
		panel.add(b1);
		PositionButton(b2, startWidth / 2 - 5, startHeight / 2 + 25);
		panel.add(b2);
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponents(g);
		
		Dimension panelSize = panel.getSize();
		
		g.drawString("Banco Imobiliario", 200, 200);
	}
	
	private void PositionButton(JButton button, int x, int y) 
	{
		Dimension buttonSize = button.getPreferredSize();
		
		int buttonX;
		int buttonY;
		int sizeX;
		int sizeY;
		
		sizeX = buttonSize.width;
		sizeY = buttonSize.height;
		buttonX = sizeX / 2;
		buttonY = sizeY / 2;
		
		button.setFocusable(false);
		button.setBounds(new Rectangle(x - buttonX, y - buttonY, sizeX, sizeY));
	}
}
