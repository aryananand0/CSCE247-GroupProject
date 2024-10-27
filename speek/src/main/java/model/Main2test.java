package model;

public class Main2test {
    
    enum QuestionType {
        MULTIPLE_CHOICE,
        TRUE_FALSE,
        FILL_IN_THE_BLANK,
        MATCHING
    }
    
    public static void main(String[] args) {
        // Assume Tim is already logged in
        UserList userList = UserList.getInstance();
        User tim = userList.getUser("ttom");
        String lessonId = "a1b2c3d4-e5f6-4a8b-9c0d-e1f2a3b4c5d6";
        tim.setCurrentLessonId(lessonId);
        System.out.println("Tim is now answering questions from Lesson 1...");

        // Simulate Tim answering 5 questions of at least 4 different types
        answerMultipleChoice(tim, true, null);       // Multiple Choice - Correct
        answerTrueFalse(tim, true, null);            // True/False - Correct
        answerFillInTheBlank(tim, false, "Hola");    // Fill in the Blank - Incorrect, missed "Hola"
        answerMatching(tim, true, null);             // Matching - Correct
        answerFillInTheBlank(tim, false, "Gracias"); // Fill in the Blank - Incorrect, missed "Gracias"

        // Display Tim's progress
        System.out.println("\nDisplaying Tim's progress...");
        tim.displayProgress();

        // Display missed words
        System.out.println("\nWords/Phrases Tim is struggling with:");
        for (Word missedWord : tim.getMissedWords()) {
            System.out.println("Word: " + missedWord.getWord() + ", Translation: " + missedWord.getTranslation());
        }

        // Tim logs out
        System.out.println("\nTim is logging out...");
        boolean loggedOut = userList.logout();
        if (loggedOut) {
            System.out.println("Tim successfully logged out.");
        } else {
            System.out.println("Error logging out Tim.");
        }
    }

    // Method for answering a Multiple Choice question
    private static void answerMultipleChoice(User user, boolean isCorrect, String missedWord) {
        System.out.println("Answering a Multiple Choice question...");
        answerQuestion(user, isCorrect, missedWord);
    }

    // Method for answering a True/False question
    private static void answerTrueFalse(User user, boolean isCorrect, String missedWord) {
        System.out.println("Answering a True/False question...");
        answerQuestion(user, isCorrect, missedWord);
    }

    // Method for answering a Fill in the Blank question
    private static void answerFillInTheBlank(User user, boolean isCorrect, String missedWord) {
        System.out.println("Answering a Fill in the Blank question...");
        answerQuestion(user, isCorrect, missedWord);
    }

    // Method for answering a Matching question
    private static void answerMatching(User user, boolean isCorrect, String missedWord) {
        System.out.println("Answering a Matching question...");
        answerQuestion(user, isCorrect, missedWord);
    }

    // Method to handle the actual question answering
    private static void answerQuestion(User user, boolean isCorrect, String missedWord) {
        if (isCorrect) {
            System.out.println("Correct answer!");
        } else {
            System.out.println("Incorrect answer! Adding missed word...");
            if (missedWord != null) {
                user.addMissedWord(missedWord); // Assuming this method is in User class
            }
        }
    }    
}
