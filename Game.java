import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Main game controller class
 */
public class Game {
    private Player player;
    private final List<Pokemon> pokemonPool;
    private final List<Place> places;
    private final ScoreManager scoreManager;
    private final Scanner scanner;
    private final Random random;
    
    public Game() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.pokemonPool = new ArrayList<>();
        this.places = new ArrayList<>();
        this.scoreManager = new ScoreManager();
        initializeGame();
    }
    
    private void initializeGame() {
        // Create Pokémon
        pokemonPool.add(new Pokemon("Charizard", Pokemon.FIRE, "Flamethrower", 78, 84, 78, "Rare"));
        pokemonPool.add(new Pokemon("Blastoise", Pokemon.WATER, "Hydro Pump", 79, 83, 100, "Rare"));
        pokemonPool.add(new Pokemon("Venusaur", Pokemon.EARTH, "Solar Beam", 80, 82, 83, "Rare"));
        
        // Create places
        places.add(new Place("Volcano", new Weather(Weather.SUNNY)));
        places.add(new Place("Ocean", new Weather(Weather.RAINY)));
        places.add(new Place("Tundra", new Weather(Weather.HAIL)));
        places.add(new Place("Desert", new Weather(Weather.SANDSTORM)));
        places.add(new Place("Meadow", new Weather(Weather.CLEAR)));
    }
    
    /**
     * Main game loop
     */
    public void start() {
        System.out.println("===== POKÉMON GA-OLE =====");
        createPlayer();
        mainMenu();
    }
    
    private void createPlayer() {
        System.out.print("Enter your name: ");
        player = new Player(scanner.nextLine());
        chooseStarters();
    }
    
    private void chooseStarters() {
        System.out.println("\nChoose 2 starter Pokémon:");
        for (int i = 0; i < 3; i++) {
            System.out.println((i+1) + ". " + pokemonPool.get(i).getStats());
        }
        
        for (int selected = 0; selected < 2; ) {
            System.out.print("Choose Pokémon " + (selected+1) + ": ");
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < 3) {
                    player.addPokemon(pokemonPool.get(choice));
                    selected++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice!");
            }
        }
    }
    
    private void mainMenu() {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Battle");
            System.out.println("2. View Team");
            System.out.println("3. Items");
            System.out.println("4. Scores");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            
            switch (scanner.nextLine()) {
                case "1": startBattle(); break;
                case "2": displayTeam(); break;
                case "3": player.useItem(scanner); break;
                case "4": scoreManager.displayScores(); break;
                case "5": return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
    
    private void startBattle() {
        Place place = places.get(random.nextInt(places.size()));
        Pokemon wildPokemon = place.generateNativePokemon();
        
        System.out.println("\nYou encounter a wild " + wildPokemon.getName() + " at " + place.getName() + "!");
        place.enter(player.getBattleTeam().get(0));
        
        Battle battle = new Battle(player, wildPokemon, place.getWeather());
        int score = battle.start();
        
        if (score > 0) {
            System.out.println("You earned " + score + " points!");
            player.increaseScore(score);
            scoreManager.saveScore(player.getName(), score);
            
            if (random.nextDouble() < 0.3) {
                System.out.println("Found a Mystery Box!");
                player.addItem(new MysteryBox());
            }
        }
        
        healTeam();
    }
    
    private void displayTeam() {
        System.out.println("\n===== YOUR TEAM =====");
        player.getBattleTeam().forEach(p -> {
            System.out.println(p.getStats());
        });
        System.out.println("Coins: " + player.getCoins());
    }
    
    private void healTeam() {
        player.getBattleTeam().forEach(p -> p.heal(p.getMaxHp()));
        System.out.println("Your team was healed!");
    }
    
    public static void main(String[] args) {
        new Game().start();
    }
}