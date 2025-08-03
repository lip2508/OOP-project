import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Handles turn-based battle logic between player and wild Pokémon
 */
public class Battle {
    private final Player player;
    private final Pokemon wildPokemon;
    private final Weather weather;
    private final Scanner scanner;
    private final Random random;

    // Effectiveness multipliers
    private static final double SUPER_EFFECTIVE = 1.5;
    private static final double NORMAL_EFFECTIVENESS = 1.0;

    public Battle(Player player, Pokemon wildPokemon, Weather weather) {
        this.player = player;
        this.wildPokemon = wildPokemon;
        this.weather = weather;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    /**
     * Starts and manages the battle
     * 
     * @return Score earned (0 if lost)
     */
    public int start() {
        Pokemon currentPokemon = getFirstActivePokemon();
        if (currentPokemon == null)
            return 0;

        int turnCount = 0;
        boolean playerRan = false;

        while (!wildPokemon.isFainted() && hasActivePokemon() && !playerRan) {
            turnCount++;
            System.out.println("\n--- Turn " + turnCount + " ---");
            displayStatus(currentPokemon);

            switch (getPlayerAction()) {
                case 1: // Attack
                    attack(currentPokemon, wildPokemon);
                    break;
                case 2: // Item
                    player.useItem(scanner);
                    break;
                case 3: // Switch
                    currentPokemon = switchPokemon();
                    continue;
                case 4: // Run
                    playerRan = attemptRun();
                    break;
                default:
                    System.out.println(currentPokemon.getName() + " does nothing!");
            }

            if (wildPokemon.isFainted())
                break;
            if (!playerRan)
                enemyTurn(currentPokemon);
            if (currentPokemon.isFainted()) {
                currentPokemon = getNextActivePokemon();
                if (currentPokemon != null) {
                    System.out.println("Go! " + currentPokemon.getName() + "!");
                }
            }
        }

        return concludeBattle(playerRan);
    }

    // Helper methods...
    private void displayStatus(Pokemon playerPokemon) {
        System.out.println("\n=== YOUR POKÉMON ===");
        System.out.println(playerPokemon.getName() + " (" + playerPokemon.getType() + ")");
        System.out.println("HP: " + playerPokemon.getHp() + "/" + playerPokemon.getMaxHp());
        System.out.println("ATK: " + playerPokemon.getAttack() +
                (playerPokemon.getAttack() > playerPokemon.getBaseAttack() ? " (boosted)" : ""));
        System.out.println("DEF: " + playerPokemon.getDefense());
        System.out.println("Move: " + playerPokemon.getMove());

        System.out.println("\n=== WILD POKÉMON ===");
        System.out.println(wildPokemon.getName() + " (" + wildPokemon.getType() + ")");
        System.out.println("HP: " + wildPokemon.getHp() + "/" + wildPokemon.getMaxHp());
        System.out.println("ATK: " + wildPokemon.getAttack());
        System.out.println("DEF: " + wildPokemon.getDefense());
        System.out.println("Rarity: " + wildPokemon.getRarity());

        // Display type effectiveness info
        System.out.println("\nType Matchup:");
        System.out.println("Your " + playerPokemon.getType() + " vs their " + wildPokemon.getType() + ": " +
                getEffectivenessDescription(playerPokemon.getType(), wildPokemon.getType()));
    }

    private String getEffectivenessDescription(String attackType, String defendType) {
        if ((attackType.equals(Pokemon.FIRE) && defendType.equals(Pokemon.EARTH)) ||
                (attackType.equals(Pokemon.EARTH) && defendType.equals(Pokemon.WATER)) ||
                (attackType.equals(Pokemon.WATER) && defendType.equals(Pokemon.FIRE))) {
            return "Super effective (1.5x damage)";
        }
        return "Normal effectiveness (1x damage)";
    }

    private int getPlayerAction() {
        System.out.println("1. Attack\n2. Item\n3. Switch\n4. Run");
        System.out.print("Choose action: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void attack(Pokemon attacker, Pokemon defender) {
        int damage = calculateDamage(attacker, defender);
        defender.takeDamage(damage);
        System.out.println(attacker.getName() + " attacks for " + damage + " damage!");
    }

    private int calculateDamage(Pokemon attacker, Pokemon defender) {
        // Original formula was too easy (2 hits KO)
        // New formula: damage = (attack * attack / (defense + 20)) + 5
        int base = (attacker.getAttack() * attacker.getAttack() / (defender.getDefense() + 20)) + 5;
        base = Math.max(1, base);

        double type = getTypeEffectiveness(attacker.getType(), defender.getDefendType());
        double weather = getWeatherMultiplier(attacker.getType());

        return (int) (base * type * weather);
    }

    private double getTypeEffectiveness(String attackType, String defendType) {
        if ((attackType.equals(Pokemon.FIRE) && defendType.equals(Pokemon.EARTH)) ||
                (attackType.equals(Pokemon.EARTH) && defendType.equals(Pokemon.WATER)) ||
                (attackType.equals(Pokemon.WATER) && defendType.equals(Pokemon.FIRE))) {
            System.out.println("It's super effective!");
            return SUPER_EFFECTIVE;
        }
        return NORMAL_EFFECTIVENESS;
    }

    private double getWeatherMultiplier(String type) {
        String weatherType = weather.getType();
        if ((weatherType.equals(Weather.SUNNY) && type.equals(Pokemon.FIRE)) ||
                (weatherType.equals(Weather.RAINY) && type.equals(Pokemon.WATER)) ||
                (weatherType.equals(Weather.SANDSTORM) && type.equals(Pokemon.EARTH))) {
            return 1.2;
        }
        return 1.0;
    }

    private boolean attemptRun() {
        if (random.nextDouble() < 0.7) {
            System.out.println("Got away safely!");
            return true;
        }
        System.out.println("Couldn't escape!");
        return false;
    }

    private Pokemon switchPokemon() {
        System.out.println("Available Pokémon:");
        List<Pokemon> team = player.getBattleTeam();
        for (int i = 0; i < team.size(); i++) {
            System.out.println((i + 1) + ". " + team.get(i).getStats());
        }

        System.out.print("Choose Pokémon: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice >= 0 && choice < team.size()) {
                return team.get(choice);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
        }
        return getFirstActivePokemon();
    }

    private void enemyTurn(Pokemon playerPokemon) {
        int damage = calculateDamage(wildPokemon, playerPokemon);
        playerPokemon.takeDamage(damage);
        System.out.println("Wild " + wildPokemon.getName() + " attacks for " + damage + " damage!");
    }

    private int concludeBattle(boolean ran) {
        if (wildPokemon.isFainted()) {
            System.out.println("You won!");
            player.getBattleTeam().forEach(p -> {
                if (!p.isFainted())
                    p.recordVictory();
            });
            return 100 + (int) (player.getBattleTeam().stream()
                    .filter(p -> !p.isFainted())
                    .mapToDouble(p -> (double) p.getHp() / p.getMaxHp())
                    .average().orElse(0) * 50);
        }
        return 0;
    }

    private Pokemon getFirstActivePokemon() {
        return player.getBattleTeam().stream()
                .filter(p -> !p.isFainted())
                .findFirst()
                .orElse(null);
    }

    private Pokemon getNextActivePokemon() {
        return getFirstActivePokemon();
    }

    private boolean hasActivePokemon() {
        return player.getBattleTeam().stream()
                .anyMatch(p -> !p.isFainted());
    }
}