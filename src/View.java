import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

/**
 * The View class represents the user interface for the quiz game.
 * It includes methods for displaying questions, answers, and handling timers.
 */
public class View {
    private JFrame frame;
    private JButton avslutaSpel;
    private final JLabel questionLabel;
    private final JPanel answersPanel;
    private final JLabel timerLabel;
    private Timer timer;
    private int timeLeft;
    private mainGui mainGui;
    private Controller controller;
    /**
     * Constructs the View and sets up the UI components.
     *  @author lana
     */
    public View() {
        frame = new JFrame("Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(questionLabel, BorderLayout.CENTER);

        avslutaSpel = new JButton("Avsluta spel");
        configureButton(avslutaSpel);
        topPanel.add(avslutaSpel, BorderLayout.EAST);
      //  avslutaSpel.addActionListener(e -> resetToMainMenu1());

        frame.add(topPanel, BorderLayout.NORTH);

        answersPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        frame.add(answersPanel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time left: ", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(timerLabel, BorderLayout.SOUTH);
   //     avslutaSpel.addActionListener(e -> resetToMainMenu1());



        avslutaSpel.addActionListener(e -> avslutaSpel());
    }

    /**
     * Handles the action for ending the game.
     *  @author lana
     */
    public void avslutaSpel() {
        frame.setVisible(false); // Gömmer spelet
        stopTimer(); // Stoppar timern

        if (controller != null) {
            Player player = controller.getPlayer();
            int score = controller.getPlayerScore();
            String name = JOptionPane.showInputDialog(null, "Enter your name for the high score:");
            if (name != null && !name.trim().isEmpty()) {
                saveHighScore(name, score); // Spara poängen
                showHighScores(); // Visa highscore-listan
            }
        }

        mainGui.setVisible(true); // Visa huvudmenyn
    }
    /**
     * Sets the main menu interface for the view.
     *
     * @param mainGui The main menu interface.
     *                   @author lana
     */

    public void setMainguiii(mainGui mainGui) {
        this.mainGui = mainGui;
    }
    /**
     * Configures the appearance of a button.
     *
     * @param button The button to configure.
     *                @author lana
     */
    private void configureButton(JButton button) {
        button.setBackground(Color.decode("#93ceaf"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
    /**
     * Sets the controller for the view.
     *
     * @param controller The controller to set.
     *                    @author robin, lorik
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Saves a high score entry to the high score file.
     *
     * @param name  The name of the player.
     * @param score The score of the player.
     *               @author lana
     */


    private void saveHighScore(String name, int score) {
        List<HighScoreEntry> highScores = readHighScores();
        highScores.add(new HighScoreEntry(name, score));
        Collections.sort(highScores);

        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("highscores.txt")))) {
            for (HighScoreEntry entry : highScores) {
                writer.println(entry.getName() + ";" + entry.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Reads the high scores from the high score file.
     *
     * @return A list of high score entries.
     *  @author lana
     */
    private List<HighScoreEntry> readHighScores() {
        List<HighScoreEntry> highScores = new ArrayList<>();
        File file = new File("highscores.txt");
        if (!file.exists()) {
            return highScores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    highScores.add(new HighScoreEntry(name, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(highScores);
        return highScores;
    }
    /**
     * Displays the high scores in a dialog.
     *  @author lana
     */
    private void showHighScores() {
        List<HighScoreEntry> highScores = readHighScores();
        StringBuilder message = new StringBuilder("High Scores:\n");
        for (HighScoreEntry entry : highScores) {
            message.append(entry).append("\n");
        }
        JOptionPane.showMessageDialog(frame, message.toString());
    }
    /**
     * Starts the timer for the current question.
     *
     * @param timeInSeconds The time in seconds for the timer.
     *                       @author soma, lana
     */
    public void startTimer(int timeInSeconds) {
        timeLeft = timeInSeconds;
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(1000, e -> {
            timeLeft--;
            updateTimerLabel();
            if (timeLeft <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Time's up! Next question...");
                controller.nextQuestion();
            }
        });
        timer.start();
    }
    /**
     * Updates the timer label to show the remaining time.
     *  @author lana
     */
    private void updateTimerLabel() {
        timerLabel.setText("Time left: " + timeLeft + "s");
    }
    /**
     * Stops the timer.
     *  @author lana, soma
     */
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    /**
     * Displays the given question.
     *
     * @param question The question to display.
     *                  @author lana
     */
    public void displayQuestion(String question) {
        questionLabel.setText(question);
    }
    /**
     * Displays the given answers and sets the action listener for each answer button.
     *
     * @param answers        A list of answers to display.
     * @param answerListener The action listener for the answer buttons.
     *     @author lana
     */
    public void displayAnswers(List<String> answers, ActionListener answerListener) {
        answersPanel.removeAll();
        Color[] colors = new Color[]{
                new Color(255, 153, 153),
                new Color(153, 204, 255),
                new Color(153, 255, 153),
                new Color(255, 255, 153)
        };
        for (int i = 0; i < answers.size(); i++) {
            JButton answerButton = new JButton(answers.get(i));
            answerButton.setFont(new Font("Arial", Font.BOLD, 20));
            answerButton.addActionListener(answerListener);
            if (i < colors.length) {
                answerButton.setBackground(colors[i]);
            } else {
                answerButton.setBackground(Color.LIGHT_GRAY); // Default color
            }

            answerButton.setForeground(Color.BLACK);
            answerButton.setOpaque(true);
            answerButton.setBorderPainted(false);

            answersPanel.add(answerButton);
        }
        answersPanel.revalidate();
        answersPanel.repaint();
    }
    /**
     * Shows the frame (the game interface).
     *  @author lana
     */
    public void show() {
        frame.setVisible(true);
    }
    /**
     * Gets the frame (the main window).
     *
     * @return The frame.
     *  @author lana
     */
    public JFrame getFrame() {
        return frame;
    }
}
