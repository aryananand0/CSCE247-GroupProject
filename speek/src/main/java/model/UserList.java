package model;

import java.util.ArrayList;

public class UserList {

    // Attributes
    private static UserList userList;
    private ArrayList<User> users;

    // Private constructor 
    private UserList() {
        users = new ArrayList<>();
    }

    // Methods, need to be implemented
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }

    // Method to get a user by a keyword, needs implementation
    public User getUser(String keyword) {
        // Search and return the user matching the keyword 
        for (User user : users) {
            // Implement  search logic here
            if (user.getName().equalsIgnoreCase(keyword)) {
                return null;
            }
        }
        return null;
    }

    
}
