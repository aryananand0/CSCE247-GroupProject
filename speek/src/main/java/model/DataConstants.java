package model;

public abstract class DataConstants {

    // File paths
    protected static final String USER_FILE = "json/User.json"; 
    protected static final String COURSE_FILE = "json/Course.json";  
    protected static final String ACHIEVEMENT_FILE = "json/Achievement.json";
    protected static final String LANGUAGE_FILE = "json/Language.json";
    protected static final String LEADERBOARD_FILE = "json/Leaderboard.json";

    // User fields
    public static final String USER_ID = "userId";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_EMAIL = "email";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_PROGRESS = "progress";
    protected static final String USER_SCORE = "score";
    protected static final String USER_DAILY_REMINDER = "dailyReminder";
    protected static final String USER_FAVORITE_LANGUAGES = "favoriteLanguages";
    protected static final String USER_CURRENT_COURSES = "currentCourses";
    protected static final String USER_ACHIEVEMENTS = "achievements";

    // Course fields
    protected static final String COURSE_ID = "courseId";
    protected static final String COURSE_NAME = "courseName";
    protected static final String COURSE_DIFFICULTY = "difficulty";
    protected static final String COURSE_PROGRESS = "progress";
    protected static final String COURSES = "courses";
    protected static final String COURSE_COMPLETION = "courseCompletion";

    // Language fields
    protected static final String LANGUAGES = "languages";
    protected static final String LANGUAGE_NAME = "languageName";
    protected static final String LANGUAGE_COURSES = "courses";
    protected static final String LANGUAGE_FLASHCARDS = "flashcards";

    // Flashcard fields
    protected static final String FLASHCARDS = "flashcards";
    protected static final String FLASHCARD_WORD = "word";
    protected static final String FLASHCARD_TRANSLATION = "translation";

    // Achievement fields
    protected static final String ACHIEVEMENTS = "achievements";
    protected static final String ACHIEVEMENT_ID = "achievementId";
    protected static final String ACHIEVEMENTS_TITLE = "title";
    protected static final String ACHIEVEMENTS_DESCRIPTION = "description";
    protected static final String ACHIEVEMENTS_REWARD_POINTS = "rewardPoints";

    // Leaderboard fields
    protected static final String LEADERBOARD = "leaderboard";
    protected static final String LEADERBOARD_USER = "user";
    protected static final String LEADERBOARD_USER_FIRST_NAME = "firstName";
    protected static final String LEADERBOARD_USER_LAST_NAME = "lastName";
    protected static final String LEADERBOARD_USER_SCORE = "score";
}
