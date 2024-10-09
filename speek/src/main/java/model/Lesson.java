package model;

public class Lesson {

    // Attributes
    private String lessonTitle;
    private String content;
    private Quiz quiz;

    // Constructor
    public Lesson(String lessonTitle, String content, Quiz quiz) {
        this.lessonTitle = lessonTitle;
        this.content = content;
        this.quiz = quiz;
    }

    // Method to navigate through the lesson
    public void navigateLessons(User user) {  // Added User parameter
        System.out.println("Navigating lesson: " + lessonTitle);
        System.out.println(content);
        if (quiz != null) {
            quiz.takeQuiz(user);  // Pass the user to the takeQuiz method
        } else {
            System.out.println("No quiz available for this lesson.");
        }
    }

    // Method to track the lesson progress
    public double trackLessonProgress() {
        double progress = 50.0;  // Assume 50% progress for completing the content
        if (quiz != null && quiz.displayFeedback() != null) {
            progress = 100.0;  // If the quiz is completed and feedback is available, set progress to 100%
        }
        return progress;
    }

    // Method to replay the lesson
    public void replayLesson(User user) {  // Added User parameter
        System.out.println("Replaying lesson: " + lessonTitle);
        navigateLessons(user);  // Replay the lesson content and quiz with the user
    }

    // Getters and Setters
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
