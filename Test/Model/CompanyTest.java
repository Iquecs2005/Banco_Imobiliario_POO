package Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest {
	
	private Company company;
	private Player player1;
	private Player player2;
	
	@Before
	public void setUp() {
		company = new Company("Test Company", 100.0f, 20.0f, 10);
		player1 = new Player("Blue", "Player", 900.0f, company);
		player2 = new Player("Red", "Player", 900.0f, null);
		Model.instance.SetLastRoll(5, 5);
	}
	
	@Test
    public void testConstructor() {
        assertEquals("Test Company", company.name);
        assertEquals(100.0f, company.getPrice(), 0.001f);
        assertEquals(120.0f, company.getRent(), 0.001f);
        assertEquals(10, company.getMultiplier());

        assertNull("Owner should be null initially", company.getOwner());
    }
	
	@Test
	public void testonLand_ownedByOther_getsRent() {
		float initialPlayerMoney = player1.GetMoney();
		float initialOwnerMoney = player2.GetMoney();
		
		company.setOwner(player2);
		Space.Codes result = company.onLand(player1);
		
		float endPlayerMoney = player1.GetMoney();
		float endOwnerMoney = player2.GetMoney();
		
		assertEquals("Should return GET_RENT when player lands on company owned by other player", Space.Codes.GET_RENT, result);
		assertEquals("Player money should update adequately", initialPlayerMoney - company.getRent(), endPlayerMoney, 0.001f);
		assertEquals("Owner money should update adequately", initialOwnerMoney + company.getRent(), endOwnerMoney, 0.001f);
	}
	

}
