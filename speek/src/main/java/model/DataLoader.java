package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataLoader {

    private static final String USER_FILE = "json/User.json";  // Path to users JSON-like file
    private static final String COURSE_FILE = "json/Course.json";  // Path to courses JSON-like file
    private static final String ACHIEVEMENT_FILE = "json/Achievement.json";
    private static final String LANGUAGE_FILE = "json/Language.json";
    private static final String LEADERBOARD_FILE = "json/Leaderboard.json";

    // Method to load users from the file
    public static ArrayList<User> loadUsers() {
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
                            favoriteLanguages.add(new Language());
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

    // Helper method to extract the value from a line like: "key": "value"
    private static String extractValue(String line) {
        return line.split(":")[1].replace("\"", "").replace(",", "").trim();
    }    
}



