package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Course {

    // Attributes
    private UUID courseId;           
    private String courseName;
    private String difficulty;
    private List<Lesson> lessons;            
    private double courseCompletion;         

    // Constructor with parameters (auto-generates courseId)
    public Course(String courseName, String difficulty) {
        this.courseId = java.util.UUID.randomUUID(); 
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    public Course(UUID courseUUID, String courseName) {
        this.courseId = courseUUID; 
        this.courseName = courseName;
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    public Course(String courseName, String difficulty, double completion) {
        this.courseId = java.util.UUID.randomUUID(); 
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = completion;
    }

    // Constructor with predefined courseId (useful for deserialization)
    public Course(UUID courseId, String courseName, String difficulty, double courseCompletion) {
        this.courseId = courseId != null ? courseId : java.util.UUID.randomUUID();
        this.courseName = courseName;
        this.difficulty = difficulty;
        this.lessons = new ArrayList<>();
        this.courseCompletion = courseCompletion;
    }

    // Default Constructor
    public Course() {
        this.courseId = java.util.UUID.randomUUID(); 
        this.courseName = "";
        this.difficulty = "";
        this.lessons = new ArrayList<>();
        this.courseCompletion = 0.0;
    }

    
    public void addLesson(Lesson lesson) {
        if (lesson != null && !this.lessons.contains(lesson)) {
            this.lessons.add(lesson);
        }
    }

    
    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equals(lessonId)) {
                return lesson;
            }
        }
        return null; // Lesson not found
    }

    
    public void updateCourseCompletion(User user) {
        if (lessons.isEmpty()) {
            this.courseCompletion = 0.0;
            return;
        }
        int completedLessons = 0;
        for (Lesson lesson : lessons) {
            if (user.getCompletedLessonIds().contains(lesson.getLessonId())) {
                completedLessons++;
            }
        }
        this.courseCompletion = ((double) completedLessons / lessons.size()) * 100.0;
    }

    
    public boolean isCourseCompleted(User user) {
        // Assuming a course is completed when all its lessons are completed
        for (Lesson lesson : lessons) {
            if (!user.getCompletedLessonIds().contains(lesson.getLessonId())) {
                return false;
            }
        }
        return true;
    }

    // Getters and Setters

    public UUID getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
    }

    public double getCourseCompletion() {
        return courseCompletion;
    }

    public void setCourseCompletion(double courseCompletion) {
        if (courseCompletion < 0.0) {
            this.courseCompletion = 0.0;
        } else if (courseCompletion > 100.0) {
            this.courseCompletion = 100.0;
        } else {
            this.courseCompletion = courseCompletion;
        }
    }

    // Overriding equals and hashCode based on courseId to ensure uniqueness
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return Objects.equals(courseId, course.courseId);
    }


    @Override
    public int hashCode() {
        return courseId != null ? courseId.hashCode() : 0;
    }

    // Overriding toString() method to display course details succinctly
    @Override
    public String toString() {
        return "Course ID: " + courseId +
                " | Course Name: " + courseName +
                " | Difficulty: " + difficulty +
                " | Completion: " + String.format("%.2f", courseCompletion) + "%";
    }


}
