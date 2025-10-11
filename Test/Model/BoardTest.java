package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoardTest 
{
	private static Bank bank;
	private static Board board;
	private static Player redPlayer;
	private static Player yellowPlayer;
	private static Player greenPlayer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		bank = new Bank(200000);
		board = new Board(bank);
		redPlayer = new Player("Red", 4000, board.GetStartSpace());
		yellowPlayer = new Player("Yellow", 4000, board.GetStartSpace());
		greenPlayer = new Player("Green", 4000, board.GetStartSpace());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
		redPlayer.SetCurrentSpace(board.GetStartSpace());
		yellowPlayer.SetCurrentSpace(board.GetStartSpace());
		greenPlayer.SetCurrentSpace(board.GetStartSpace());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void GetStartSpaceOk() 
	{
		Space startSpace = board.GetStartSpace();
		assertEquals("StartSpace", startSpace.name);
	}
	
	@Test
	public void GetSpaceOk() 
	{
		Space getSpace;
		
		getSpace = board.GetSpace(0);
		assertEquals("StartSpace", getSpace.name);
		getSpace = board.GetSpace(1);
		assertEquals("Leblon", getSpace.name);
		getSpace = board.GetSpace(2);
		assertEquals("?", getSpace.name);
	}
	
	@Test
	public void CorrectSizeTest() 
	{
		int boardSize = board.GetBoardSize();
		
		Space landedSpace = board.GetSpace(boardSize);
		assertEquals(board.GetStartSpace().name, landedSpace.name);
	}
	
	//Teste 2) Deslocar piões de acordo com o jogador da vez e com os valores obtidos no
	//lançamento dos dados
	@Test
	public void CorrectMovementTest() 
	{
		Space landedSpace = board.MovePlayer(redPlayer, 1);
		assertEquals("Leblon", landedSpace.name);
		assertEquals("Leblon", redPlayer.GetCurrentSpace().name);
		
		landedSpace = board.MovePlayer(yellowPlayer, 2);
		assertEquals("?", landedSpace.name);
		assertEquals("?", yellowPlayer.GetCurrentSpace().name);
		
		landedSpace = board.MovePlayer(greenPlayer, 2);
		assertEquals("?", landedSpace.name);
		assertEquals("?", yellowPlayer.GetCurrentSpace().name);
	}
	
	@Test
	public void AroundTheBoardMovementTest() 
	{
		int boardSize = board.GetBoardSize();
		float originalMoney;
		Space landedSpace;
		
		originalMoney = redPlayer.GetMoney();
		landedSpace = board.MovePlayer(redPlayer, boardSize);
		assertEquals("StartSpace", landedSpace.name);
		assertEquals("StartSpace", redPlayer.GetCurrentSpace().name);
		assertEquals(originalMoney + 200, redPlayer.GetMoney(), 0.01);
		
		originalMoney = yellowPlayer.GetMoney();
		landedSpace = board.MovePlayer(yellowPlayer, boardSize * 2 + 1);
		assertEquals("Leblon", landedSpace.name);
		assertEquals("Leblon", yellowPlayer.GetCurrentSpace().name);
		assertEquals(originalMoney + 200 * 2, yellowPlayer.GetMoney(), 0.01);
		
		originalMoney = greenPlayer.GetMoney();
		landedSpace = board.MovePlayer(greenPlayer, boardSize * 3 + 2);
		assertEquals("?", landedSpace.name);
		assertEquals("?", greenPlayer.GetCurrentSpace().name);
		assertEquals(originalMoney + 200 * 3, greenPlayer.GetMoney(), 0.01);
	}
	
	@Test
	public void PlayerNullPositionMovementTest() 
	{
		Player playerNullPosition = new Player("Yellow", 4000, null);
		Space landedSpace = board.MovePlayer(playerNullPosition, 1);
		assertNull(landedSpace);
	}

	@Test
	public void PlayerNotOnBoardMovementTest() 
	{
		House house = new House(10, 10);
		Hotel hotel = new Hotel(10, 10);
		Player notOnBoardPlayer = new Player("Yellow", 4000, new Property("X", 10, 10, house, hotel));
		Space landedSpace = board.MovePlayer(notOnBoardPlayer, 1);
		assertNull(landedSpace);
	}
	
	@Test
	public void NullPlayerMovementTest() 
	{
		Space landedSpace = board.MovePlayer(null, 1);
		assertNull(landedSpace);
	}
}
