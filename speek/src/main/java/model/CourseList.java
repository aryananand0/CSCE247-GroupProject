package model;

import java.util.ArrayList;

public class CourseList {

    private ArrayList<Course> courses;

    // Constructor
    public CourseList() {
        courses = new ArrayList<>();
    }

    // Add a course to the list
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Get all courses
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // Get a specific course by name
    public Course getCourseByName(String courseName) {
        
        return null;
    }
}

