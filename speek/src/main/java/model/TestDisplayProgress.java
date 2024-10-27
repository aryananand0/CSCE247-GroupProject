package model;

import java.util.ArrayList;
import java.util.List;

public class TestDisplayProgress {
    public static void main(String[] args) {
        // Step 1: Create a User instance
        User testUser = new User("john_doe", "John", "Doe", "john@example.com", "password123");

        // Step 2: Set up current course and lesson
        testUser.setCurrentCourseId("890e1234-e89b-45d3-b789-123456789000");
        testUser.setCurrentLessonId("b8aab9e3-44c1-40ba-a093-4d046079de83");

        // Step 3: Add some completed courses and lessons
        List<String> completedCourses = new ArrayList<>();
        completedCourses.add("123e4567-e89b-12d3-a456-426614174000");
        testUser.setCompletedCourseIds(completedCourses);

        List<String> completedLessons = new ArrayList<>();
        completedLessons.add("lesson1");
        testUser.setCompletedLessonIds(completedLessons);

        // Step 4: Add some missed words
        Word missedWord1 = new Word("Hola", "Hello");
        Word missedWord2 = new Word("Gracias", "Thank you");
        testUser.addMissedWord(missedWord1.getWord());
        testUser.addMissedWord(missedWord2.getWord());

        // Step 5: Call the displayProgress method
        System.out.println("Displaying user progress:");
        testUser.displayProgress();
    }
}
