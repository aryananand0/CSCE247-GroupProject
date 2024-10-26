package model;

import model.User;
import model.UserList;

public class Main1 {

    public static void main(String[] args) {
        // Singleton instance of UserList to manage users
        UserList userList = UserList.getInstance();

        // 1. Check if Tammy Tomacka exists and add her if she's not in users.json
        System.out.println("Checking existing users...");
        User tammy = userList.getUser("ttomacka","passwordTammy");
        if (tammy == null) {
            System.out.println("Tammy Tomacka is not in users.json. Adding Tammy...");
            boolean tammyAdded = userList.addUser("ttomacka", "Tammy", "Tomacka", "tammy.tomacka@example.com", "passwordTammy");
            if (tammyAdded) {
                System.out.println("Tammy Tomacka successfully added to users.json.");
            } else {
                System.out.println("Error: Failed to add Tammy Tomacka. Check if the username 'ttomacka' is already in use.");
                return;
            }
        } else {
            System.out.println("Tammy Tomacka is already present in users.json.");
        }

        // 2. Check if Tim Tomacka already exists
        User tim = userList.getUser("Tim Tomacka");
        if (tim != null) {
            System.out.println("Tim Tomacka already exists in users.json. Please remove him before running this scenario.");
            return;
        }

        // 3. Attempt to create a user with the username "ttomacka" (should fail since Tammy uses it)
        System.out.println("\nAttempting to create user with username 'ttomacka'...");
        boolean isAdded = userList.addUser("ttomacka", "Tim", "Tomacka", "tim.tomacka@example.com", "password123");

        if (!isAdded) {
            System.out.println("User creation failed: Username 'ttomacka' already exists.");
        } else {
            System.out.println("Error: User 'ttomacka' was added despite a duplicate username. Check the UserList class.");
            return;
        }

        // 4. Create a user with the username "ttom"
        System.out.println("\nCreating user with username 'ttom'...");
        isAdded = userList.addUser("ttom", "Tim", "Tomacka", "tim.tomacka@example.com", "password123");

        if (isAdded) {
            System.out.println("User 'ttom' successfully created.");

            // Automatically log in the newly created user
            System.out.println("Automatically logging in 'ttom'...");
            boolean loginSuccess = userList.loginCheck("tim.tomacka@example.com", "password123");
            if (loginSuccess) {
                System.out.println("Login successful: 'ttom' is now logged in.");
            } else {
                System.out.println("Error: Automatic login failed for user 'ttom'.");
                return;
            }
        } else {
            System.out.println("Error: Failed to create user 'ttom'. Check for issues in the addUser method.");
            return;
        }

        // 5. Log out the user
        System.out.println("\nLogging out the user...");
        boolean isLoggedOut = userList.logout();
        if (isLoggedOut) {
            System.out.println("User successfully logged out.");
        } else {
            System.out.println("Error: Logout failed.");
            return;
        }

        // 6. Verify that the user "ttom" is saved in users.json
        User savedUser = userList.getUser("ttom");
        if (savedUser != null) {
            System.out.println("Verification successful: 'ttom' exists in users.json.");
        } else {
            System.out.println("Error: User 'ttom' was not found in users.json after saving.");
            return;
        }

        // 7. Log in the user "ttom" with email and password
        System.out.println("\nAttempting to log in with 'ttom'...");
        boolean loginSuccess = userList.loginCheck("tim.tomacka@example.com", "password123");

        if (loginSuccess) {
            System.out.println("Login successful: 'ttom' logged in successfully.");
        } else {
            System.out.println("Error: Login failed for user 'ttom'.");
        }
    }
}