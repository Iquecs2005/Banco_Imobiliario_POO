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
	private static Player redPlayer;
	private static Player yellowPlayer;
	private static Player greenPlayer;
	private static Board board;
	
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
		
		Space landedSpace = board.MovePlayer(redPlayer, boardSize);
		assertEquals("StartSpace", landedSpace.name);
		assertEquals("StartSpace", redPlayer.GetCurrentSpace().name);
		
		//landedSpace = board.MovePlayer(yellowPlayer, 2);
		//assertEquals("?", landedSpace.name);
		//assertEquals("?", yellowPlayer.GetCurrentSpace().name);
		
		//landedSpace = board.MovePlayer(greenPlayer, 2);
		//assertEquals("?", landedSpace.name);
		//assertEquals("?", yellowPlayer.GetCurrentSpace().name);
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
		Player notOnBoardPlayer = new Player("Yellow", 4000, new Property("X", 10, 10));
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
