import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    JTextField host = new JTextField(12);
    JTextField port = new JTextField("4400",5);
    JButton connect = new JButton("Connect");
    JButton disconnect = new JButton("Disconnect");

    JButton[][] pixels = new JButton[5][5];
    JTextArea log = new JTextArea(5, 18);

    JTextField numero = new JTextField(10);
    JButton get = new JButton("Get");
    JButton reset = new JButton("Reset");

    public MainFrame() {
        super("Gabriele Cappellaro 2044279");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainListener listener = new MainListener(this);

        JPanel p1 = new JPanel();
        p1.add(new JLabel("Server address: "));
        p1.add(host);
        p1.add(new JLabel("Port: "));
        p1.add(port);
        connect.addActionListener(listener);
        p1.add(connect);
        disconnect.setEnabled(false);
        disconnect.addActionListener(listener);
        p1.add(disconnect);
        add(p1, BorderLayout.NORTH);

        JPanel p2 = new JPanel();
        JPanel grid = new JPanel(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                pixels[i][j] = new JButton();
                pixels[i][j].setEnabled(false);
                pixels[i][j].setBackground(Color.WHITE);
                pixels[i][j].setPreferredSize(new Dimension(100, 100));
                grid.add(pixels[i][j]);
            }
        }
        p2.add(grid);
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Log"));
        logPanel.add(new JScrollPane(log));
        log.setEditable(false);
        p2.add(logPanel);
        add(p2, BorderLayout.CENTER);

        JPanel p3 = new JPanel();
        p3.add(new JLabel("Numero:"));
        p3.add(numero);
        get.setEnabled(false);
        get.addActionListener(listener);
        p3.add(get);
        reset.setEnabled(false);
        reset.addActionListener(listener);
        p3.add(reset);
        add(p3, BorderLayout.SOUTH);

        pack();

        setVisible(true);
    }

    public void reset() {
        for (JButton[] row : pixels) {
            for (JButton pixel : row) {
                pixel.setBackground(Color.WHITE);
            }
        }
    }

    public void setPixelColor(int i, int j, Color color) {
        pixels[i][j].setBackground(color);
    }

    public static void main(String[] args) {
        new MainFrame();
    }

}
