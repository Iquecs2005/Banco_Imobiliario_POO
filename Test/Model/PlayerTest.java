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
	public void TransferMoneyOk() 
	{
		Player testPlayer1 = new Player("Red", 4000, null);
		Player testPlayer2 = new Player("Yellow", 4000, null);
		
		assertEquals(testPlayer1.TransferMoney(testPlayer2, 1000), TransferMoneyResult.Ok);
		assertEquals(testPlayer1.GetMoney(), 4000-1000, 0.01);
		assertEquals(testPlayer2.GetMoney(), 4000+1000, 0.01);
	}
	
	@Test
	public void TransferMoneyNotEnough() 
	{
		Player testPlayer1 = new Player("Red", 4000, null);
		Player testPlayer2 = new Player("Yellow", 4000, null);
		
		assertEquals(testPlayer1.TransferMoney(testPlayer2, 5000), TransferMoneyResult.NotEnoughMoney);
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
		assertEquals(testPlayer2.GetMoney(), 4000, 0.01);
	}
	
	@Test
	public void TransferMoneyNullReceiver() 
	{
		Player testPlayer1 = new Player("Red", 4000, null);
		
		assertEquals(testPlayer1.TransferMoney(null, 1000), TransferMoneyResult.NullReceiver);
	}
	
	@Test
	public void BuySpaceOk()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Player testPlayer1 = new Player("Red", 4000, board.GetSpace(1));
		
		assertTrue(testPlayer1.BuySpace(bank));
		assertEquals(testPlayer1.GetOwnedSpaces().get(0), board.GetSpace(1));
	}
	
	@Test
	public void BuySpaceNonBuyableSpace()
	{
		Bank bank = new Bank(4000);
		Board board = new Board(bank);
		Player testPlayer1 = new Player("Red", 4000, board.GetStartSpace());
		
		assertFalse(testPlayer1.BuySpace(bank));
	}
	
	@Test
	public void BuySpaceSpace()
	{
		Bank bank = new Bank(4000);
		Player testPlayer1 = new Player("Red", 4000, null);
		
		assertFalse(testPlayer1.BuySpace(bank));
	}
}
