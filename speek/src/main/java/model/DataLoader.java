package model;

import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    public static ArrayList<User> loadUsers() {
        ArrayList<User> user= new ArrayList<User>();
        try{
            FileReader reader = new FileReader(USER_FILE);
            JSONParser parser = new JSONParser();
            JSONArray userJSON = (JSONArray) new JSONParser().parse(reader);

            for(int i = 0; i < userJSON.size(); i++){
                JSONObject usersJSON = (JSONObject) userJSON.get(i);
                String firstName = (String) usersJSON.get(USER_FIRST_NAME);
                String lastName = (String) usersJSON.get(USER_LAST_NAME);
                String email = (String) usersJSON.get(USER_EMAIL);
                user.add(new User(firstName,lastName,email));
            }
            return user;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    

}


/*
 * public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            User user = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"firstName\":")) {
                    String firstName = extractValue(line);
                    user = new User();
                    user.setFirstName(firstName);
                } else if (line.startsWith("\"lastName\":")) {
                    String lastName = extractValue(line);
                    user.setLastName(lastName);
                } else if (line.startsWith("\"email\":")) {
                    String email = extractValue(line);
                    user.setEmail(email);
                } else if (line.startsWith("\"password\":")) {
                    String password = extractValue(line);
                    user.setPassword(password);
                } else if (line.startsWith("\"progress\":")) {
                    double progress = Double.parseDouble(extractValue(line));
                    user.setProgress(progress);
                } else if (line.startsWith("\"score\":")) {
                    double score = Double.parseDouble(extractValue(line));
                    user.setScore(score);
                } else if (line.startsWith("\"dailyReminder\":")) {
                    boolean dailyReminder = Boolean.parseBoolean(extractValue(line));
                    user.setDailyReminder(dailyReminder);
                } else if (line.startsWith("\"favoriteLanguages\":")) {
                    ArrayList<Language> favoriteLanguages = new ArrayList<>();
                    // Parsing favorite languages manually
                    while (!(line = reader.readLine().trim()).startsWith("]")) {
                        String language = line.replace("\"", "").replace(",", "").trim();
                        if (!language.isEmpty()) {
                            favoriteLanguages.add(new Language(language));
                        }
                    }
                    user.setFavoriteLanguages(favoriteLanguages);
                } else if (line.startsWith("\"currentCourses\":")) {
                    ArrayList<Course> currentCourses = new ArrayList<>();
                    // Parsing current courses manually
                    while (!(line = reader.readLine().trim()).startsWith("]")) {
                        if (line.startsWith("{")) {
                            String courseName = null;
                            double courseProgress = 0;
                            while (!(line = reader.readLine().trim()).startsWith("}")) {
                                if (line.startsWith("\"courseName\":")) {
                                    courseName = extractValue(line);
                                } else if (line.startsWith("\"progress\":")) {
                                    courseProgress = Double.parseDouble(extractValue(line));
                                }
                            }
                            currentCourses.add(new Course(courseName, "unknown"));  // Default difficulty
                        }
                    }
                    user.setCurrentCourses(currentCourses);
                } else if (line.startsWith("},")) {  // End of user object
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Method to load courses from the file
    public static ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            Course course = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"courseName\":")) {
                    String courseName = extractValue(line);
                    course = new Course(courseName, "unknown");  // Default difficulty
                } else if (line.startsWith("\"difficulty\":")) {
                    String difficulty = extractValue(line);
                    course.setDifficulty(difficulty);
                } else if (line.startsWith("\"courseCompletion\":")) {
                    double courseCompletion = Double.parseDouble(extractValue(line));
                    course.setCourseCompletion(courseCompletion);
                } else if (line.startsWith("\"lessons\":")) {
                    ArrayList<Lesson> lessons = new ArrayList<>();
                    // Parsing lessons manually
                    while (!(line = reader.readLine().trim()).startsWith("]")) {
                        if (line.startsWith("{")) {
                            String lessonTitle = null;
                            String content = null;
                            while (!(line = reader.readLine().trim()).startsWith("}")) {
                                if (line.startsWith("\"lessonTitle\":")) {
                                    lessonTitle = extractValue(line);
                                } else if (line.startsWith("\"content\":")) {
                                    content = extractValue(line);
                                }
                            }
                            lessons.add(new Lesson(lessonTitle, content, null));  // No quiz initially
                        }
                    }
                    course.setLessons(lessons);
                } else if (line.startsWith("},")) {  // End of course object
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

    // Method to load user progress for each course from file (placeholder implementation)
    public static HashMap<Course, Double> loadUserProgress(User user) {
        HashMap<Course, Double> progressMap = new HashMap<>();
        // Simplified: Assuming courses are loaded and we match progress by course name
        ArrayList<Course> courses = loadCourses();
        for (Course course : courses) {
            for (Course userCourse : user.getCurrentCourses()) {
                if (course.getCourseName().equals(userCourse.getCourseName())) {
                    progressMap.put(course, course.getCourseCompletion());
                }
            }
        }
        return progressMap;
    }
    
    public static ArrayList<Achievements> loadAchievements() {
        ArrayList<Achievements> achievements = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ACHIEVEMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{")) {
                    String title = null;
                    String description = null;
                    int rewardPoints = 0;

                    while (!(line = reader.readLine().trim()).startsWith("}")) {
                        if (line.startsWith("\"title\":")) {
                            title = extractValue(line);
                        } else if (line.startsWith("\"description\":")) {
                            description = extractValue(line);
                        } else if (line.startsWith("\"rewardPoints\":")) {
                            rewardPoints = Integer.parseInt(extractValue(line));
                        }
                    }
                    achievements.add(new Achievements(title, description, rewardPoints));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return achievements;
    }

    public static ArrayList<Language> loadLanguages() {
        ArrayList<Language> languages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(LANGUAGE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{")) {
                    String name = null;
                    ArrayList<Course> courses = new ArrayList<>();
                    ArrayList<Flashcard> flashcards = new ArrayList<>();

                    while (!(line = reader.readLine().trim()).startsWith("}")) {
                        if (line.startsWith("\"name\":")) {
                            name = extractValue(line);
                        } else if (line.startsWith("\"courses\":")) {
                            while (!(line = reader.readLine().trim()).startsWith("]")) {
                                String courseName = line.replace("\"", "").replace(",", "").trim();
                                courses.add(new Course(courseName, "unknown"));  // Default difficulty
                            }
                        } else if (line.startsWith("\"flashcards\":")) {
                            while (!(line = reader.readLine().trim()).startsWith("]")) {
                                String word = null;
                                String translation = null;
                                if (line.startsWith("{")) {
                                    while (!(line = reader.readLine().trim()).startsWith("}")) {
                                        if (line.startsWith("\"word\":")) {
                                            word = extractValue(line);
                                        } else if (line.startsWith("\"translation\":")) {
                                            translation = extractValue(line);
                                        }
                                    }
                                    flashcards.add(new Flashcard(word, translation));
                                }
                            }
                        }
                    }
                    Language language = new Language(name);
                    language.setCourses(courses);
                    language.setFlashcards(flashcards);
                    languages.add(language);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return languages;
    }


    public static ArrayList<Leaderboard> loadLeaderboard() {
        ArrayList<Leaderboard> leaderboard = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{")) {
                    String user = null;
                    double score = 0;

                    while (!(line = reader.readLine().trim()).startsWith("}")) {
                        if (line.startsWith("\"user\":")) {
                            user = extractValue(line);
                        } else if (line.startsWith("\"score\":")) {
                            score = Double.parseDouble(extractValue(line));
                        }
                    }
                    leaderboard.add(new Leaderboard(user, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }

    // Helper method to extract the value from a line like: "key": "value"
private static String extractValue(String line) {
    return line.split(":")[1].replace("\"", "").replace(",", "").trim();
}
 */

