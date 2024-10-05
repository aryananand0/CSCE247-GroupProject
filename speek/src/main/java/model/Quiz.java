package model;
import java.util.ArrayList;

public class Quiz {
  private String quizName;
  private ArrayList<Question> questions;
  private String feedback;

  public Quiz(String quizName) {
    this.quizName = quizName;
    questions = new ArrayList<>();

  }

  public void takeQuiz() {
    // TODO: IMPLEMENT
  }

  public String displayFeedback() {
    return "Hi";
  }
}
