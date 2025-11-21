package Model;

import static org.junit.Assert.*;
import org.junit.Test;

public class SpaceTest {
    
    @Test
    public void testConstructor() {
        Space space = new Space("Test Space") {
            // Concrete implementation for testing abstract class
            @Override
            public Codes onLand(Player p) {
                return Codes.NOTHING;
            }
        };
        
        assertEquals("Test Space", space.name);
    }
    
    @Test
    public void testOnLand_ReturnsNothing() {
        Space space = new Space("Test Space") {
            @Override
            public Codes onLand(Player p) {
                return Codes.NOTHING;
            }
        };
        
        Player player = new Player("Red", "Player", 500.0f, null);
        Space.Codes result = space.onLand(player);
        
        assertEquals("Should return NOTHING", Space.Codes.NOTHING, result);
    }
    
}