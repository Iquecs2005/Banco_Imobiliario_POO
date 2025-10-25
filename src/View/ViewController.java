package View;

public class ViewController 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		WindowFrame window = new WindowFrame(500, 500);
		window.setTitle("Banco Imobiliario");
		window.setVisible(true);
		
		InitialPanel frame = new InitialPanel();
		window.getContentPane().add(frame);
	}

}
