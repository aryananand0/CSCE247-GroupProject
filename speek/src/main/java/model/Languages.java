package model;

public class Languages {
    // Attributes
    private String languageName;

    // Predefined language instances
    public static final Languages SPANISH = new Languages("Spanish");
    public static final Languages ENGLISH = new Languages("English");
    public static final Languages FRENCH = new Languages("French");

    // Private constructor to prevent other instances
    private Languages(String languageName) {
        this.languageName = languageName;
    }

    // Getter for languageName
    public String getLanguageName() {
        return languageName;
    }

    // Method to describe the language (stub)
    public String describeLanguage() {
        // You can add specific logic for each language if needed
        return "Description for " + languageName;
    }

    // Optional: You could add a method to compare languages
    public boolean equals(Languages otherLanguage) {
        return this.languageName.equalsIgnoreCase(otherLanguage.languageName);
    }
}
