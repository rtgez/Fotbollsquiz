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
    private int timeSeconds;

    public Controller(Player player, QnA[] questions, View view) {
        this.player = player;
        this.view = view;
        this.questions=questions;
        this.view.setController(this);
        this.categoryToFilePath = new HashMap<>();
        categoryToFilePath.put("Landslag", "src/landslag_questions.txt");
        categoryToFilePath.put("Champions League", "src/champions_leauge_questions.txt");
        categoryToFilePath.put("Allsvenskan", "src/questions.txt");
    }
    private void loadQuestionsFromFile(String filePath) throws IOException {
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
                System.out.println("Line read: '" + line + "'");
                String[] parts = line.split(";");
                if (parts.length < 2) {
                    System.out.println("Incorrect format (expected at least question;answers): '" + line + "'");
                    continue;
                }
                String question = parts[0].trim();
                String[] answers = parts[1].split(",");
                if (answers.length < 2) {
                    System.out.println("Not enough answers in line: '" + line + "'");
                    continue;
                }
                String correctAnswer = parts.length > 2 ? parts[2].trim() : null;
                questionList.add(new QnA(question, answers, correctAnswer));
                System.out.println("Loaded question: '" + question + "' with answers: " + Arrays.toString(answers) + (correctAnswer != null ? " and correct answer: " + correctAnswer : ""));
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



    public void startGame(String currentCategory, String difficulty) {
        String filePath = categoryToFilePath.get(currentCategory);
        if (filePath == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "Category does not exist.");
            return;
        }
        try {
            loadQuestionsFromFile(filePath);
            applyDifficulty(difficulty);
            nextQuestion();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view.getFrame(), "Failed to load questions for the category: " + currentCategory);
        }
        view.show();
        nextQuestion();
    }
    private int applyDifficulty(String difficulty) {
        if ("Easy".equals(difficulty)) {
            timeSeconds=20;
            view.startTimer(20);
        } else if ("Medium".equals(difficulty)) {
            timeSeconds=10;
            view.startTimer(10);
        } else if ("Hard".equals(difficulty)) {
            timeSeconds=5;
            view.startTimer(5);
        }

        return timeSeconds;
    }

    public void nextQuestion() {
        if (questions == null || questions.length == 0) {
            view.stopTimer();
            JOptionPane.showMessageDialog(view.getFrame(), "No questions available.");
            System.exit(1);
            return;
        }

        int index = new Random().nextInt(questions.length);
        QnA question = questions[index];
        view.displayQuestion(question.getQuestion());
        view.displayAnswers(Arrays.asList(question.getAnswers()), e -> checkAnswer(question, e));

        view.startTimer(timeSeconds);
    }


    private void checkAnswer(QnA currentQuestion, ActionEvent e) {
        String selectedAnswer = ((JButton) e.getSource()).getText();
        if (currentQuestion.check(selectedAnswer)) {
            view.stopTimer();
            player.scorePoint();
            JOptionPane.showMessageDialog(view.getFrame(), "Correct! Score: " + player.getScore());
            nextQuestion();
        } else {
            JOptionPane.showMessageDialog(view.getFrame(), "Wrong answer. Score: " + player.getScore());
        }
        nextQuestion();
    }
    public Player getPlayer() {
        return player;
    }

    public int getPlayerScore() {
        return player != null ? player.getScore() : 0;
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