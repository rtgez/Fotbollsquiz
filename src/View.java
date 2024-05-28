/*
    public void printNameRequest() {
        System.out.print("Your name: ");
    }

    public void printInputRequest() {
        System.out.print("Please chose a letter: ");
    }

    public void printSuccessMessage() {
        System.out.println("That was right!\n");
    }

    public void printGameOverMessage() {
        System.out.println("This was wrong. Game over.");
    }
}*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class View {
    private JFrame frame;
    private final JLabel questionLabel;
    private final JPanel answersPanel;
    private final JLabel timerLabel;
    private Timer timer;
    private int timeLeft;
    private Controller controller;

    public View() {
        frame = new JFrame("Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));

        answersPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(answersPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        timerLabel = new JLabel("Time left: ", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(timerLabel, BorderLayout.SOUTH);
    }
    public void setController(Controller controller) {
        this.controller = controller;
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
        Color[] colors = new Color[] {
                new Color(255, 153, 153),
                new Color(153, 204, 255),
                new Color(153, 255, 153),
                new Color(255, 255, 153)
        };
        for (int i=0; i<answers.size();i++) {
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

  /* public void setFrame(JFrame frame) {
        this.frame = frame;
    }*/
}
