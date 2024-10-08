package model;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("");
        System.out.println("Loading users...");
        ArrayList<User> users = DataLoader.loadUsers();
        
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println(user.toString());
            }
        } else {
            System.out.println("No users found.");
        }
        System.out.println("");
        System.out.println("Loading courses...");
        ArrayList<Course> courses = DataLoader.loadCourses();
        
        if (!courses.isEmpty()) {
            System.out.println("Courses loaded successfully.");
            for (Course course : courses) {
                System.out.println( course.toString());
            }
        } else {
            System.out.println("No courses found.");
        }
        System.out.println("");

        System.out.println("Loading language...");
        ArrayList<Language> languages = DataLoader.loadLanguages();
        
        if (!languages.isEmpty()) {
            System.out.println("Courses loaded successfully.");
            for (Language language : languages) {
                System.out.println( language.toString());
            }
        } else {
            System.out.println("No courses found.");
        }
    }
}


