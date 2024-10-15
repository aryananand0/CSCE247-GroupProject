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

    public Course(String courseName, String difficulty, double courseCompletion) {
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.courseCompletion = courseCompletion;
    }

    // Getter for courseName
    public String getCourseName() {
        return this.courseName;
    }

    // Setter for lessons
    public void setLessons(ArrayList<Lesson> lessons) {
      this.lessons = lessons;
  }
    // Setter for courseName
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String displayCourseDetails() {
        StringBuffer sb = new StringBuffer();
        sb.append("Course: " + this.courseName + "\n");
        sb.append("Difficulty: " + this.difficulty + "\n");

        sb.append("Lesson Details: ");
        for(Lesson l : lessons) {
            sb.append(l.getContent() + "\n");
        }

        sb.append("Quiz details: ");
        for(Quiz q : quizzes) {
            sb.append(q.getQuestions() + "\n");
        }

        return sb.toString();
    }
    
    public boolean completeCourse() {
        // TODO: IMPLEMENT
        if(this.getCourseCompletion() == 100) {
            return true;
        } else {
            return false;
        }

    }

    // Getter for difficulty
    public String getDifficulty() {
        return this.difficulty;
    }

    // Setter for difficulty
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Getter for courseCompletion
    public double getCourseCompletion() {
        return this.courseCompletion;
    }

    // Setter for courseCompletion
    public void setCourseCompletion(double courseCompletion) {
        this.courseCompletion = courseCompletion;
    }
    // To test the DataLoader
    @Override
    public String toString() {
        return "Course Name: " + courseName + " | Difficulty: " + difficulty;
    }

}

