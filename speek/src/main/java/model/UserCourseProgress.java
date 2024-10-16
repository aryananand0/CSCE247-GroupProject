package model;

import java.util.ArrayList;
import java.util.List;

public class UserCourseProgress {
    // Attributes
    private String courseId;
    private String courseName;
    private String currentLessonId;
    private String currentLessonName;
    private String lessonProgress; 
    private String courseProgress; 
    private List<Lesson> lessons;  

    // Constructor with all parameters
    public UserCourseProgress(String courseId, String courseName, List<Lesson> lessons) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.lessons = lessons != null ? lessons : new ArrayList<>();
        if (!this.lessons.isEmpty()) {
            this.currentLessonId = this.lessons.get(0).getLessonId();
            this.currentLessonName = this.lessons.get(0).getLessonTitle();
            this.lessonProgress = "0%";
            this.courseProgress = "0%";
        } else {
            this.currentLessonId = "";
            this.currentLessonName = "";
            this.lessonProgress = "0%";
            this.courseProgress = "0%";
        }
    }

    // Overloaded constructor without lessons
    public UserCourseProgress(String courseId, String courseName) {
        this(courseId, courseName, new ArrayList<>());
    }

    // Default constructor
    public UserCourseProgress() {
        this("", "", new ArrayList<>());
    }

    // Getters and Setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCurrentLessonId() {
        return currentLessonId;
    }

    public void setCurrentLessonId(String currentLessonId) {
        this.currentLessonId = currentLessonId;
    }

    public String getCurrentLessonName() {
        return currentLessonName;
    }

    public void setCurrentLessonName(String currentLessonName) {
        this.currentLessonName = currentLessonName;
    }

    public String getLessonProgress() {
        return lessonProgress;
    }

    public void setLessonProgress(String lessonProgress) {
        this.lessonProgress = lessonProgress;
    }

    public String getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(String courseProgress) {
        this.courseProgress = courseProgress;
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
    }

    
    public boolean goToNextLesson() {
        if (lessons.isEmpty()) {
            System.out.println("⚠️ No lessons available in this course.");
            return false;
        }

        // Find current lesson index
        int currentIndex = -1;
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(currentLessonId)) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            System.out.println("⚠️ Current lesson not found.");
            return false;
        }

        // Check if next lesson exists
        if (currentIndex + 1 < lessons.size()) {
            Lesson nextLesson = lessons.get(currentIndex + 1);
            this.currentLessonId = nextLesson.getLessonId();
            this.currentLessonName = nextLesson.getLessonTitle();
            this.lessonProgress = "0%"; // Reset progress for the new lesson
            System.out.println("➡️ Moved to next lesson: " + nextLesson.getLessonTitle());
            return true;
        } else {
            System.out.println("✅ Completed all lessons in the course.");
            return false;
        }
    }

    // Additional methods as needed
}
