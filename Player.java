// CLASS: Player
// Stores player information, their Pokémon, and their score.

import java.util.*;

public class Player {
    private String name;
    private ArrayList<Pokemon> inventory = new ArrayList<>();
    private ArrayList<Pokemon> battleTeam = new ArrayList<>();
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    // Add Pokémon to player’s inventory
    public void addPokemon(Pokemon p) {
        inventory.add(p);
    }

    // Player selects 2 Pokémon from inventory for battle
    public void chooseBattleTeam(Scanner sc) {
        System.out.println("\nChoose 2 Pokémon for battle:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i).getStats());
        }
        while (battleTeam.size() < 2) {
            System.out.print("Enter number for Pokémon " + (battleTeam.size() + 1) + ": ");
            int choice = sc.nextInt();
            if (choice > 0 && choice <= inventory.size()) {
                battleTeam.add(inventory.get(choice - 1));
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // Getters and score updater
    public ArrayList<Pokemon> getBattleTeam() { return battleTeam; }
    public int getScore() { return score; }
    public void increaseScore(int value) { score += value; }
    public String getName() { return name; }
}