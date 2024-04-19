public class QnA {
    private String question;
    private String[] answers;
    private String correctAnswer;
    public QnA(String question, String[] answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public boolean check(String answer) {
        return answer.equals(correctAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }
}
