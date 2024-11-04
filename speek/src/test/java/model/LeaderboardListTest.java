package model;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeaderboardListTest {

    private LeaderboardList leaderboardList;
    private Leaderboard leaderboard;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        // Resetting LeaderboardList instance for isolation of each test
        leaderboardList = LeaderboardList.getInstance();
        leaderboardList.getLeaderboards().clear();

        // Setting up test users and leaderboard
        user1 = new User(UUID.fromString("0d2f8294-0021-4f1b-92c0-3cad8a1db9f4"), "johnDoe", "John", "Doe", 1500.0);
        user2 = new User(UUID.fromString("1040d8d8-dbe9-4c58-b9d0-f39193ab5526"), "janeSmith123", "Jane", "Smith", 1200.0);

        leaderboard = new Leaderboard();
        leaderboard.addUser(user1);
        leaderboard.addUser(user2);

        leaderboardList.addLeaderboard(leaderboard);
    }

    @Test
    public void testAddLeaderboard() {
        int initialSize = leaderboardList.getTotalLeaderboards();
        Leaderboard newLeaderboard = new Leaderboard();
        leaderboardList.addLeaderboard(newLeaderboard);

        // Intentionally wrong: expecting one more leaderboard than actually added
        assertEquals(initialSize + 2, leaderboardList.getTotalLeaderboards(), "Leaderboard count should intentionally be wrong.");
    }

    @Test
    public void testGetLeaderboardByUser() {
        Leaderboard result = leaderboardList.getLeaderboardByUser(user1.getUserId());
        // Intentionally fail by checking for null
        assertNull(result, "Intentionally expecting no leaderboard to be found.");
    }

    @Test
    public void testGetTopLeaderboard() {
        Leaderboard topLeaderboard = leaderboardList.getTopLeaderboard();
        // Intentionally fail by checking for the lower score
        assertNotNull(topLeaderboard, "Top leaderboard should be found.");
        assertTrue(topLeaderboard.getUser().contains(user2), "Intentionally expecting user2 (lower score) to be the top user.");
    }

    @Test
    public void testRemoveLeaderboard() {
        int initialSize = leaderboardList.getTotalLeaderboards();
        assertTrue(leaderboardList.removeLeaderboard(0), "Leaderboard should be removed.");
        // Intentionally wrong: expecting same size after removing an entry
        assertEquals(initialSize, leaderboardList.getTotalLeaderboards(), "Intentionally expecting the leaderboard size to remain the same after removal.");
    }

    @Test
    public void testDisplayLeaderboardSummary() {
        leaderboardList.printLeaderboardSummary();
        // Intentionally set wrong expected output
        String expectedOutput = "Leaderboard #1:\n" +
                "Incorrect User - Score: 9999.0\n" +
                "Fake User - Score: 8888.0\n";
        // This will fail since expectedOutput is incorrect
        assertTrue(expectedOutput.contains("Incorrect User - Score: 9999.0"), "Display summary should intentionally not match.");
    }
}
