import java.util.*;

// Main game engine that runs the flow of the game
public class Game {
    private Scanner sc = new Scanner(System.in);
    private Player player;
    private ArrayList<Pokemon> pokemonPool = new ArrayList<>();
    private ScoreManager scoreManager = new ScoreManager();

    public void start() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        player = new Player(name);

        generatePokemons(); // Add 5 sample Pokémon to choose from

        System.out.println("\nChoose your 2 Pokémon:");
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + ". " + pokemonPool.get(i).getStats());
        }

        // Player chooses 2 Pokémon
        for (int i = 0; i < 2; i++) {
            System.out.print("Pick Pokémon number: ");
            int pick = sc.nextInt();
            player.addPokemon(pokemonPool.get(pick - 1));
        }

        player.chooseBattleTeam(sc);

        // Prepare wild opponents
        Pokemon[] wilds = new Pokemon[] {
            pokemonPool.get(3),
            pokemonPool.get(4)
        };

        Pokemon[] team = new Pokemon[] {
            player.getBattleTeam().get(0),
            player.getBattleTeam().get(1)
        };

        // Start the battle
        Battle battle = new Battle(team, wilds);
        int earned = battle.startBattle();
        player.increaseScore(earned);

        // Score result and attempt to catch
        System.out.println("\nYour total score: " + player.getScore());
        scoreManager.saveScore(player.getName(), player.getScore());
        scoreManager.displayScores();

        // Catch logic with multiple attempts
        Pokeball ball = new Pokeball("common");
        int attempts = 0;
        boolean caught = false;

        while (attempts < 2 && !caught) {
            System.out.println("Attempting to catch " + wilds[0].getName() + " (Try " + (attempts + 1) + ")...");
            caught = ball.tryCatch(wilds[0]);
            if (caught) {
                System.out.println("You caught " + wilds[0].getName() + "!");
                player.addPokemon(wilds[0]);
            } else {
                System.out.println("Catch failed!");
                attempts++;
            }
        }

        if (!caught) {
            System.out.println(wilds[0].getName() + " escaped! You failed to catch it.");
        }
    }

    // Generates sample Pokémon for selection and wild battle
    private void generatePokemons() {
        pokemonPool.clear();
        pokemonPool.add(new Pokemon("Terradillo", "Earth", "Rock Smash", 90, 20, 10, "common"));
        pokemonPool.add(new Pokemon("Lavabite", "Fire", "Blaze Kick", 85, 22, 9, "rare"));
        pokemonPool.add(new Pokemon("Aqualisk", "Water", "Water Pulse", 95, 18, 11, "common"));
        pokemonPool.add(new Pokemon("Molterra", "Earth", "Mud Shot", 100, 21, 12, "rare"));
        pokemonPool.add(new Pokemon("Hydrake", "Water", "Bubble Beam", 88, 20, 8, "legendary"));
    }

    // Main entry point
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}