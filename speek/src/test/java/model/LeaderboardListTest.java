package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class LeaderboardListTest {

    private LeaderboardList leaderboardList;
    private Leaderboard defaultLeaderboard;
    private User user1, user2;

    @BeforeEach
    public void setUp() {
        leaderboardList = LeaderboardList.getInstance();
        leaderboardList.getLeaderboards().clear(); // Clear the leaderboard list to ensure test isolation
        
        // Set up default leaderboard with some dummy data
        defaultLeaderboard = new Leaderboard();
        user1 = new User(UUID.randomUUID(), "John", "Doe", 500);
        user2 = new User(UUID.randomUUID(), "Jane", "Doe", 600);
        defaultLeaderboard.addUser(user1);
        defaultLeaderboard.addUser(user2);

        leaderboardList.addLeaderboard(defaultLeaderboard);
    }

    @Test
    public void testSingletonInstance() {
        LeaderboardList anotherInstance = LeaderboardList.getInstance();
        assertSame(leaderboardList, anotherInstance, "Both instances should be the same, confirming singleton behavior.");
    }

    @Test
    public void testAddLeaderboard() {
        Leaderboard newLeaderboard = new Leaderboard();
        leaderboardList.addLeaderboard(newLeaderboard);
        assertTrue(leaderboardList.getLeaderboards().contains(newLeaderboard), "New leaderboard should be added to the list.");
    }

    @Test
    public void testGetLeaderboardByIndex() {
        Leaderboard retrievedLeaderboard = leaderboardList.getLeaderboard(0);
        assertNotNull(retrievedLeaderboard, "Should retrieve the default leaderboard.");
        assertEquals(defaultLeaderboard, retrievedLeaderboard, "Retrieved leaderboard should match the default leaderboard.");
    }

    @Test
    public void testRemoveLeaderboardByIndex() {
        assertTrue(leaderboardList.removeLeaderboard(0), "Should successfully remove the leaderboard at index 0.");
        assertEquals(0, leaderboardList.getTotalLeaderboards(), "Leaderboard list should be empty after removal.");
    }

    @Test
    public void testGetLeaderboardByUser() {
        Leaderboard foundLeaderboard = leaderboardList.getLeaderboardByUser(user1.getUserId());
        assertNotNull(foundLeaderboard, "Should find a leaderboard containing user1.");
        assertTrue(foundLeaderboard.getUser().contains(user1), "Found leaderboard should contain user1.");
    }

    @Test
    public void testGetTopLeaderboard() {
        Leaderboard topLeaderboard = leaderboardList.getTopLeaderboard();
        assertNotNull(topLeaderboard, "Should return the top leaderboard.");
        assertTrue(topLeaderboard.getUser().contains(user2), "Top leaderboard should contain the highest scoring user, user2.");
    }

    @Test
    public void testGetTotalLeaderboards() {
        assertEquals(1, leaderboardList.getTotalLeaderboards(), "Total leaderboards should initially be 1.");
        leaderboardList.addLeaderboard(new Leaderboard());
        assertEquals(2, leaderboardList.getTotalLeaderboards(), "Total leaderboards should increase to 2 after adding another.");
    }
}
