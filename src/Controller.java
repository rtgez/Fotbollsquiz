import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
/**
 * The Controller class manages the flow of the game, including loading questions,
 * handling player actions, and updating the view.
 */
public class Controller {
    private Player player;
    private QnA[] questions;
    private View view;
    private QnA currentQuestion;
    private Map<String, String> categoryToFilePath;
    private int timeSeconds;
    private int questionCounter=0;

    /**
     * Constructs a Controller with the specified player, questions, and view.
     *
     * @param player    The player of the game.
     * @param questions The array of questions.
     * @param view      The view for displaying game information.
     * @author lana
     */
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
    /**
     * Loads questions from a specified file path.
     *
     * @param filePath The file path from which to load questions.
     * @throws IOException If an I/O error occurs.
     *  @author lana
     */
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
    /**
     * Starts the game with the specified category and difficulty level.
     *
     * @param currentCategory The current category selected by the player.
     * @param difficulty      The difficulty level selected by the player.
     *  @author lana
     */


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
    /**
     * Applies the specified difficulty level by setting the timer accordingly.
     *
     * @param difficulty The difficulty level.
     * @return The number of seconds for the timer based on the difficulty level.
     *  @author lana, soma
     */
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
    /**
     * Displays the next question to the player.
     *  @author lana
     */
    public void nextQuestion() {
        if (questionCounter == 15) { // Kontrollera om 15 frågor har visats
            view.stopTimer();
            JOptionPane.showMessageDialog(view.getFrame(), "You have completed the quiz!");
            view.avslutaSpel(); // Anropa avslutaSpel() för att hantera avslutningen av spelet
            return;
        }

        int index = new Random().nextInt(questions.length);
        QnA question = questions[index];
        view.displayQuestion(question.getQuestion());
        view.displayAnswers(Arrays.asList(question.getAnswers()), e -> checkAnswer(question, e));

        view.startTimer(timeSeconds);
        questionCounter++; // Öka räknaren för antal visade frågor
    }
    /**
     * Checks the player's answer and updates the score if the answer is correct.
     *
     * @param currentQuestion The current question being answered.
     * @param e               The action event triggered by the player's answer selection.
    *  @author lana
     */

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
    /**
     * Gets the player.
     *
     * @return The player.
     *  @author lana
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * Gets the player's score.
     *
     * @return The player's score.
     *  @author lana
     */
    public int getPlayerScore() {
        return player != null ? player.getScore() : 0;
    }

}