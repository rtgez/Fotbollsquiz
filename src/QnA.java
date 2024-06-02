/**
 * The QnA class represents a question and its possible answers in the quiz game.
 * It includes the question text, possible answers, and the correct answer.
 *
 */
public class QnA {
    private String question;
    private String[] answers;
    private String correctAnswer;
    /**
     * Constructs a QnA object with the specified question, possible answers, and correct answer.
     *
     * @param question      The question text.
     * @param answers       An array of possible answers.
     * @param correctAnswer The correct answer.
     *   @author mergim, lana
     */
    public QnA(String question, String[] answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
    /**
     * Checks if the provided answer is correct.
     *
     * @param answer The answer to check.
     * @return true if the answer is correct, false otherwise.
     *  @author lana, mergim
     */
    public boolean check(String answer) {
        return answer.equals(correctAnswer);
    }
    /**
     * Gets the question text.
     *
     * @return The question text.
     *  @author lana
     */
    public String getQuestion() {
        return question;
    }
    /**
     * Gets the possible answers.
     *
     * @return An array of possible answers.
     *  @author lana
     */
    public String[] getAnswers() {
        return answers;
    }
}
