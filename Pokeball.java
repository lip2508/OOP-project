import java.util.Scanner;

public class Pokeball extends Item {
    private final double catchRate;

    public Pokeball(String type) {
        super(type + " Ball");
        this.catchRate = switch (type.toLowerCase()) {
            case "great" -> 0.6;
            case "ultra" -> 0.8;
            case "master" -> 1.0;
            default -> 0.4; // Regular Poké Ball
        };
    }

    @Override
    public void applyEffect(Player player, Scanner scanner) {
        // This will be called during battle when trying to catch
        System.out.println("Threw a " + getName() + "!");
        // In battle, the wild Pokémon is passed differently - we'll modify this
    }

    public boolean tryCatch(Pokemon target) {
        // If Pokémon is defeated (HP = 0), capture is guaranteed
        if (target.getHp() <= 0) {
            return true;
        }

        // Original probability-based capture for non-defeated Pokémon
        double hpFactor = 1.0 - (target.getHp() / (double) target.getMaxHp());
        double rarityFactor = switch (target.getRarity().toLowerCase()) {
            case "rare" -> 0.5;
            case "uncommon" -> 0.7;
            default -> 1.0;
        };
        double chance = catchRate * hpFactor * rarityFactor;
        return Math.random() < Math.max(0.1, Math.min(0.95, chance));
    }
}