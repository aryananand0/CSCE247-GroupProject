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
    public Leaderboard(ArrayList<User> users) {
        this.users = users;
=======
    public Leaderboard(ArrayList<User> user){
        this.users=user;
    }

    public ArrayList<User> getUser(){
        return this.users;
>>>>>>> aba15741b4e44d1ed3fa8384bc73b9686032f400
    }

    // Methods
    public ArrayList<User> displayLeaderboard() {
        // (implement logic later)
        return this.scoreboard;
    }

    public ArrayList<User> displayUsers() {
        return this.users;
    }

}
