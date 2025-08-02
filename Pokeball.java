import java.util.Random;

public class Pokeball {
    private String type;
    private double catchRate;
    private Random rand = new Random();

    public Pokeball(String type) {
        this.type = type;
        switch (type.toLowerCase()) {
            case "common" -> catchRate = 0.5;
            case "rare" -> catchRate = 0.75;
            case "legendary" -> catchRate = 0.95;
            default -> catchRate = 0.5;
        }
    }

    public boolean tryCatch(Pokemon target) {
        return rand.nextDouble() < catchRate;
    }
}