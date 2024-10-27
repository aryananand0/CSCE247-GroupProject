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
    @Override
    public String toString() {
        return word + " -> " + translation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;
        return word.equalsIgnoreCase(word1.word) && translation.equalsIgnoreCase(word1.translation);
    }

    @Override
    public int hashCode() {
        return word.toLowerCase().hashCode() + translation.toLowerCase().hashCode();
    }
}
