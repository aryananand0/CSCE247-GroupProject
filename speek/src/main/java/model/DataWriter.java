package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    // Method to write users to the JSON file
    public static void saveUsers(ArrayList<User> users) {
        JSONArray userArray = new JSONArray();

        for (User user : users) {
            JSONObject userDetails = new JSONObject();
            userDetails.put(USER_ID, user.getUserId().toString());
            userDetails.put(USER_FIRST_NAME, user.getFirstName());
            userDetails.put(USER_LAST_NAME, user.getLastName());
            userDetails.put(USER_EMAIL, user.getEmail());
            userDetails.put(USER_PASSWORD, user.getPassword());
            userDetails.put(USER_PROGRESS, user.getProgress());
            userDetails.put(USER_DAILY_REMINDER, user.isDailyReminder());

            // Add favorite languages
            JSONArray favLanguagesArray = new JSONArray();
            for (Language lang : user.getFavoriteLanguages()) {
                favLanguagesArray.add(lang.getLanguageName());
            }
            userDetails.put(USER_FAVORITE_LANGUAGES, favLanguagesArray);

            // Add current courses
            JSONArray coursesArray = new JSONArray();
            for (Course course : user.getCurrentCourses()) {
                JSONObject courseDetails = new JSONObject();
                courseDetails.put(COURSE_ID, course.getCourseName()); // Assuming course ID
                courseDetails.put(COURSE_PROGRESS, course.getCourseCompletion());
                coursesArray.add(courseDetails);
            }
            userDetails.put(USER_CURRENT_COURSES, coursesArray);

            // Add achievements
            JSONArray achievementsArray = new JSONArray();
            for (Achievements achievement : user.getAchievements()) {
                JSONObject achievementDetails = new JSONObject();
                achievementDetails.put(ACHIEVEMENT_ID, achievement.getAchievementId());
                achievementDetails.put(ACHIEVEMENT_TITLE, achievement.getTitle());
                achievementDetails.put(ACHIEVEMENT_DESCRIPTION, achievement.getDescription());
                achievementDetails.put(ACHIEVEMENT_REWARD_POINTS, achievement.getRewardPoints());
                achievementsArray.add(achievementDetails);
            }
            userDetails.put(USER_ACHIEVEMENTS, achievementsArray);

            userArray.add(userDetails);
        }

        try (FileWriter file = new FileWriter(USER_FILE)) {
            file.write(userArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write courses to the JSON file
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
    public static void saveAchievements(ArrayList<Achievement> achievements) {
        JSONArray achievementArray = new JSONArray();

        for (Achievement achievement : achievements) {
            JSONObject achievementDetails = new JSONObject();
            achievementDetails.put(ACHIEVEMENT_ID, achievement.getAchievementId());
            achievementDetails.put(ACHIEVEMENT_TITLE, achievement.getTitle());
            achievementDetails.put(ACHIEVEMENT_DESCRIPTION, achievement.getDescription());
            achievementDetails.put(ACHIEVEMENT_REWARD_POINTS, achievement.getRewardPoints());

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
    public static void saveLeaderboard(Leaderboard leaderboard) {
        JSONArray leaderboardArray = new JSONArray();

        for (User user : leaderboard.getUsers()) {
            JSONObject leaderboardDetails = new JSONObject();
            leaderboardDetails.put(USER_NAME, user.getFirstName() + " " + user.getLastName());
            leaderboardDetails.put(LEADERBOARD_SCORE, user.getScore());

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
