package model;

public class ShortAnswerQuestion extends Question {
    private String correctAnswer; 

    
    public ShortAnswerQuestion(String text, String correctAnswer) {
        super(text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String display() {
        return getText();
    }

    @Override
    public boolean validateAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        return userAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }
    @Override
    public String getType() {
        return "ShortAnswer";
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}



