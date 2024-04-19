import javax.swing.*;
import java.awt.*;

public class NextSlide extends JFrame{

    public NextSlide(String kategori){
        setTitle(kategori + " Quiz");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        JLabel quiestionLabel = new JLabel("grgrgrg");
        quiestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(quiestionLabel, BorderLayout.CENTER);

        Font font = quiestionLabel.getFont();
        quiestionLabel.setFont(font.deriveFont(Font.PLAIN, 30));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2,2));
        JButton answer1Button = new JButton("Svar 1");
        JButton answer2Button = new JButton("Svar 2");
        JButton answer3Button = new JButton("Svar 3");
        JButton answer4Button = new JButton("Svar 4");
        southPanel.add(answer1Button);
        southPanel.add(answer2Button);
        southPanel.add(answer3Button);
        southPanel.add(answer4Button);


        panel.add(northPanel);
        panel.add(southPanel);
        setContentPane(panel);
        //  quizFrame.add(panel);
        setVisible(true);
    }

}
