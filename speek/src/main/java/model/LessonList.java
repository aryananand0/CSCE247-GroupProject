package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * LessonList class manages a collection of Lesson objects, supporting operations such as adding, 
 * retrieving, and removing lessons. Implements Singleton pattern to maintain a single instance.
 */
public class LessonList {

    private ArrayList<Lesson> lessons;     // Collection of lessons
    private static LessonList instance;    // Singleton instance

    /**
     * Private constructor to load lessons via DataLoader. Ensures instantiation only through getInstance().
     */
    private LessonList() {
        lessons = DataLoader.loadLessons();
    }

    /**
     * Retrieves the singleton instance of LessonList, creating it if necessary.
     *
     * @return The single instance of LessonList.
     */
    public static LessonList getInstance() {
        if (instance == null) {
            instance = new LessonList();
        }
        return instance;
    }

    /**
     * Adds a lesson to the list if it is not already present.
     *
     * @param lesson The Lesson object to add.
     */
    public void addLesson(Lesson lesson) {
        if (lesson != null && !lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    /**
     * Retrieves questions associated with a specific lesson ID.
     *
     * @param lessonId The UUID of the lesson.
     * @return A list of Question objects or null if the lesson is not found.
     */
    public List<Question> getQuestions(UUID lessonId) {
        Lesson lesson = this.getLessonById(lessonId);
        return lesson != null ? lesson.getQuestions() : null;
    }

    /**
     * Retrieves a lesson by its unique ID.
     *
     * @param lessonId The UUID of the lesson.
     * @return The Lesson object if found; otherwise, null.
     */
    public Lesson getLessonById(UUID lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null;
    }

    /**
     * Retrieves a lesson by title, ignoring case sensitivity.
     *
     * @param lessonTitle The title of the lesson.
     * @return The Lesson object if found; otherwise, null.
     */
    public Lesson getLessonByTitle(String lessonTitle) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonTitle().equalsIgnoreCase(lessonTitle)) {
                return lesson;
            }
        }
        return null;
    }

    /**
     * Returns the total number of lessons.
     *
     * @return The count of lessons.
     */
    public int getTotalLessons() {
        return lessons.size();
    }

    /**
     * Removes a lesson by its title, ignoring case sensitivity.
     *
     * @param lessonTitle The title of the lesson to remove.
     * @return True if the lesson was found and removed; false otherwise.
     */
    public boolean removeLessonByTitle(String lessonTitle) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonTitle().equalsIgnoreCase(lessonTitle)) {
                lessons.remove(lesson);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of all lessons.
     *
     * @return An ArrayList containing all lessons.
     */
    public ArrayList<Lesson> getLessons() {
        return new ArrayList<>(this.lessons);
    }
}
