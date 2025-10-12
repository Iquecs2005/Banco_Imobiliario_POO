package Model;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DiceTest {
	
	private static int sides;
	private static Dice dice;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		sides = 6;
		dice = new Dice(sides);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//Teste 1) Realizar o lançamento virtual dos dados (procure métodos que gerem números
	//randômicos), sem que seja necessário visualizá-los graficamente;
	@Test
	public void singleRoll() {
		int roll = dice.roll();
		assertTrue("Roll is not in expected range", (roll >= 1 && roll <= sides));
	}
	
	@Test
	public void multipleRolls() {
		Vector<Integer> rolls = new Vector<>(100);
		rolls = dice.roll(100);
		boolean inRange = true;
		for (int i = 0; i < 100; ++i)
		{
			if (rolls.get(i) < 1 && rolls.get(i) > sides)
			{
				inRange = false;
				break;
			}
		}
		assertTrue("Rolls are not in expected range", inRange);
	}

}
