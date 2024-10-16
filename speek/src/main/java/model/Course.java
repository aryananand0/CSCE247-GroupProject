package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {

    // Attributes
    private final String courseId;           // Unique identifier for the course
    private String courseName;
    private String difficulty;
    private List<Lesson> lessons;            // List of lessons in the course
    private double courseCompletion;         // Represents completion percentage (0.0 to 100.0)

    // Constructor with parameters (auto-generates courseId)
    public Course(String courseName, String difficulty) {
        this.courseId = java.util.UUID.randomUUID().toString(); // Automatically generate a unique courseId
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    public Course(String courseName, String difficulty, double completion) {
        this.courseId = java.util.UUID.randomUUID().toString(); // Automatically generate a unique courseId
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = completion;
    }

    // Constructor with predefined courseId (useful for deserialization)
    public Course(String courseId, String courseName, String difficulty, double courseCompletion) {
        this.courseId = courseId != null ? courseId : java.util.UUID.randomUUID().toString();
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = courseCompletion;
    }

    // Default Constructor
    public Course() {
        this.courseId = java.util.UUID.randomUUID().toString(); // Automatically generate a unique courseId
        this.courseName = "";
        this.difficulty = "";
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    /**
     * Adds a lesson to the course.
     *
     * @param lesson The lesson to add.
     */
    public void addLesson(Lesson lesson) {
        if (lesson != null && !this.lessons.contains(lesson)) {
            this.lessons.add(lesson);
            System.out.println("📖 Lesson \"" + lesson.getLessonTitle() + "\" added to course \"" + courseName + "\".");
        }
    }

    /**
     * Retrieves a lesson by its lessonId.
     *
     * @param lessonId The ID of the lesson to retrieve.
     * @return The Lesson object if found, null otherwise.
     */
    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null; // Lesson not found
    }

    /**
     * Updates the course completion percentage based on the user's completed lessons.
     *
     * @param user The user whose progress is being tracked.
     */
    public void updateCourseCompletion(User user) {
        if (lessons.isEmpty()) {
            this.courseCompletion = 0.0;
            return;
        }
        int completedLessons = 0;
        for (Lesson lesson : lessons) {
            if (user.getCompletedLessonIds().contains(lesson.getLessonId())) {
                completedLessons++;
            }
        }
        this.courseCompletion = ((double) completedLessons / lessons.size()) * 100.0;
    }

    /**
     * Checks if the course is completed by the user.
     *
     * @param user The user whose progress is being checked.
     * @return True if the course is completed, false otherwise.
     */
    public boolean isCourseCompleted(User user) {
        // Assuming a course is completed when all its lessons are completed
        for (Lesson lesson : lessons) {
            if (!user.getCompletedLessonIds().contains(lesson.getLessonId())) {
                return false;
            }
        }
        return true;
    }

    // Getters and Setters

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
    }

    public double getCourseCompletion() {
        return courseCompletion;
    }

    public void setCourseCompletion(double courseCompletion) {
        if (courseCompletion < 0.0) {
            this.courseCompletion = 0.0;
        } else if (courseCompletion > 100.0) {
            this.courseCompletion = 100.0;
        } else {
            this.courseCompletion = courseCompletion;
        }
    }

    // Overriding equals and hashCode based on courseId to ensure uniqueness
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return Objects.equals(courseId, course.courseId);
    }


    @Override
    public int hashCode() {
        return courseId != null ? courseId.hashCode() : 0;
    }

    // Overriding toString() method to display course details succinctly
    @Override
    public String toString() {
        return "Course ID: " + courseId +
                " | Course Name: " + courseName +
                " | Difficulty: " + difficulty +
                " | Completion: " + String.format("%.2f", courseCompletion) + "%";
    }
}
