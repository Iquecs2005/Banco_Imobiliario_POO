package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest 
{
	private static Player redPlayer;
	private static Player yellowPlayer;
	private static Player greenPlayer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		redPlayer = new Player("Red", 4000, null);
		yellowPlayer = new Player("Yellow", 4000, null);
		greenPlayer = new Player("Green", 4000, null);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{
		redPlayer.SetCurrentSpace(null);
		yellowPlayer.SetCurrentSpace(null);
		greenPlayer.SetCurrentSpace(null);
	}

	@After
	public void tearDown() throws Exception {
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
		
		assertFalse(testPlayer1.TransferMoney(testPlayer2, 1000));
		assertEquals(testPlayer1.GetMoney(), 4000-1000, 0.01);
		assertEquals(testPlayer2.GetMoney(), 4000+1000, 0.01);
	}
	
	@Test
	public void TransferMoneyNullReceiver() 
	{
		Player testPlayer1 = new Player("Red", 4000, null);
		
		assertFalse(testPlayer1.TransferMoney(null, 1000));
	}
}
