package model;
import java.util.ArrayList;

public class Language {
  private String name;
  private ArrayList<Course> courses;
  private ArrayList<Flashcard> flashcards;

  public Language() {
    this.name = name;
    this.courses = new ArrayList<>();
    this.flashcards = new ArrayList<>();
  }

  public Languages selectLanguage(String name) {
    return Languages.ENGLISH;
  }
}
