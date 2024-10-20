
package model;

import java.util.ArrayList;

public class UserList {

    private static UserList userList;
    private ArrayList<User> users;

    private UserList() {
        users = DataLoader.loadUsers();
    }

    // Singleton pattern to ensure only one instance of UserList
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    // Remove user by userId
    public boolean removeUser(String userID) {
        for (User user : users) {
            if (user.getUserId().toString().equals(userID)) {
                users.remove(user);
                DataWriter.saveUsers(users);  // Save changes after removing the user
                return true;
            }
        }
        return false;
    }

    public User getUser(String keyword) {
        for (User user : users) {
            String currentUsername = user.getUserName();
            String currentEmail = user.getEmail().toLowerCase();
            
            if (currentUsername.equals(keyword)) {
                System.out.println("Match found using username: " + currentUsername);
                return user;
            }
            
            if (currentEmail.equalsIgnoreCase(keyword)) {
                System.out.println("Match found using email: " + currentEmail);
                return user;
            }
    
            System.out.println("No match for keyword: " + keyword + " with current user: " + currentUsername + ", " + currentEmail);
        }
        
        System.out.println("No user found matching the keyword: " + keyword);
        return null;
    }
    

    // Check if a user exists by username
    public boolean haveUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Add a new user to the list
    public boolean addUser(String username, String firstName, String lastName, String email, String password) {
        if (haveUser(username)) return false;
        else {
            users.add(new User(username, firstName, lastName, email, password));
            DataWriter.saveUsers(users);  // Save the new user to the file
            return true;
        }
    }

    // Check login credentials (username or email and password)
    public boolean LoginCheck(String usernameOrEmail, String password) {
        for (User user : users) {
            // Check if the username or email matches
            boolean usernameMatches = user.getUserName() != null && user.getUserName().equals(usernameOrEmail);
            boolean emailMatches = user.getEmail() != null && user.getEmail().equalsIgnoreCase(usernameOrEmail);

            // Check if the password matches
            if ((usernameMatches || emailMatches) && user.getPassword() != null && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
