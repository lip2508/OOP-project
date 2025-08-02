import java.util.Scanner;

/**
 * Abstract base class for all usable items
 */
public abstract class Item {
    protected String name;
    
    public Item(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * Applies the item's effect
     * @param player The using player
     * @param scanner For user input if needed
     */
    public abstract void applyEffect(Player player, Scanner scanner);
}