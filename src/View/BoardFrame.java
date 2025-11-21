package View;

public class BoardFrame extends BaseFrame 
{
	private BoardPanel panel;
	
	public BoardFrame(int width,int height, int nPlayers) {
		super("Tabuleiro", width, height);
		
		panel = new BoardPanel(frameWidth,frameHeight);
		getContentPane().add(panel);
		panel.AddPlayer(nPlayers);
	}
	
}
