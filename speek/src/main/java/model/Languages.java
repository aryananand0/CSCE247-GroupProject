package model;

/**
 * Enum representing supported languages in the application.
 * Provides functionality to retrieve the name of each language as a formatted string.
 */
public enum Languages {
    SPANISH,
    ENGLISH,
    FRENCH;

    /**
     * Retrieves the language name as a formatted string (title case).
     *
     * @return The formatted name of the language.
     */
    public String getLanguageName() {
        // Format the enum name to have the first letter capitalized and the rest in lowercase
        String lowercase = name().toLowerCase();
        return Character.toUpperCase(lowercase.charAt(0)) + lowercase.substring(1);
    }
}
