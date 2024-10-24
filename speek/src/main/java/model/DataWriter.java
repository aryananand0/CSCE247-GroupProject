package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataWriter extends DataConstants {

    // Method to save users to JSON file
    @SuppressWarnings("unchecked")
    public static void saveUsers(ArrayList<User> users) {
        JSONArray existingUserArray = loadExistingUsers();  // Load existing users

        for (User user : users) {
            if (!userExists(existingUserArray, user)) {
                JSONObject userDetails = createUserDetails(user);  // Create user details
                existingUserArray.add(userDetails);  // Add new user to existing array
            }
        }

        writeToFile(existingUserArray, WRITER_USER_FILE);  // Write updated users to file
    }

    // Method to load existing users from file
    private static JSONArray loadExistingUsers() {
        try (FileReader reader = new FileReader(WRITER_USER_FILE)) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            return (JSONArray) obj;
        } catch (IOException | ParseException e) {
            return new JSONArray();  // Return empty array if file doesn't exist or parsing fails
        }
    }

    // Method to check if user already exists in the JSON array
    private static boolean userExists(JSONArray existingUserArray, User user) {
        for (Object o : existingUserArray) {
            JSONObject existingUser = (JSONObject) o;
            if (existingUser.get(USER_ID).equals(user.getUserId().toString())) {
                return true;
            }
        }
        return false;
    }

    // Method to create user details for saving
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

        if (user.getCurrentQuestion() != null) {
            userDetails.put("currentQuestion", createCurrentQuestionDetails(user));
        }

        return userDetails;
    }

    // Method to create favorite languages array
    @SuppressWarnings("unchecked")
    private static JSONArray createFavLanguagesArray(User user) {
        JSONArray favLanguagesArray = new JSONArray();
        for (Language lang : user.getFavoriteLanguages()) {
            favLanguagesArray.add(lang.getLanguageName());
        }
        return favLanguagesArray;
    }

    // Method to create courses array
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

    // Method to create achievements array
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

    // Method to create completed courses array
    @SuppressWarnings("unchecked")
    private static JSONArray createCompletedCoursesArray(User user) {
        JSONArray completedCoursesArray = new JSONArray();
        for (String courseId : user.getCompletedCourseIds()) {
            completedCoursesArray.add(courseId);
        }
        return completedCoursesArray;
    }

    // Method to create completed lessons array
    @SuppressWarnings("unchecked")
    private static JSONArray createCompletedLessonsArray(User user) {
        JSONArray completedLessonsArray = new JSONArray();
        for (String lessonId : user.getCompletedLessonIds()) {
            completedLessonsArray.add(lessonId);
        }
        return completedLessonsArray;
    }

    // Method to create question history array
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

    // Method to create current question details
    @SuppressWarnings("unchecked")
    private static JSONObject createCurrentQuestionDetails(User user) {
        JSONObject currentQuestionDetails = new JSONObject();
        currentQuestionDetails.put("questionId", user.getCurrentQuestion().getId());
        currentQuestionDetails.put("type", user.getCurrentQuestion().getType());
        currentQuestionDetails.put("text", user.getCurrentQuestion().getText());
        currentQuestionDetails.put("correctAnswer", user.getCurrentQuestion().getCorrectAnswer());
        return currentQuestionDetails;
    }

    // Helper method to write JSON array to file
    private static void writeToFile(JSONArray jsonArray, String filePath) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write courses to the JSON file
    @SuppressWarnings("unchecked")
    public static void saveCourses(ArrayList<Course> courses) {
        JSONArray courseArray = new JSONArray();

        for (Course course : courses) {
            JSONObject courseDetails = new JSONObject();
            courseDetails.put(COURSE_NAME, course.getCourseName());
            courseDetails.put(COURSE_DIFFICULTY, course.getDifficulty());

            courseArray.add(courseDetails);
        }

        try (FileWriter file = new FileWriter(COURSE_FILE)) {
            file.write(courseArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write languages to the JSON file
    @SuppressWarnings("unchecked")
    public static void saveLanguages(ArrayList<Language> languages) {
        JSONArray languageArray = new JSONArray();

        for (Language language : languages) {
            JSONObject languageDetails = new JSONObject();
            languageDetails.put(LANGUAGE_NAME, language.getLanguageName());

            // Add courses related to this language
            JSONArray coursesArray = new JSONArray();
            for (Course course : language.getCourses()) {
                coursesArray.add(course.getCourseName());  // Add course name (or course details if needed)
            }
            languageDetails.put(LANGUAGE_COURSES, coursesArray);

            // Add flashcards for this language
            JSONArray flashcardsArray = new JSONArray();
            for (Flashcard flashcard : language.getFlashcards()) {
                JSONObject flashcardDetails = new JSONObject();
                flashcardDetails.put(FLASHCARD_WORD, flashcard.getWord());
                flashcardDetails.put(FLASHCARD_TRANSLATION, flashcard.getTranslation());
                flashcardsArray.add(flashcardDetails);
            }
            languageDetails.put(LANGUAGE_FLASHCARDS, flashcardsArray);

            languageArray.add(languageDetails);
        }

        try (FileWriter file = new FileWriter(LANGUAGE_FILE)) {
            file.write(languageArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write achievements to the JSON file
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

        try (FileWriter file = new FileWriter(ACHIEVEMENT_FILE)) {
            file.write(achievementArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write leaderboard to the JSON file
    @SuppressWarnings("unchecked")
    public static void saveLeaderboard(Leaderboard leaderboard) {
        JSONArray leaderboardArray = new JSONArray();

        for (User user : leaderboard.getUser()) {
            JSONObject leaderboardDetails = new JSONObject();
            leaderboardDetails.put(LEADERBOARD_USER, user.getFirstName() + " " + user.getLastName());
            leaderboardDetails.put(LEADERBOARD_USER_SCORE, user.getScore());

            leaderboardArray.add(leaderboardDetails);
        }

        try (FileWriter file = new FileWriter(LEADERBOARD_FILE)) {
            file.write(leaderboardArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}