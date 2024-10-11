package model;

import java.util.ArrayList;
import java.util.List;


public class QuestionManager {
    private List<Question> questions; // List of all questions

    /**
     * Constructor initializes the questions list.
     */
    public QuestionManager() {
        this.questions = new ArrayList<>();
    }

    /**
     * Adds a question to the manager.
     *
     * @param question The question to add.
     */
    public void addQuestion(Question question) {
        if (question != null) {
            questions.add(question);
        } else {
            throw new IllegalArgumentException("Question cannot be null.");
        }
    }

    /**
     * Presents a question to the user and validates the answer.
     *
     * @param questionIndex The index of the question to present.
     * @param userAnswer    The user's answer.
     * @return True if the answer is correct, false otherwise.
     */
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

    /**
     * Retrieves the total number of questions.
     *
     * @return The number of questions.
     */
    public int getTotalQuestions() {
        return questions.size();
    }

    /**
     * Retrieves a question by its index.
     *
     * @param questionIndex The index of the question.
     * @return The question at the specified index.
     */
    public Question getQuestion(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= questions.size()) {
            throw new IndexOutOfBoundsException("Invalid question index.");
        }
        return questions.get(questionIndex);
    }

    /**
     * Displays all questions managed by the QuestionManager.
     */
    public void displayAllQuestions() {
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(questions.get(i).display());
            System.out.println("-------------------------");
        }
    }
}


