package model;

/**
 * The Word class represents a vocabulary word with its translation.
 * It provides methods to get and set the word and translation, and overrides
 * the equals and hashCode methods for object comparison and hashing.
 */
public class Word {
    private String word;
    private String translation;

    /**
     * Constructs a Word instance with the specified word and its translation.
     *
     * @param word The vocabulary word.
     * @param translation The translation of the word.
     */
    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    /**
     * Gets the vocabulary word.
     *
     * @return The word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Gets the translation of the word.
     *
     * @return The translation of the word.
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * Sets the vocabulary word.
     *
     * @param word The word to set.
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Sets the translation of the word.
     *
     * @param translation The translation to set.
     */
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    /**
     * Returns a string representation of the Word, formatted as "word -> translation".
     *
     * @return A string representing the Word object.
     */
    @Override
    public String toString() {
        return word + " -> " + translation;
    }

    /**
     * Compares this word with another object for equality based on case-insensitive word and translation.
     *
     * @param o The object to compare with.
     * @return true if both word and translation match case-insensitively; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;
        return word.equalsIgnoreCase(word1.word) && translation.equalsIgnoreCase(word1.translation);
    }

    /**
     * Returns a hash code for this Word, computed from the word and translation in lowercase.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return word.toLowerCase().hashCode() + translation.toLowerCase().hashCode();
    }
}
