package model;
import java.util.ArrayList;

public class Language {
  private String name;
  private ArrayList<Course> courses;
  private ArrayList<Flashcard> flashcards;

  public Language(String name) {
    this.name = name;
    this.courses = new ArrayList<>();
    this.flashcards = new ArrayList<>();
  }

  public Languages selectLanguage(String name) {
    return Languages.ENGLISH;
  }
  public void setCourses(ArrayList<Course> courses){
    this.courses = courses;
  }

  public void setFlashcards(ArrayList<Flashcard> flashcards){
    this.flashcards = flashcards;
  }
}
