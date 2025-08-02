import java.util.Random;
import java.util.Scanner;

public class Battle {
    private Pokemon[] team;
    private Pokemon[] wilds;
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    public Battle(Pokemon[] team, Pokemon[] wilds) {
        this.team = team;
        this.wilds = wilds;
    }

    public int startBattle() {
        int score = 0;
        for (int i = 0; i < wilds.length; i++) {
            Pokemon playerPoke = team[i];
            Pokemon wild = wilds[i];

            System.out.println("\n\uD83D\uDD34 Battle Start: " + playerPoke.getName() + " vs. " + wild.getName());

            while (!playerPoke.isFainted() && !wild.isFainted()) {
                System.out.println("\n" + playerPoke.getStats());
                System.out.println(wild.getStats());
                System.out.println("1. Attack  2. Use Potion  3. Catch  4. Run");
                System.out.print("Choose action: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> wild.takeDamage(playerPoke.getAttack());
                    case 2 -> playerPoke.heal(20);
                    case 3 -> {
                        Pokeball ball = new Pokeball("rare");
                        if (ball.tryCatch(wild)) {
                            System.out.println("You caught " + wild.getName() + "!");
                            return score + 50;
                        } else {
                            System.out.println("Catch failed!");
                        }
                    }
                    case 4 -> {
                        System.out.println("You ran away.");
                        return score;
                    }
                    default -> System.out.println("Invalid. Skipped turn.");
                }

                if (!wild.isFainted()) {
                    playerPoke.takeDamage(wild.getAttack());
                }
            }

            if (playerPoke.isFainted()) {
                System.out.println(playerPoke.getName() + " fainted!");
            } else {
                System.out.println("\u2705 " + wild.getName() + " was defeated!");
                playerPoke.increaseUsage();
                score += 100;
            }
        }
        return score;
    }
}