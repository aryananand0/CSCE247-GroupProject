package model;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchWords implements QuestionType {
    private ArrayList<String> questionOption;
    private ArrayList<String> questionAns;
    private HashMap<String, String> answer;

    public MatchWords(ArrayList<String> questionOption, ArrayList<String> questionAns, HashMap<String, String> answer) {
        // Needs to be implemented
    }

    @Override
    public String getAnswer() {
        // Needs to be implemented
        throw new UnsupportedOperationException("Unimplemented method 'getAnswer'");
    }

    @Override
    public String getQuestion() {
        // Needs to be implemented
        throw new UnsupportedOperationException("Unimplemented method 'getQuestion'");
    }

    @Override
    public boolean isCorrect() {
        // Needs to be implemented
        throw new UnsupportedOperationException("Unimplemented method 'isCorrect'");
    }

    @Override
    public String toString() {
        // Needs to be implemented
        return super.toString();
    }
    
}
