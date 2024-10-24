package model;

import java.util.ArrayList;
import java.util.List;


public class QuestionManager {
    private List<Question> questions; // List of all questions

    
    public QuestionManager() {
        this.questions = new ArrayList<>();
    }

    
    public void addQuestion(Question question) {
        if (question != null) {
            questions.add(question);
        } else {
            throw new IllegalArgumentException("Question cannot be null.");
        }
    }

    
    public boolean presentQuestion(int questionIndex, String userAnswer) {
        if (questionIndex < 0 || questionIndex >= questions.size()) {
            throw new IndexOutOfBoundsException("Invalid question index.");
        }
        Question question = questions.get(questionIndex);
        System.out.println(question.display());
        boolean isCorrect = question.validateAnswer(userAnswer);
        System.out.println("Your answer is " + (isCorrect ? "correct." : "incorrect.") + "\n");
        return isCorrect;
    }

    public String getAnswerText(Question question, String userAnswer) {
        if (question == null) {
            return "No Answer";
        } else {
            boolean isCorrect = question.validateAnswer(userAnswer);
            return ("Your answer is " + (isCorrect ? "correct." : "incorrect.") + "\n");
        }
    }

    
    public int getTotalQuestions() {
        return questions.size();
    }

    
    public Question getQuestion(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= questions.size()) {
            throw new IndexOutOfBoundsException("Invalid question index.");
        }
        return questions.get(questionIndex);
    }

    
    public void displayAllQuestions() {
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(questions.get(i).display());
            System.out.println("-------------------------");
        }
    }
}


