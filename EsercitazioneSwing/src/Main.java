import javax.swing.*;
import java.awt.*;

public class Main {

    public static JFrame frame1, frame2;
    protected static JTextArea text, label;
    private static JButton print, cancel, show, exit;


    public static void main(String[] args) {
        //creo le finestre
        frame1 = new JFrame();
        frame1.setTitle("Esercitazione Swing [1]");
        frame1.setVisible(true);

        frame2 = new JFrame();
        frame2.setTitle("Esercitazione Swing [2]");

        //creo gli elementi
        text = new JTextArea(">>> ", 15, 40);
        print = new JButton("Stampa");
        cancel = new JButton("Cancella");
        show = new JButton("Mostra");
        exit = new JButton("Esci");

        label = new JTextArea();
        label.setEditable(false);
        label.setCaretColor(Color.WHITE);
        JButton ok = new JButton("OK");

        //creo un JPanel per organizzare i bottoni sulla stessa riga
        JPanel panel = new JPanel();
        LayoutManager lm = new GridLayout(1,4);
        panel.setLayout(lm);
        panel.add(print);
        panel.add(cancel);
        panel.add(show);
        panel.add(exit);

        //aggiungo gli elementi al frame
        frame1.add(text, BorderLayout.CENTER);
        frame1.add(panel, BorderLayout.SOUTH);
        frame1.pack();

        frame2.add(label, BorderLayout.CENTER);
        frame2.add(ok, BorderLayout.SOUTH);

        //associo in listener ad ogni bottone
        Ascoltatore listener = new Ascoltatore();

        print.addActionListener(listener);
        cancel.addActionListener(listener);
        show.addActionListener(listener);
        exit.addActionListener(listener);

        ok.addActionListener(listener);
    }

    public static void setVisibleFrame1(boolean visible) {
        text.setEnabled(visible);
        print.setEnabled(visible);
        cancel.setEnabled(visible);
        show.setEnabled(visible);
        exit.setEnabled(visible);
    }
}