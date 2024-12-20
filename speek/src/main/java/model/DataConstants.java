package model;

/**
 * Contains constant values for file paths and JSON field names.
 * These constants are used throughout the data models for accessing and 
 * managing JSON files related to users, courses, achievements, languages, 
 * and other data components in the application.
 */
public abstract class DataConstants {

    // File paths
    /** File path for user data */
    protected static String USER_FILE = "json/User.json";
    /** File path for the writer's user data */
    protected static final String WRITER_USER_FILE = "json/User1.json";
    /** File path for course data */
    protected static String COURSE_FILE = "json/Course.json";
    /** File path for achievement data */
    protected static String ACHIEVEMENT_FILE = "json/Achievement.json";
    /** File path for language data */
    protected static final String LANGUAGE_FILE = "json/Language.json";
    /** File path for leaderboard data */
    protected static String LEADERBOARD_FILE = "json/Leaderboard.json";
    /** File path for word data */
    protected static final String WORD_FILE = "json/Word.json";

    // User fields
    /** User's unique ID field */
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
    protected static final String USER_UUID = "userId";
    protected static final String USER_USER_NAME = "userName";

    // Course fields
    protected static final String COURSE_ID = "courseId";
    protected static final String COURSE_NAME = "courseName";
    protected static final String COURSE_DIFFICULTY = "difficulty";
    protected static final String COURSE_PROGRESS = "progress";
    protected static final String COURSES = "courses";
    protected static final String COURSE_COMPLETION = "courseCompletion";
    protected static final String MODULE = "module";
    protected static final String WORDS = "words";
    protected static final String LESSON_ID = "lessonId";
    protected static final String LESSON_NAME = "lessonName";
    protected static final String WORD = "word";
    protected static final String TRANSLATION = "translation";

    // Language fields
    protected static final String LANGUAGES = "languages";
    protected static final String LANGUAGE_NAME = "name";
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
    protected static final String LEADERBOARD_USER_NAME = "userName";
    protected static final String LEADERBOARD_USER_UUID = "userId";

    // Game fields
    /** File path for game data */
    protected static final String GAME_FILE = "json/Game.json";
    protected static final String GAME_QUESTION = "Question";
    protected static final String GAME_ANSWER = "Answer";
    protected static final String GAME_EASY = "easy";
    protected static final String GAME_MED = "medium";
    protected static final String GAME_HARD = "hard";
}
