package model;
import java.util.ArrayList;

public class Language {
    private String languageName;
    private ArrayList<Course> courses;
    private ArrayList<Flashcard> flashcards;

    // Constructor
    public Language(String languageName) {
        this.languageName = languageName;
        this.courses = new ArrayList<Course>();
        this.flashcards = new ArrayList<Flashcard>();
    }

    // Getter for languageName
    public String getLanguageName() {
        return this.languageName;
    }

    // Getter for courses (to fix the error in DataWriter)
    public ArrayList<Course> getCourses() {
        return this.courses;
    }

    // Getter for flashcards
    public ArrayList<Flashcard> getFlashcards() {
        return this.flashcards;
    }

    // Setter for courses
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    // Setter for flashcards
    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    // Placeholder method to select a language (optional, adjust if needed)
    public Languages selectLanguage(String languageName) {
        return Languages.ENGLISH;
    }

    // Added for testing DataLoader
    @Override
    public String toString() {
        return "LANGUAGE: " + this.getLanguageName();
    }
}
