package model;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        CourseList cl = CourseList.getInstance();
        LessonList ll = LessonList.getInstance();
    
        try {
            FileReader reader = new FileReader("../json/User.json");
            JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);
    
            for (Object userObj : usersJSON) {
                JSONObject userJSON = (JSONObject) userObj;
    
                // Create a new User object
                User user = createUser(userJSON);
                user.setAchievements(loadAchievements(userJSON));
                user.setCurrentCourses(loadCurrentCourses(userJSON, cl, ll));
                user.setQuestionHistory(loadQuestionHistory(userJSON));
    
                // Add the user to the users list
                users.add(user);
            }
            return users;
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to parse words from Word.json and use Course class
    public static List<Course> parseWordsFromJson() {
        List<Course> courses = new ArrayList<>();

        try (FileReader reader = new FileReader(WORD_FILE)) { // Use the constant WORD_FILE
            // Parse the JSON file
            JSONParser parser = new JSONParser();
            JSONArray jsonModulesArray = (JSONArray) parser.parse(reader);

            // Iterate through each module
            for (Object moduleObj : jsonModulesArray) {
                JSONObject jsonModule = (JSONObject) moduleObj;

                String moduleName = (String) jsonModule.get(MODULE);
                String courseIdStr = (String) jsonModule.get(COURSE_ID);
                UUID courseId = UUID.fromString(courseIdStr);

                // Create a new Course object
                Course course = new Course(courseId, moduleName, "", 0.0);

                // Extract the lessons from the module
                JSONArray lessonsArray = (JSONArray) jsonModule.get(WORDS);

                for (Object lessonObj : lessonsArray) {
                    JSONObject jsonLesson = (JSONObject) lessonObj;

                    String lessonIdStr = (String) jsonLesson.get(LESSON_ID);
                    UUID lessonId = UUID.fromString(lessonIdStr);
                    String lessonName = (String) jsonLesson.get(LESSON_NAME);

                    // Create a new Lesson object
                    Lesson lesson = new Lesson(lessonId, lessonName, "", new ArrayList<>());

                    // Extract words from the lesson
                    JSONArray wordsArray = (JSONArray) jsonLesson.get(WORDS);

                    for (Object wordObj : wordsArray) {
                        JSONObject jsonWord = (JSONObject) wordObj;
                        String word = (String) jsonWord.get(WORD);
                        String translation = (String) jsonWord.get(TRANSLATION);

                        // Create a new Word object
                        Word wordObjParsed = new Word(word, translation);
                        lesson.addWord(wordObjParsed); // Assuming Lesson has a method addWord
                    }

                    // Add the lesson to the course
                    course.addLesson(lesson);
                }

                // Add the course to the courses list
                courses.add(course);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public static ArrayList<Word> loadWords() {
        ArrayList<Word> words = new ArrayList<>();
    
        try (FileReader reader = new FileReader(WORD_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray modulesArray = (JSONArray) parser.parse(reader);
    
            System.out.println("Debug: Loaded modulesArray from JSON file.");
    
            for (Object moduleObj : modulesArray) {
                JSONObject moduleJSON = (JSONObject) moduleObj;
                //System.out.println("Debug: Processing module: " + moduleJSON.get("module"));
    
                // Ensure that "words" key exists and is not null
                JSONArray lessonsArray = (JSONArray) moduleJSON.get("words");
                if (lessonsArray == null) {
                    System.out.println("Debug: No 'words' key found at module level.");
                    continue; // Skip if "words" is not present
                }
    
                // Loop through lessons in each module
                for (Object lessonObj : lessonsArray) {
                    JSONObject lessonJSON = (JSONObject) lessonObj;
                    //System.out.println("Debug: Processing lesson: " + lessonJSON.get("lessonName"));
    
                    // Ensure that "words" key exists and is not null
                    JSONArray wordsArray = (JSONArray) lessonJSON.get("words");
                    if (wordsArray == null) {
                        System.out.println("Debug: No 'words' key found at lesson level.");
                        continue; // Skip if "words" is not present
                    }
    
                    // Loop through words in each lesson
                    for (Object wordObj : wordsArray) {
                        JSONObject wordJSON = (JSONObject) wordObj;
    
                        // Retrieve word and translation
                        String wordText = (String) wordJSON.get("word");
                        String translation = (String) wordJSON.get("translation");
    
                        if (wordText != null && translation != null) {
                            Word word = new Word(wordText, translation);
                            words.add(word);
                            //System.out.println("Debug: Successfully added word: " + wordText + " - " + translation);
                        } else {
                            System.out.println("⚠️ Missing data for word or translation in JSON object: " + wordJSON);
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    
        return words;
    }
    
    
    
    
    
    
    // Create a new user object from JSON
    private static User createUser(JSONObject userJSON) {
        UUID uuid = UUID.fromString((String) userJSON.get(USER_UUID));
        String userName = (String) userJSON.get(USER_USER_NAME);
        String firstName = (String) userJSON.get(USER_FIRST_NAME);
        String lastName = (String) userJSON.get(USER_LAST_NAME);
        String password = (String) userJSON.get(USER_PASSWORD);
        String email = (String) userJSON.get(USER_EMAIL);
    
        User user = new User(uuid, userName, firstName, lastName);
        user.setPassword(password);
        user.setEmail(email);
    
        return user;
    }
    
    // Load achievements from JSON
    private static ArrayList<Achievements> loadAchievements(JSONObject userJSON) {
        ArrayList<Achievements> achievements = new ArrayList<>();
        JSONArray achievementsJSON = (JSONArray) userJSON.get("achievements");
    
        for (Object achievementObj : achievementsJSON) {
            JSONObject achievementJSON = (JSONObject) achievementObj;
            String title = (String) achievementJSON.get("title");
            String description = (String) achievementJSON.get("description");
            int rewardPoints = ((Long) achievementJSON.get("rewardPoints")).intValue();
    
            achievements.add(new Achievements(title, description, rewardPoints));
        }
        return achievements;
    }
    
    // Load current courses from JSON
    private static ArrayList<Course> loadCurrentCourses(JSONObject userJSON, CourseList cl, LessonList ll) {
        ArrayList<Course> currentCourses = new ArrayList<>();
        JSONArray currentCoursesJSON = (JSONArray) userJSON.get("currentCourses");
    
        for (Object courseObj : currentCoursesJSON) {
            JSONObject courseJSON = (JSONObject) courseObj;
            UUID courseId = UUID.fromString((String) courseJSON.get("courseId"));
            String currentLessonId = (String) courseJSON.get("currentLessonId");
            String lessonProgress = (String) courseJSON.get("lessonProgress");
            String courseProgress = (String) courseJSON.get("courseProgress");
    
            // Retrieve the course and current lesson, and set progress
            Course course = cl.getCourse(courseId);
            course.setCourseCompletion(Double.parseDouble(courseProgress));
    
            Lesson currentLesson = ll.getLessonById(UUID.fromString(currentLessonId));
            course.addLesson(currentLesson);
    
            currentCourses.add(course);
        }
        return currentCourses;
    }
    
    // Load question history from JSON
    private static ArrayList<QuestionHistory> loadQuestionHistory(JSONObject userJSON) {
        ArrayList<QuestionHistory> questionHistory = new ArrayList<>();
        JSONArray questionHistoryJSON = (JSONArray) userJSON.get("questionHistory");
    
        for (Object questionObj : questionHistoryJSON) {
            JSONObject questionJSON = (JSONObject) questionObj;
            String questionId = (String) questionJSON.get("questionId");
            String questionText = (String) questionJSON.get("questionText");
            String userAnswer = (String) questionJSON.get("userAnswer");
            String correctAnswer = (String) questionJSON.get("correctAnswer");
            boolean isCorrect = (Boolean) questionJSON.get("isCorrect");
    
            questionHistory.add(new QuestionHistory(questionId, questionText, userAnswer, correctAnswer, isCorrect));
        }
        return questionHistory;
    }
    public static ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            FileReader reader = new FileReader(COURSE_FILE);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray courseJSONArray = (JSONArray) jsonObject.get(COURSES);

            for (Object courseObj : courseJSONArray) {
                JSONObject courseJSONObject = (JSONObject) courseObj;
                String courseName = (String) courseJSONObject.get(COURSE_NAME);
                String difficulty = (String) courseJSONObject.get(COURSE_DIFFICULTY);

                courses.add(new Course(courseName, difficulty));
            }

            return courses;

        } catch (Exception e) {
            e.printStackTrace();  
        }

        return courses; 
    }

    public static ArrayList<Language> loadLanguages() {
        ArrayList<Language> languages = new ArrayList<>();

        try {
            // Read the JSON file
            FileReader reader = new FileReader(LANGUAGE_FILE);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);  // Parse the root object

            // Extract the languages array
            JSONArray languageArray = (JSONArray) jsonObject.get(LANGUAGES);  // Get the "languages" array

            // Iterate through each language
            for (Object obj : languageArray) {
                JSONObject languageJSON = (JSONObject) obj;
                String languageName = (String) languageJSON.get(LANGUAGE_NAME);  // Extract language name
                
                // Create a new Language object
                Language language = new Language(languageName);

                // Load courses
                JSONArray coursesArray = (JSONArray) languageJSON.get(COURSES);
                for (Object courseObj : coursesArray) {
                    JSONObject courseJSON = (JSONObject) courseObj;
                    String courseName = (String) courseJSON.get(COURSE_NAME);
                    String difficulty = (String) courseJSON.get(COURSE_DIFFICULTY);
                    double courseCompletion = (Double) courseJSON.get(COURSE_COMPLETION);

                    // Create a Course object and add it to the language
                    Course course = new Course(courseName, difficulty, courseCompletion);
                    language.getCourses().add(course);
                }

                // Load flashcards
                JSONArray flashcardsArray = (JSONArray) languageJSON.get(FLASHCARDS);
                for (Object flashcardObj : flashcardsArray) {
                    JSONObject flashcardJSON = (JSONObject) flashcardObj;
                    String word = (String) flashcardJSON.get(FLASHCARD_WORD);
                    String translation = (String) flashcardJSON.get(FLASHCARD_TRANSLATION);

                    // Create Flashcard object and add to language
                    // Flashcard flashcard = new Flashcard(word, translation);
                    // language.getFlashcards().add(flashcard);
                }

                // Add the language to the list
                languages.add(language);
            }

            return languages;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return languages;
    }
    

    public static Leaderboard loadLeaderboard() {
        ArrayList<User> users = new ArrayList<User>();
        Leaderboard leader = new Leaderboard();
    
        try {
            FileReader reader = new FileReader(LEADERBOARD_FILE);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray leaderboardJsonArray = (JSONArray) jsonObject.get(LEADERBOARD);
    
            for (Object leaderboardObject : leaderboardJsonArray) {
                JSONObject leaderboardJsonObject = (JSONObject) leaderboardObject;
    
                JSONObject userJsonObject = (JSONObject) leaderboardJsonObject.get(LEADERBOARD_USER);
    
                String firstName = (String) userJsonObject.get(LEADERBOARD_USER_FIRST_NAME);
                String lastName = (String) userJsonObject.get(LEADERBOARD_USER_LAST_NAME);
                
                Number score = (Number) userJsonObject.get(LEADERBOARD_USER_SCORE);
                double points = score.doubleValue();  
                UUID uuid = UUID.fromString((String) userJsonObject.get(LEADERBOARD_USER_UUID));
                String userName = (String) userJsonObject.get(LEADERBOARD_USER_NAME);
    
                users.add(new User(uuid,userName,firstName, lastName, points));
            }
    
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    return Double.compare(u2.getScore(), u1.getScore());  
                }
            });
    
            leader = new Leaderboard(users);
            return leader;
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return leader;  
    }

    public static ArrayList<Achievements> loadAchievements() {
        ArrayList<Achievements> achievements = new ArrayList<>();

        try {
            FileReader reader = new FileReader("../json/User.json");
            JSONParser parser = new JSONParser();
            JSONArray usersArray = (JSONArray) parser.parse(reader);  // This should be a JSONArray
           
            // Iterate through each user in the users array
            for (Object userObject : usersArray) {
                JSONObject userJSONObject = (JSONObject) userObject;

                // Now, get the 'achievements' array for each user
                JSONArray achievementsJSON = (JSONArray) userJSONObject.get(ACHIEVEMENTS);
                if (achievementsJSON != null) {
                    for (Object achievementObject : achievementsJSON) {
                        JSONObject achievementJSONObject = (JSONObject) achievementObject;

                        String title = (String) achievementJSONObject.get(ACHIEVEMENTS_TITLE);
                        String description = (String) achievementJSONObject.get(ACHIEVEMENTS_DESCRIPTION);

                        // Reward points are likely a Long in the JSON, so cast accordingly
                        Long rewardPointsLong = (Long) achievementJSONObject.get(ACHIEVEMENTS_REWARD_POINTS);
                        int rewardPoints = rewardPointsLong.intValue();  // Convert to int

                        achievements.add(new Achievements(title, description, rewardPoints));
                    }
                }
            }

            return achievements;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
    public static User getUser(String usernameOrEmail, String password) {
        ArrayList<User> users = loadUsers();
        for (User user : users) {
            if((user.getUserName().equals(usernameOrEmail) || user.getEmail().equalsIgnoreCase(usernameOrEmail)) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;  // Return null if user not found
    }
    */

    

    public static ArrayList<Course> loadCoursesFromJson() {
        ArrayList<Course> coursesList = new ArrayList<>();
        JSONParser parser = new JSONParser();
    
        try (FileReader reader = new FileReader("../json/Lesson.json")) {
            JSONObject root = (JSONObject) parser.parse(reader);
            JSONArray languages = (JSONArray) root.get("languages");
    
            for (Object langObj : languages) {
                JSONObject language = (JSONObject) langObj;
    
                // Process courses for each language
                JSONArray courses = (JSONArray) language.get("courses");
                for (Object courseObj : courses) {
                    Course course = parseCourse((JSONObject) courseObj);
                    if (course != null) {
                        coursesList.add(course);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Failed to parse JSON: " + e.getMessage());
            e.printStackTrace();
        }
    
        return coursesList;
    }
    
    private static Course parseCourse(JSONObject courseJson) {
        try {
            UUID courseId = UUID.fromString((String) courseJson.get("courseId"));
            String courseName = (String) courseJson.get("courseName");
            String difficulty = (String) courseJson.get("difficulty");
    
            Course course = new Course(courseId, courseName, difficulty, 0.0);
    
            // Process lessons for each course
            JSONArray lessons = (JSONArray) courseJson.get("lessons");
            for (Object lessonObj : lessons) {
                Lesson lesson = parseLesson((JSONObject) lessonObj, courseId);
                if (lesson != null) {
                    course.addLesson(lesson);
                }
            }
    
            return course;
        } catch (Exception e) {
            System.out.println("Error parsing course: " + e.getMessage());
            return null;
        }
    }
    
    private static Lesson parseLesson(JSONObject lessonJson,UUID courseID) {
        try {
            UUID lessonId = UUID.fromString((String) lessonJson.get("lessonId"));
            String lessonName = (String) lessonJson.get("lessonName");
    
            // Process content for each lesson
            Map<String, List<String>> content = parseContent((JSONObject) lessonJson.get("content"));
    
            // Convert the content map to a formatted string
            String contentStr = formatContentMap(content);
    
            Lesson lesson = new Lesson(lessonId, lessonName, contentStr, new ArrayList<>());

            lesson.setFlashcard(loadFlashcardsForLesson(courseID, lessonId));
    
            // Process tests and questions for each lesson
            JSONArray tests = (JSONArray) lessonJson.get("tests");
            for (Object testObj : tests) {
                parseQuestions((JSONObject) testObj, lesson);
            }
    
            return lesson;
        } catch (Exception e) {
            System.out.println("Error parsing lesson: " + e.getMessage());
            return null;
        }
    }
    
    private static Map<String, List<String>> parseContent(JSONObject contentJson) {
        Map<String, List<String>> contentMap = new HashMap<>();
    
        for (Object keyObj : contentJson.keySet()) {
            String key = (String) keyObj;
            Object value = contentJson.get(key);
    
            try {
                // Try to convert the value to a List of Strings
                List<String> contentList = convertToListOfStrings(value);
                contentMap.put(capitalizeFirstLetter(key), contentList);
            } catch (Exception e) {
                System.out.println("Error processing content for key: " + key + " with value: " + value);
            }
        }
    
        return contentMap;
    }
    
    private static List<String> convertToListOfStrings(Object value) throws Exception {
        List<String> contentList = new ArrayList<>();
    
        try {
            // Attempt to treat the value as a JSONArray
            JSONArray jsonArray = (JSONArray) value;
            for (Object item : jsonArray) {
                contentList.add(item.toString());  // Convert each item in the array to a string
            }
        } catch (ClassCastException e) {
            // If it's not a JSONArray, treat it as a single string
            contentList.add(value.toString());
        }
    
        return contentList;
    }
    
    private static String formatContentMap(Map<String, List<String>> contentMap) {
        StringBuilder contentBuilder = new StringBuilder();
    
        for (Map.Entry<String, List<String>> entry : contentMap.entrySet()) {
            contentBuilder.append(entry.getKey()).append(": ")
                    .append(String.join(", ", entry.getValue()))
                    .append("\n");
        }
    
        return contentBuilder.toString().trim();
    }
    
    private static void parseQuestions(JSONObject testJson, Lesson lesson) {
        JSONArray questions = (JSONArray) testJson.get("questions");
        for (Object questionObj : questions) {
            JSONObject questionJson = (JSONObject) questionObj;
            UUID questionId = UUID.fromString((String) questionJson.get("questionId"));
            String type = (String) questionJson.get("type");
            String text = (String) questionJson.get("text");
    
            Question question = createQuestion(questionJson, questionId, type, text);
            if (question != null) {
                lesson.addQuestion(question);  // Correctly adding questions to the lesson
            }
        }
    }
    
    private static Question createQuestion(JSONObject questionJson, UUID questionId, String type, String text) {
        try {
            switch (type) {
                case "MultipleChoice":
                    JSONArray optionsJson = (JSONArray) questionJson.get("options");
                    List<String> optionsList = new ArrayList<>();
                    for (Object option : optionsJson) {
                        optionsList.add((String) option);
                    }
                    String correctAnswerMC = (String) questionJson.get("correctAnswer");
                    return new MultipleChoiceQuestion(questionId, text, optionsList, correctAnswerMC);
    
                case "ShortAnswer":
                    String correctAnswerSA = (String) questionJson.get("correctAnswer");
                    return new ShortAnswerQuestion(questionId, text, correctAnswerSA);
    
                case "TrueOrFalse":
                    String correctAnswerTF = (String) questionJson.get("correctAnswer");
                    boolean correctBool = correctAnswerTF.equalsIgnoreCase("True");
                    return new TrueFalseQuestion(questionId, text, correctBool);
    
                case "MatchWords":
                    JSONObject pairsJson = (JSONObject) questionJson.get("pairs");
                    if (pairsJson != null) {
                        Map<String, String> correctMatches = new HashMap<>();
                        for (Object pairKey : pairsJson.keySet()) {
                            String key = (String) pairKey;
                            String value = (String) pairsJson.get(key);
                            correctMatches.put(key, value);
                        }
                        List<String> prompts = new ArrayList<>(correctMatches.keySet());
                        List<String> responses = new ArrayList<>(correctMatches.values());
                        return new MatchWordsQuestion(questionId, text, prompts, responses, correctMatches);
                    } else {
                        System.out.println("⚠️ 'pairs' not found for MatchWords question ID: " + questionId);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error creating question: " + e.getMessage());
        }
    
        return null;
    }
    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static ArrayList<Lesson> loadLessons() {
        ArrayList<Lesson> lessonsList = new ArrayList<>();
        JSONParser parser = new JSONParser();
    
        try (FileReader reader = new FileReader("../json/Lesson.json")) {
            JSONObject root = (JSONObject) parser.parse(reader);
            JSONArray languages = (JSONArray) root.get("languages");
    
            for (Object langObj : languages) {
                JSONObject language = (JSONObject) langObj;
    
                JSONArray courses = (JSONArray) language.get("courses");
                for (Object courseObj : courses) {
                    JSONObject courseJson = (JSONObject) courseObj;
    
                    // Extract lessons
                    JSONArray lessons = (JSONArray) courseJson.get("lessons");
                    for (Object lessonObj : lessons) {
                        JSONObject lessonJson = (JSONObject) lessonObj;
                        UUID lessonId = UUID.fromString((String) lessonJson.get("lessonId"));
                        String lessonName = (String) lessonJson.get("lessonName");
    
                        // Create a new Lesson object with an empty content (or set default content if needed)
                        Lesson lesson = new Lesson(lessonId, lessonName, "", new ArrayList<>());
    
                        // Load tests and questions for the lesson
                        JSONArray tests = (JSONArray) lessonJson.get("tests");
                        for (Object testObj : tests) {
                            JSONObject testJson = (JSONObject) testObj;
                            JSONArray questions = (JSONArray) testJson.get("questions");
    
                            for (Object questionObj : questions) {
                                JSONObject questionJson = (JSONObject) questionObj;
                                String questionId = (String) questionJson.get("questionId");
                                String type = (String) questionJson.get("type");
                                String text = (String) questionJson.get("text");
    
                                Question question = null;
                                switch (type) {
                                    case "MultipleChoice":
                                        JSONArray optionsJson = (JSONArray) questionJson.get("options");
                                        List<String> optionsList = new ArrayList<>();
                                        for (Object option : optionsJson) {
                                            optionsList.add((String) option);
                                        }
                                        String correctAnswerMC = (String) questionJson.get("correctAnswer");
                                        question = new MultipleChoiceQuestion(text, optionsList, correctAnswerMC);
                                        break;
                                    case "ShortAnswer":
                                        String correctAnswerSA = (String) questionJson.get("correctAnswer");
                                        question = new ShortAnswerQuestion(text, correctAnswerSA);
                                        break;
                                    case "TrueOrFalse":
                                        String correctAnswerTF = (String) questionJson.get("correctAnswer");
                                        boolean correctBool = correctAnswerTF.equalsIgnoreCase("True");
                                        question = new TrueFalseQuestion(text, correctBool);
                                        break;
                                    case "MatchWords":
                                        JSONObject pairsJson = (JSONObject) questionJson.get("pairs");
                                        if (pairsJson != null) {
                                            Map<String, String> correctMatches = new HashMap<>();
                                            for (Object pairKey : pairsJson.keySet()) {
                                                String key = (String) pairKey;
                                                String value = (String) pairsJson.get(key);
                                                correctMatches.put(key, value);
                                            }
                                            List<String> prompts = new ArrayList<>(correctMatches.keySet());
                                            List<String> responses = new ArrayList<>(correctMatches.values());
                                            question = new MatchWordsQuestion(text, prompts, responses, correctMatches);
                                        } else {
                                            System.out.println("⚠️ 'pairs' not found for MatchWords question ID: " + questionId);
                                        }
                                        break;
                                    default:
                                        System.out.println("❗ Unknown question type: " + type + " for question ID: " + questionId);
                                        break;
                                }
    
                                if (question != null) {
                                    lesson.getQuestions().add(question);
                                }
                            }
                        }
    
                        // Add the lesson with its questions to the list
                        lessonsList.add(lesson);
                    }
                }
            }
    
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Failed to parse JSON: " + e.getMessage());
            e.printStackTrace();
        }
    
        return lessonsList;
    }

    public static Flashcard loadFlashcardsForLesson(UUID courseId, UUID lessonId) {
        Flashcard flashcard = new Flashcard(); // Create an empty Flashcard object

        try (FileReader reader = new FileReader("../json/Flashcard.json")) { // Adjust the file path if necessary
            // Parse the JSON file
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Get the 'languages' array and assume the first one is Spanish
            JSONArray languagesArray = (JSONArray) jsonObject.get("languages");
            if (languagesArray == null || languagesArray.isEmpty()) {
                System.err.println("Error: 'languages' array not found or empty in JSON file.");
                return flashcard;
            }

            // Since Spanish is the default language, we can take the first language object
            JSONObject language = (JSONObject) languagesArray.get(0);
            if (language == null) {
                System.err.println("Error: Language JSON object is null.");
                return flashcard;
            }

            // Get the 'courses' array
            JSONArray coursesArray = (JSONArray) language.get("courses");
            if (coursesArray == null || coursesArray.isEmpty()) {
                System.err.println("Error: 'courses' array not found or empty in language JSON object.");
                return flashcard;
            }

            // Iterate over each course
            for (Object courseObj : coursesArray) {
                JSONObject course = (JSONObject) courseObj;
                if (course == null) continue;

                String courseIdStr = (String) course.get("courseId");
                if (courseIdStr == null) {
                    System.err.println("Warning: 'courseId' not found in course JSON object.");
                    continue; // Proceed to the next course
                }

                // Check if the courseId matches
                if (courseId.equals(UUID.fromString(courseIdStr))) {
                    // Get the 'flashcards' array for the course
                    JSONArray lessonsArray = (JSONArray) course.get("flashcards");
                    if (lessonsArray == null || lessonsArray.isEmpty()) {
                        System.err.println("Error: 'flashcards' array not found or empty in course JSON object.");
                        return flashcard;
                    }

                    // Iterate over each lesson
                    for (Object lessonObj : lessonsArray) {
                        JSONObject lesson = (JSONObject) lessonObj;
                        if (lesson == null) continue;

                        String lessonIdStr = (String) lesson.get("lessonId");
                        if (lessonIdStr == null) {
                            System.err.println("Warning: 'lessonId' not found in lesson JSON object.");
                            continue; // Proceed to the next lesson
                        }

                        // Check if the lessonId matches
                        if (lessonId.equals(UUID.fromString(lessonIdStr))) {
                            // Get the 'flashcards' array for the lesson
                            JSONArray flashcardsArray = (JSONArray) lesson.get("flashcards");
                            if (flashcardsArray == null || flashcardsArray.isEmpty()) {
                                System.err.println("Error: 'flashcards' array not found or empty in lesson JSON object.");
                                return flashcard;
                            }

                            // Iterate over each flashcard and add to the Flashcard object
                            for (Object flashcardObj : flashcardsArray) {
                                JSONObject flashcardEntry = (JSONObject) flashcardObj;
                                if (flashcardEntry == null) continue;

                                String word = (String) flashcardEntry.get("word");
                                String translation = (String) flashcardEntry.get("translation");

                                if (word == null || translation == null) {
                                    System.err.println("Warning: 'word' or 'translation' missing in flashcard JSON object.");
                                    continue; // Skip this flashcard
                                }

                                flashcard.addFlashcard(word, translation); // Add flashcard to the Flashcard object
                            }

                            // Return the flashcards for this lesson
                            return flashcard;
                        }
                    }

                    // If we reach here, the lessonId was not found in this course
                    System.err.println("Error: Lesson ID " + lessonId + " not found in course ID " + courseId);
                    return flashcard;
                }
            }

            // If we reach here, the courseId was not found
            System.err.println("Error: Course ID " + courseId + " not found in JSON file.");

        } catch (IOException e) {
            System.err.println("Error: Unable to read JSON file. " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error: Unable to parse JSON file. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid UUID format. " + e.getMessage());
        }

        return flashcard;
    }

}
    


