package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Represents the Rocket Game, which loads questions and answers from a JSON file
 * and tracks the user's score.
 */
public class RocketGame extends DataConstants {
    private ArrayList<String> words;          // List of questions (words to translate)
    private HashMap<String, String> answers;  // Map of questions to their correct answers
    private int score;                        // Current game score

    private static final String[] DIFFICULTY_LEVELS = {GAME_EASY, GAME_MED, GAME_HARD};

    /**
     * Default constructor initializing empty lists for words and answers and setting score to zero.
     */
    public RocketGame() {
        this.words = new ArrayList<>();
        this.answers = new HashMap<>();
        this.score = 0;
    }

    /**
     * Constructor initializing the game with a provided list of words and answers.
     *
     * @param words   List of words (questions) to initialize with.
     * @param answers Map of answers for each question.
     */
    public RocketGame(ArrayList<String> words, HashMap<String, String> answers) {
        this.words = words;
        this.answers = answers;
        this.score = 0;
    }

    /**
     * Loads the questions and answers from the specified JSON file into the game.
     * Assumes the file contains entries categorized by difficulty levels.
     */
    public void loadGameList() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(GAME_FILE)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            for (String difficulty : DIFFICULTY_LEVELS) {
                JSONArray levelArray = (JSONArray) jsonObject.get(difficulty);
                if (levelArray == null) {
                    System.out.println("Warning: No data found for difficulty level: " + difficulty);
                    continue;
                }

                for (Object element : levelArray) {
                    JSONObject qaPair = (JSONObject) element;
                    String question = (String) qaPair.get(GAME_QUESTION);
                    String answer = (String) qaPair.get(GAME_ANSWER);

                    if (question != null && answer != null) {
                        words.add(question);
                        answers.put(question, answer);
                    }
                }
            }
            System.out.println("Game data loaded successfully.");

        } catch (IOException e) {
            System.err.println("Error: Unable to read game file: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error: Failed to parse game file. Check JSON format: " + e.getMessage());
        }
    }

    /**
     * Returns the list of questions (words) in the game.
     *
     * @return The list of words.
     */
    public ArrayList<String> getWords() {
        return new ArrayList<>(words); // Return a copy to avoid external modification
    }

    /**
     * Sets the list of questions (words) for the game.
     *
     * @param words The list of words to set.
     */
    public void setWords(ArrayList<String> words) {
        this.words = new ArrayList<>(words); // Create a copy to avoid external modification
    }

    /**
     * Returns the map of questions and their correct answers.
     *
     * @return The map of answers.
     */
    public HashMap<String, String> getAnswers() {
        return new HashMap<>(answers); // Return a copy for safety
    }

    /**
     * Sets the map of questions and answers for the game.
     *
     * @param answers The map of answers to set.
     */
    public void setAnswers(HashMap<String, String> answers) {
        this.answers = new HashMap<>(answers); // Create a copy to avoid external modification
    }

    /**
     * Returns the current score.
     *
     * @return The score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Increments the game score by 1.
     */
    public void increaseScore() {
        this.score++;
    }
}
