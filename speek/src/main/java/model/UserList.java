package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Manages a list of users within the system using the Singleton design pattern.
 * Provides functionalities to add, remove, authenticate, and manage user data.
 */
public class UserList {

    // Singleton instance
    private static UserList userList = null;

    // List of all users
    private ArrayList<User> users;

    // Currently logged-in user
    private User currentUser;

    /**
     * Private constructor to prevent external instantiation.
     * Loads users from the data source upon creation.
     */
    private UserList() {
        users = DataLoader.loadUsers();
    }

    /**
     * Retrieves the singleton instance of UserList.
     * If the instance doesn't exist, it initializes a new one.
     *
     * @return The singleton instance of UserList.
     */
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    /**
     * Adds a new user to the user list.
     * Ensures that the username is unique before adding.
     *
     * @param username  The username of the new user.
     * @param firstName The first name of the new user.
     * @param lastName  The last name of the new user.
     * @param email     The email address of the new user.
     * @param password  The password for the new user's account.
     * @return True if the user was added successfully; false if the username already exists.
     */
    public boolean addUser(String username, String firstName, String lastName, String email, String password) {
        if (haveUser(username)) {
            return false; // User already exists
        } else {
            users.add(new User(username, firstName, lastName, email, password));
            DataWriter.saveUser1(users); // Persist the new user to the data store
            return true;
        }
    }

    /**
     * Logs out the current user.
     * Saves all user data before logging out.
     *
     * @return True if a user was logged out successfully; false if no user was logged in.
     */
    public boolean logout() {
        if (currentUser != null) {
            // Save the current user data before logging out
            DataWriter.saveUser1(users); // Save all users, including any changes made by the current user
            currentUser = null;
            return true;
        }
        return false;
    }

    /**
     * Removes a user from the user list based on their user ID.
     *
     * @param userID The unique identifier of the user to remove.
     * @return True if the user was found and removed; false otherwise.
     */
    public boolean removeUser(String userID) {
        for (User user : users) {
            if (user.getUserId().toString().equals(userID)) {
                users.remove(user);
                DataWriter.saveUsers(users); // Save changes after removing the user
                return true;
            }
        }
        return false; // User not found
    }

    /**
     * Checks if a user exists in the user list based on their username.
     *
     * @param username The username to check for existence.
     * @return True if a user with the given username exists; false otherwise.
     */
    public boolean haveUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a user from the user list based on their username or email.
     *
     * @param keyword The username or email of the user to retrieve.
     * @return The User object if found; null otherwise.
     */
    public User getUser(String keyword) {
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(keyword) || user.getEmail().equalsIgnoreCase(keyword)) {
                return user;
            }
        }
        return null; // User not found
    }

    /**
     * Validates user login credentials based on username/email and password.
     * Sets the currentUser upon successful authentication.
     *
     * @param usernameOrEmail The username or email entered by the user.
     * @param password        The password entered by the user.
     * @return True if credentials are valid and authentication is successful; false otherwise.
     */
    public boolean loginCheck(String usernameOrEmail, String password) {
        for (User user : users) {
            boolean usernameMatches = user.getUserName() != null && user.getUserName().equalsIgnoreCase(usernameOrEmail);
            boolean emailMatches = user.getEmail() != null && user.getEmail().equalsIgnoreCase(usernameOrEmail);

            if ((usernameMatches || emailMatches) && user.getPassword() != null && user.getPassword().equals(password)) {
                currentUser = user; // Set the currentUser upon successful login
                return true;
            }
        }
        return false; // Invalid credentials
    }

    /**
     * Retrieves the total number of users in the user list.
     *
     * @return The total number of users.
     */
    public int getTotalUsers() {
        return users.size();
    }

    /**
     * Retrieves the entire list of users.
     *
     * @return An ArrayList containing all users.
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
     * Retrieves a user based on their username or email and password.
     *
     * @param usernameOrEmail The username or email of the user.
     * @param password        The password of the user.
     * @return The User object if found and credentials match; null otherwise.
     */
    public User getUser(String usernameOrEmail, String password) {
        for (User user : users) {
            if ((user.getUserName().equals(usernameOrEmail) || user.getEmail().equalsIgnoreCase(usernameOrEmail))
                    && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Return null if user not found
    }

    /**
     * Updates the information of an existing user identified by their UUID.
     *
     * @param userId    The unique identifier of the user to update.
     * @param firstName The new first name to set.
     * @param lastName  The new last name to set.
     * @param email     The new email address to set.
     * @param password  The new password to set.
     * @return True if the user was found and updated successfully; false otherwise.
     */
    public boolean updateUser(UUID userId, String firstName, String lastName, String email, String password) {
        User user = getUserById(userId);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            DataWriter.saveUsers(users); // Save changes after updating the user
            return true;
        }
        return false; // User not found
    }

    /**
     * Retrieves a user from the user list based on their UUID.
     *
     * @param userId The unique identifier of the user to retrieve.
     * @return The User object if found; null otherwise.
     */
    public User getUserById(UUID userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null; // User not found
    }

    /**
     * Clears the entire user list.
     * Useful for testing purposes or resetting the system.
     */
    public void clearUserList() {
        users.clear();
        DataWriter.saveUsers(users);
    }
}
