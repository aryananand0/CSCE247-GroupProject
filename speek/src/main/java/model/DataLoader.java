package model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

                // Create a new User object
                User user = new User(firstName, lastName, email);

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
    
                users.add(new User(firstName, lastName, points));
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
}


