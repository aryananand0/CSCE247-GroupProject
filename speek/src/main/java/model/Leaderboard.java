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

    public ArrayList<User> getUser(){
        return this.users;
    }

    public double getScore(){
        return this.score;
    }

    public void setScore(double score){
        this.score = score;
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
