package model;

import java.util.ArrayList;

public class Language {
    private String languageName;
    private ArrayList<Course> courses;
    private ArrayList<Flashcard> flashcards;

    public Language(String languageName) {
        this.languageName = languageName;
        this.courses = new ArrayList<Course>();
        this.flashcards = new ArrayList<Flashcard>();
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @Override
    public String toString() {
        return "LANGUAGE: " + this.getLanguageName();
    }
}
