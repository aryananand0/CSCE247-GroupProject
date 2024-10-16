package model;

import java.util.ArrayList;

public class CourseList {

    // Attributes
    private final ArrayList<Course> courses;

    private static CourseList instance;

    // Constructor
    private CourseList() {
        courses = new ArrayList<>();
        DataLoader.loadCourses();
    }

    /* This method will return an instance of this class 
     * @return new instance of this class
     *
     */
    public static CourseList getInstance() {
        if (instance == null) {
            instance = new CourseList();
        }
        return instance;
    }

    // Method to add a course to the list
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Method to retrieve all courses
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // Method to retrieve the courses from 
    // the specified user
    // TODO: Fix parameters (Stubbed for now)
    public ArrayList<Course> getCourse(String uuid) {
        // Find user with uuid 
        UserList ul = UserList.getInstance();
        User u = ul.getUser(uuid);
        if(u == null) {
            System.out.println("ERROR: COULD NOT FIND USER. CourseList.getCourse(uuid)");
        }
        return u.getCurrentCourses();
        // return the courses for that user 
    }

    // Method to get a specific course by name
    public Course getCourses(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        return null;  // Return null if course not found
    }

    // Method to remove a course by name
    public boolean removeCourse(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                courses.remove(course);
                return true;  // Return true if course was successfully removed
            }
        }
        return false;  // Return false if course not found
    }

    // Method to get the total number of courses
    public int getTotalCourses() {
        return courses.size();
    }
}