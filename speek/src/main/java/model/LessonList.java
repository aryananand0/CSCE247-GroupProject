package model;

import java.util.ArrayList;
import java.util.UUID;

public class LessonList {

    // Attributes
    private ArrayList<Lesson> lessons;
    private static LessonList instance;

    // Constructor
    private LessonList() {
        lessons = DataLoader.loadLessons(); // Assuming DataLoader is used to load lessons from a JSON file
        
    }

    // Singleton pattern to ensure a single instance of LessonList
    public static LessonList getInstance() {
        if (instance == null) {
            instance = new LessonList();
        }
        return instance;
    }

    // Method to add a lesson to the list
    public void addLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    // Method to retrieve a lesson by its UUID
    public Lesson getLessonById(UUID lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null; // Return null if lesson not found
    }

    // Method to retrieve a lesson by its title
    public Lesson getLessonByTitle(String lessonTitle) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonTitle().equalsIgnoreCase(lessonTitle)) {
                return lesson;
            }
        }
        return null; // Return null if lesson not found
    }

    // Method to get the total number of lessons
    public int getTotalLessons() {
        return lessons.size();
    }

    // Method to remove a lesson by title
    public boolean removeLessonByTitle(String lessonTitle) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonTitle().equalsIgnoreCase(lessonTitle)) {
                lessons.remove(lesson);
                return true;  // Lesson removed
            }
        }
        return false;  // Lesson not found
    }

    // Get the list of all lessons
    public ArrayList<Lesson> getLessons() {
        return this.lessons;
    }
}