package model;

import java.util.ArrayList;

public class Leaderboard {

    // Attributes
    private ArrayList<User> users;
    private ArrayList<User> scoreboard;
    private double score;

    
    public Leaderboard() {
        // Initialize the lists
        users = new ArrayList<>();
        scoreboard = new ArrayList<>();
        score = 0.0;
    }

    public Leaderboard(ArrayList<User> users) {
        this.users = users;
    }

    // Methods
    public ArrayList<User> displayLeaderboard() {
        // (implement logic later)
        return scoreboard;
    }

    // Add additional methods or logic as necessary
}
