package model;

import java.util.ArrayList;

public class MainU {
    public static void main(String[] args) {
        // Test the loadUsers method
        ArrayList<User> users = DataLoader.loadUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
        if (users != null) {
            for (User user : users) {
                System.out.println("=== User Info ===");
                System.out.println(user.toString());

                System.out.println("\n--- Current Courses ---");
                for (Course course : user.getCurrentCourses()) {
                    System.out.println(course.toString());
                }

                System.out.println("\n--- Question History ---");
                for (QuestionHistory qh : user.getQuestionHistory()) {
                    System.out.println(qh.toString());
                }

                System.out.println("========================\n");
            }
        } else {
            System.out.println("No users loaded.");
        }
    }

    }

