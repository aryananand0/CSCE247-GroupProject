package model;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    public static ArrayList<User> loadUsers() {
        ArrayList<User> user= new ArrayList<User>();
        try{
            FileReader reader = new FileReader(USER_FILE);
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

    public static ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            FileReader reader = new FileReader(COURSE_FILE);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray courseJSONArray = (JSONArray) jsonObject.get(COURSE);

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
        ArrayList<Language> languages = new ArrayList<Language>();

        try {
            FileReader reader = new FileReader(LANGUAGE_FILE);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray languageJsonArray = (JSONArray) jsonObject.get(LANGUAGES);
            for (Object languageObject : languageJsonArray) {
                JSONObject languageJsonObject = (JSONObject) languageObject;
                String LanguageName =  (String) languageJsonObject.get(LANGUAGE_NAME);
                languages.add(new Language(LanguageName));   
            }
            return languages;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Leaderboard loadLeaderboard() {
        ArrayList<User> user= new ArrayList<User>();
        try {
            FileReader reader = new FileReader(LEADERBOARD_FILE);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray leaderboardJsonArray = (JSONArray) jsonObject.get(LEADERBOARD);

            for (Object leaderboardObject : leaderboardJsonArray) {
                JSONObject leaderboardJsonObject = (JSONObject) leaderboardObject;
                JSONArray userJsonArray = (JSONArray) leaderboardJsonObject.get(LEADERBOARD_USER);
                for (Object userObject : userJsonArray) {
                    JSONObject userJsonObject = (JSONObject) userObject;
                    String firstName = (String) userJsonObject.get(LEADERBOARD_USER_FIRST_NAME);
                    String lastName = (String) userJsonObject.get(LEADERBOARD_USER_LAST_NAME);
                    double points = (Double) userJsonObject.get(LEADERBOARD_USER_SCORE);
                    user.add(new User(firstName,lastName,points));
                }
            }
            Collections.sort(user, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return Double.compare(u2.getScore(), u1.getScore());  // Sort by points, descending
            }
        });
        Leaderboard leader = new Leaderboard(user);
        return leader;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    
    

}


/*
 * 

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

 */

