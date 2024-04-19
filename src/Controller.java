import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Controller {
    private Player player;
    private QnA[] questions;
    private View view;
    private QnA currentQuestion;

    public Controller(Player player, QnA[] questions, View view) {
        this.player = player;
        this.questions = questions;
        this.view = view;
        try {
            loadQuestionsFromFile("C:\\Users\\lanam\\Documents\\questions.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private void loadQuestionsFromFile(String filePath) throws IOException {
        List<QnA> questionList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            String question = parts[0];
            String[] answers = parts[1].split(",");
            questionList.add(new QnA(question, answers));
        }
        reader.close();
        questions = questionList.toArray(new QnA[0]);
    }
    public void startGame(String currentCategory) {
        view.show();
        nextQuestion();
    }

    private void nextQuestion() {
        if (questions == null || questions.length == 0) {
            // Handle the case where there are no questions available
            JOptionPane.showMessageDialog(view.getFrame(), "No questions available.");
            System.exit(1); // or any other appropriate action
            return;
        }
        int index = new Random().nextInt(questions.length);
        QnA question = questions[index];
        view.displayQuestion(question.getQuestion());
        view.displayAnswers(Arrays.asList(question.getAnswers()), e -> checkAnswer(question, e));
    }


    private void checkAnswer(QnA currentQuestion, ActionEvent e) {
        String selectedAnswer = ((JButton) e.getSource()).getText();
        if (currentQuestion.check(selectedAnswer)) {
            player.scorePoint();
            JOptionPane.showMessageDialog(view.getFrame(), "Correct! Score: " + player.getScore());
            nextQuestion(); // Only call nextQuestion if the answer was correct.
        } else {
            JOptionPane.showMessageDialog(view.getFrame(), "Wrong answer. Game over! Score: " + player.getScore());
            System.exit(0); // or reset the game
        }
        nextQuestion(); // Move to the next question or end the game
    }

    public void startSpel(String currentCategory) {
        startGame(currentCategory);
    }

/*
    public String getString() {
        return input.next();
    }

    public String getGuessOfPlayer() {
        String guess = input.next();
        if(!guess.equalsIgnoreCase("A") && !guess.equalsIgnoreCase("B")
                && !guess.equalsIgnoreCase("C") && !guess.equalsIgnoreCase("D")) {
            throw new IllegalArgumentException("Enter A, B, C or D");
        }
        return guess;
    }

    public void mainLoop() {
        view.printNameRequest();
        player.setName(getString());

        while(true) {
            String actualQuestion = question.getRandomQuestion();

            // Check if actualQuestion is null to avoid NullPointerException
            if (actualQuestion == null) {
                System.out.println("Error: Question is null.");
                break;
            }

            view.printQuestion(actualQuestion, question);
            view.printInputRequest();
            String input = getGuessOfPlayer();
            if(question.check(input)) {
                player.scorePoint();
                view.printSuccessMessage();
            } else {
                view.printGameOverMessage();
                view.printScoreOfPlayer(player);
                break;
            }
        }
    }
*/
}