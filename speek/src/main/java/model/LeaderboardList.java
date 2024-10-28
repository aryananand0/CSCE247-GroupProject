package model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton class that manages a list of leaderboards.
 * Provides methods for retrieving, adding, and removing leaderboards, 
 * as well as functionality to retrieve specific leaderboards based on criteria.
 */
public class LeaderboardList {

    // List to store multiple leaderboards
    private ArrayList<Leaderboard> leaderboards;

    // Default leaderboard instance
    private Leaderboard leaderboard;

    // Singleton instance of LeaderboardList
    private static LeaderboardList instance;

    /**
     * Private constructor initializes the leaderboard list and adds 
     * a default leaderboard if available.
     */
    private LeaderboardList() {
        leaderboards = new ArrayList<>();
        leaderboard = new Leaderboard();
        Leaderboard defaultLeaderboard = DataLoader.loadLeaderboard();
        if (defaultLeaderboard != null) {
            leaderboards.add(defaultLeaderboard);
        }
    }

    /**
     * Retrieves the singleton instance of LeaderboardList.
     * 
     * @return the single instance of LeaderboardList.
     */
    public static LeaderboardList getInstance() {
        if (instance == null) {
            instance = new LeaderboardList();
        }
        return instance;
    }

    /**
     * Adds a leaderboard to the list if it is not null and does not already exist.
     * 
     * @param leaderboard the leaderboard to add.
     */
    public void addLeaderboard(Leaderboard leaderboard) {
        if (leaderboard != null && !leaderboards.contains(leaderboard)) {
            leaderboards.add(leaderboard);
        }
    }

    /**
     * Retrieves a leaderboard by its index.
     * 
     * @param index the index of the leaderboard.
     * @return the leaderboard at the specified index or null if out of bounds.
     */
    public Leaderboard getLeaderboard(int index) {
        if (index >= 0 && index < leaderboards.size()) {
            return leaderboards.get(index);
        }
        return null;
    }

    /**
     * Returns the default leaderboard instance.
     * 
     * @return the default leaderboard.
     */
    public Leaderboard getLeaderboard() {
        return this.leaderboard;
    }

    /**
     * Finds a leaderboard associated with a specific user by UUID.
     * 
     * @param userId the UUID of the user.
     * @return the leaderboard containing the user or null if not found.
     */
    public Leaderboard getLeaderboardByUser(UUID userId) {
        for (Leaderboard lb : leaderboards) {
            for (User user : lb.getUser()) {
                if (user.getUserId().equals(userId)) {
                    return lb;
                }
            }
        }
        return null;
    }

    /**
     * Gets the total number of leaderboards.
     * 
     * @return the size of the leaderboards list.
     */
    public int getTotalLeaderboards() {
        return leaderboards.size();
    }

    /**
     * Removes a leaderboard by its index.
     * 
     * @param index the index of the leaderboard to remove.
     * @return true if the leaderboard was removed, false if the index is invalid.
     */
    public boolean removeLeaderboard(int index) {
        if (index >= 0 && index < leaderboards.size()) {
            leaderboards.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Finds the leaderboard containing the highest scoring user.
     * 
     * @return the leaderboard with the highest scoring user, or null if empty.
     */
    public Leaderboard getTopLeaderboard() {
        Leaderboard topLeaderboard = null;
        double highestScore = 0.0;

        for (Leaderboard lb : leaderboards) {
            for (User user : lb.getUser()) {
                if (user.getScore() > highestScore) {
                    highestScore = user.getScore();
                    topLeaderboard = lb;
                }
            }
        }
        return topLeaderboard;
    }

    /**
     * Gets the complete list of all leaderboards.
     * 
     * @return the list of all leaderboards.
     */
    public ArrayList<Leaderboard> getLeaderboards() {
        return this.leaderboards;
    }

    /**
     * Prints a summary of each leaderboard, displaying each user's name and score.
     */
    public void printLeaderboardSummary() {
        for (int i = 0; i < leaderboards.size(); i++) {
            Leaderboard lb = leaderboards.get(i);
            System.out.println("Leaderboard #" + (i + 1) + ":");
            ArrayList<User> users = lb.getUser();
            for (User user : users) {
                System.out.println(user.getFirstName() + " " + user.getLastName() + " - Score: " + user.getScore());
            }
            System.out.println();
        }
    }
}
