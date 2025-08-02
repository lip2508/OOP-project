/**
 * Used to catch wild Pokémon (now standalone, not an Item)
 */
public class Pokeball {
    private final String type;
    private final double catchRate;
    
    public Pokeball(String type) {
        this.type = type;
        this.catchRate = switch(type.toLowerCase()) {
            case "great" -> 0.6;
            case "ultra" -> 0.8;
            case "master" -> 1.0;
            default -> 0.4;
        };
    }
    
    public boolean tryCatch(Pokemon target) {
        double hpFactor = 1.0 - (target.getHp()/(double)target.getMaxHp());
        double rarityFactor = switch(target.getRarity().toLowerCase()) {
            case "rare" -> 0.5;
            case "uncommon" -> 0.7;
            default -> 1.0;
        };
        double chance = catchRate * hpFactor * rarityFactor;
        return Math.random() < Math.max(0.1, Math.min(0.95, chance));
    }
    
    public String getType() {
        return type;
    }
}