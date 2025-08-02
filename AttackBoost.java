import java.util.Scanner;

/**
 * Temporarily increases a Pokémon's attack power
 */
public class AttackBoost extends Item {
    private final int boostAmount;
    private final int duration;
    
    public AttackBoost(int amount, int turns) {
        super("Attack Boost (+" + amount + " ATK for " + turns + " turns)");
        this.boostAmount = amount;
        this.duration = turns;
    }
    
    @Override
    public void applyEffect(Player player, Scanner scanner) {
        Pokemon target = player.selectPokemon(scanner);
        if (target != null && !target.isFainted()) {
            target.applyAttackBoost(boostAmount, duration);
            System.out.println(target.getName() + "'s attack rose by " + boostAmount + "!");
        }
    }
}