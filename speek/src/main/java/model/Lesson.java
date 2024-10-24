package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lesson {

    // Attributes
    private UUID lessonId;                   
    private String lessonTitle;
    private String content;
    private List<Question> questions;
    private Flashcard flashcard;               
    private boolean isCompleted;                    

    // Constructor with parameters (auto-generates lessonId and initializes questions)
    public Lesson(String lessonTitle, String content, List<Question> questions) {
        this.lessonId = UUID.randomUUID(); // Automatically generate a unique lessonId
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.isCompleted = false;
    }

    public Lesson(UUID uid, String lessonName){
        this.lessonId = uid;
        this.lessonTitle = lessonName;
        this.questions = new ArrayList<>();
        this.isCompleted = false;
    }

    // Constructor with predefined lessonId (useful for deserialization)
    public Lesson(UUID lessonId, String lessonTitle, String content, List<Question> questions) {
        this.lessonId = lessonId != null ? lessonId : UUID.randomUUID();
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.isCompleted = false;
        this.flashcard = new Flashcard();
    }

    // Default Constructor
    public Lesson() {
        this.lessonId = java.util.UUID.randomUUID(); 
        this.lessonTitle = "";
        this.content = "";
        this.questions = new ArrayList<>();
        this.isCompleted = false;
    }

    public void setFlashcard(Flashcard flashcard){
        this.flashcard = flashcard;
    }

    public Flashcard getFlashcard(){
        return this.flashcard;
    }

    // Getters and Setters

    public UUID getLessonId() {
        return lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
    }
    public void addQuestion(Question newQ){
        this.questions.add(newQ);
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    // Overriding toString() method to display lesson details succinctly
    @Override
    public String toString() {
        return "Lesson ID: " + lessonId + " | Lesson Title: " + lessonTitle + " | Completion: " + (isCompleted ? "Completed" : "Incomplete");
    }
}
