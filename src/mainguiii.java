import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;




public class mainguiii extends JDialog implements ActionListener {
    private JButton premierLeagueButton, vmButton, championsLeagueButton, avslutaButton, startGameButton;
    private JLabel titleLabel;
    private JPanel mainPanel;
    private Controller controller;
    private GridBagConstraints gbc;
    private String currentCategory = "";
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private String currentDifficulty = "";
    private static final String FILE_NAME = "usernames.txt";


    public static void main(String[] args) {

       // list to store unique names
        List<String> uniqueNames = new ArrayList<>();

        //read existing names from file
        readNamesFromFile(uniqueNames);

        //loop to enter name
        while (true) {
            String name = JOptionPane.showInputDialog("Ange ditt användarnamn: ");

           /* if (name == null) {
                //break loop if user cancels
                break;
            }
*/
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Namn behöver anges. Vänligen ange ditt användarnamn.");

            } else if (uniqueNames.contains(name)) {
                JOptionPane.showMessageDialog(null, "Användarnamn upptaget, ange ett annat namn.");


            } else {
                uniqueNames.add(name);
                writeNameToFile(name);
                JOptionPane.showMessageDialog(null, "Varmt välkommen, " + name + "!");
                break;
            }
        }

        QnA[] questions = intitializeQuestions();
        Player player = new Player(DEFAULT_MODALITY_TYPE.name());
        View view = new View();
        Controller controller = new Controller(player, questions, view);
        new mainguiii(controller).setVisible(true);



    }

    //method to read names from file
    private static void readNamesFromFile(List<String> uniqueNames) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                uniqueNames.add(line);
            }
        } catch (IOException e) {
            System.out.println("No existing usernames found, new file will be created");
        }
    }


    //method to write name to the file
    private static void writeNameToFile(String name) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(name);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public mainguiii(Controller controller) {
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

        vmButton = new JButton("VM");
        championsLeagueButton = new JButton("Champions League");
        premierLeagueButton = new JButton("Premier League");
        avslutaButton = new JButton("Avsluta");
        startGameButton = new JButton("Starta quiz");

        addButton(vmButton);
        addButton(championsLeagueButton);
        addButton(premierLeagueButton);
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
        } else if (source == vmButton || source == championsLeagueButton || source == premierLeagueButton) {
            if (source == vmButton) {
                currentCategory = "Landslag";
            } else if (source == championsLeagueButton) {
                currentCategory = "Champions League";
            } else if (source == premierLeagueButton) {
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

        String[] options = {"Easy", "Hard"};
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

        addButton(vmButton);
        addButton(championsLeagueButton);
        addButton(premierLeagueButton);
        addButton(avslutaButton);
        validate();
        repaint();
    }



    private static QnA[] intitializeQuestions() {
        QnA[] qnAS = new QnA[0];
        return qnAS;
    }
}
