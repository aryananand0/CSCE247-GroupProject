package model;
import java.util.ArrayList;

public class Language {
  private String languageName;
  private ArrayList<Course> courses;
  private ArrayList<Flashcard> flashcards;

  public Language(String languageName) {
    this.languageName = languageName;
    this.courses = new ArrayList<>();
    this.flashcards = new ArrayList<>();
  }

  public String getLanguageName(){
    return this.languageName;
  }

  public Languages selectLanguage(String languageName) {
    return Languages.ENGLISH;
  }
  public void setCourses(ArrayList<Course> courses){
    this.courses = courses;
  }

  public void setFlashcards(ArrayList<Flashcard> flashcards){
    this.flashcards = flashcards;
  }

  // Added for testing DataLoader
  @Override
  public String toString() {
      // TODO Auto-generated method stub
      return "LANGUAGE: "+this.getLanguageName();
  }
}
