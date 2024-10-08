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

<<<<<<< HEAD
    public Leaderboard(String user, double score){
     //Need to be implemented

=======
    public Leaderboard(ArrayList<User> users) {
        this.users = users;
>>>>>>> branch-Camron
    }

    // Methods
    public ArrayList<User> displayLeaderboard() {
        // (implement logic later)
        return scoreboard;
    }

}
