import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainGUI extends JDialog {
    private JButton allsvenskanButton;
    private JButton landslagButton;
    private JButton championsLeagueButton;
    private JLabel MENYLabel;
    private JButton avslutaButton;
    private JPanel Mainpanel;
    private JButton buttonOK;
    private JButton buttonCancel;

    public mainGUI() {
        createUIComponents();
        setContentPane(Mainpanel);
        setTitle("Fotbollsquiz");
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,300);
        setLocationRelativeTo(null);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        Mainpanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        landslagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //kalla på landslag spelplan
            }
        });
        championsLeagueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // kalla på championsleauge spelplan
            }
        });
        allsvenskanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // kalla på allsvenska spelplan
            }
        });
        avslutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            System.exit(0);
            }
        });
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        mainGUI dialog = new mainGUI();

    }

    private void createUIComponents() {
        Mainpanel = new JPanel();
        landslagButton = new JButton("Landslag");
        championsLeagueButton = new JButton("Champions League");
        allsvenskanButton = new JButton("Allsvenskan");
        MENYLabel = new JLabel("MENY");
        buttonOK = new JButton("OK");
        buttonCancel = new JButton("Cancel");

        // Set layout and add components to Mainpanel.
        Mainpanel.setLayout(new BorderLayout()); // Use appropriate layout.
        Mainpanel.add(MENYLabel, BorderLayout.NORTH);
        Mainpanel.add(landslagButton, BorderLayout.WEST);
        Mainpanel.add(championsLeagueButton, BorderLayout.CENTER);
        Mainpanel.add(allsvenskanButton, BorderLayout.EAST);
        Mainpanel.add(buttonOK, BorderLayout.SOUTH);
        Mainpanel.add(buttonCancel, BorderLayout.SOUTH);
    }
}
