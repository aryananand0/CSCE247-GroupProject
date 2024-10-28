package model;

import java.util.ArrayList;

/**
 * The Language class represents a language that contains courses and flashcards.
 * It provides methods to manage language-related details, courses, and flashcards.
 */
public class Language {

    private String languageName;
    private ArrayList<Course> courses;
    private ArrayList<Flashcard> flashcards;

    /**
     * Constructs a Language instance with the specified language name.
     *
     * @param languageName The name of the language.
     */
    public Language(String languageName) {
        this.languageName = languageName;
        this.courses = new ArrayList<>();
        this.flashcards = new ArrayList<>();
    }

    /**
     * Gets the name of the language.
     *
     * @return The name of the language.
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     * Sets the name of the language.
     *
     * @param languageName The new name of the language.
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    /**
     * Gets the list of courses associated with the language.
     *
     * @return An ArrayList of courses.
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Sets the list of courses for the language.
     *
     * @param courses The ArrayList of courses to be set.
     */
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    /**
     * Gets the list of flashcards associated with the language.
     *
     * @return An ArrayList of flashcards.
     */
    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    /**
     * Sets the list of flashcards for the language.
     *
     * @param flashcards The ArrayList of flashcards to be set.
     */
    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    /**
     * Returns a string representation of the Language, displaying the language name.
     *
     * @return A string representing the Language object.
     */
    @Override
    public String toString() {
        return "LANGUAGE: " + languageName;
    }
}
