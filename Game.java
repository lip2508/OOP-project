import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

/**
 * Main game controller class
 */
public class Game {

    private static final int MYSTERY_BOX_PRICE = 50;
    private static final int HEALTH_POTION_PRICE = 20;
    private static final int ATTACK_BOOST_PRICE = 30;

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
            System.out.println((i + 1) + ". " + pokemonPool.get(i).getStats());
        }

        for (int selected = 0; selected < 2;) {
            System.out.print("Choose Pokémon " + (selected + 1) + ": ");
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
            System.out.println("4. Shop");
            System.out.println("5. Scores");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            switch (scanner.nextLine()) {
                case "1":
                    startBattle();
                    break;
                case "2":
                    displayTeam();
                    break;
                case "3":
                    player.useItem(scanner);
                    break;
                case "4":
                    openShop();
                    break;
                case "5":
                    scoreManager.displayScores();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void openShop() {
        while (true) {
            System.out.println("\n===== POKéSHOP =====");
            System.out.println("Coins: " + player.getCoins());
            System.out.println("1. Poké Ball - " + MYSTERY_BOX_PRICE + " coins");
            System.out.println("2. Great Ball - " + HEALTH_POTION_PRICE + " coins");
            System.out.println("3. Ultra Ball - " + ATTACK_BOOST_PRICE + " coins");
            System.out.println("4. Health Potion - 20 coins");
            System.out.println("5. Attack Boost - 30 coins");
            System.out.println("6. Back to Menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    if (player.deductCoins(MYSTERY_BOX_PRICE)) {
                        player.addItem(new Pokeball("Poké"));
                        System.out.println("Purchased Poké Ball!");
                    } else {
                        System.out.println("Not enough coins!");
                    }
                    break;
                case "2":
                    if (player.deductCoins(HEALTH_POTION_PRICE)) {
                        player.addItem(new Pokeball("Great"));
                        System.out.println("Purchased Great Ball!");
                    }
                    break;
                case "3":
                    if (player.deductCoins(ATTACK_BOOST_PRICE)) {
                        player.addItem(new Pokeball("Ultra"));
                        System.out.println("Purchased Ultra Ball!");
                    }
                    break;
                case "4":
                    if (player.deductCoins(20)) {
                        player.addItem(new HealthPotion(30));
                        System.out.println("Purchased Health Potion!");
                    }
                    break;
                case "5":
                    if (player.deductCoins(30)) {
                        player.addItem(new AttackBoost(15, 3));
                        System.out.println("Purchased Attack Boost!");
                    }
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice!");
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

            // Free mystery box conditions:
            boolean getsFreeBox = false;

            // Condition 1: Random chance
            if (random.nextDouble() < 0.15)
                getsFreeBox = true;

            // Condition 2: Defeating rare Pokémon
            if (wildPokemon.getRarity().equalsIgnoreCase("rare"))
                getsFreeBox = true;

            // Condition 3: Winning with low HP
            if (player.getBattleTeam().stream().anyMatch(p -> !p.isFainted() && p.getHp() < p.getMaxHp() / 4)) {
                getsFreeBox = true;
            }

            if (getsFreeBox) {
                System.out.println("\nYou found a FREE Mystery Box!");
                player.addItem(new MysteryBox());
            }

            if (wildPokemon.isFainted()) {
                attemptCatch(wildPokemon);
            }
        }

        healTeam();
    }

    private void attemptCatch(Pokemon wildPokemon) {
        System.out.println("\nWould you like to try to catch " + wildPokemon.getName() + "?");
        System.out.println("1. Yes\n2. No");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 1) {
                Optional<Item> ball = player.getItems().stream()
                        .filter(i -> i instanceof Pokeball)
                        .findFirst();

                if (ball.isPresent()) {
                    if (((Pokeball) ball.get()).tryCatch(wildPokemon)) {
                        // Create a NEW copy of the Pokémon
                        Pokemon caughtPokemon = new Pokemon(wildPokemon);
                        player.addPokemon(caughtPokemon);
                        player.getItems().remove(ball.get());
                        System.out.println("Gotcha! " + caughtPokemon.getName() + " was caught!");
                    } else {
                        System.out.println("Oh no! The Pokémon broke free!");
                    }
                } else {
                    System.out.println("You don't have any Poké Balls!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
        }
    }

    private void displayTeam() {
        System.out.println("\n===== YOUR TEAM =====");
        System.out.println("-- Battle Team (Active) --");
        player.getBattleTeam().forEach(p -> System.out.println(p.getStats()));

        System.out.println("\n-- Caught Pokémon (" + player.getInventory().size() + ") --");
        player.getInventory().stream()
                .filter(p -> !player.getBattleTeam().contains(p)) // Only show non-active
                .forEach(p -> System.out.println(p.getStats()));
    }

    private void healTeam() {
        player.getBattleTeam().forEach(p -> p.heal(p.getMaxHp()));
        System.out.println("Your team was healed!");
    }

    public static void main(String[] args) {
        new Game().start();
    }
}