package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataWriter extends DataConstants {

    @SuppressWarnings("unchecked")
    public static void saveUsers(ArrayList<User> users) {
        JSONArray existingUserArray = new JSONArray();

        // Load existing users from the JSON file if they exist
        try (FileReader reader = new FileReader(WRITER_USER_FILE)) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            existingUserArray = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        // Check for duplicates and add new users
        for (User user : users) {
            boolean exists = false;

            // Iterate through existing users to check if the user already exists
            Iterator<JSONObject> iterator = existingUserArray.iterator();
            while (iterator.hasNext()) {
                JSONObject existingUser = iterator.next();
                if (existingUser.get(USER_ID).equals(user.getUserId().toString())) {
                    exists = true;
                    break;
                }
            }

            // If user does not exist, add to the array
            if (!exists) {
                JSONObject userDetails = new JSONObject();
                userDetails.put(USER_ID, user.getUserId().toString());
                userDetails.put(USER_FIRST_NAME, user.getFirstName());
                userDetails.put(USER_LAST_NAME, user.getLastName());
                userDetails.put(USER_EMAIL, user.getEmail());
                userDetails.put(USER_PASSWORD, user.getPassword());
                userDetails.put(USER_USER_NAME, user.getUserName());
                userDetails.put(USER_PROGRESS, user.trackProgress());
                userDetails.put(USER_DAILY_REMINDER, user.isDailyReminder());

                // Add favorite languages
                JSONArray favLanguagesArray = new JSONArray();
                for (Language lang : user.getFavoriteLanguages()) {
                    favLanguagesArray.add(lang.getLanguageName());
                }
                userDetails.put(USER_FAVORITE_LANGUAGES, favLanguagesArray);

                // Add current courses with more details
                JSONArray coursesArray = new JSONArray();
                for (Course course : user.getCurrentCourses()) {
                    JSONObject courseDetails = new JSONObject();
                    courseDetails.put(COURSE_ID, course.getCourseId().toString());
                    courseDetails.put(COURSE_NAME, course.getCourseName());
                    courseDetails.put(COURSE_PROGRESS, user.getProgress(course.getCourseId().toString()));
                    courseDetails.put("currentLessonId", user.getCurrentLessonId());
                    coursesArray.add(courseDetails);
                }
                userDetails.put(USER_CURRENT_COURSES, coursesArray);

                // Add achievements
                JSONArray achievementsArray = new JSONArray();
                for (Achievements achievement : user.getAchievements()) {
                    JSONObject achievementDetails = new JSONObject();
                    achievementDetails.put(ACHIEVEMENT_ID, achievement.getAchievementId());
                    achievementDetails.put(ACHIEVEMENTS_TITLE, achievement.getTitle());
                    achievementDetails.put(ACHIEVEMENTS_DESCRIPTION, achievement.getDescription());
                    achievementDetails.put(ACHIEVEMENTS_REWARD_POINTS, achievement.getRewardPoints());
                    achievementsArray.add(achievementDetails);
                }
                userDetails.put(USER_ACHIEVEMENTS, achievementsArray);

                // Add completed course IDs
                JSONArray completedCoursesArray = new JSONArray();
                for (String courseId : user.getCompletedCourseIds()) {
                    completedCoursesArray.add(courseId);
                }
                userDetails.put("completedCourseIds", completedCoursesArray);

                // Add completed lesson IDs
                JSONArray completedLessonsArray = new JSONArray();
                for (String lessonId : user.getCompletedLessonIds()) {
                    completedLessonsArray.add(lessonId);
                }
                userDetails.put("completedLessonIds", completedLessonsArray);

                // Add question history
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
                userDetails.put("questionHistory", questionHistoryArray);

                // Add current question
                if (user.getCurrentQuestion() != null) {
                    JSONObject currentQuestionDetails = new JSONObject();
                    currentQuestionDetails.put("questionId", user.getCurrentQuestion().getId());
                    currentQuestionDetails.put("type", user.getCurrentQuestion().getType());
                    currentQuestionDetails.put("text", user.getCurrentQuestion().getText());
                    currentQuestionDetails.put("correctAnswer", user.getCurrentQuestion().getCorrectAnswer());
                    userDetails.put("currentQuestion", currentQuestionDetails);
                }

                existingUserArray.add(userDetails);  // Add new user details to the existing array
            }
        }

        // Write the updated array back to the file
        try (FileWriter file = new FileWriter(WRITER_USER_FILE)) {
            file.write(existingUserArray.toJSONString());
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