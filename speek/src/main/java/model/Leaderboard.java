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
    public Leaderboard(ArrayList<User> user){
        this.users=user;
    }

    public ArrayList<User> getUser(){
        return this.users;
    }

    // Methods
    public ArrayList<User> displayLeaderboard() {
        // (implement logic later)
        return this.scoreboard;
    }

    public ArrayList<User> displayUsers() {
        return this.users;
    }

    // Add additional methods or logic as necessary
}
