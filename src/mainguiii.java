package src;

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
    private static Controller controller;
    private GridBagConstraints gbc;
    private String currentCategory = "";
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private static String Player;
    private static String player;
    private static boolean multiplayer;
    private String currentDifficulty = "";
    private static final String FILE_NAME = "usernames.txt";


    public static void main(String[] args) {

       // list to store unique names
        List<String> uniqueNames = new ArrayList<>();

        //read existing names from file
        readNamesFromFile(uniqueNames);

        //loop to enter name
        while (true) {
            String options = JOptionPane.showInputDialog("Ange 1 för Singleplayer\nAnge 2 för Multiplayer");
            int choice = Integer.parseInt(options);

            switch (choice) {
                case 1:
                    JOptionPane.showMessageDialog(null, "Du valde Singleplayer!");
                    multiplayer = false;
                    String name1 = JOptionPane.showInputDialog("Ange ditt användarnamn för Player1: ");
                    Player = name1;
                    if (name1 == null) {
                        // break loop if user cancels
                        break;
                    }

                    if (name1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Namn behöver anges. Vänligen ange ditt användarnamn.");
                    } else if (uniqueNames.contains(name1)) {
                        JOptionPane.showMessageDialog(null, "Användarnamn upptaget, ange ett annat namn.");
                    } else {
                        uniqueNames.add(name1);
                        writeNameToFile(name1);
                        JOptionPane.showMessageDialog(null, "Varmt välkommen, " + name1 + "!");
                        break;
                    }
                    break; // Lägg till break här för att avsluta switch-satsen korrekt

                case 2:
                    JOptionPane.showMessageDialog(null, "Du valde Multiplayer!");
                    multiplayer = true;
                    String name_1 = JOptionPane.showInputDialog("Ange ditt användarnamn för Player1: ");
                    Player = name_1;
                    if (name_1 == null) {
                        // break loop if user cancels
                        break;
                    }

                    if (name_1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Namn behöver anges. Vänligen ange ditt användarnamn.");
                    } else if (uniqueNames.contains(name_1)) {
                        JOptionPane.showMessageDialog(null, "Användarnamn upptaget, ange ett annat namn.");
                    } else {
                        uniqueNames.add(name_1);
                        writeNameToFile(name_1);
                        JOptionPane.showMessageDialog(null, "Varmt välkommen, " + name_1 + "!");
                    }

                    String name2 = JOptionPane.showInputDialog("Ange ditt användarnamn för Player2: ");
                    player = name2;
                    if (name2 == null) {
                        // break loop if user cancels
                        break;
                    }

                    if (name2.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Namn behöver anges. Vänligen ange ditt användarnamn.");
                    } else if (uniqueNames.contains(name2)) {
                        JOptionPane.showMessageDialog(null, "Användarnamn upptaget, ange ett annat namn.");
                    } else {
                        uniqueNames.add(name2);
                        writeNameToFile(name2);
                        JOptionPane.showMessageDialog(null, "Varmt välkommen, " + name2 + "!");
                        break;
                    }
                    break; // Lägg till break här för att avsluta switch-satsen korrekt

                default:
                    JOptionPane.showMessageDialog(null, "Ogiltigt val, vänligen ange 1 eller 2.");
                    break;
            }
            break;
        }


        QnA[] questions = intitializeQuestions();
        Player player1 = new Player(Player);
        System.out.println(player1.getName());
        Player player2 = new Player(player);
        System.out.println(player2.getName());
        View view = new View();
        Controller controller = new Controller(player1, player2, questions, view);
        if (multiplayer){
            controller.setMultiplayer();
        }
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
