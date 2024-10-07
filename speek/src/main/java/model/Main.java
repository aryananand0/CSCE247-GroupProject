package model;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Load basic user information from the file
        System.out.println("Loading users...");
        ArrayList<User> users = DataLoader.loadUsers();
        
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
            }
        } else {
            System.out.println("No users found.");
        }

        // Load basic course information from the file
        System.out.println("Loading courses...");
        ArrayList<Course> courses = DataLoader.loadCourses();
        
        if (!courses.isEmpty()) {
            System.out.println("Courses loaded successfully.");
            for (Course course : courses) {
                System.out.println("Course: " + course.getCourseName());
            }
        } else {
            System.out.println("No courses found.");
        }
    }
}

