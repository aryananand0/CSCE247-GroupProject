package model;
import java.util.*;
import java.util.concurrent.*;
public class MainG {
    
    public static void clearScreen() {
        // Simulating screen clearing with newlines
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
    public static void main(String[] args) {
        // Create a RocketGame object
        RocketGame game = new RocketGame();
        
        // Load the game list from the JSON file
        try {
            game.loadGameList();
        } catch (Exception e) {
            System.out.println("Failed to load game data: " + e.getMessage());
            return; // Exit the game if loading fails
        }
        // Get the words and answers
        ArrayList<String> words = game.getWords();
        HashMap<String, String> answers = game.getAnswers();
        // Randomize the order of the words
        Collections.shuffle(words);
        // Create a Scanner object for input
        Scanner scanner = new Scanner(System.in);
        // Game variables
        int level = 1;
        int timeLimit = 25; // Initial time limit in seconds
        int correctAnswers = 0; // Tracks the number of correct answers for level progression
        final int POINTS_TO_LEVEL_UP = 7; // Every 7 points, the difficulty increases
        final int TIME_DECREASE = 3; // Time reduction per level
        final int MINIMUM_TIME = 5; // Minimum time allowed per question
        // Loop through questions in random order
        for (String question : words) {
            // Clear the screen
            clearScreen();
            
            // Display score, level, and question
            System.out.println("Score: " + game.getScore() + "        |             Level: " + level);
            System.out.println("You have " + timeLimit + " seconds.");
            System.out.println("Translate this word: " + question);
            // Create a FutureTask to handle user input with a time limit
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(() -> {
                return scanner.nextLine(); // Get user input
            });
            try {
                // Wait for the user input or for the specified time limit
                String userAnswer = future.get(timeLimit, TimeUnit.SECONDS);
                // Check the answer
                if (userAnswer.equalsIgnoreCase(answers.get(question))) {
                    System.out.println("Correct!");
                    game.increaseScore();
                    correctAnswers++;
                } else {
                    System.out.println();
                    System.out.println("Incorrect answer.");
                    System.out.println("The correct answer was: " + answers.get(question));
                    System.out.println("Game Over!");
                    break; // End the game if the answer is wrong
                }
                // Level up logic: Increase level and reduce time limit after every 7 correct answers
                if (correctAnswers >= POINTS_TO_LEVEL_UP) {
                    level++;
                    correctAnswers = 0; // Reset the correct answers for the next level
                    if (timeLimit - TIME_DECREASE >= MINIMUM_TIME) {
                        timeLimit -= TIME_DECREASE; // Reduce time, but not below minimum
                    }
                }
            } catch (TimeoutException e) {
                System.out.println();
                System.out.println("Time's up!");
                System.out.println("The correct answer was: " + answers.get(question));
                System.out.println("Game Over!");
                future.cancel(true); // Cancel the input task
                break; // End the game if time runs out
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } finally {
                executor.shutdown(); // Shut down the executor
            }
            // Small pause to let the player read feedback before clearing the screen
            try {
                Thread.sleep(1000); // 1-second pause
            } catch (InterruptedException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        // Cleanup and close resources
        scanner.close();
        System.out.println("Thanks for playing!");
    }
}