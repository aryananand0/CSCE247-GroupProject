package model;

import java.util.List;

public class TestDataLoader {

    public static void main(String[] args) {
        // Test the parseWordsFromJson method
        testParseWords();
    }

    private static void testParseWords() {
        // Call the parseWordsFromJson method
        List<Course> courses = DataLoader.parseWordsFromJson();
        
        // Check if the list is not empty
        if (courses == null || courses.isEmpty()) {
            System.out.println("❌ Parsing failed: No courses found.");
            return;
        }

        // Print the parsed data to verify
        System.out.println("✅ Parsing succeeded! Courses and their lessons:");
        for (Course course : courses) {
            System.out.println("Course: " + course.getCourseName());
            for (Lesson lesson : course.getLessons()) {
                System.out.println("  Lesson: " + lesson.getLessonTitle());

                // Display words for each lesson
                if (lesson.getWords().isEmpty()) {
                    System.out.println("    No words found for this lesson.");
                } else {
                    System.out.println("    Words:");
                    for (Word word : lesson.getWords()) {
                        System.out.println("      Word: " + word.getWord() + ", Translation: " + word.getTranslation());
                    }
                }
            }
        }
    }
}
