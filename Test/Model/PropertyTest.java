package Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PropertyTest {
    
    private Property property;
    private Player player;
    private Player otherPlayer;
    private Bank bank;
    
    @Before
    public void setUp() {
        bank = new Bank(10000.0f);
        
        House testHouse = new House(50); // cost 50
        Hotel testHotel = new Hotel(100); // cost 100
        
        
        property = new Property("Test Property", 100, 0, testHouse, testHotel);
        player = new Player("Red", 500.0f, property);
        otherPlayer = new Player("Blue", 500.0f, property);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Test Property", property.name);
        assertEquals(100.0f, property.getPrice(), 0.001f);
        assertEquals(property.getPrice() * 0.1f, property.getRent(), 0.001f);
        assertNull("Owner should be null initially", property.getOwner());
    }
    
    @Test
    public void testSetOwner() {
        // Act
        property.setOwner(player);
        
        // Assert
        assertEquals("Owner should be set correctly", player, property.getOwner());
    }
    
    @Test
    public void testOnLand_Unowned_ReturnsCanBuy() {
        // Act
        Space.Codes result = property.onLand(player);
        
        // Assert
        assertEquals("Should return CAN_BUY for unowned property", 
                     Space.Codes.CAN_BUY, result);
    }
    
    
    
    
    // Teste 5) Pagar, automaticamente, aluguel quando o jogador da vez cair em uma
    // propriedade de outro jogador. Esta propriedade deve ter pelo menos uma casa;
    @Test
    public void testOnLand_OwnedByOther_WithHouse_ReturnsGetRent() {
        // Arrange
        property.setOwner(otherPlayer);
        float initialPlayerMoney = player.GetMoney();
        
        Space.Codes buildReturn = property.buildHouse(otherPlayer, bank);
        float initialOwnerMoney = otherPlayer.GetMoney();
        
        // Act
        Space.Codes result = property.onLand(player);
        
        // Assert
        
        assertEquals("Should return BUILT when house is built", Space.Codes.BUILT, buildReturn);
        assertEquals("Should return GET_RENT when owned by other player", 
                     Space.Codes.GET_RENT, result);
        assertEquals("Player should pay rent", 
                     initialPlayerMoney - property.getRent(), player.GetMoney(), 0.001f);
        assertEquals("Owner should receive rent", 
                     initialOwnerMoney + property.getRent(), otherPlayer.GetMoney(), 0.001f);
    }
    
    @Test
    public void testOnLand_OwnedBySelf_CantBuildBothInitially() {
        // Arrange
        property.setOwner(player);
        
        // Act
        Space.Codes result = property.onLand(player);
        
        // Assert
        assertEquals("Should return CAN_BUILD_HOUSE when no buildings", 
                     Space.Codes.CAN_BUILD_HOUSE, result);
    }
    
    @Test
    // Teste 4) Construir uma casa em uma propriedade em que o jogador da vez caiu e que lhe
    // pertence;
    public void testBuildHouse_Successful() {
        // Arrange
        property.setOwner(player);
        float initialPlayerMoney = player.GetMoney();
        float initialBankMoney = bank.GetMoney();
        
        // Act
        Space.Codes result = property.buildHouse(player, bank);
        
        // Assert
        assertEquals("Should return BUILT", Space.Codes.BUILT, result);
        assertEquals("House count should increase", 1, property.house.GetAmount());
        assertEquals("Player should pay house cost", 
                     initialPlayerMoney - property.house.GetCost(), player.GetMoney(), 0.001f);
        assertEquals("Bank should receive payment", 
                     initialBankMoney + property.house.GetCost(), bank.GetMoney(), 0.001f);
    }
    
    @Test
    public void testBuildHouse_InsufficientFunds() {
        // Arrange
        property.setOwner(player);
        Player poorPlayer = new Player("Poor", 10.0f, null); // Only has 10, house costs 50
        
        // Act
        Space.Codes result = property.buildHouse(poorPlayer, bank);
        
        // Assert
        assertEquals("Should return NOT_BUILT", Space.Codes.NOT_BUILT, result);
        assertEquals("House count should not increase", 0, property.house.GetAmount());
    }
    
    @Test
    public void testBuildHotel_Successful() {
        // Arrange
        property.setOwner(player);
        float initialPlayerMoney = player.GetMoney();
        float initialBankMoney = bank.GetMoney();
        
        // Act
        Space.Codes result = property.buildHotel(player, bank);
        
        // Assert
        assertEquals("Should return BUILT", Space.Codes.BUILT, result);
        assertEquals("Hotel count should increase", 1, property.hotel.GetAmount());
        assertEquals("Player should pay hotel cost", 
                     initialPlayerMoney - property.hotel.GetCost(), player.GetMoney(), 0.001f);
        assertEquals("Bank should receive payment", 
                     initialBankMoney + property.hotel.GetCost(), bank.GetMoney(), 0.001f);
    }
    
    @Test
    public void testBuildHotel_InsufficientFunds() {
        // Arrange
        property.setOwner(player);
        Player poorPlayer = new Player("Poor", 50.0f, null); // Only has 50, hotel costs 100
        
        // Act
        Space.Codes result = property.buildHotel(poorPlayer, bank);
        
        // Assert
        assertEquals("Should return NOT_BUILT", Space.Codes.NOT_BUILT, result);
        assertEquals("Hotel count should not increase", 0, property.hotel.GetAmount());
    }
    
    @Test
    public void testOnLand_BuildingProgression() {
        // Test the building progression logic
        property.setOwner(player);
        
        // Initially can build house
        assertEquals(Space.Codes.CAN_BUILD_HOUSE, property.onLand(player));
        
        
        // Build 1st house
        property.buildHouse(player, bank);
        
        // After 1 house, can build both
        assertEquals(Space.Codes.CAN_BUILD_BOTH, property.onLand(player));
        
        // Build 3 houses
        for (int i = 0; i < 3; i++) {
            property.buildHouse(player, bank);
        }
        
        // After 4 houses, can only build hotel
        assertEquals(Space.Codes.CAN_BUILD_HOTEL, property.onLand(player));
        
        // Build hotel
        property.buildHotel(player, bank);
        
        // After hotel, cannot build anything
        assertEquals(Space.Codes.CANT_BUILD, property.onLand(player));
    }
    
    @Test
    public void testRentCalculation_WithBuildings() {
        // Arrange
        property.setOwner(player);
        float baseRent = property.getRent();
        
        // Act - Build houses and hotel
        property.buildHouse(player, bank);
        property.buildHouse(player, bank);
        property.buildHotel(player, bank);
        
        // Assert - Rent should be base + (2 houses * 10) + (1 hotel * 50)
        float expectedRent = baseRent + (2 * property.getPrice() * 0.15f) + (1 * property.getPrice() * 0.3f);
        
        // Land on property to trigger rent payment check
        property.onLand(otherPlayer);
        
        // Verify the rent was calculated correctly by checking money transfer
        float initialMoney = otherPlayer.GetMoney();
        property.onLand(otherPlayer); // Pay rent again
        float rentPaid = initialMoney - otherPlayer.GetMoney();
        
        assertEquals("Rent should include building bonuses", 
                     expectedRent, rentPaid, 0.001f);
    }
    
    @Test
    public void testBuildHouse_NotOwner() {
        // Arrange - Property has no owner
        Space.Codes result = property.buildHouse(player, bank);
        
        // Assert - Should fail since player doesn't own the property
        assertEquals("Should return NOT_BUILT when not owner", 
                     Space.Codes.NOT_BUILT, result);
    }
    
    @Test
    public void testBuildHotel_NotOwner() {
        // Arrange - Property has no owner
        Space.Codes result = property.buildHotel(player, bank);
        
        // Assert - Should fail since player doesn't own the property
        assertEquals("Should return NOT_BUILT when not owner", 
                     Space.Codes.NOT_BUILT, result);
    }
    
    @Test
    public void testMultiplePropertiesSameOwner() {
        // Test that owner can build on multiple properties

        House prop2House = new House(50);
        Hotel prop2Hotel = new Hotel(100);
        Property property2 = new Property("Second Property", 150, 35, prop2House, prop2Hotel);
        
        property.setOwner(player);
        property2.setOwner(player);
        
        // Both should allow building
        assertEquals(Space.Codes.CAN_BUILD_HOUSE, property.onLand(player));
        
        player.SetCurrentSpace(property2);
        assertEquals(Space.Codes.CAN_BUILD_HOUSE, property2.onLand(player));
        
        // Build on first property
        property.buildHouse(player, bank);
        assertEquals(1, property.house.GetAmount());
        assertEquals(0, property2.house.GetAmount()); // Second property unchanged
    }
}