package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * DataWriter is responsible for saving user, course, language, and leaderboard data to JSON files.
 * This class uses the Gson library for pretty-printing JSON.
 */
public class DataWriter extends DataConstants {
    

    public static void setFilePathsForTesting() {
        USER_FILE = "json/test_user.json";
        COURSE_FILE = "json/test_course.json";
        ACHIEVEMENT_FILE = "json/test_achievement.json";
        LEADERBOARD_FILE = "json/test_leaderboard.json";
    }

    public static void resetFilePaths() {
        USER_FILE = "json/User.json";
        COURSE_FILE = "json/Course.json";
        ACHIEVEMENT_FILE = "json/Achievement.json";
        LEADERBOARD_FILE = "json/Leaderboard.json";
    }

    /**
     * Saves a list of users to the JSON file.
     * 
     * @param users the list of users to save
     */
    public static void saveUser1(ArrayList<User> users) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        try (FileWriter writer = new FileWriter(USER_FILE)) {
            gson.toJson(users, writer);
            System.out.println("✅ Done");
        } catch (IOException e) {
            System.out.println("❌ Failed:");
            e.printStackTrace();
        }
    }

    
    /**
     * Loads existing users from the JSON file.
     * 
     * @return JSONArray of existing users
     */
    private static JSONArray loadExistingUsers() {
        try (FileReader reader = new FileReader(USER_FILE)) {
            return (JSONArray) new JSONParser().parse(reader);
        } catch (IOException | ParseException e) {
            return new JSONArray();
        }
    }

    /**
     * Creates JSON details for a user.
     * 
     * @param user the user to convert to JSON
     * @return JSONObject representing the user
     */
    @SuppressWarnings("unchecked")
    private static JSONObject createUserDetails(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, user.getUserId().toString());
        userDetails.put(USER_FIRST_NAME, user.getFirstName());
        userDetails.put(USER_LAST_NAME, user.getLastName());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_USER_NAME, user.getUserName());
        userDetails.put(USER_PROGRESS, user.trackProgress());
        userDetails.put(USER_DAILY_REMINDER, user.isDailyReminder());
        userDetails.put(USER_FAVORITE_LANGUAGES, createFavLanguagesArray(user));
        userDetails.put(USER_CURRENT_COURSES, createCoursesArray(user));
        userDetails.put(USER_ACHIEVEMENTS, createAchievementsArray(user));
        userDetails.put("completedCourseIds", createCompletedCoursesArray(user));
        userDetails.put("completedLessonIds", createCompletedLessonsArray(user));
        userDetails.put("questionHistory", createQuestionHistoryArray(user));
        userDetails.put("missedWords", createMissedWordsArray(user));

        if (user.getCurrentQuestion() != null) {
            userDetails.put("currentQuestion", createCurrentQuestionDetails(user));
        }
        return userDetails;
    }

    /**
     * Creates a JSON array for user's missed words.
     */
    @SuppressWarnings("unchecked")
    private static JSONArray createMissedWordsArray(User user) {
        JSONArray missedWordsArray = new JSONArray();
        for (Word word : user.getMissedWords()) {
            LinkedHashMap<String, String> wordDetails = new LinkedHashMap<>();
            wordDetails.put("word", word.getWord());
            wordDetails.put("translation", word.getTranslation());
            missedWordsArray.add(new JSONObject(wordDetails));
        }
        return missedWordsArray;
    }

    /**
     * Creates a JSON array for user's favorite languages.
     */
    @SuppressWarnings("unchecked")
    private static JSONArray createFavLanguagesArray(User user) {
        JSONArray favLanguagesArray = new JSONArray();
        for (Language lang : user.getFavoriteLanguages()) {
            favLanguagesArray.add(lang.getLanguageName());
        }
        return favLanguagesArray;
    }

    /**
     * Creates a JSON array for user's courses.
     */
    @SuppressWarnings("unchecked")
    private static JSONArray createCoursesArray(User user) {
        JSONArray coursesArray = new JSONArray();
        for (Course course : user.getCurrentCourses()) {
            JSONObject courseDetails = new JSONObject();
            courseDetails.put("courseId", course.getCourseId().toString());
            courseDetails.put(COURSE_NAME, course.getCourseName());
            courseDetails.put(COURSE_PROGRESS, user.getProgress(course.getCourseId().toString()));
            courseDetails.put("currentLessonId", user.getCurrentLessonId());
            coursesArray.add(courseDetails);
        }
        return coursesArray;
    }

    /**
     * Creates a JSON array for user's achievements.
     */
    @SuppressWarnings("unchecked")
    private static JSONArray createAchievementsArray(User user) {
        JSONArray achievementsArray = new JSONArray();
        for (Achievements achievement : user.getAchievements()) {
            JSONObject achievementDetails = new JSONObject();
            achievementDetails.put(ACHIEVEMENT_ID, achievement.getAchievementId());
            achievementDetails.put(ACHIEVEMENTS_TITLE, achievement.getTitle());
            achievementDetails.put(ACHIEVEMENTS_DESCRIPTION, achievement.getDescription());
            achievementDetails.put(ACHIEVEMENTS_REWARD_POINTS, achievement.getRewardPoints());
            achievementsArray.add(achievementDetails);
        }
        return achievementsArray;
    }

    /**
     * Creates a JSON array for user's completed courses.
     */
    @SuppressWarnings("unchecked")
    private static JSONArray createCompletedCoursesArray(User user) {
        JSONArray completedCoursesArray = new JSONArray();
        for (String courseId : user.getCompletedCourseIds()) {
            completedCoursesArray.add(courseId);
        }
        return completedCoursesArray;
    }

    /**
     * Creates a JSON array for user's completed lessons.
     */
    @SuppressWarnings("unchecked")
    private static JSONArray createCompletedLessonsArray(User user) {
        JSONArray completedLessonsArray = new JSONArray();
        for (String lessonId : user.getCompletedLessonIds()) {
            completedLessonsArray.add(lessonId);
        }
        return completedLessonsArray;
    }

    /**
     * Creates a JSON array for user's question history.
     */
    @SuppressWarnings("unchecked")
    private static JSONArray createQuestionHistoryArray(User user) {
        JSONArray questionHistoryArray = new JSONArray();
        for (QuestionHistory history : user.getQuestionHistory()) {
            JSONObject historyDetails = new JSONObject();
            historyDetails.put("questionId", history.getQuestionId());
            historyDetails.put("questionText", history.getQuestionText());
            historyDetails.put("userAnswer", history.getUserAnswer());
            historyDetails.put("correctAnswer", history.getCorrectAnswer());
            historyDetails.put("isCorrect", history.isCorrect());
            questionHistoryArray.add(historyDetails);
        }
        return questionHistoryArray;
    }

    /**
     * Creates JSON details for user's current question.
     */
    @SuppressWarnings("unchecked")
    private static JSONObject createCurrentQuestionDetails(User user) {
        JSONObject currentQuestionDetails = new JSONObject();
        currentQuestionDetails.put("questionId", user.getCurrentQuestion().getId());
        currentQuestionDetails.put("type", user.getCurrentQuestion().getType());
        currentQuestionDetails.put("text", user.getCurrentQuestion().getText());
        currentQuestionDetails.put("correctAnswer", user.getCurrentQuestion().getCorrectAnswer());
        return currentQuestionDetails;
    }

    /**
     * Writes JSON array to specified file.
     */
    private static void writeToFile(JSONArray jsonArray, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a list of courses to the JSON file.
     */
    @SuppressWarnings("unchecked")
    public static void saveCourses(ArrayList<Course> courses) {
        JSONArray courseArray = new JSONArray();

        for (Course course : courses) {
            JSONObject courseDetails = new JSONObject();
            courseDetails.put(COURSE_NAME, course.getCourseName());
            courseDetails.put(COURSE_DIFFICULTY, course.getDifficulty());
            courseArray.add(courseDetails);
        }
        writeToFile(courseArray, COURSE_FILE);
    }

    /**
     * Saves a list of achievements to the JSON file.
     */
    @SuppressWarnings("unchecked")
    public static void saveAchievements(ArrayList<Achievements> achievements) {
        JSONArray achievementArray = new JSONArray();

        for (Achievements achievement : achievements) {
            JSONObject achievementDetails = new JSONObject();
            achievementDetails.put(ACHIEVEMENT_ID, achievement.getAchievementId());
            achievementDetails.put(ACHIEVEMENTS_TITLE, achievement.getTitle());
            achievementDetails.put(ACHIEVEMENTS_DESCRIPTION, achievement.getDescription());
            achievementDetails.put(ACHIEVEMENTS_REWARD_POINTS, achievement.getRewardPoints());
            achievementArray.add(achievementDetails);
        }
        writeToFile(achievementArray, ACHIEVEMENT_FILE);
    }

    /**
     * Saves leaderboard data to the JSON file.
     */
    @SuppressWarnings("unchecked")
    public static void saveLeaderboard(Leaderboard leaderboard) {
        JSONArray leaderboardArray = new JSONArray();

        for (User user : leaderboard.getUser()) {
            JSONObject leaderboardDetails = new JSONObject();
            leaderboardDetails.put(LEADERBOARD_USER, user.getFirstName() + " " + user.getLastName());
            leaderboardDetails.put(LEADERBOARD_USER_SCORE, user.getScore());
            leaderboardArray.add(leaderboardDetails);
        }
        writeToFile(leaderboardArray, LEADERBOARD_FILE);
    }
}
