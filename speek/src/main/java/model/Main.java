package model;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("");
        
        // Loading users
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
        
        // Loading courses
        System.out.println("Loading courses...");
        ArrayList<Course> courses = DataLoader.loadCourses();
        
        if (!courses.isEmpty()) {
            System.out.println("Courses loaded successfully.");
            for (Course course : courses) {
                System.out.println(course.toString());
            }
        } else {
            System.out.println("No courses found.");
        }
        
        System.out.println("");

        // Loading languages
        System.out.println("Loading languages...");
        ArrayList<Language> languages = DataLoader.loadLanguages();
        
        if (!languages.isEmpty()) {
            System.out.println("Languages loaded successfully.");
            for (Language language : languages) {
                System.out.println(language.toString());
            }
        } else {
            System.out.println("No languages found.");
        }

        System.out.println("");

        // Loading Leader Board
        System.out.println("Loading Leader Board...");
        Leaderboard lead = DataLoader.loadLeaderboard();
        
        if (!lead.getUser().isEmpty()) {
            System.out.println("Leader Board loaded successfully.");
            for (User user : lead.getUser()) {
                System.out.println(user.PrintLeaderboard());
            }
        } else {
            System.out.println("No Leader Board found.");
        }

        System.out.println("");

        // Loading Achievements
        System.out.println("Loading Achievements...");

        ArrayList<Achievements> achievements = DataLoader.loadAchievements();
        
        if (!achievements.isEmpty()) {
            System.out.println("Achievements loaded successfully.");
            for (Achievements achiv : achievements) {
                System.out.println(achiv.toString());
            }
        } else {
            System.out.println("No Achievements found.");
        }
    }
}
