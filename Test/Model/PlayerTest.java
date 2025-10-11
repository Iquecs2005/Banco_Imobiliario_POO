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
		Player testPlayer = new Player("Red", 4000, null);
		
		assertEquals(testPlayer.GetColor(), "Red");
		assertEquals(testPlayer.GetMoney(), 4000, 0.01);
		assertEquals(testPlayer.GetCurrentSpace(), null);
	}
	
	@Test
	public void AddCardOk() 
	{
		Player testPlayer = new Player("Red", 4000, null);
		Card card = new Card("Test");
		
		assertNull(testPlayer.FindCard(card.GetCardId()));
		assertTrue(testPlayer.AddCard(card));
		assertEquals(card.GetCardId(), testPlayer.FindCard(card.GetCardId()).GetCardId());
	}
	
	@Test
	public void AddCardNull() 
	{
		Player testPlayer = new Player("Red", 4000, null);
		
		assertFalse(testPlayer.AddCard(null));
	}

	@Test
	public void RemoveCardOk() 
	{
		Player testPlayer = new Player("Red", 4000, null);
		Card card = new Card("Test");
		
		assertNull(testPlayer.FindCard(card.GetCardId()));
		assertTrue(testPlayer.AddCard(card));
		assertEquals(card.GetCardId(), testPlayer.FindCard(card.GetCardId()).GetCardId());
		assertTrue(testPlayer.RemoveCard(card));
		assertNull(testPlayer.FindCard(card.GetCardId()));
	}
	
	@Test
	public void RemoveCardNull() 
	{
		Player testPlayer = new Player("Red", 4000, null);
		
		assertFalse(testPlayer.RemoveCard(null));
	}
	
	@Test
	public void RemoveCardNotHeld() 
	{
		Player testPlayer = new Player("Red", 4000, null);
		Card card = new Card("Test");
		
		assertFalse(testPlayer.RemoveCard(card));
	}
	
	@Test
	public void BuySpaceOk()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Player testPlayer1 = new Player("Red", 4000, board.GetSpace(1));
		
		assertTrue(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetOwnedSpaces().get(0), board.GetSpace(1));
		assertEquals(testPlayer1.GetMoney(), 4000 - ((Buyable)board.GetSpace(1)).price, 0.01);
		assertEquals(bank.GetMoney(), 4000 + ((Buyable)board.GetSpace(1)).price, 0.01);
	}
	
	@Test
	public void BuySpaceNonBuyableSpace()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Player testPlayer1 = new Player("Red", 4000, board.GetStartSpace());
		
		assertFalse(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}
	
	@Test
	public void BuySpaceNullSpace()
	{
		Bank bank = new Bank(4000);
		Player testPlayer1 = new Player("Red", 4000, null);
		
		assertFalse(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}
	
	@Test
	public void BuySpaceOwnedSpace()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Player testPlayer1 = new Player("Red", 4000, board.GetSpace(1));
		Player testPlayer2 = new Player("Yellow", 4000, board.GetSpace(1));
		Buyable BuyableTerrain = (Buyable)board.GetSpace(1);
		
		assertTrue(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetMoney(), 4000 - BuyableTerrain.price, 0.01);
		assertFalse(testPlayer2.BuySpace(bank));
		assertEquals(testPlayer2.GetMoney(), 4000, 0.01);
	}
}
