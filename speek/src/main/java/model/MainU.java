package model;

import java.util.ArrayList;

public class MainU {
    public static void main(String[] args) {
        LearningAppFacade facade = LearningAppFacade.getInstance();
        ArrayList<User> users = facade.loadUsers();
        String u = users.get(1).getCurrentCourses().get(0).getCourseName();
        System.out.println(u);
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

                System.out.println("\n--- Missed Words ---");
                for (Word qh : user.getMissedWords()) {
                    System.out.println(qh.toString());
                }

                System.out.println("========================\n");
            }
        }
    }

    }

