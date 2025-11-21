package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest 
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		
	}

	@Before
	public void setUp() throws Exception 
	{
		
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}

	@Test
	public void PlayerConstructorOk() 
	{
		Player testPlayer = new Player("Red", "Player", 4000, null);
		
		assertEquals(testPlayer.GetColor(), "Red");
		assertEquals(testPlayer.GetName(), "Player");
		assertEquals(testPlayer.GetMoney(), 4000, 0.01);
		assertEquals(testPlayer.GetCurrentSpace(), null);
	}
	
	@Test
	public void BuySpaceOk()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Deck deck = new Deck();
		Jail jail = new Jail(board, deck);
		board.CreateSpaces(jail, deck);
		Player testPlayer1 = new Player("Red", "Player", 4000, board.GetSpace(1));
		
		assertTrue(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetOwnedSpaces().get(0), board.GetSpace(1));
		assertEquals(testPlayer1.GetMoney(), 4000 - ((Buyable)board.GetSpace(1)).getPrice(), 0.01);
		assertEquals(bank.GetMoney(), 4000 + ((Buyable)board.GetSpace(1)).getPrice(), 0.01);
	}
	
	@Test
	public void BuySpaceNonBuyableSpace()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Deck deck = new Deck();
		Jail jail = new Jail(board, deck);
		board.CreateSpaces(jail, deck);
		Player testPlayer1 = new Player("Red", "Player", 4000, board.GetStartSpace());
		
		assertFalse(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}
	
	@Test
	public void BuySpaceNullSpace()
	{
		Bank bank = new Bank(4000);
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertFalse(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}
	
	@Test
	public void BuySpaceOwnedSpace()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Deck deck = new Deck();
		Jail jail = new Jail(board, deck);
		board.CreateSpaces(jail, deck);
		Player testPlayer1 = new Player("Red", "Player", 4000, board.GetSpace(1));
		Player testPlayer2 = new Player("Yellow", "Player", 4000, board.GetSpace(1));
		Buyable BuyableTerrain = (Buyable)board.GetSpace(1);
		
		assertTrue(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetMoney(), 4000 - BuyableTerrain.getPrice(), 0.01);
		assertFalse(testPlayer2.BuySpace(bank));
		assertEquals(testPlayer2.GetMoney(), 4000, 0.01);
	}
}
