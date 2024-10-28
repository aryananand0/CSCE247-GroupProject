package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a course with a unique ID, name, difficulty level, and list of lessons.
 * Tracks course completion progress and provides methods to manage lessons within the course.
 */
public class Course {

    // Attributes
    private UUID courseId;
    private String courseName;
    private String difficulty;
    private List<Lesson> lessons;
    private double courseCompletion;

    /**
     * Constructs a Course with a generated UUID, specified name, and difficulty level.
     * 
     * @param courseName The name of the course.
     * @param difficulty The difficulty level of the course.
     */
    public Course(String courseName, String difficulty) {
        this.courseId = UUID.randomUUID();
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    /**
     * Constructs a Course with a specified UUID and course name.
     * 
     * @param courseUUID The UUID of the course.
     * @param courseName The name of the course.
     */
    public Course(UUID courseUUID, String courseName) {
        this.courseId = courseUUID;
        this.courseName = courseName;
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    /**
     * Constructs a Course with a generated UUID, specified name, difficulty, and completion.
     * 
     * @param courseName       The name of the course.
     * @param difficulty       The difficulty level of the course.
     * @param courseCompletion The completion percentage of the course.
     */
    public Course(String courseName, String difficulty, double courseCompletion) {
        this.courseId = UUID.randomUUID();
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = courseCompletion;
    }

    /**
     * Constructs a Course with specified attributes.
     * 
     * @param courseId         The UUID of the course.
     * @param courseName       The name of the course.
     * @param difficulty       The difficulty level of the course.
     * @param courseCompletion The completion percentage of the course.
     */
    public Course(UUID courseId, String courseName, String difficulty, double courseCompletion) {
        this.courseId = courseId != null ? courseId : UUID.randomUUID();
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = courseCompletion;
    }

    /**
     * Default constructor that initializes a Course with default values.
     */
    public Course() {
        this.courseId = UUID.randomUUID();
        this.courseName = "";
        this.difficulty = "";
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    /**
     * Adds a lesson to the course if it is not null and not already present.
     * 
     * @param lesson The lesson to be added.
     */
    public void addLesson(Lesson lesson) {
        if (lesson != null && !this.lessons.contains(lesson)) {
            this.lessons.add(lesson);
        }
    }

    /**
     * Retrieves a lesson by its ID.
     * 
     * @param lessonId The ID of the lesson.
     * @return The lesson if found; otherwise, null.
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
     * Gets the unique identifier of the course.
     * 
     * @return The UUID of the course.
     */
    public UUID getCourseId() {
        return courseId;
    }

    /**
     * Gets the name of the course.
     * 
     * @return The course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course.
     * 
     * @param courseName The course name to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the difficulty level of the course.
     * 
     * @return The difficulty level.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the course.
     * 
     * @param difficulty The difficulty level to set.
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Retrieves the list of lessons in the course.
     * 
     * @return A list of lessons.
     */
    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    /**
     * Sets the list of lessons for the course.
     * 
     * @param lessons The list of lessons to set.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
    }

    /**
     * Gets the completion percentage of the course.
     * 
     * @return The completion percentage.
     */
    public double getCourseCompletion() {
        return courseCompletion;
    }

    /**
     * Sets the completion percentage for the course. Values are clamped between 0 and 100.
     * 
     * @param courseCompletion The completion percentage to set.
     */
    public void setCourseCompletion(double courseCompletion) {
        if (courseCompletion < 0.0) {
            this.courseCompletion = 0.0;
        } else if (courseCompletion > 100.0) {
            this.courseCompletion = 100.0;
        } else {
            this.courseCompletion = courseCompletion;
        }
    }

    /**
     * Checks equality based on the course ID.
     * 
     * @param o The object to compare with.
     * @return true if the objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return Objects.equals(courseId, course.courseId);
    }

    /**
     * Generates a hash code based on the course ID.
     * 
     * @return The hash code of the course.
     */
    @Override
    public int hashCode() {
        return courseId != null ? courseId.hashCode() : 0;
    }

    /**
     * Returns a string representation of the course details.
     * 
     * @return A string with course ID, name, difficulty, and completion percentage.
     */
    @Override
    public String toString() {
        return "Course ID: " + courseId +
                " | Course Name: " + courseName +
                " | Difficulty: " + difficulty +
                " | Completion: " + String.format("%.2f", courseCompletion) + "%";
    }
}
