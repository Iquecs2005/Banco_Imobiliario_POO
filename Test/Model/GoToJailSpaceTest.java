package Model;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class GoToJailSpaceTest {
    
    private GoToJailSpace goToJailSpace;
    private Player player;
    private Jail jail;
    
    @Before
    public void setUp() {
        jail = new Jail();
        goToJailSpace = new GoToJailSpace("Go To Jail", jail);
        player = new Player("Red", 500.0f, null);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Go To Jail", goToJailSpace.name);
        assertEquals(jail, goToJailSpace.getJail());
    }
    
    @Test
    public void testConstructorWithNullJail() {
        GoToJailSpace spaceWithNullJail = new GoToJailSpace("Null Jail Space", null);
        assertEquals("Null Jail Space", spaceWithNullJail.name);
        assertNull("Jail should be null", spaceWithNullJail.getJail());
    }
    
    @Test
    public void testOnLand_ValidPlayer() {
        // Act
        Space.Codes result = goToJailSpace.onLand(player);
        
        // Assert
        assertEquals("Should return SENT_TO_JAIL", Space.Codes.SENT_TO_JAIL, result);
        assertTrue("Player should be in jail", player.GetJailedStatus());
    }
    
    @Test
    public void testOnLand_NullPlayer() {
        // Act
        Space.Codes result = goToJailSpace.onLand(null);
        
        // Assert
        assertEquals("Should return NOTHING with null player", Space.Codes.NOTHING, result);
    }
    
    @Test
    public void testOnLand_WithNullJailInConstructor() {
        // Arrange
        GoToJailSpace spaceWithNullJail = new GoToJailSpace("Test", null);
        
        // Act
        Space.Codes result = spaceWithNullJail.onLand(player);
        
        // Assert
        assertEquals("Should return NOTHING with null jail", Space.Codes.NOTHING, result);
        assertFalse("Player should not be in jail", player.GetJailedStatus());
    }
    
    @Test
    public void testOnLand_MultiplePlayers() {
        // Arrange
        Player player2 = new Player("Blue", 500.0f, null);
        
        // Act
        Space.Codes result1 = goToJailSpace.onLand(player);
        Space.Codes result2 = goToJailSpace.onLand(player2);
        
        // Assert
        assertEquals("First player should return SENT_TO_JAIL", Space.Codes.SENT_TO_JAIL, result1);
        assertEquals("Second player should return SENT_TO_JAIL", Space.Codes.SENT_TO_JAIL, result2);
        assertTrue("First player should be in jail", player.GetJailedStatus());
        assertTrue("Second player should be in jail", player2.GetJailedStatus());
    }
    
    @Test
    public void testOnLand_PlayerAlreadyInJail() {
        // Arrange - Player already in jail
        jail.sendToJail(player);
        assertTrue("Player should already be in jail", player.GetJailedStatus());
        
        // Act
        Space.Codes result = goToJailSpace.onLand(player);
        
        // Assert
        assertEquals("Should still return SENT_TO_JAIL", Space.Codes.SENT_TO_JAIL, result);
        assertTrue("Player should remain in jail", player.GetJailedStatus());
    }
    
    @Test
    public void testOnLand_ProperlyOverridesParentMethod() {
        // This test verifies the method properly overrides the parent class
        Space space = goToJailSpace; // Treat as base Space type
        
        Space.Codes result = space.onLand(player);
        
        assertEquals("Should return SENT_TO_JAIL when called as Space type", 
                     Space.Codes.SENT_TO_JAIL, result);
        assertTrue("Player should be in jail", player.GetJailedStatus());
    }
    
    @Test
    public void testJailIntegration() {
        // Test that the jail actually receives the player through proper dependency injection
        // Act
        goToJailSpace.onLand(player);
        
        // Assert - Player should be marked as in jail
        assertTrue("Player should be marked as in jail", player.GetJailedStatus());
        
        // Verify jail can process the prisoner
        Player testPlayer = new Player("Test", 500.0f, null);
        jail.sendToJail(testPlayer);
        
        Vector<Integer> dice = new Vector<>();
        dice.add(1);
        dice.add(2);
        jail.tryToLeaveJail(testPlayer, dice, false);
        
        assertTrue("Jail should still hold the player after failed attempt", 
                   testPlayer.GetJailedStatus());
    }
    
    @Test
    public void testMultipleGoToJailSpaces() {
        // Test multiple GoToJail spaces with different jails
        Jail jail2 = new Jail();
        GoToJailSpace goToJailSpace2 = new GoToJailSpace("Second Jail", jail2);
        
        Player player2 = new Player("Green", 500.0f, null);
        
        // Act
        goToJailSpace.onLand(player);
        goToJailSpace2.onLand(player2);
        
        // Assert
        assertTrue("First player should be in first jail", player.GetJailedStatus());
        assertTrue("Second player should be in second jail", player2.GetJailedStatus());
    }
}