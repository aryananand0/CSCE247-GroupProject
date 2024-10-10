package model;

public class ShortAnswer implements QuestionType{
    private String question;
    private String answer;

    public ShortAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean isCorrect(String UserAnswer) {
        return UserAnswer!=null && UserAnswer.equalsIgnoreCase(answer);
    }

    @Override
    public String toString() {
        return this.getQuestion()+":";
    }

}
