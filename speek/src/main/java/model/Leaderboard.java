package model;

import java.util.ArrayList;

/**
 * Represents a leaderboard that manages users' scores in the application.
 * Provides basic functionality to display users and their scores.
 */
public class Leaderboard {

    // List of users associated with the leaderboard
    private ArrayList<User> users;

    // Temporary scoreboard list for displaying sorted user scores
    private ArrayList<User> scoreboard;

    // Total score associated with the leaderboard
    private double score;

    /**
     * Default constructor initializes the leaderboard with empty user lists and zero score.
     */
    public Leaderboard() {
        users = new ArrayList<>();
        scoreboard = new ArrayList<>();
        score = 0.0;
    }

    /**
     * Constructor to initialize the leaderboard with a list of users.
     * 
     * @param users The list of users to add to the leaderboard.
     */
    public Leaderboard(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Gets the list of users on the leaderboard.
     * 
     * @return The list of users.
     */
    public ArrayList<User> getUser() {
        return this.users;
    }

    /**
     * Gets the current score associated with the leaderboard.
     * 
     * @return The score.
     */
    public double getScore() {
        return this.score;
    }

    /**
     * Sets the score for the leaderboard.
     * 
     * @param score The score to set.
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Displays the current leaderboard, including user ranks.
     * 
     * @return The scoreboard list showing ranked users.
     */
    public ArrayList<User> displayLeaderboard() {
        return this.scoreboard;
    }

    /**
     * Displays the list of users without any sorting or ranking.
     * 
     * @return The list of all users.
     */
    public ArrayList<User> displayUsers() {
        return this.users;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Leaderboard:\n");
        for (User user : users) {
            builder.append(user.getUserName()).append(" - Score: ").append(user.getScore()).append("\n");
        }
        return builder.toString();
    }
}
