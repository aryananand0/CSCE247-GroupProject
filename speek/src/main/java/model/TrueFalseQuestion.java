package model;


public class TrueFalseQuestion extends Question {
    private boolean correctAnswer; 

    
    public TrueFalseQuestion(String text, boolean correctAnswer) {
        super(text);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String display() {
        return getText() + " (True/False)";
    }

    @Override
    public boolean validateAnswer(String userAnswer) {
        if (userAnswer == null) return false;
        String normalized = userAnswer.trim().toLowerCase();
        if (normalized.equals("true") || normalized.equals("t")) {
            return correctAnswer;
        } else if (normalized.equals("false") || normalized.equals("f")) {
            return !correctAnswer;
        }
        return false;
    }

    @Override
    public String getType() {
        return "TrueFalse";
    }

    @Override
    public String getCorrectAnswer() {
        return String.valueOf(correctAnswer);
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

