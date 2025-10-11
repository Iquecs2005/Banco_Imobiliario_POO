package Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Vector;

public class JailTest {
    
    private Jail jail;
    private Player player;
    private Player otherPlayer;
    
    @Before
    public void setUp() {
        jail = new Jail();
        player = new Player("Red", 500.0f, null);
        otherPlayer = new Player("Blue", 500.0f, null);
    }
    
    @Test
    public void testSendToJail() {
        // Act
        jail.sendToJail(player);
        
        // Assert - Only test through public behavior
        assertTrue("Player should be marked as in jail", player.GetJailedStatus());
    }
    
    @Test
    public void testSendToJail_MultiplePlayers() {
        // Act
        jail.sendToJail(player);
        jail.sendToJail(otherPlayer);
        
        // Assert
        assertTrue("Both players should be in jail", 
                   player.GetJailedStatus() && otherPlayer.GetJailedStatus());
    }
    
    @Test
    public void testTryToLeaveJail_WithMatchingDice() {
        // Arrange
        jail.sendToJail(player);
        Vector<Integer> matchingDice = new Vector<>();
        matchingDice.add(3);
        matchingDice.add(3); // Matching dice
        
        // Act
        jail.tryToLeaveJail(player, matchingDice, false);
        
        // Assert
        assertFalse("Player should be released with matching dice", player.GetJailedStatus());
    }
    
    @Test
    public void testTryToLeaveJail_WithLeaveCard() {
        // Arrange
        jail.sendToJail(player);
        Vector<Integer> nonMatchingDice = new Vector<>();
        nonMatchingDice.add(2);
        nonMatchingDice.add(4); // Non-matching dice
        
        // Act
        jail.tryToLeaveJail(player, nonMatchingDice, true); // Has leave card
        
        // Assert
        assertFalse("Player should be released with leave card", player.GetJailedStatus());
    }
    
    @Test
    public void testTryToLeaveJail_AfterMaxTurns() {
        // Arrange
        jail.sendToJail(player);
        Vector<Integer> nonMatchingDice = new Vector<>();
        nonMatchingDice.add(1);
        nonMatchingDice.add(2); // Non-matching dice
        
        // Act - Simulate 4 failed attempts
        for (int i = 0; i < 4; i++) {
            jail.tryToLeaveJail(player, nonMatchingDice, false);
        }
        
        // Assert - Should be released after 4 turns
        assertFalse("Player should be released after max turns", player.GetJailedStatus());
    }
    
    @Test
    public void testTryToLeaveJail_PlayerNotInJail() {
        // Arrange - Player not sent to jail
        Vector<Integer> dice = new Vector<>();
        dice.add(1);
        dice.add(1); // Matching dice
        
        // Act - This should do nothing since player isn't in jail
        jail.tryToLeaveJail(player, dice, false);
        
        // Assert
        assertFalse("Player should not be in jail", player.GetJailedStatus());
    }
    
    @Test
    public void testTryToLeaveJail_FailedAttempt() {
        // Arrange
        jail.sendToJail(player);
        Vector<Integer> nonMatchingDice = new Vector<>();
        nonMatchingDice.add(1);
        nonMatchingDice.add(2); // Non-matching dice
        
        // Act - First attempt
        jail.tryToLeaveJail(player, nonMatchingDice, false);
        
        // Assert
        assertTrue("Player should still be in jail after failed attempt", player.GetJailedStatus());
    }
    
    @Test
    public void testOnLand() {
        // Act
        Space.Codes result = jail.onLand(player);
        
        // Assert
        assertEquals("onLand should return NOTHING", Space.Codes.NOTHING, result);
        assertFalse("Player should not be sent to jail by landing on it", player.GetJailedStatus());
    }
    
    @Test
    public void testMultipleFailedAttemptsThenSuccess() {
        // Arrange
        jail.sendToJail(player);
        Vector<Integer> nonMatchingDice = new Vector<>();
        nonMatchingDice.add(1);
        nonMatchingDice.add(2);
        Vector<Integer> matchingDice = new Vector<>();
        matchingDice.add(4);
        matchingDice.add(4);
        
        // Act - 2 failed attempts, then success
        jail.tryToLeaveJail(player, nonMatchingDice, false);
        jail.tryToLeaveJail(player, nonMatchingDice, false);
        jail.tryToLeaveJail(player, matchingDice, false);
        
        // Assert
        assertFalse("Player should be released on successful attempt", player.GetJailedStatus());
    }
    
    @Test
    public void testLeaveWithBothMatchingDiceAndCard() {
        // Test that having both conditions still works
        jail.sendToJail(player);
        Vector<Integer> matchingDice = new Vector<>();
        matchingDice.add(5);
        matchingDice.add(5);
        
        jail.tryToLeaveJail(player, matchingDice, true); // Both conditions true
        
        assertFalse("Player should be released", player.GetJailedStatus());
    }
    
    @Test
    public void testLeaveWithCardDespiteNonMatchingDice() {
        // Test that card overrides non-matching dice
        jail.sendToJail(player);
        Vector<Integer> nonMatchingDice = new Vector<>();
        nonMatchingDice.add(1);
        nonMatchingDice.add(6);
        
        jail.tryToLeaveJail(player, nonMatchingDice, true); // Has card
        
        assertFalse("Player should be released with card despite non-matching dice", 
                   player.GetJailedStatus());
    }
    
    @Test
    public void testThirdTurnRelease() {
        // Test release exactly on the 4th turn (after 3 failed attempts)
        jail.sendToJail(player);
        Vector<Integer> nonMatchingDice = new Vector<>();
        nonMatchingDice.add(1);
        nonMatchingDice.add(2);
        
        // 3 failed attempts
        for (int i = 0; i < 3; i++) {
            jail.tryToLeaveJail(player, nonMatchingDice, false);
            assertTrue("Player should still be in jail after attempt " + (i + 1), 
                       player.GetJailedStatus());
        }
        
        // 4th attempt - should be released due to max turns
        jail.tryToLeaveJail(player, nonMatchingDice, false);
        assertFalse("Player should be released on 4th turn", player.GetJailedStatus());
    }
}