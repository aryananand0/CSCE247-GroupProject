package model;
import java.util.ArrayList;

public class Course {
  private String courseName;
  private String difficulty;
  private ArrayList<Lesson> lessons;
  private ArrayList<Quiz> quizzes;
  private double courseCompletion;

  public Course(String courseName, String difficulty) {
    this.courseName = courseName;
    this.difficulty = difficulty;
    lessons = new ArrayList<>();
    quizzes = new ArrayList<>();
  }
  public bool enrollInCourse(User user) {
    return true;
  }

  public String displayCourseDetils() {
    return "Hi";
  }
  
  public void completeCourse() {
    // TODO: IMPLEMENT
  }

}
