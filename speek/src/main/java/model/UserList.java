package model;

import java.util.ArrayList;

public class UserList {

    private static UserList userList;
    private ArrayList<User> users;

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

    
    public User getUser(String keyword) {
        // Search and return the user matching the keyword 
        for (User user : users) {
            // Implement  search logic here
            if (user.getFirstName().equalsIgnoreCase(keyword)) {
                return user;
            }
        }
        return null;
    }

    
}
