package model;

import java.util.ArrayList;

public class Course {

    // Attributes
    private String courseName;
    private String difficulty;
    private ArrayList<Lesson> lessons;
    private ArrayList<Quiz> quizzes;
    private double courseCompletion;

    // Constructor
    public Course(String courseName, String difficulty) {
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.quizzes = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    // Getter for courseName
    public String getCourseName() {
        return courseName;
    }

    // Setter for lessons
    public void setLessons(ArrayList<Lesson> lessons) {
      this.lessons = lessons;
  }

    // Setter for courseName
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Getter for difficulty
    public String getDifficulty() {
        return difficulty;
    }

    // Setter for difficulty
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Getter for courseCompletion
    public double getCourseCompletion() {
        return courseCompletion;
    }

    // Setter for courseCompletion
    public void setCourseCompletion(double courseCompletion) {
        this.courseCompletion = courseCompletion;
    }

}

