package model;

public class TrueFalse implements QuestionType {
    private String question;
    private String answer;

    public TrueFalse(String question, String answer) {
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
        //Needs to be implemented
        return this.getQuestion()+" True or False:";
    }

    
}
