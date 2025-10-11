package Model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HotelTest {
	
	private static int cost;
	private static int rent;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		cost = 10;
		rent = 5;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCreateHotel() {
		Hotel hotel = new Hotel(cost, rent);
		assertEquals("Hotel cost not set correctly", cost, hotel.GetCost());
		assertEquals("Hotel rent not set correctly", rent, hotel.GetRent());
	}
	
	@Test
	public void testAddHotel() {
		Hotel hotel = new Hotel(cost, rent);
		hotel.AddBuilding();
		assertEquals("Hotel not added correctly", 1, hotel.GetAmount());
	}

}
