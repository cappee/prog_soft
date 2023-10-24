import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class Main {

    static Logger log = Logger.getLogger("Main");

    public static void main(String[] args) {
        //inizializzo la finestra
        JFrame frame = new JFrame();
        frame.setTitle("Esercitazione File");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        log.info("created JFrame");

        JTextArea text = new JTextArea("prova.txt", 5, 0);
        JButton google = new JButton("www.google.com");
        JButton example = new JButton("www.example.com");
        JButton w3 = new JButton("www.w3.org");
        JButton x = new JButton("www.x.org");
        JLabel label = new JLabel(" ");

        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.add(google);
        panel.add(example);
        panel.add(w3);
        panel.add(x);

        frame.add(text, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(label, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        log.info("setVisible JFrame");

        Ascoltatore listener = new Ascoltatore(text, label);
        google.addActionListener(listener);
        example.addActionListener(listener);
        w3.addActionListener(listener);
        x.addActionListener(listener);

    }

}
