package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class RocketGame extends DataConstants {
    private ArrayList<String> words;
    private HashMap<String,String> answers;
    private int score;

    public RocketGame(){
        this.words = new ArrayList<>();
        this.answers = new HashMap<>();
        score=0;
    }

    public RocketGame(ArrayList<String> words,HashMap<String,String> answers){
        this.words = words;
        this.answers = answers;
        score=0;

    }

    public void loadGameList() {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(GAME_FILE));

            JSONObject jsonObject = (JSONObject) obj;

            for (String difficulty : new String[]{GAME_EASY, GAME_MED, GAME_HARD}) {
                JSONArray levelArray = (JSONArray) jsonObject.get(difficulty);

                for (Object element : levelArray) {
                    JSONObject qaPair = (JSONObject) element;

                    String question = (String) qaPair.get(GAME_QUESTION);
                    String answer = (String) qaPair.get(GAME_ANSWER);

                    words.add(question);

                    answers.put(question, answer);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getWords() {
        return this.words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public HashMap<String,String> getAnswers() {
        return this.answers;
    }

    public void setAnswers(HashMap<String,String> answers) {
        this.answers = answers;
    }

    public int getScore(){
        return this.score;
    }

    public void increaseScore(){
        this.score++;
    }
}
