import java.util.Scanner;
import java.util.Random;

/**
 * Provides random items when opened
 */
public class MysteryBox extends Item {
    private enum Rarity {
        COMMON(70), UNCOMMON(25), RARE(5);
        
        final int chance;
        Rarity(int chance) { this.chance = chance; }
    }
    
    private final Random random = new Random();
    private final int cost = 50;
    
    public MysteryBox() {
        super("Mystery Box");
    }
    
    /**
     * Opens the box and applies its effect
     */
    @Override
    public void applyEffect(Player player, Scanner scanner) {
        if (player.deductCoins(cost)) {
            Item item = generateItem();
            System.out.println("You got: " + item.getName() + "!");
            player.addItem(item);
        } else {
            System.out.println("Not enough coins to open!");
        }
    }
    
    private Item generateItem() {
        Rarity rarity = determineRarity();
        
        switch(rarity) {
            case COMMON: 
                return new HealthPotion(30);
            case UNCOMMON: 
                return new AttackBoost(15, 3);
            case RARE:
                // Return a Pokeball wrapped as an Item
                return new Item("Master Ball") {
                    @Override
                    public void applyEffect(Player player, Scanner scanner) {
                        System.out.println("Master Ball can only be used in catch attempts!");
                    }
                };
            default: 
                return new HealthPotion(20);
        }
    }
    
    private Rarity determineRarity() {
        int roll = random.nextInt(100);
        if (roll < Rarity.RARE.chance) return Rarity.RARE;
        if (roll < Rarity.RARE.chance + Rarity.UNCOMMON.chance) return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }
}