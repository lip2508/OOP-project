import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the player with their team, items, and progression
 */
public class Player {
    private String name;
    private ArrayList<Pokemon> inventory;
    private ArrayList<Pokemon> battleTeam;
    private ArrayList<Item> items;
    private int score;
    private int coins;

    /**
     * Creates a new player
     * @param name Player's name
     */
    public Player(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.battleTeam = new ArrayList<>();
        this.items = new ArrayList<>();
        this.score = 0;
        this.coins = 100;
    }

    /**
     * Adds a Pokémon to collection
     * @param pokemon Pokémon to add
     */
    public void addPokemon(Pokemon pokemon) {
        inventory.add(pokemon);
        if (battleTeam.size() < 2) {
            battleTeam.add(pokemon);
        }
    }

    /**
     * Selects battle team via console
     * @param scanner Input scanner
     */
    public void chooseBattleTeam(Scanner scanner) {
        battleTeam.clear();
        System.out.println("\nChoose your 2 Pokémon:");

        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i+1) + ". " + inventory.get(i).getStats());
        }

        while (battleTeam.size() < 2) {
            System.out.print("Select Pokémon " + (battleTeam.size()+1) + ": ");
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < inventory.size()) {
                    Pokemon selected = inventory.get(choice);
                    if (!battleTeam.contains(selected)) {
                        battleTeam.add(selected);
                    } else {
                        System.out.println("Already in team!");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter a number!");
            }
        }
    }

    /**
     * Uses item from inventory
     * @param scanner Input scanner
     */
    public void useItem(Scanner scanner) {
        if (items.isEmpty()) {
            System.out.println("No items!");
            return;
        }

        System.out.println("\nItems:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i+1) + ". " + items.get(i).getName());
        }

        System.out.print("Choose item (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice == -1) return;
            if (choice >= 0 && choice < items.size()) {
                Item item = items.remove(choice);
                item.applyEffect(this, scanner);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    // Getters and utility methods
    public List<Pokemon> getBattleTeam() { return battleTeam; }
    public String getName() { return name; }
    public int getScore() { return score; }
    public int getCoins() { return coins; }
    public void addCoins(int amount) { coins += amount; }
    public boolean deductCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            return true;
        }
        return false;
    }
    public void addItem(Item item) {
    items.add(item);
    System.out.println("Added " + item.getName() + " to inventory!");
}
    public void increaseScore(int points) { score += points; }

    /**
     * Selects Pokémon from team
     * @param scanner Input scanner
     * @return Selected Pokémon or null
     */
    public Pokemon selectPokemon(Scanner scanner) {
        System.out.println("\nChoose Pokémon:");
        for (int i = 0; i < battleTeam.size(); i++) {
            System.out.println((i+1) + ". " + battleTeam.get(i).getStats());
        }

        System.out.print("Selection (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice == -1) return null;
            if (choice >= 0 && choice < battleTeam.size()) {
                return battleTeam.get(choice);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
        return null;
    }
}