import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    JTextField host = new JTextField(12);
    JTextField port = new JTextField(5);
    JButton connect = new JButton("Connect");
    JButton disconnect = new JButton("Disconnect");

    JPanel flagSX = new JPanel(new BorderLayout());
    JPanel flagCX = new JPanel(new BorderLayout());
    JPanel flagDX= new JPanel(new BorderLayout());

    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");

    public MainFrame() {
        super("Gabriele Cappellaro 2044279");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainListener listener = new MainListener(this);

        JPanel panelConnect = new JPanel();
        JPanel panelFlag = new JPanel(new GridLayout(1,3));
        JPanel panelControl = new JPanel();

        panelConnect.add(new JLabel("IP Address"));
        panelConnect.add(host);
        panelConnect.add(new JLabel("Port"));
        panelConnect.add(port);
        connect.addActionListener(listener);
        panelConnect.add(connect);
        disconnect.setEnabled(false);
        disconnect.addActionListener(listener);
        panelConnect.add(disconnect);

        flagSX.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        flagSX.setBackground(Color.LIGHT_GRAY);
        flagSX.setPreferredSize(new Dimension(200,300));
        panelFlag.add(flagSX);

        flagCX.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        flagCX.setBackground(Color.LIGHT_GRAY);
        flagCX.setPreferredSize(new Dimension(200,300));
        panelFlag.add(flagCX);

        flagDX.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        flagDX.setBackground(Color.LIGHT_GRAY);
        flagDX.setPreferredSize(new Dimension(200,300));
        panelFlag.add(flagDX);

        start.setEnabled(false);
        start.addActionListener(listener);
        panelControl.add(start);
        stop.setEnabled(false);
        stop.addActionListener(listener);
        panelControl.add(stop);

        add(panelConnect, BorderLayout.NORTH);
        add(panelFlag, BorderLayout.CENTER);
        add(panelControl, BorderLayout.SOUTH);

        pack();

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
