import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * The mainGui class represents the main GUI dialog for the quiz game.
 * It allows the user to select a category and difficulty level, and start the game.
 */
public class mainGui extends JDialog implements ActionListener {
    private JButton allsvenskanButton, landslagButton, championsLeagueButton, avslutaButton, startGameButton;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private Controller controller;
    private GridBagConstraints gbc;
    private String currentCategory = "";
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private String currentDifficulty = "";
    private static final String HIGH_SCORE_FILE = "highscores.txt";
    private JButton backButton;
    private JFrame frame;
    /**
     * Constructs the main GUI with the specified controller.
     *
     * @param controller The controller for the game.
     *  @author lana
     *
     */
    public mainGui(Controller controller) {
        this.controller = controller;
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#171954"));
        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        titleLabel = new JLabel("MENY", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        mainPanel.add(titleLabel, gbc);

        landslagButton = new JButton("Landslag");
        championsLeagueButton = new JButton("Champions League");
        allsvenskanButton = new JButton("Allsvenskan");
        avslutaButton = new JButton("Avsluta");
        startGameButton = new JButton("Start Game");

        addButton(landslagButton);
        addButton(championsLeagueButton);
        addButton(allsvenskanButton);
        addButton(avslutaButton);

        avslutaButton.addActionListener(e -> System.exit(0));

        setContentPane(mainPanel);
        setTitle("Fotbollsquiz");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setModal(true);
        // setVisible(true);
    }
    /**
     * Adds difficulty selection buttons to the GUI.
     *  @author lana
     */
    private void addDifficultyButtons() {
        easyButton = new JButton("Easy");
        mediumButton = new JButton("Medium");
        hardButton = new JButton("Hard");

        addButton(easyButton);
        addButton(mediumButton);
        addButton(hardButton);

        easyButton.addActionListener(e -> currentDifficulty = "Easy");
        mediumButton.addActionListener(e -> currentDifficulty = "Medium");
        hardButton.addActionListener(e -> currentDifficulty = "Hard");
    }
    /**
     * Adds a button to the GUI and configures its properties.
     *
     * @param button The button to add.
     *   @author lana
     */
    private void addButton(JButton button) {
        configureButton(button);
        mainPanel.add(button, gbc);
        button.addActionListener(this);
    }
    /**
     * Configures the appearance and properties of a button.
     *
     * @param button The button to configure.
     *   @author lana
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
     * Handles action events for the buttons.
     *
     * @param e The action event.
     *           @author lana
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == avslutaButton) {
            System.exit(0);
        } else if (source == startGameButton) {
            StartGame();
        } else if (source == landslagButton || source == championsLeagueButton || source == allsvenskanButton) {
            if (source == landslagButton) {
                currentCategory = "Landslag";
            } else if (source == championsLeagueButton) {
                currentCategory = "Champions League";
            } else if (source == allsvenskanButton) {
                currentCategory = "Allsvenskan";
            }
            showQuiz(currentCategory);
    }}
    /**
     * Displays the quiz interface for the selected category.
     *
     * @param category The selected category.
     *                  @author lana
     */
    public void showQuiz(String category) {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridBagLayout());

        titleLabel.setText(category + " Quiz");
        mainPanel.add(titleLabel, gbc);

        String[] options = {"Easy", "Medium", "Hard"};
        int difficultyIndex = JOptionPane.showOptionDialog(
                this,
                "Choose the difficulty level:",
                "Select Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (difficultyIndex == JOptionPane.CLOSED_OPTION) {
            resetToMainMenu();
            return;
        }

        currentDifficulty = options[difficultyIndex];

        startGameButton = new JButton("Start Game");
        configureButton(startGameButton);
        mainPanel.add(startGameButton, gbc);
        startGameButton.addActionListener(e -> StartGame());

         backButton = new JButton("Back to Menu");
        configureButton(backButton);
        mainPanel.add(backButton, gbc);
        backButton.addActionListener(e -> resetToMainMenu());

        validate();
        repaint();
        // setVisible(true);
    }
    /**
     * Starts the game with the selected category and difficulty.
     *  @author lana
     */
    private void StartGame() {
        System.out.println("Starting game for category: " + currentCategory + " with difficulty: " + currentDifficulty);
        setVisible(false);
        controller.startGame(currentCategory, currentDifficulty);
    }
    /**
     * Resets the GUI to the main menu.
     *  @author lana
     */
    public void resetToMainMenu() {
        System.out.println("hej");
        mainPanel.removeAll();
        titleLabel.setText("MENY");
        mainPanel.add(titleLabel, gbc);

        addButton(landslagButton);
        addButton(championsLeagueButton);
        addButton(allsvenskanButton);
        addButton(avslutaButton);
        validate();
        repaint();
    }

    /**
     * Saves a high score entry to the high score file.
     *
     * @param name  The name of the player.
     * @param score The score of the player.
     *   @author lana
     */
    private void saveHighScore(String name, int score) {
        List<HighScoreEntry> highScores = readHighScores();
        highScores.add(new HighScoreEntry(name, score));
        Collections.sort(highScores);


        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(HIGH_SCORE_FILE)))) {
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
        File file = new File(HIGH_SCORE_FILE);
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
        JOptionPane.showMessageDialog(this, message.toString());
    }
    /**
     * The main method to start the application.
     *
     * @param args The command line arguments.
     *   @author lana
     */
    public static void main(String[] args) {
        QnA[] questions = intitializeQuestions();
        Player player = new Player("Player");
        View view = new View();
        Controller controller = new Controller(player, questions, view);
        mainGui gui = new mainGui(controller);
        view.setMainguiii(gui); // Pass the mainGui instance to the View
        gui.setVisible(true);
    }
    /**
     * Initializes the questions for the game.
     *
     * @return An array of QnA objects.
     *  @author lana
     */
    private static QnA[] intitializeQuestions() {
        return new QnA[0];
    }
}
