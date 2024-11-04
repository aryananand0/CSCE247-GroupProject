package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

class UserListTest {

    private UserList userList;

    @BeforeEach
    void setUp() {
        userList = UserList.getInstance();
        userList.getUsers().clear(); // Clear users to start fresh for each test
    }

    @Test
    void testAddUser_duplicateUsernameFails() {
        userList.addUser("jdoe", "John", "Doe", "jdoe@example.com", "password123");
        boolean result = userList.addUser("jdoe", "Jane", "Smith", "jsmith@example.com", "password123"); // Expected to fail due to duplicate
        assertFalse(result, "Adding a user with a duplicate username should fail."); // Should pass
    }

    @Test
    void testAddUser_duplicateUsernamePassesUnexpectedly() {
        userList.addUser("jdoe", "John", "Doe", "jdoe@example.com", "password123");
        boolean result = userList.addUser("jdoe", "Jane", "Smith", "jsmith@example.com", "password123"); // Expected to fail due to duplicate
        assertTrue(result, "Adding a user with a duplicate username should fail, but passed unexpectedly."); // Should fail
    }

    @Test
    void testLogout_userLoggedOut() {
        userList.addUser("jdoe", "John", "Doe", "jdoe@example.com", "password123");
        userList.loginCheck("jdoe", "password123");
        boolean result = userList.logout();
        assertTrue(result, "User should be able to log out successfully."); // Should pass
    }

    @Test
    void testLogout_userNotLoggedOut() {
        userList.addUser("jdoe", "John", "Doe", "jdoe@example.com", "password123");
        userList.loginCheck("jdoe", "password123");
        boolean result = userList.logout();
        assertFalse(result, "User logout failed due to intentional error."); // Should fail
    }

    @Test
    void testRemoveUser_nonexistentUserFails() {
        userList.addUser("jdoe", "John", "Doe", "jdoe@example.com", "password123");
        boolean result = userList.removeUser("nonexistent-id");
        assertFalse(result, "Removing a non-existent user should fail."); // Should pass
    }

    @Test
    void testRemoveUser_existingUserFailsUnexpectedly() {
        userList.addUser("jsmith1", "Jane", "Smith", "jsmith@example.com", "password123");
        String userID = userList.getUsers().get(0).getUserId().toString();
        boolean result = userList.removeUser(userID);
        assertTrue(result, "Removing an existing user should succeed, but failed unexpectedly."); // Should fail
    }
}