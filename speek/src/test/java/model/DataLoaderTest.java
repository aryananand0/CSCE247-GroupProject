package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the DataLoader class.
 * Tests loading methods with invalid, malformed, or unexpected data to ensure robustness.
 */
public class DataLoaderTest {

    @TempDir
    Path tempDir;

    // Paths to temporary JSON files
    private Path tempUserFile;
    private Path tempCourseFile;
    private Path tempFlashcardFile;
    private Path tempLessonFile;
    private Path tempLeaderboardFile;
    private Path tempWordsFile;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize temporary files
        tempUserFile = tempDir.resolve("json/Users.json");
        tempCourseFile = tempDir.resolve("json/Courses.json");
        tempFlashcardFile = tempDir.resolve("json/Flashcards.json");
        tempLessonFile = tempDir.resolve("json/Lessons.json");
        tempLeaderboardFile = tempDir.resolve("json/Leaderboard.json");
        tempWordsFile = tempDir.resolve("json/Words.json");

        // Create directories if necessary
        Files.createDirectories(tempUserFile.getParent());

    }

    /**
     * Helper method to write JSON content to a temporary file.
     *
     * @param filePath    The path of the temporary file.
     * @param jsonContent The JSON content to write.
     * @throws Exception If an I/O error occurs.
     */
    private void writeJsonToFile(Path filePath, String jsonContent) throws Exception {
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, jsonContent);
    }

    /**
     * Test loading users with malformed JSON to see if DataLoader handles parsing errors.
     */
    @Test
    @DisplayName("Test loadUsers with malformed JSON")
    public void testLoadUsers_MalformedJson() throws Exception {
        // Malformed JSON (missing closing braces)
        String malformedJson = "[{ \"userName\": \"johnDoe\", \"firstName\": \"John\", \"lastName\": \"Doe\" ";

        writeJsonToFile(tempUserFile, malformedJson);

        ArrayList<User> users = DataLoader.loadUsers();
        assertNull(users, "loadUsers should return null or throw an exception when JSON is malformed.");
    }

    /**
     * Test loading users with missing required fields to see if DataLoader handles incomplete data.
     */
    @Test
    @DisplayName("Test loadUsers with missing required fields")
    public void testLoadUsers_MissingFields() throws Exception {
        // JSON user object missing fields like userId, firstName, lastName, etc.
        String incompleteJson = "[\n" +
                "  {\n" +
                "    \"userName\": \"janeDoe\",\n" +
                "    \"email\": \"janedoe@example.com\"\n" +
                "  }\n" +
                "]";

        writeJsonToFile(tempUserFile, incompleteJson);

        ArrayList<User> users = DataLoader.loadUsers();
        assertNotNull(users, "loadUsers should handle users with missing fields gracefully.");
        assertEquals(1, users.size(), "There should be one user loaded despite missing fields.");

        User user = users.get(0);
        assertEquals("janeDoe", user.getUserName(), "Username should match.");
        assertEquals("janedoe@example.com", user.getEmail(), "Email should match.");
        assertNull(user.getUserId(), "userId should be null due to missing field.");
        assertNull(user.getFirstName(), "firstName should be null due to missing field.");
        assertNull(user.getLastName(), "lastName should be null due to missing field.");
    }

    /**
     * Test loading users with invalid data types to see if DataLoader validates field types.
     */
    @Test
    @DisplayName("Test loadUsers with invalid data types")
    public void testLoadUsers_InvalidDataTypes() throws Exception {
        // userName should be a string, but provided as a number
        String invalidTypeJson = "[\n" +
                "  {\n" +
                "    \"userName\": 12345,\n" +
                "    \"firstName\": \"Alice\",\n" +
                "    \"lastName\": \"Wonderland\",\n" +
                "    \"email\": \"alice@example.com\",\n" +
                "    \"password\": \"securepassword\"\n" +
                "  }\n" +
                "]";

        writeJsonToFile(tempUserFile, invalidTypeJson);

        ArrayList<User> users = DataLoader.loadUsers();
        assertNull(users, "loadUsers should return null or throw an exception when data types are invalid.");
    }

    /**
     * Test loading users from an empty JSON array to see if DataLoader handles empty data.
     */
    @Test
    @DisplayName("Test loadUsers with empty JSON array")
    public void testLoadUsers_EmptyJsonArray() throws Exception {
        // Empty JSON array
        String emptyJson = "[]";

        writeJsonToFile(tempUserFile, emptyJson);

        ArrayList<User> users = DataLoader.loadUsers();
        assertNotNull(users, "loadUsers should return an empty list when JSON array is empty.");
        assertTrue(users.isEmpty(), "Users list should be empty.");
    }

    /**
     * Test loading courses with malformed JSON.
     */
    @Test
    @DisplayName("Test loadCourses with malformed JSON")
    public void testLoadCourses_MalformedJson() throws Exception {
        // Malformed JSON (missing closing braces)
        String malformedJson = "{ \"courses\": [ { \"courseName\": \"Spanish 101\", \"difficulty\": \"Beginner\" } ";

        writeJsonToFile(tempCourseFile, malformedJson);

        ArrayList<Course> courses = DataLoader.loadCourses();
        assertNull(courses, "loadCourses should return null or throw an exception when JSON is malformed.");
    }

    /**
     * Test loading courses with missing required fields.
     */
    @Test
    @DisplayName("Test loadCourses with missing required fields")
    public void testLoadCourses_MissingFields() throws Exception {
        // JSON course object missing 'difficulty' field
        String incompleteJson = "{ \"courses\": [ { \"courseName\": \"French 101\" } ] }";

        writeJsonToFile(tempCourseFile, incompleteJson);

        ArrayList<Course> courses = DataLoader.loadCourses();
        assertNotNull(courses, "loadCourses should handle courses with missing fields gracefully.");
        assertEquals(1, courses.size(), "There should be one course loaded despite missing fields.");

        Course course = courses.get(0);
        assertEquals("French 101", course.getCourseName(), "Course name should match.");
        assertNull(course.getDifficulty(), "Difficulty should be null due to missing field.");
    }

    /**
     * Test loading flashcards with invalid courseId and lessonId.
     */
    @Test
    @DisplayName("Test loadFlashcardsForLesson with invalid courseId and lessonId")
    public void testLoadFlashcardsForLesson_InvalidIds() throws Exception {
        UUID invalidCourseId = UUID.randomUUID();
        UUID invalidLessonId = UUID.randomUUID();

        String flashcardJson = "{\n" +
                "  \"languages\": [\n" +
                "    {\n" +
                "      \"courses\": [\n" +
                "        {\n" +
                "          \"courseId\": \"" + invalidCourseId + "\",\n" +
                "          \"flashcards\": [\n" +
                "            {\n" +
                "              \"lessonId\": \"" + invalidLessonId + "\",\n" +
                "              \"flashcards\": [\n" +
                "                {\"word\": \"hola\", \"translation\": \"hello\"},\n" +
                "                {\"word\": \"adiós\", \"translation\": \"goodbye\"}\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempFlashcardFile, flashcardJson);

        Flashcard flashcard = DataLoader.loadFlashcardsForLesson(invalidCourseId, invalidLessonId);
        assertNotNull(flashcard, "Flashcard object should not be null even if IDs are invalid.");
        assertTrue(flashcard.getAllFlashcards().isEmpty(), "Flashcards should be empty for invalid courseId and lessonId.");
    }

    /**
     * Test loading lessons with invalid JSON structure.
     */
    @Test
    @DisplayName("Test loadLessons with invalid JSON structure")
    public void testLoadLessons_InvalidJsonStructure() throws Exception {
        // Missing 'lessonId' in lesson objects
        String invalidJson = "{ \"languages\": [ { \"courses\": [ { \"courseId\": \"invalid-uuid\", \"lessons\": [ { \"lessonName\": \"Introduction\" } ] } ] } ] }";

        writeJsonToFile(tempLessonFile, invalidJson);

        ArrayList<Lesson> lessons = DataLoader.loadLessons();
        assertNull(lessons, "loadLessons should return null or throw an exception when JSON structure is invalid.");
    }

    /**
     * Test loading leaderboard with duplicate user entries.
     */
    @Test
    @DisplayName("Test loadLeaderboard with duplicate user entries")
    public void testLoadLeaderboard_DuplicateUsers() throws Exception {
        String leaderboardJson = "{\n" +
                "  \"leaderboard\": [\n" +
                "    { \"user\": { \"firstName\": \"Alice\", \"lastName\": \"Smith\", \"score\": 1500.0, \"uuid\": \"" + UUID.randomUUID() + "\", \"userName\": \"alice_s\" } },\n" +
                "    { \"user\": { \"firstName\": \"Bob\", \"lastName\": \"Brown\", \"score\": 1200.0, \"uuid\": \"" + UUID.randomUUID() + "\", \"userName\": \"bob_b\" } },\n" +
                "    { \"user\": { \"firstName\": \"Alice\", \"lastName\": \"Smith\", \"score\": 1500.0, \"uuid\": \"" + UUID.randomUUID() + "\", \"userName\": \"alice_s\" } }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempLeaderboardFile, leaderboardJson);

        Leaderboard leaderboard = DataLoader.loadLeaderboard();
        assertNotNull(leaderboard, "Leaderboard should not be null.");
        assertEquals(3, leaderboard.getUser().size(), "Leaderboard should have three user entries, including duplicates.");

        // Depending on implementation, duplicates may be allowed or handled
        // Here, we assume duplicates are allowed
    }

    /**
     * Test loading words with invalid JSON.
     */
    

    /**
     * Test loading flashcards with empty flashcards list.
     */
    @Test
    @DisplayName("Test loadFlashcardsForLesson with empty flashcards list")
    public void testLoadFlashcardsForLesson_EmptyFlashcards() throws Exception {
        UUID courseId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();

        String flashcardJson = "{\n" +
                "  \"languages\": [\n" +
                "    {\n" +
                "      \"courses\": [\n" +
                "        {\n" +
                "          \"courseId\": \"" + courseId + "\",\n" +
                "          \"flashcards\": [\n" +
                "            {\n" +
                "              \"lessonId\": \"" + lessonId + "\",\n" +
                "              \"flashcards\": []\n" + // Empty flashcards
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempFlashcardFile, flashcardJson);

        Flashcard flashcard = DataLoader.loadFlashcardsForLesson(courseId, lessonId);
        assertNotNull(flashcard, "Flashcard object should not be null even with empty flashcards list.");
        assertTrue(flashcard.getAllFlashcards().isEmpty(), "Flashcards should be empty when JSON flashcards list is empty.");
    }

    /**
     * Test loading courses with duplicate course IDs.
     */
    @Test
    @DisplayName("Test loadCourses with duplicate course IDs")
    public void testLoadCourses_DuplicateCourseIds() throws Exception {
        UUID duplicateCourseId = UUID.randomUUID();

        String coursesJson = "{\n" +
                "  \"languages\": [\n" +
                "    {\n" +
                "      \"courses\": [\n" +
                "        { \"courseId\": \"" + duplicateCourseId + "\", \"courseName\": \"Spanish 101\", \"difficulty\": \"Beginner\" },\n" +
                "        { \"courseId\": \"" + duplicateCourseId + "\", \"courseName\": \"Spanish 201\", \"difficulty\": \"Intermediate\" }\n" + // Duplicate courseId
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempCourseFile, coursesJson);

        ArrayList<Course> courses = DataLoader.loadCourses();
        assertNotNull(courses, "loadCourses should not return null even with duplicate course IDs.");
        assertEquals(2, courses.size(), "There should be two courses loaded, even with duplicate IDs.");

        // Depending on implementation, duplicates may be allowed or handled appropriately
        // Here, we assume duplicates are allowed but should have unique IDs
    }

    /**
     * Test loading lessons with null fields.
     */
    @Test
    @DisplayName("Test loadLessons with null fields in lessons")
    public void testLoadLessons_NullFields() throws Exception {
        String lessonsJson = "{\n" +
                "  \"languages\": [\n" +
                "    {\n" +
                "      \"courses\": [\n" +
                "        {\n" +
                "          \"courseId\": \"" + UUID.randomUUID() + "\",\n" +
                "          \"lessons\": [\n" +
                "            { \"lessonId\": \"" + UUID.randomUUID() + "\", \"lessonName\": null, \"content\": null, \"tests\": null }\n" + // Null fields
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempLessonFile, lessonsJson);

        ArrayList<Lesson> lessons = DataLoader.loadLessons();
        assertNotNull(lessons, "loadLessons should handle lessons with null fields gracefully.");
        assertEquals(1, lessons.size(), "There should be one lesson loaded.");

        Lesson lesson = lessons.get(0);
        assertNull(lesson.getLessonTitle(), "Lesson title should be null.");
        assertNull(lesson.getContent(), "Lesson content should be null.");
        assertTrue(lesson.getQuestions().isEmpty(), "Lesson questions should be empty when 'tests' is null.");
    }

    /**
     * Test loading leaderboard with incorrect score data type.
     */
    @Test
    @DisplayName("Test loadLeaderboard with incorrect score data type")
    public void testLoadLeaderboard_IncorrectScoreType() throws Exception {
        String leaderboardJson = "{\n" +
                "  \"leaderboard\": [\n" +
                "    { \"user\": { \"firstName\": \"Eve\", \"lastName\": \"Adams\", \"score\": \"high\", \"uuid\": \"" + UUID.randomUUID() + "\", \"userName\": \"eve_a\" } }\n" + // 'score' should be a number
                "  ]\n" +
                "}";

        writeJsonToFile(tempLeaderboardFile, leaderboardJson);

        Leaderboard leaderboard = DataLoader.loadLeaderboard();
        assertNull(leaderboard, "loadLeaderboard should return null or throw an exception when score data type is incorrect.");
    }

   
    /**
     * Test loading leaderboard with empty leaderboard array.
     */
    @Test
    @DisplayName("Test loadLeaderboard with empty leaderboard array")
    public void testLoadLeaderboard_EmptyLeaderboard() throws Exception {
        String emptyLeaderboardJson = "{ \"leaderboard\": [] }";

        writeJsonToFile(tempLeaderboardFile, emptyLeaderboardJson);

        Leaderboard leaderboard = DataLoader.loadLeaderboard();
        assertNotNull(leaderboard, "Leaderboard should not be null.");
        assertTrue(leaderboard.getUser().isEmpty(), "Leaderboard should have no users.");
    }

    /**
     * Test loading courses with extra unexpected fields to see if DataLoader ignores them.
     */
    @Test
    @DisplayName("Test loadCourses with extra unexpected fields")
    public void testLoadCourses_ExtraFields() throws Exception {
        String coursesJson = "{\n" +
                "  \"languages\": [\n" +
                "    {\n" +
                "      \"courses\": [\n" +
                "        { \"courseId\": \"" + UUID.randomUUID() + "\", \"courseName\": \"German 101\", \"difficulty\": \"Beginner\", \"extraField\": \"unexpected\" }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempCourseFile, coursesJson);

        ArrayList<Course> courses = DataLoader.loadCourses();
        assertNotNull(courses, "loadCourses should handle extra unexpected fields gracefully.");
        assertEquals(1, courses.size(), "There should be one course loaded.");

        Course course = courses.get(0);
        assertEquals("German 101", course.getCourseName(), "Course name should match.");
        assertEquals("Beginner", course.getDifficulty(), "Course difficulty should match.");
    }

    /**
     * Test loading users with additional unexpected nested structures.
     */
    @Test
    @DisplayName("Test loadUsers with additional unexpected nested structures")
    public void testLoadUsers_ExtraNestedStructures() throws Exception {
        String usersJson = "[\n" +
                "  {\n" +
                "    \"userName\": \"markTwain\",\n" +
                "    \"firstName\": \"Mark\",\n" +
                "    \"lastName\": \"Twain\",\n" +
                "    \"email\": \"marktwain@example.com\",\n" +
                "    \"password\": \"securepassword\",\n" +
                "    \"achievements\": [],\n" +
                "    \"currentCourses\": [],\n" +
                "    \"extraNestedStructure\": { \"unexpected\": \"data\" }\n" + // Extra nested structure
                "  }\n" +
                "]";

        writeJsonToFile(tempUserFile, usersJson);

        ArrayList<User> users = DataLoader.loadUsers();
        assertNotNull(users, "loadUsers should handle additional unexpected nested structures gracefully.");
        assertEquals(1, users.size(), "There should be one user loaded.");

        User user = users.get(0);
        assertEquals("markTwain", user.getUserName(), "Username should match.");
        assertEquals("Mark", user.getFirstName(), "First name should match.");
        assertEquals("Twain", user.getLastName(), "Last name should match.");
        assertEquals("marktwain@example.com", user.getEmail(), "Email should match.");
        assertEquals("securepassword", user.getPassword(), "Password should match.");
        assertTrue(user.getAchievements().isEmpty(), "Achievements should be empty.");
        assertTrue(user.getCurrentCourses().isEmpty(), "Current courses should be empty.");
        // Extra nested structures should be ignored without affecting the user object
    }

    /**
     * Test loading lessons with duplicate lesson IDs.
     */
    @Test
    @DisplayName("Test loadLessons with duplicate lesson IDs")
    public void testLoadLessons_DuplicateLessonIds() throws Exception {
        UUID duplicateLessonId = UUID.randomUUID();

        String lessonsJson = "{\n" +
                "  \"languages\": [\n" +
                "    {\n" +
                "      \"courses\": [\n" +
                "        {\n" +
                "          \"courseId\": \"" + UUID.randomUUID() + "\",\n" +
                "          \"lessons\": [\n" +
                "            { \"lessonId\": \"" + duplicateLessonId + "\", \"lessonName\": \"Introduction\", \"content\": \"Welcome to the course.\", \"tests\": [] },\n" +
                "            { \"lessonId\": \"" + duplicateLessonId + "\", \"lessonName\": \"Basics\", \"content\": \"Let's learn the basics.\", \"tests\": [] }\n" + // Duplicate lessonId
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempLessonFile, lessonsJson);

        ArrayList<Lesson> lessons = DataLoader.loadLessons();
        assertNotNull(lessons, "loadLessons should not return null even with duplicate lesson IDs.");
        assertEquals(2, lessons.size(), "There should be two lessons loaded, even with duplicate IDs.");

        // Depending on implementation, duplicates may be allowed or handled appropriately
    }

  
    

    /**
     * Test loading flashcards with missing word or translation fields.
     */
    @Test
    @DisplayName("Test loadFlashcardsForLesson with missing word or translation")
    public void testLoadFlashcardsForLesson_MissingFields() throws Exception {
        UUID courseId = UUID.randomUUID();
        UUID lessonId = UUID.randomUUID();

        String flashcardJson = "{\n" +
                "  \"languages\": [\n" +
                "    {\n" +
                "      \"courses\": [\n" +
                "        {\n" +
                "          \"courseId\": \"" + courseId + "\",\n" +
                "          \"flashcards\": [\n" +
                "            {\n" +
                "              \"lessonId\": \"" + lessonId + "\",\n" +
                "              \"flashcards\": [\n" +
                "                { \"word\": \"hola\" },\n" + // Missing 'translation'
                "                { \"translation\": \"goodbye\" }\n" + // Missing 'word'
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        writeJsonToFile(tempFlashcardFile, flashcardJson);

        Flashcard flashcard = DataLoader.loadFlashcardsForLesson(courseId, lessonId);
        assertNotNull(flashcard, "Flashcard object should not be null even with missing fields.");
        assertEquals(0, flashcard.getAllFlashcards().size(), "Flashcards should not include entries with missing fields.");
    }

    /**
     * Test loading users with extra unexpected array elements.
     */
    @Test
    @DisplayName("Test loadUsers with extra unexpected array elements")
    public void testLoadUsers_ExtraArrayElements() throws Exception {
        String usersJson = "[\n" +
                "  {\n" +
                "    \"userName\": \"tomRiddle\",\n" +
                "    \"firstName\": \"Tom\",\n" +
                "    \"lastName\": \"Riddle\",\n" +
                "    \"email\": \"tomriddle@example.com\",\n" +
                "    \"password\": \"password123\",\n" +
                "    \"achievements\": [ { \"achievementId\": \"ach1\", \"title\": \"Master\", \"description\": \"Mastered advanced topics\", \"rewardPoints\": 300 } ],\n" +
                "    \"currentCourses\": [ { \"courseId\": \"" + UUID.randomUUID() + "\", \"courseProgress\": \"100%\", \"currentLessonId\": \"" + UUID.randomUUID() + "\", \"lessonProgress\": \"100%\" } ],\n" +
                "    \"extraArrayElement\": [ \"unexpected1\", \"unexpected2\" ]\n" + // Extra array elements
                "  }\n" +
                "]";

        writeJsonToFile(tempUserFile, usersJson);

        ArrayList<User> users = DataLoader.loadUsers();
        assertNotNull(users, "loadUsers should handle extra unexpected array elements gracefully.");
        assertEquals(1, users.size(), "There should be one user loaded despite extra array elements.");

        User user = users.get(0);
        assertEquals("tomRiddle", user.getUserName(), "Username should match.");
        assertEquals("Tom", user.getFirstName(), "First name should match.");
        assertEquals("Riddle", user.getLastName(), "Last name should match.");
        assertEquals("tomriddle@example.com", user.getEmail(), "Email should match.");
        assertEquals("password123", user.getPassword(), "Password should match.");
        assertEquals(1, user.getAchievements().size(), "User should have one achievement.");
        assertEquals(1, user.getCurrentCourses().size(), "User should have one current course.");
        // Extra array elements should be ignored without affecting the user object
    }
}
