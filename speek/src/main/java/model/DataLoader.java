package model;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataLoader {

    
    private static final String COURSE_FILE = "courses.json";

    
    public DataLoader() {
        
    }

    
    public static CourseList loadCourses() {
        
        return new CourseList();
    }

    
    private static Course parseCourse(JSONObject courseJSON) {
        
        return new Course();
    }
}

