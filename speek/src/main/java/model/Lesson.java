package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lesson {

    // Attributes
    private final String lessonId;                   
    private String lessonTitle;
    private String content;
    private List<Question> questions;               
    private boolean isCompleted;                    

    // Constructor with parameters (auto-generates lessonId and initializes questions)
    public Lesson(String lessonTitle, String content, List<Question> questions) {
        this.lessonId = UUID.randomUUID().toString(); // Automatically generate a unique lessonId
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.isCompleted = false;
    }

    // Constructor with predefined lessonId (useful for deserialization)
    public Lesson(String lessonId, String lessonTitle, String content, List<Question> questions) {
        this.lessonId = lessonId != null ? lessonId : UUID.randomUUID().toString();
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.isCompleted = false;
    }

    // Default Constructor
    public Lesson() {
        this.lessonId = java.util.UUID.randomUUID().toString(); 
        this.lessonTitle = "";
        this.content = "";
        this.questions = new ArrayList<>();
        this.isCompleted = false;
    }

    // Getters and Setters

    public String getLessonId() {
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

    public boolean isCompleted() {
        return isCompleted;
    }

    // Overriding toString() method to display lesson details succinctly
    @Override
    public String toString() {
        return "Lesson ID: " + lessonId + " | Lesson Title: " + lessonTitle + " | Completion: " + (isCompleted ? "Completed" : "Incomplete");
    }
}
