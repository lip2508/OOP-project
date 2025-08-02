import java.util.Scanner;

/**
 * Restores HP to a Pokémon
 */
public class HealthPotion extends Item {
    private final int healAmount;
    
    public HealthPotion(int amount) {
        super("Health Potion (+" + amount + " HP)");
        this.healAmount = amount;
    }
    
    @Override
    public void applyEffect(Player player, Scanner scanner) {
        Pokemon target = player.selectPokemon(scanner);
        if (target != null && !target.isFainted()) {
            target.heal(healAmount);
            System.out.println(target.getName() + " recovered " + healAmount + " HP!");
        } else {
            System.out.println("Can't use on fainted Pokémon!");
        }
    }
}