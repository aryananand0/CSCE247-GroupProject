package model;

public class TestLogout {
    public static void main(String[] args) {
        // Step 1: Create the facade instance to interact with the system
        LearningAppFacade facade = LearningAppFacade.getInstance();

        // Step 2: Log in as an existing user (e.g., johnDoe)
        System.out.println("Logging in as 'johnDoe'...");
        boolean loginSuccessful = facade.loginUser("johnDoe", "hashedpassword123") != null;

        if (loginSuccessful) {
            System.out.println("Login successful!");

            // Step 3: Add a missed word
            System.out.println("Adding a missed word: 'Hola'...");
            facade.getCurrentUser().addMissedWord("Hola");

            // Step 4: Update progress in a course
            System.out.println("Updating progress in course ID '890e1234-e89b-45d3-b789-123456789000' to 85%...");
            facade.getCurrentUser().updateUserProgress("890e1234-e89b-45d3-b789-123456789000", 85.0);

            // Step 5: Display the current user's progress
            System.out.println("Displaying current progress...");
            facade.getCurrentUser().displayProgress();

            // Step 6: Log out the user and save the data
            System.out.println("Logging out...");
            boolean logoutSuccessful = facade.logout();

            if (logoutSuccessful) {
                System.out.println("Logout successful! User data saved.");
            } else {
                System.out.println("Logout failed.");
            }

            // Step 7: Verify if the user data is saved correctly
            System.out.println("Checking if data is saved in user.json...");
            // You should manually inspect the user.json to verify that the changes are reflected.
        } else {
            System.out.println("Login failed.");
        }
    }
}
