package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankBalanceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TransferMoneyOk() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		Player testPlayer2 = new Player("Yellow", "Player", 4000, null);
		Bank bank = new Bank(3000);
		
		assertEquals(testPlayer1.TransferMoney(testPlayer2, 1000), TransferMoneyResult.Ok);
		assertEquals(testPlayer1.GetMoney(), 4000-1000, 0.01);
		assertEquals(testPlayer2.GetMoney(), 4000+1000, 0.01);
				
		assertEquals(testPlayer2.TransferMoney(bank, 1000), TransferMoneyResult.Ok);
		assertEquals(bank.GetMoney(), 4000, 0.01);
		assertEquals(testPlayer2.GetMoney(), 4000, 0.01);
		
		assertEquals(bank.TransferMoney(testPlayer2, 1000), TransferMoneyResult.Ok);
		assertEquals(bank.GetMoney(), 3000, 0.01);
		assertEquals(testPlayer2.GetMoney(), 5000, 0.01);
	}
	
	@Test
	public void TransferMoneyNotEnough() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		Player testPlayer2 = new Player("Yellow", "Player", 4000, null);
		Bank bank = new Bank(3000);
		
		assertEquals(testPlayer1.TransferMoney(testPlayer2, 5000), TransferMoneyResult.Ok);
		assertEquals(testPlayer1.GetMoney(), 0, 0.01);
		assertEquals(testPlayer2.GetMoney(), 8000, 0.01);
		
		assertEquals(bank.TransferMoney(testPlayer2, 5000), TransferMoneyResult.Ok);
		assertEquals(bank.GetMoney(), 0, 0.01);
		assertEquals(testPlayer2.GetMoney(), 11000, 0.01);
	}
	
	@Test
	public void TransferMoneyNullReceiver() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertEquals(testPlayer1.TransferMoney(null, 1000), TransferMoneyResult.NullReceiver);
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}
	
	@Test
	public void TransferMoneySenderReceiverEquals() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertEquals(testPlayer1.TransferMoney(testPlayer1, 1000), TransferMoneyResult.SenderReceiverEquals);
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}
	
	@Test
	public void TransferMoneyNegativeAmounts() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertEquals(testPlayer1.TransferMoney(testPlayer1, -1000), TransferMoneyResult.SenderReceiverEquals);
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}

	@Test
	public void IsBankruptFalse() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertFalse(testPlayer1.IsBankrupt());
	}
	
	// Teste 7) Pagar aluguel (item 5), falir e sair do jogo.
	@Test
	public void IsBankruptTrue() 
	{
		Player testPlayer1 = new Player("Red", "Player", -5, null);
		
		assertTrue(testPlayer1.IsBankrupt());
	}	
	
	@Test
	public void CanAffordTrue() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertTrue(testPlayer1.CanAfford(4000));
	}	
	
	@Test
	public void CanAffordFalse() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertFalse(testPlayer1.CanAfford(4001));
	}	
	
	@Test
	public void GetMoneyOk() 
	{
		Player testPlayer1 = new Player("Red", "Player", 4000, null);
		
		assertEquals(testPlayer1.GetMoney(), 4000, 0.01);
	}	
}
