package model;

import java.io.FileReader;
import java.io.IOException;
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

        try {
            FileReader reader = new FileReader(USER_FILE);
            JSONArray userJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < userJSON.size(); i++) {
                JSONObject usersJSON = (JSONObject) userJSON.get(i);
                String firstName = (String) usersJSON.get(USER_FIRST_NAME);
                String lastName = (String) usersJSON.get(USER_LAST_NAME);
                String email = (String) usersJSON.get(USER_EMAIL);
                UUID uuid = UUID.fromString((String) usersJSON.get(USER_UUID));
                String userName = (String) usersJSON.get(USER_USER_NAME);
                String password = (String) usersJSON.get(USER_PASSWORD);
                // Create a new User object
                User user = new User(uuid, userName, firstName, lastName);
                user.setPassword(password);
                user.setEmail(email);
                // Load achievements
                JSONArray achievementsJSON = (JSONArray) usersJSON.get("achievements");
                ArrayList<Achievements> achievements = new ArrayList<>();
                for (int j = 0; j < achievementsJSON.size(); j++) {
                    JSONObject achievementJSON = (JSONObject) achievementsJSON.get(j);
                    String title = (String) achievementJSON.get("title");
                    String description = (String) achievementJSON.get("description");
                    int rewardPoints = ((Long) achievementJSON.get("rewardPoints")).intValue();

                    // Add achievement to the list
                    achievements.add(new Achievements(title, description, rewardPoints));
                }
                // Add achievements to the user
                user.setAchievements(achievements);

                  // Load currentCourses
            JSONArray currentCoursesJSON = (JSONArray) usersJSON.get("currentCourses");
            ArrayList<Course> currentCourses = new ArrayList<>();
            for (Object courseObj : currentCoursesJSON) {
                JSONObject courseJSON = (JSONObject) courseObj;
                UUID courseId = UUID.fromString((String) courseJSON.get("courseId"));
                String courseName = (String) courseJSON.get("courseName");
                String currentLessonId = (String) courseJSON.get("currentLessonId");
                String currentLessonName = (String) courseJSON.get("currentLessonName");
                String lessonProgress = (String) courseJSON.get("lessonProgress");
                String courseProgress = (String) courseJSON.get("courseProgress");

                // Create a new Course object and set the current lesson and progress
                Course course = new Course(courseId, courseName);
                course.setCourseCompletion(Double.parseDouble(courseProgress));

                // Create a new Lesson object for the current lesson
                Lesson currentLesson = new Lesson(UUID.fromString(currentLessonId), currentLessonName);
                currentLesson.setContent(""); // Assuming content is not part of the provided JSON

                // Add lesson to the course
                course.addLesson(currentLesson);

                // Add course to currentCourses list
                currentCourses.add(course);
            }
            user.setCurrentCourses(currentCourses);

            // Load questionHistory
            JSONArray questionHistoryJSON = (JSONArray) usersJSON.get("questionHistory");
            ArrayList<QuestionHistory> questionHistory = new ArrayList<>();
            for (Object questionObj : questionHistoryJSON) {
                JSONObject questionJSON = (JSONObject) questionObj;
                String questionId = (String) questionJSON.get("questionId");
                String questionText = (String) questionJSON.get("questionText");
                String userAnswer = (String) questionJSON.get("userAnswer");
                String correctAnswer = (String) questionJSON.get("correctAnswer");
                boolean isCorrect = (Boolean) questionJSON.get("isCorrect");

                QuestionHistory history = new QuestionHistory(questionId, questionText, userAnswer, correctAnswer, isCorrect);
                questionHistory.add(history);
            }
            user.setQuestionHistory(questionHistory);

                // Add the user to the users list
                users.add(user);
            }
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                    Flashcard flashcard = new Flashcard(word, translation);
                    language.getFlashcards().add(flashcard);
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
            FileReader reader = new FileReader(USER_FILE);
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

    public static User getUser(String usernameOrEmail, String password) {
        ArrayList<User> users = loadUsers();
        for (User user : users) {
            if((user.getUserName().equals(usernameOrEmail) || user.getEmail().equalsIgnoreCase(usernameOrEmail)) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;  // Return null if user not found
    }
    

    

    public static ArrayList<Course> loadCoursesFromJson() {
        ArrayList<Course> coursesList = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("json/Lesson.json")) {
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
            System.out.println(" IO Error: " + e.getMessage());
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
                Lesson lesson = parseLesson((JSONObject) lessonObj);
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

    private static Lesson parseLesson(JSONObject lessonJson) {
        try {
            UUID lessonId = UUID.fromString((String) lessonJson.get("lessonId"));
            String lessonName = (String) lessonJson.get("lessonName");

            // Process content for each lesson
            String content = parseContent((JSONObject) lessonJson.get("content"));

            Lesson lesson = new Lesson(lessonId, lessonName, content, new ArrayList<>());

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

    private static String parseContent(JSONObject contentJson) {
        StringBuilder contentBuilder = new StringBuilder();
        for (Object keyObj : contentJson.keySet()) {
            String key = (String) keyObj;
            Object value = contentJson.get(key);
            contentBuilder.append(capitalizeFirstLetter(key)).append(": ");

            if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                List<String> items = new ArrayList<>();
                for (Object item : array) {
                    items.add((String) item);
                }
                contentBuilder.append(String.join(", ", items)).append("\n");
            } else {
                contentBuilder.append((String) value).append("\n");
            }
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
    
        try (FileReader reader = new FileReader("json/Lesson.json")) {
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

}
    


