package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton class that manages a list of courses and provides methods to add, retrieve, and remove courses.
 * Ensures only one instance of the CourseList exists.
 */
public class CourseList {

    // Attributes
    private ArrayList<Course> courses;
    private static CourseList instance;

    /**
     * Private constructor that initializes the list of courses by loading from JSON.
     */
    private CourseList() {
        courses = DataLoader.loadCoursesFromJson();
    }

    /**
     * Retrieves the single instance of the CourseList class.
     * Creates a new instance if one doesn't already exist.
     * 
     * @return The singleton instance of CourseList.
     */
    public static CourseList getInstance() {
        if (instance == null) {
            instance = new CourseList();
        }
        return instance;
    }

    /**
     * Adds a course to the list.
     * 
     * @param course The course to add.
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Retrieves all courses in the list.
     * 
     * @return An ArrayList of all courses.
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Retrieves a specific course by its unique identifier (UUID).
     * 
     * @param uuid The UUID of the course.
     * @return The course if found; otherwise, null.
     */
    public Course getCourse(UUID uuid) {
        for (Course course : courses) {
            if (course.getCourseId().equals(uuid)) {
                return course;
            }
        }
        return null; // Course not found
    }

    /**
     * Retrieves a specific course by its name, case-insensitive.
     * 
     * @param courseName The name of the course.
     * @return The course if found; otherwise, null.
     */
    public Course getCourses(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        return null; // Course not found
    }

    /**
     * Removes a course by its name, case-insensitive.
     * 
     * @param courseName The name of the course to remove.
     * @return true if the course was removed; false if not found.
     */
    public boolean removeCourse(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                courses.remove(course);
                return true; // Course removed
            }
        }
        return false; // Course not found
    }

    /**
     * Gets the total number of courses in the list.
     * 
     * @return The number of courses.
     */
    public int getTotalCourses() {
        return courses.size();
    }
}
