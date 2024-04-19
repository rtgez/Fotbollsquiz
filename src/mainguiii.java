import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainguiii extends JDialog implements ActionListener {
    private JButton allsvenskanButton, landslagButton, championsLeagueButton, avslutaButton, startGameButton;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private  Controller controller;
    private GridBagConstraints gbc;
    private String currentCategory = "";
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private String currentDifficulty = "";

    public mainguiii(Controller controller) {
        this.controller=controller;
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
        setVisible(true);
    }
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

    private void addButton(JButton button) {
        configureButton(button);
        mainPanel.add(button, gbc);
        button.addActionListener(this);
    }

    private void configureButton(JButton button) {
        button.setBackground(Color.decode("#93ceaf"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

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
        }
    }

    private void showQuiz(String category) {
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

        JButton backButton = new JButton("Back to Menu");
        configureButton(backButton);
        mainPanel.add(backButton, gbc);
        backButton.addActionListener(e -> resetToMainMenu());

        validate();
        repaint();
        setVisible(true);
    }



    private void StartGame() {
        System.out.println("Starting game for category: " + currentCategory + " with difficulty: " + currentDifficulty);
        controller.startGame(currentCategory, currentDifficulty);
    }


    private void resetToMainMenu() {
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

    public static void main(String[] args) {
        QnA[] questions = intitializeQuestions();
        Player player = new Player(DEFAULT_MODALITY_TYPE.name());
        View view = new View();
        Controller controller = new Controller(player, questions, view);
        new mainguiii(controller).setVisible(true);
    }

    private static QnA[] intitializeQuestions() {
        QnA[] qnAS = new QnA[0];
        return qnAS;
    }
}
