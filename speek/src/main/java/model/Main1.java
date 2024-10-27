package model;

import java.util.ArrayList;

import model.User;
import model.UserList;

/**
 * The {@code Main1} class is the entry point of the language learning application.
 * It demonstrates the basic functionalities of loading users, creating a new user,
 * logging in, logging out, and verifying user data through the {@code LearningAppFacade}.
 * 
 * This class also includes error handling for duplicate usernames and failed user actions,
 * providing feedback on the success or failure of each operation.
 * 
 * Dependencies:
 * - {@code User}: Represents a user in the application.
 * - {@code UserList}: Manages a list of users as a singleton.
 * - {@code LearningAppFacade}: Serves as a facade for managing users and their actions.
 * 
 * @version 1.0
 */
public class Main1 {

    /**
     * The main method serves as the entry point of the application.
     * It performs a sequence of user management tasks such as loading users,
     * creating a user, logging in, and logging out.
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Singleton instance of UserList to manage users
        System.out.println("Loading users...");
        ArrayList<User> users = new ArrayList<>(); 
        LearningAppFacade facade = LearningAppFacade.getInstance();
        users = facade.loadUsers();

        // Check if users were loaded successfully
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println(user.toString());
            }
        } else {
            System.out.println("No users found.");
        }

        // 1. Attempt to create a user with the username "ttomacka" (should fail if username is taken)
        System.out.println("\nAttempting to create user with username 'ttomacka'...");
        boolean isAdded = facade.registerUser("ttomacka", "Tim", "Tomacka", "tim.tomacka@example.com", "password123");

        if (!isAdded) {
            System.out.println("User creation failed: Username 'ttomacka' already exists.");
        } else {
            System.out.println("Error: User 'ttomacka' was added despite a duplicate username. Check the UserList class.");
            return;
        }

        // 2. Create a user with the username "ttom"
        System.out.println("\nCreating user with username 'ttom'...");
        isAdded = facade.registerUser("ttom", "Tim", "Tomacka", "tim.tomacka@example.com", "password123");

        if (isAdded) {
            System.out.println("User 'ttom' successfully created.");

            // Automatically log in the newly created user
            System.out.println("Automatically logging in 'ttom'...");
            User user = facade.loginUser("tim.tomacka@example.com", "password123");
            if (user != null) {
                System.out.println("Login successful: 'ttom' is now logged in.");
            } else {
                System.out.println("Error: Automatic login failed for user 'ttom'.");
                return;
            }
        } else {
            System.out.println("Error: Failed to create user 'ttom'. Check for issues in the addUser method.");
            return;
        }

        // 3. Log out the user
        System.out.println("\nLogging out the user...");
        boolean isLoggedOut = facade.logout();
        if (isLoggedOut) {
            System.out.println("User successfully logged out.");
        } else {
            System.out.println("Error: Logout failed.");
            return;
        }

        // 4. Verify that the user "ttom" is saved in users.json
        System.out.println("Loading users...");
        LearningAppFacade facades = LearningAppFacade.getInstance();
        users = facades.loadUsers();
        if (!users.isEmpty()) {
            System.out.println("Users loaded successfully.");
            for (User user : users) {
                System.out.println(user.toString());
            }
        } else {
            System.out.println("No users found.");
        }

        // 5. Log in the user "ttom" with email and password
        System.out.println("\nAttempting to log in with 'ttom'...");
        User user = facade.loginUser("tim.tomacka@example.com", "password123");

        if (user != null) {
            System.out.println("Login successful: 'ttom' logged in successfully.");
        } else {
            System.out.println("Error: Login failed for user 'ttom'.");
        }
    }
}
