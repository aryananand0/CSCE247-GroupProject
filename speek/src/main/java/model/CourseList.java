package model;

import java.util.ArrayList;
import java.util.UUID;

public class CourseList {

    // Attributes
    private ArrayList<Course> courses;

    private static CourseList instance;

    // Constructor
    private CourseList() {
        courses = DataLoader.loadCoursesFromJson();
    }

    
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
    public Course getCourse(UUID uuid) {
        // Find user with uuid 
        for(Course course : courses){
            if(course.getCourseId().equals(uuid)){
                return course;
            }
        }

        return null;
    }

    

    // Method to get a specific course by name
    public Course getCourses(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        return null;  
    }

    // Method to remove a course by name
    public boolean removeCourse(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                courses.remove(course);
                return true;  
            }
        }
        return false;  
    }

    // Method to get the total number of courses
    public int getTotalCourses() {
        return courses.size();
    }
}