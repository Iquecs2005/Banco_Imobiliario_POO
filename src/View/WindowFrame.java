package View;

import java.awt.*;
import javax.swing.*;

class WindowFrame extends JFrame
{
	public WindowFrame(int startWidth, int startHeight) 
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int x = (screenWidth - startWidth) / 2;
		int y = (screenHeight - startWidth) / 2;
		
		setBounds(x, y, startWidth, startHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
