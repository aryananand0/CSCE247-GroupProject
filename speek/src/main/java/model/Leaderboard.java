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

    public Leaderboard(String user, double score){
     //Need to be implemented

    }

    // Methods
    public ArrayList<User> displayLeaderboard() {
        // (implement logic later)
        return scoreboard;
    }

}
