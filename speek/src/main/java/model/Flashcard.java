package model;

public class Flashcard {

    // Attributes
    private String word;
    private String translation;
    private String pronunciationAudio;  // Path or identifier for audio pronunciation file

    // Constructor
    public Flashcard(String word, String translation, String pronunciationAudio) {
        this.word = word;
        this.translation = translation;
        this.pronunciationAudio = pronunciationAudio;
    }

    public Flashcard(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    // Getter for word
    public String getWord() {
        return word;
    }

    // Setter for word
    public void setWord(String word) {
        this.word = word;
    }

    // Getter for translation
    public String getTranslation() {
        return translation;
    }

    // Setter for translation
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    // Getter for pronunciationAudio
    public String getPronunciationAudio() {
        return pronunciationAudio;
    }

    // Setter for pronunciationAudio
    public void setPronunciationAudio(String pronunciationAudio) {
        this.pronunciationAudio = pronunciationAudio;
    }

    // Method to display flashcard details
    public String displayFlashcard() {
        return "Word: " + word + ", Translation: " + translation;
    }

    // Method to practice flashcard (prints word and asks for the translation)
    public boolean practiceFlashcard(String userAnswer) {
        return userAnswer.equalsIgnoreCase(translation);
    }

    // Method to play pronunciation (this is a placeholder for actual audio logic)
    public void playPronunciation() {
        System.out.println("Playing pronunciation for: " + word);
        // In a real-world scenario, this would play the audio file stored in pronunciationAudio
    }

    @Override
    public String toString() {
        return "Flashcard [Word: " + word + ", Translation: " + translation + "]";
    }
}
