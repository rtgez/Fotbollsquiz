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

    public View() {
        frame = new JFrame("Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));

        answersPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2 grid with padding
        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(answersPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null); // Center the window
    }

    public void displayQuestion(String question) {
        questionLabel.setText(question);
    }
    public void initialize() {
        frame.setVisible(true);
    }

    public void displayAnswers(List<String> answers, ActionListener answerListener) {
        answersPanel.removeAll(); // Clear previous answers
        for (String answer : answers) {
            JButton answerButton = new JButton(answer);
            answerButton.setFont(new Font("Arial", Font.BOLD, 20));
            answerButton.addActionListener(answerListener);
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

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
