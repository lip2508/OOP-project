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
            out.println(name + "," + score);
        } catch (IOException e) {
            System.out.println("Error saving score!");
        }
    }
    
    /**
     * Displays top 5 scores
     */
    public void displayScores() {
        List<String> scores = new ArrayList<>();
        
        try (BufferedReader in = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = in.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            System.out.println("No scores yet!");
            return;
        }
        
        scores.sort((a, b) -> {
            int scoreA = Integer.parseInt(a.split(",")[1]);
            int scoreB = Integer.parseInt(b.split(",")[1]);
            return Integer.compare(scoreB, scoreA);
        });
        
        System.out.println("\n===== HIGH SCORES =====");
        for (int i = 0; i < Math.min(5, scores.size()); i++) {
            String[] parts = scores.get(i).split(",");
            System.out.printf("%d. %s - %d%n", i+1, parts[0], Integer.parseInt(parts[1]));
        }
    }
}