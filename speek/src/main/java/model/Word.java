package model;

/**
 * The {@code Word} class represents a vocabulary word along with its translation.
 * It provides methods to retrieve and modify the word and its translation.
 * Additionally, it overrides the {@link Object#equals(Object)} and {@link Object#hashCode()}
 * methods to enable proper comparison and hashing based on the word and translation.
 */
public class Word {
    private String word;
    private String translation;

    /**
     * Constructs a new {@code Word} instance with the specified word and its translation.
     *
     * @param word         The vocabulary word.
     * @param translation  The translation of the word.
     */
    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    /**
     * Retrieves the vocabulary word.
     *
     * @return The vocabulary word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Retrieves the translation of the word.
     *
     * @return The translation of the word.
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * Sets or updates the vocabulary word.
     *
     * @param word The new word to set.
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Sets or updates the translation of the word.
     *
     * @param translation The new translation to set.
     */
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    /**
     * Returns a string representation of the {@code Word} object.
     * The format is "word -> translation".
     *
     * @return A string representing the {@code Word} object.
     */
    @Override
    public String toString() {
        return word + " -> " + translation;
    }

    /**
     * Compares this {@code Word} object with another object for equality.
     * Two {@code Word} objects are considered equal if both their words and translations
     * match case-insensitively.
     *
     * @param o The object to compare with.
     * @return {@code true} if both objects are {@code Word} instances with matching words
     *         and translations; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;
        return word.equalsIgnoreCase(word1.word) &&
               translation.equalsIgnoreCase(word1.translation);
    }

    /**
     * Returns a hash code value for the {@code Word} object.
     * The hash code is computed based on the lowercase versions of the word and translation.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int result = word.toLowerCase().hashCode();
        result = 31 * result + translation.toLowerCase().hashCode();
        return result;
    }
}
