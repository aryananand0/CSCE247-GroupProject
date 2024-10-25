package model;

public class Word {
    private String word;
    private String translation;

    // Constructor
    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    // Getters and Setters
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    // Overriding toString() for better display
    @Override
    public String toString() {
        return "Word: " + word + ", Translation: " + translation;
    }
}
