package model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;

public class LearningAppFacade {

    private UserList user;
    private CourseList course;
    private static final String USER_FILE_PATH = "/json/User.json";
    private User currentUser;  // Store the current logged-in user

    // Constructor
    public LearningAppFacade() {
        user = UserList.getInstance();
        course = CourseList.getInstance();
    }

    // Methods

    // Registers a new user with email and password, returns the created User object
    public boolean registerUser(String username, String firstName, String lastName,String email, String password) {
        if(!user.haveUser(username)) {
            boolean isUserAdded = user.addUser(username, firstName, lastName, email, password);
            if(isUserAdded) {
                addUserToJson(username, firstName, lastName, email, password);
                return true;
            }
        }  
        return false;
    }

    private void addUserToJson(String username, String firstName, String lastName, String email, String password) {
        try {
            String s = new String(Files.readAllBytes(Paths.get(USER_FILE_PATH)));
            JSONArray usersArray = new JSONArray(s);

            // Create a new user
            JSONObject newUser = new JSONObject();
            newUser.put("userId", UUID.randomUUID().toString());
            newUser.put("userName", username);
            newUser.put("firstName", firstName);
            newUser.put("lastName", lastName);
            newUser.put("email", email);
            newUser.put("password", password);
            newUser.put("progress", 0);
            newUser.put("dailyReminder", false);
            newUser.put("favoriteLanguages", new JSONArray());

            // Start each new user with a fresh wipe
            newUser.put("currentCourses", new JSONArray());
            newUser.put("achievments", new JSONArray());
            newUser.put("questionHistory", new JSONArray());
            newUser.put("currentQuestion", JSONObject.NULL);

            usersArray.put(newUser);

            try (FileWriter file = new FileWriter(USER_FILE_PATH)) {
                file.write(usersArray.toString(4));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Logs in a user with username or email and password
    public User loginUser(String usernameOrEmail, String password) {
        // Check if login is valid
        if (user.LoginCheck(usernameOrEmail, password)) {
            currentUser = DataLoader.getUser(usernameOrEmail, password);  // Retrieve the user
            return currentUser;  // Return the user on successful login
        }
        return null;  // Return null on login failure
    }

    // Method to get the currently logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Logs out the current user
    public static boolean logout() {
        LearningAppFacade facade = new LearningAppFacade();
        if (facade.getCurrentUser() != null) {
            facade.currentUser = null;
            return true;
        }
        return false;
    }

    // Enrolls a user in a course
    public void enrollUserInCourse(User user, Course course) {
        // Method stub
    }

    // Tracks the user's progress in a specific course
    public double trackUserProgress(User user, Course course) {
        // Method stub
        return 0.0;  // Placeholder return
    }

    // Allows the user to take a quiz
    public void takeQuiz(User user, Quiz quiz) {
        // Method stub
    }

    // Retrieves the leaderboard
    public ArrayList<User> getLeaderboard() {
        // Method stub
        return new ArrayList<>();  // Placeholder return
    }
}
