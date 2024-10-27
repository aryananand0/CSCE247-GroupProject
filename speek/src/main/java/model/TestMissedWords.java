package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestMissedWords {
    public static void main(String[] args) {
        // Step 1: Create a User instance with a fixed USER_ID
        UUID existingUserId = UUID.fromString("0d2f8294-0021-4f1b-92c0-3cad8a1db9f4"); // Replace with the correct USER_ID
        User testUser = new User(existingUserId, "john_doe", "John", "Doe");

        // Step 2: Add a word to the missed words list
        System.out.println("Adding a missed word 'Hola'...");
        testUser.addMissedWord("Hola");

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

        // Save the updated user back to JSON
        ArrayList<User> users = new ArrayList<>();
        users.add(testUser);
        DataWriter.saveUsers(users);
        System.out.println("User data saved to user.json");
    }
}
