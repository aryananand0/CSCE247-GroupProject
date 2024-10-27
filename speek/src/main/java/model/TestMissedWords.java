package model;

import java.util.ArrayList;
import java.util.List;

public class TestMissedWords {
    public static void main(String[] args) {
        // Step 1: Create a User instance
        User testUser = new User("john_doe", "John", "Doe", "john@example.com", "password123");

        // Step 2: Add a word to the missed words list
        System.out.println("Adding a missed word 'Hola'...");
        testUser.addMissedWord("Hola"); // Add the word "Hola" to missed words

        // Step 3: Check the list of missed words
        List<Word> missedWords = testUser.getMissedWords();

        // Display the missed words
        if (!missedWords.isEmpty()) {
            System.out.println("Missed words list:");
            for (Word word : missedWords) {
                System.out.println("Word: " + word.getWord() + ", Translation: " + word.getTranslation());
            }
        } else {
            System.out.println("No missed words found.");
        }

        // Step 4: Save the user data to JSON
        ArrayList<User> users = new ArrayList<>();
        users.add(testUser);

        // Save users to JSON file
        DataWriter.saveUsers(users);
        System.out.println("User data saved to user.json");
    }
}
