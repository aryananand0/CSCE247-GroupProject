package model;

import java.util.ArrayList;

public class CourseList {

    // Attributes
    private ArrayList<Course> courses;

    // Constructor
    public CourseList() {
        courses = new ArrayList<>();
    }

    // Method to add a course to the list
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Method to retrieve all courses
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // Method to get a specific course by name
    public Course getCourse(String courseName) {
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



