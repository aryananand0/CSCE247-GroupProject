package model;

public class TrueFalse implements QuestionType {
    private String question;
    private String answer;

    public TrueFalse(String question, String answer) {
        //Needs to be implemented
    }
    @Override
    public String getAnswer() {
        //Needs to be implemented
        throw new UnsupportedOperationException("Unimplemented method 'getAnswer'");
    }

    @Override
    public String getQuestion() {
        //Needs to be implemented
        throw new UnsupportedOperationException("Unimplemented method 'getQuestion'");
    }

    @Override
    public boolean isCorrect() {
        //Needs to be implemented
        throw new UnsupportedOperationException("Unimplemented method 'isCorrect'");
    }

    @Override
    public String toString() {
        //Needs to be implemented
        return super.toString();
    }

    
}