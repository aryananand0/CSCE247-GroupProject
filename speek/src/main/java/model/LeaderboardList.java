package model;

import java.util.ArrayList;
import java.util.UUID;

public class LeaderboardList {

    // Attributes
    private ArrayList<Leaderboard> leaderboards;
    private static LeaderboardList instance;

    // Private Constructor
    private LeaderboardList() {
        leaderboards = new ArrayList<>();
        Leaderboard leaderboard = DataLoader.loadLeaderboard();
        if (leaderboard != null) {
            leaderboards.add(leaderboard);
        }
    }

    // Singleton pattern to ensure a single instance of LeaderboardList
    public static LeaderboardList getInstance() {
        if (instance == null) {
            instance = new LeaderboardList();
        }
        return instance;
    }

    // Method to add a leaderboard to the list
    public void addLeaderboard(Leaderboard leaderboard) {
        if (leaderboard != null && !leaderboards.contains(leaderboard)) {
            leaderboards.add(leaderboard);
        }
    }

    // Method to retrieve a leaderboard by index
    public Leaderboard getLeaderboard(int index) {
        if (index >= 0 && index < leaderboards.size()) {
            return leaderboards.get(index);
        }
        return null; 
    }

    // Method to retrieve a leaderboard based on a user's UUID
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

    // Method to get the total leaderboards
    public int getTotalLeaderboards() {
        return leaderboards.size();
    }

    // Method to remove a leaderboard by index
    public boolean removeLeaderboard(int index) {
        if (index >= 0 && index < leaderboards.size()) {
            leaderboards.remove(index);
            return true;  
        }
        return false;  
    }

    // Method to retrieve the leaderboard with the highest scoring user
    public Leaderboard getTopLeaderboard() {
        Leaderboard topLeaderboard = null;
        double highestScore = 0.0;

        for (Leaderboard lb : leaderboards) {
            ArrayList<User> users = lb.getUser();
            for (User user : users) {
                if (user.getScore() > highestScore) {
                    highestScore = user.getScore();
                    topLeaderboard = lb;
                }
            }
        }
        return topLeaderboard;
    }

    // Get the list of all leaderboards
    public ArrayList<Leaderboard> getLeaderboards() {
        return this.leaderboards;
    }

    // Method to print leaderboard summary
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
