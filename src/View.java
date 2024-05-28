import javax.print.attribute.standard.PresentationDirection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;

public class View {
    private JFrame frame;
    private JButton avslutaSpel;
    private final JLabel questionLabel;
    private final JPanel answersPanel;
    private final JLabel timerLabel;
    private Timer timer;
    private int timeLeft;
    private mainguiii mainguiii;
    private Controller controller;

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


    public void avslutaSpel() {
        frame.setVisible(false);
        stopTimer();
       // mainguiii.setVisible(true);

       // System.out.println("hallå");

        if (controller != null) {
            Player player = controller.getPlayer();
            int score = controller.getPlayerScore();
            String name = JOptionPane.showInputDialog(null, "Enter your name for the high score:");
            if (name != null && !name.trim().isEmpty()) {
                saveHighScore(name, score);
                showHighScores();
                mainguiii.setVisible(true);
            }
        }

    }

    public void setMainguiii(mainguiii mainguiii) {
        this.mainguiii = mainguiii;
    }

    private void configureButton(JButton button) {
        button.setBackground(Color.decode("#93ceaf"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }




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

    private void showHighScores() {
        List<HighScoreEntry> highScores = readHighScores();
        StringBuilder message = new StringBuilder("High Scores:\n");
        for (HighScoreEntry entry : highScores) {
            message.append(entry).append("\n");
        }
        JOptionPane.showMessageDialog(frame, message.toString());
    }

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

    private void updateTimerLabel() {
        timerLabel.setText("Time left: " + timeLeft + "s");
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void displayQuestion(String question) {
        questionLabel.setText(question);
    }

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

    public void show() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
