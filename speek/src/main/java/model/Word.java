package model;

public class Word {
    private String word;
    private String translation;

    // Constructor
    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    // Getters
    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    // Optional - Setters if needed
    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
