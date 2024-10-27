package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.io.FileWriter;
import java.io.IOException;


public class Main3 {


   private static LearningAppFacade facade = LearningAppFacade.getInstance();
   private static ArrayList<User> users;

   public static void main(String[] args) {


        // Step 2: She logs into her Account
        User tammy = facade.loginUser("ttomacka", "hashedpassword789");
        if(tammy == null) {
            System.out.println("Error: User does not exist. ");
        }

        // Step 2: Display Tammy's progress
        tammy.displayProgress();

        // Step 3: Review the struggling items
        List<Word> wordsToReview = tammy.getMissedWords();
        List<String> phrasesToReview = tammy.getStruggingPhrases();

        // Setp 4: Print study sheet to text file
        try(FileWriter writer = new FileWriter("study_sheet.txt")) {
            writer.write("Study Sheet for Tammy\n");
            writer.write("Words:\n");
            for(Word word : wordsToReview) {
                writer.write("- " + word + "\n");
            }
            writer.write("Phrases:\n");
            for(String phrase : phrasesToReview) {
                writer.write("- " + phrase + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step5: Generate Questoins for words and phrases 
        HashMap<String, Boolean> reviewResults = new HashMap<>();
        for(String word : wordsToReview) {
            boolean correct = askQuestion(word);
            reviewResults.put(word, correct);
        }
        for(String phrase : phrasesToReview) {
            boolean correct = askQuestion(phrase);
            reviewResults.put(phrase, correct);
        }
   }

   private static boolean askQuestion(String item) {
    // TODO: Create method somewhere else that can askQuestoins based on 
    // the missed questions
    return true;
   }


   private static void reloadAndDisplayUsers() {
       System.out.println("Loading User:");
       
       ArrayList<User> reloadedUsers = facade.loadUsers();
       for (User user : reloadedUsers) {
           System.out.println(user.toString());
       }
       System.out.println("");
   }


   private static void displayLoginResult(String usernameOrEmail, User user) {
       if (user != null) {
           System.out.println("Login successful for: " + usernameOrEmail);
       } else {
           System.out.println("Login failed. Invalid credentials.");
       }
       System.out.println("");
   }
}