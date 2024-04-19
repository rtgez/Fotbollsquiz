import java.util.Arrays;
import java.util.List;

public class QnA {
    private String correctAnswer;
    private String question;
    private String[] answers;
    private char questName;


    public QnA(String question, String[] answers) {
        this.question = question;
        this.answers = answers;
        //this.correctAnswer = correctAnswer;
        // this.questName = questName;

    }
    public QnA(QnA[] questionList, String correctAnswer) {

        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }
    public String[] getAnswers() {
        return answers;
    }
    public String getRandomQuestion() {


        return question;
    }
    public boolean check(String selectedAnswer) {
        // Implement your answer checking logic here
        return Arrays.asList(answers).contains(selectedAnswer);
    }
}
