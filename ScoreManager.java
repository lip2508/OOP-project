import java.io.*;
import java.util.*;

/**
 * Handles score saving and loading
 */
public class ScoreManager {
    private static final String FILE = "scores.txt";

    /**
     * Saves a player's score
     */
    public void saveScore(String name, int score) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE, true))) {
            out.println(name + "," + score + "," + System.currentTimeMillis());
        } catch (IOException e) {
            System.out.println("Error saving score!");
        }
    }

    /**
     * Displays scores
     */
    public void displayScores() {
        List<String> scores = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                // Skip empty/template lines
                if (!line.trim().isEmpty() && line.contains(",")) {
                    scores.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("No scores yet!");
            return;
        }

        // Show only actual scores (skip headers/templates)
        System.out.println("\n===== HIGH SCORES =====");
        if (scores.isEmpty()) {
            System.out.println("No recorded battles yet!");
        } else {
            scores.sort((a, b) -> {
                int scoreB = Integer.parseInt(b.split(",")[1]);
                int scoreA = Integer.parseInt(a.split(",")[1]);
                return Integer.compare(scoreB, scoreA);
            });

            for (int i = 0; i < Math.min(5, scores.size()); i++) {
                String[] parts = scores.get(i).split(",");
                System.out.printf("%d. %s - %d%n", i + 1, parts[0], Integer.parseInt(parts[1]));
            }
        }
    }
}