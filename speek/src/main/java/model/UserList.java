package model;

import java.util.ArrayList;
import java.util.UUID;

public class UserList {

    // Singleton instance
    private static UserList userList = null;
    private ArrayList<User> users;
    private User currentUser;

    // Private constructor to load users from the data source
    private UserList() {
        users = DataLoader.loadUsers();
    }

    // Singleton pattern to ensure only one instance of UserList exists
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    // Method to add a new user
    public boolean addUser(String username, String firstName, String lastName, String email, String password) {
        if (haveUser(username)) {
            return false; // User already exists
        } else {
            users.add(new User(username, firstName, lastName, email, password));
            DataWriter.saveUsers(users);  // Persist the new user to the data store
            return true;
        }
    }

    // Logs out the current user
    public boolean logout() {
        if (currentUser != null) {
            // Save the current user data before logging out
            DataWriter.saveUsers(users);  // Save all users, including any changes made by the current user
            currentUser = null;
            return true;
        }
        return false;
    }


    // Method to remove a user by userId
    public boolean removeUser(String userID) {
        for (User user : users) {
            if (user.getUserId().toString().equals(userID)) {
                users.remove(user);
                DataWriter.saveUsers(users);  // Save changes after removing the user
                return true;
            }
        }
        return false; // User not found
    }

    // Method to check if a user exists by username
    public boolean haveUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Method to get a user by their username or email
    public User getUser(String keyword) {
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(keyword) || user.getEmail().equalsIgnoreCase(keyword)) {
                return user;
            }
        }
        return null; // User not found
    }

    // Method to validate login credentials (username or email, and password)
    public boolean loginCheck(String usernameOrEmail, String password) {
        for (User user : users) {
            boolean usernameMatches = user.getUserName() != null && user.getUserName().equalsIgnoreCase(usernameOrEmail);
            boolean emailMatches = user.getEmail() != null && user.getEmail().equalsIgnoreCase(usernameOrEmail);

            if ((usernameMatches || emailMatches) && user.getPassword() != null && user.getPassword().equals(password)) {
                currentUser = user; // Set the currentUser upon successful login in one version
                return true;
            }
        }
        return false; // Invalid credentials
    }

    // Method to get the total number of users in the list
    public int getTotalUsers() {
        return users.size();
    }

    // Method to retrieve all users (getter)
    public ArrayList<User> getUsers() {
        return this.users;
    }

    // Method to get a user by their username or email and password
    public User getUser(String usernameOrEmail, String password) {
        for (User user : users) {
            if ((user.getUserName().equals(usernameOrEmail) || user.getEmail().equalsIgnoreCase(usernameOrEmail)) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;  // Return null if user not found
    }

    // Method to update user information
    public boolean updateUser(UUID userId, String firstName, String lastName, String email, String password) {
        User user = getUserById(userId);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            DataWriter.saveUsers(users);  // Save changes after updating the user
            return true;
        }
        return false; // User not found
    }

    // Method to retrieve a user by their UUID
    public User getUserById(UUID userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null; // User not found
    }

    // Method to clear the entire user list (useful for testing or resetting the system)
    public void clearUserList() {
        users.clear();
        DataWriter.saveUsers(users);
    }
}