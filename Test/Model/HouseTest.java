package Model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HouseTest {
	
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
	public void testCreateHouse() {
		House house = new House(cost);
		assertEquals("House cost not set correctly", cost, house.GetCost());
	}
	
	@Test
	public void testAddHouse() {
		House house = new House(cost);
		house.AddBuilding();
		assertEquals("House not added correctly", 1, house.GetAmount());
	}

}
