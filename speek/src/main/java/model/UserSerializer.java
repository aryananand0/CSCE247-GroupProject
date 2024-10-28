package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

/**
 * Serializes a {@link User} object into its JSON representation.
 * Implements the {@link JsonSerializer} interface provided by Gson.
 */
public class UserSerializer implements JsonSerializer<User> {

    /**
     * Serializes a {@link User} object into its equivalent JSON representation.
     *
     * @param user        The {@link User} object to serialize.
     * @param typeOfSrc   The actual type (fully genericized version) of the source object.
     * @param context     Context for serialization, which can be used to serialize sub-components.
     * @return A {@link JsonElement} representing the serialized {@link User}.
     */
    @Override
    public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonUser = new JsonObject();

        // Basic Fields
        jsonUser.addProperty("lastName", user.getLastName());
        jsonUser.addProperty("userName", user.getUserName());

        // Serialize userId
        UUID userId = user.getUserId();
        if (userId != null) {
            jsonUser.addProperty("userId", userId.toString());
        } else {
            System.out.println("⚠️ Warning: userId is null for user: " + user.getUserName() + ". Assigning default UUID.");
            jsonUser.addProperty("userId", "00000000-0000-0000-0000-000000000000");
        }

        jsonUser.addProperty("firstName", user.getFirstName());
        jsonUser.addProperty("password", user.getPassword());
        jsonUser.addProperty("progress", user.trackProgress());
        jsonUser.addProperty("dailyReminder", user.isDailyReminder());
        jsonUser.addProperty("email", user.getEmail());

        // Serialize Achievements
        List<Achievements> achievements = user.getAchievements();
        JsonElement jsonAchievements = context.serialize(achievements);
        jsonUser.add("achievements", jsonAchievements);

        // Serialize Current Courses
        List<Course> currentCourses = user.getCurrentCourses();
        JsonArray jsonCurrentCourses = new JsonArray();
        for (Course course : currentCourses) {
            JsonObject jsonCourse = new JsonObject();

            // Serialize courseId
            UUID courseId = course.getCourseId();
            if (courseId != null) {
                jsonCourse.addProperty("courseId", courseId.toString());
            } else {
                System.out.println("⚠️ Warning: courseId is null for a course in user: " + user.getUserName() + ". Assigning default UUID.");
                jsonCourse.addProperty("courseId", "00000000-0000-0000-0000-000000000000");
            }

            // Serialize courseProgress
            Double courseProgress = user.getProgressPerCourse().get(
                courseId != null ? courseId.toString() : "00000000-0000-0000-0000-000000000000"
            );
            jsonCourse.addProperty("courseProgress", courseProgress != null ? courseProgress.toString() : "0");

            // Serialize currentLessonId from Course's Lessons
            List<Lesson> lessons = course.getLessons(); // Assuming getLessons() exists
            if (lessons != null && !lessons.isEmpty()) {
                Lesson firstLesson = lessons.get(0); // Choose the first lesson as current
                UUID lessonId = firstLesson.getLessonId();
                if (lessonId != null) {
                    jsonCourse.addProperty("currentLessonId", lessonId.toString());
                } else {
                    System.out.println("⚠️ Warning: lessonId is null for a lesson in course: " + course.getCourseName() + " of user: " + user.getUserName() + ". Assigning default UUID.");
                    jsonCourse.addProperty("currentLessonId", "00000000-0000-0000-0000-000000000000");
                }
            } else {
                System.out.println("⚠️ Warning: No lessons found for course: " + course.getCourseName() + " in user: " + user.getUserName() + ". Assigning default UUID.");
                jsonCourse.addProperty("currentLessonId", "00000000-0000-0000-0000-000000000000");
            }

            // Serialize lessonProgress (Placeholder value)
            String lessonProgress = "45%"; // Replace with actual logic if available
            jsonCourse.addProperty("lessonProgress", lessonProgress);

            jsonCurrentCourses.add(jsonCourse);
        }
        jsonUser.add("currentCourses", jsonCurrentCourses);

        // Serialize Current Question
        Question currentQuestion = user.getCurrentQuestion();
        if (currentQuestion != null) {
            JsonObject jsonCurrentQuestion = new JsonObject();

            String questionId = currentQuestion.getId().toString();
            if (questionId != null && !questionId.trim().isEmpty()) {
                jsonCurrentQuestion.addProperty("questionId", questionId);
            } else {
                System.out.println("⚠️ Warning: questionId is null or empty for a question in user: " + user.getUserName() + ". Assigning default questionId.");
                jsonCurrentQuestion.addProperty("questionId", "00000000-0000-0000-0000-000000000000");
            }

            jsonCurrentQuestion.addProperty("text", currentQuestion.getText());
            jsonCurrentQuestion.addProperty("type", currentQuestion.getType());
            jsonCurrentQuestion.addProperty("correctAnswer", currentQuestion.getCorrectAnswer());

            jsonUser.add("currentQuestion", jsonCurrentQuestion);
        } else {
            jsonUser.add("currentQuestion", null);
        }

        // Serialize Question History
        List<QuestionHistory> questionHistory = user.getQuestionHistory();
        JsonElement jsonQuestionHistory = context.serialize(questionHistory);
        jsonUser.add("questionHistory", jsonQuestionHistory);

        // Serialize Missed Words
        List<Word> missedWords = user.getMissedWords();
        if (missedWords != null && !missedWords.isEmpty()) {
            JsonArray jsonMissedWords = new JsonArray();
            for (Word word : missedWords) {
                jsonMissedWords.add(word.getWord());
            }
            jsonUser.add("missedWords", jsonMissedWords);
        } else {
            jsonUser.add("missedWords", null);
        }

        // Serialize Favorite Languages
        List<Language> favoriteLanguages = user.getFavoriteLanguages();
        JsonArray jsonFavoriteLanguages = new JsonArray();
        for (Language lang : favoriteLanguages) {
            jsonFavoriteLanguages.add(lang.getLanguageName());
        }
        jsonUser.add("favoriteLanguages", jsonFavoriteLanguages);

        // Serialize Completed Lesson IDs
        List<String> completedLessonIds = user.getCompletedLessonIds();
        JsonElement jsonCompletedLessonIds = context.serialize(completedLessonIds);
        jsonUser.add("completedLessonIds", jsonCompletedLessonIds);

        // Serialize Completed Course IDs
        List<String> completedCourseIds = user.getCompletedCourseIds();
        JsonElement jsonCompletedCourseIds = context.serialize(completedCourseIds);
        jsonUser.add("completedCourseIds", jsonCompletedCourseIds);

        return jsonUser;
    }
}
