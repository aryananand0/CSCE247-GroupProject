package model;

import java.util.*;
import java.util.concurrent.*;

/**
 * GameTest class to run a RocketGame. Provides a timed translation quiz where players must answer correctly within a time limit.
 * The game difficulty increases after every 7 correct answers, reducing the time allowed for each question.
 */
public class GameTest {

    /**
     * Clears the console screen. Uses ANSI escape codes for Unix-based systems or prints newlines.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Main method to start the RocketGame. Loads game data, handles player inputs, and increases difficulty as the player progresses.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize game and load game data
        RocketGame game = new RocketGame();
        try {
            game.loadGameList();
        } catch (Exception e) {
            System.out.println("Failed to load game data: " + e.getMessage());
            return;
        }

        // Retrieve words and answers
        ArrayList<String> words = game.getWords();
        HashMap<String, String> answers = game.getAnswers();
        Collections.shuffle(words); // Randomize word order for the game

        try (Scanner scanner = new Scanner(System.in)) { // Use try-with-resources for automatic scanner closure
            int level = 1;
            int timeLimit = 25;
            int correctAnswers = 0;
            final int POINTS_TO_LEVEL_UP = 7;
            final int TIME_DECREASE = 3;
            final int MINIMUM_TIME = 5;

            for (String question : words) {
                clearScreen();
                System.out.printf("Score: %d        |             Level: %d%n", game.getScore(), level);
                System.out.printf("You have %d seconds.%n", timeLimit);
                System.out.println("Translate this word: " + question);

                // Manage timed user input with FutureTask
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<String> future = executor.submit(scanner::nextLine);
                try {
                    String userAnswer = future.get(timeLimit, TimeUnit.SECONDS);

                    if (userAnswer.equalsIgnoreCase(answers.get(question))) {
                        System.out.println("Correct!");
                        game.increaseScore();
                        correctAnswers++;
                    } else {
                        System.out.println("\nIncorrect answer.");
                        System.out.printf("The correct answer was: %s%n", answers.get(question));
                        System.out.println("Game Over!");
                        break;
                    }

                    // Level up: Increase difficulty after every 7 correct answers
                    if (correctAnswers >= POINTS_TO_LEVEL_UP) {
                        level++;
                        correctAnswers = 0;
                        if (timeLimit - TIME_DECREASE >= MINIMUM_TIME) {
                            timeLimit -= TIME_DECREASE;
                        }
                    }
                } catch (TimeoutException e) {
                    System.out.println("\nTime's up!");
                    System.out.printf("The correct answer was: %s%n", answers.get(question));
                    System.out.println("Game Over!");
                    future.cancel(true);
                    break;
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                } finally {
                    executor.shutdown();
                }

                // Pause to allow player to read feedback
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        }
        System.out.println("Thanks for playing!");
    }
}
