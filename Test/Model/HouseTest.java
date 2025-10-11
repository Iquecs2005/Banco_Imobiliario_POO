package Model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HouseTest {
	
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
	public void testCreateHouse() {
		House house = new House(cost, rent);
		assertEquals("House cost not set correctly", cost, house.GetCost());
		assertEquals("House rent not set correctly", rent, house.GetRent());
	}
	
	@Test
	public void testAddHouse() {
		House house = new House(cost, rent);
		house.AddBuilding();
		assertEquals("House not added correctly", 1, house.GetAmount());
	}

}
