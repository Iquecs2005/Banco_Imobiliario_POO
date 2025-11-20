package Model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HotelTest {
	
	private static int cost;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		cost = 10;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreateHotel() {
		Hotel hotel = new Hotel(cost);
		assertEquals("Hotel cost not set correctly", cost, hotel.GetCost());
	}
	
	@Test
	public void testAddHotel() {
		Hotel hotel = new Hotel(cost);
		hotel.AddBuilding();
		assertEquals("Hotel not added correctly", 1, hotel.GetAmount());
	}

}
