import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

public class Controller {
    private Player player;
    private QnA[] questions;
    private View view;
    private QnA currentQuestion;
    private Map<String, String> categoryToFilePath;

    public Controller(Player player, QnA[] questions, View view) {
        this.player = player;
        this.view = view;
        this.categoryToFilePath = new HashMap<>();
        categoryToFilePath.put("Landslag", "src/landslag_questions.txt");
        categoryToFilePath.put("Champions League", "src/champions_leauge_questions.txt");
        categoryToFilePath.put("Allsvenskan", "src/questions.txt");
    }
    private void loadQuestionsFromFile(String filePath) throws IOException{
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath());
            return;
        }

        List<QnA> questionList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("Reading file: " + filePath);
            while ((line = reader.readLine()) != null) {
                try {
                    System.out.println("Line read: '" + line + "'");
                    String[] parts = line.split(";");
                    if (parts.length != 2) {
                        System.out.println("Incorrect format (expected question;answer1,answer2,...): '" + line + "'");
                        continue;
                    }
                    String question = parts[0].trim();
                    String[] answers = parts[1].split(",");
                    if (answers.length < 2) {
                        System.out.println("Not enough answers in line: '" + line + "'");
                        continue;
                    }
                    questionList.add(new QnA(question, answers));
                    System.out.println("Loaded question: '" + question + "' with answers: " + Arrays.toString(answers));
                } catch (Exception e) {
                    System.out.println("Error processing line: '" + line + "'");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (questionList.isEmpty()) {
            System.out.println("No questions were loaded.");
        } else {
            System.out.println("Total questions loaded: " + questionList.size());
        }
        questions = questionList.toArray(new QnA[0]);
    }


    public void startGame(String currentCategory) {
        String filePath = categoryToFilePath.get(currentCategory);
        if (filePath == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "Category does not exist.");
            return;
        }
        try {
            loadQuestionsFromFile(filePath);
            // ...
            nextQuestion();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view.getFrame(), "Failed to load questions for the category: " + currentCategory);
        }
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