package model;

import java.util.ArrayList;

public class LearningAppFacade {
    // Attributes
    // private ArrayList<User> users;
    // private ArrayList<Course> courses;
    // private User currentUser;
    private UserList user;
    private CourseList course;

    // Constructor
    public LearningAppFacade() {
        user = UserList.getInstance();
        course = CourseList.getInstance();
    }

    // Methods

    // Registers a new user with email and password, returns the created User object
    public boolean registerUser(String username, String firstName, String lastName,String email, String password) {
        if(!user.haveUser(username)){
            return user.addUser(username, firstName, lastName, email, password);
        }
        
        return false;  
    }

    // Logs in a user with email and password, returns the User object if successful
    public User loginUser(String usernameOrEmail, String password) {
        if(user.LoginCheck(usernameOrEmail, password)){
            return DataLoader.getUser(usernameOrEmail, password);
        }
        return null;  
    }



    // Enrolls a user in a course
    public void enrollUserInCourse(User user, Course course) {
        // Method stub
    }

    // Tracks the user's progress in a specific course, returns progress as a double
    public double trackUserProgress(User user, Course course) {
        // Method stub
        return 0.0;  // Placeholder return
    }

    // Allows the user to take a quiz
    public void takeQuiz(User user, Quiz quiz) {
        // Method stub
    }

    // Retrieves the leaderboard as an ArrayList of users
    public ArrayList<User> getLeaderboard() {
        // Method stub
        return new ArrayList<>();  // Placeholder return
    }

    // Logs out the current user
    public static boolean logout() {
        // Method stub
        return false;
    }
}
