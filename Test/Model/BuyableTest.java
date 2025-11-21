package Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BuyableTest {
    
    private Buyable buyable;
    private Player player;
    private Player otherPlayer;
    private Bank bank;
    
    @Before
    public void setUp() {
        // Create a test Buyable space
        buyable = new Buyable("Test Property", 100.0f, 25.0f) {
            // Anonymous subclass to test abstract Space class
            @Override
            public Codes onLand(Player p) {
                return super.onLand(p);
            }
        };
        
        player = new Player("Red", "Player", 500.0f, buyable);
        otherPlayer = new Player("Blue", "Player", 500.0f, buyable);
        bank = new Bank(10000.0f);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Test Property", buyable.name);
        assertEquals(100.0f, buyable.getPrice(), 0.001f);
        assertEquals(25.0f, buyable.getRent(), 0.001f);
        assertNull("Owner should be null initially", buyable.getOwner());
    }
    
    @Test
    public void testOnLand_WhenUnowned_ReturnsCanBuy() {
        // Arrange - buyable has no owner
        
        // Act
        Space.Codes result = buyable.onLand(player);
        
        // Assert
        assertEquals("Should return CAN_BUY for unowned property", 
                     Space.Codes.CAN_BUY, result);
        assertNull("Owner should remain null", buyable.getOwner());
    }
    
    
    @Test
    public void testOnLand_WhenOwnedByOtherPlayer_ReturnsGetRent() {
        // Arrange
        buyable.setOwner(otherPlayer);
        
        
        // Act
        Space.Codes result = buyable.onLand(player);
        
        // Assert
        assertEquals("Should return GET_RENT when owned by other player", 
                     Space.Codes.GET_RENT, result);
    }
    
    @Test
    public void testOnLand_WhenOwnedBySamePlayer_ReturnsIsMine() {
        // Arrange
        buyable.setOwner(player);
        float initialMoney = player.GetMoney();
        
        // Act
        Space.Codes result = buyable.onLand(player);
        
        // Assert
        assertEquals("Should return IS_MINE when owned by same player", 
                     Space.Codes.IS_MINE, result);
        assertEquals("Player money should not change", 
                     initialMoney, player.GetMoney(), 0.001f);
    }
    
    //Teste 3) Comprar uma propriedade que não tenha proprietário
    @Test
    public void testPurchaseBuyable_SuccessfulPurchase() {
        // Arrange
        float initialPlayerMoney = player.GetMoney();
        float initialBankMoney = bank.GetMoney();
        
        // Act
        boolean result = buyable.purchaseBuyable(player, bank);
        
        // Assert
        assertTrue("Purchase should be successful", result);
        assertEquals("Player should be set as owner", player, buyable.getOwner());
        assertEquals("Player money should decrease by price", 
                     initialPlayerMoney - buyable.getPrice(), player.GetMoney(), 0.001f);
        assertEquals("Bank money should increase by price", 
                     initialBankMoney + buyable.getPrice(), bank.GetMoney(), 0.001f);
    }
    
    @Test
    public void testPurchaseBuyable_InsufficientFunds() {
        // Arrange
        Player poorPlayer = new Player("Green", "Player", 50.0f, null); // Only has 50, needs 100
        float initialMoney = poorPlayer.GetMoney();
        float initialBankMoney = bank.GetMoney();
        
        // Act
        boolean result = buyable.purchaseBuyable(poorPlayer, bank);
        
        // Assert
        assertFalse("Purchase should fail with insufficient funds", result);
        assertNull("Owner should remain null", buyable.getOwner());
        assertEquals("Player money should not change", 
                     initialMoney, poorPlayer.GetMoney(), 0.001f);
        assertEquals("Bank money should not change", 
                     initialBankMoney, bank.GetMoney(), 0.001f);
    }
    
    @Test
    public void testPurchaseBuyable_NullPlayer() {
        // Arrange & Act
        boolean result = buyable.purchaseBuyable(null, bank);
        
        // Assert
        assertFalse("Purchase should fail with null player", result);
        assertNull("Owner should remain null", buyable.getOwner());
    }
    
    @Test
    public void testPurchaseBuyable_NullBank() {
        // Arrange & Act
        boolean result = buyable.purchaseBuyable(player, null);
        
        // Assert
        assertFalse("Purchase should fail with null bank", result);
        assertNull("Owner should remain null", buyable.getOwner());
    }
    
    @Test
    public void testRentPayment_BankruptcyScenario() {
        // Arrange
        buyable.setOwner(otherPlayer);
        Player poorPlayer = new Player("Bankrupt", "Player", 20.0f, buyable); // Only has 20, rent is 25
        
        // Act
        Space.Codes result = buyable.onLand(poorPlayer);
        
        // Assert
        assertEquals("Should still return GET_RENT", Space.Codes.GET_RENT, result);
        assertEquals("Player should have 0 money after paying what they can", 
                     0.0f, poorPlayer.GetMoney(), 0.001f);
        assertEquals("Owner should receive only what player could pay", 
                     520.0f, otherPlayer.GetMoney(), 0.001f); // 500 + 20
    }
    
    @Test
    public void testMultiplePurchases() {
        // Test that multiple properties can be owned by same player
        Buyable secondProperty = new Buyable("Second Property", 150.0f, 35.0f) {
            @Override
            public Codes onLand(Player p) {
                return super.onLand(p);
            }
        };
        
        // First purchase
        boolean firstPurchase = buyable.purchaseBuyable(player, bank);
        boolean secondPurchase = secondProperty.purchaseBuyable(player, bank);
        
        assertTrue("First purchase should succeed", firstPurchase);
        assertTrue("Second purchase should succeed", secondPurchase);
        assertEquals("Both properties should have same owner", 
                     player, buyable.getOwner());
        assertEquals("Both properties should have same owner", 
                     player, secondProperty.getOwner());
    }
    
    @Test
    public void testOnLand_AfterPurchase() {
        // Arrange - purchase the property first
        buyable.purchaseBuyable(player, bank);
        
        // Act - land on it again as owner
        Space.Codes result = buyable.onLand(player);
        
        // Assert
        assertEquals("Should return IS_MINE after purchase", 
                     Space.Codes.IS_MINE, result);
    }
}