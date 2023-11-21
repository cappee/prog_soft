import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {

    protected JTextField host, port;
    protected JButton connect, disconnect;
    protected JTextArea console;
    protected JTextField command;
    protected JButton execute, interrupt;

    public ClientFrame(String host, String port) {
        super("Gabriele Cappellaro 2044279");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelServer = new JPanel();
        panelServer.add(new JLabel("Server address:"));
        this.host = new JTextField(host, 10);
        panelServer.add(this.host);
        panelServer.add(new JLabel("Port:"));
        this.port = new JTextField(port, 5);
        panelServer.add(this.port);
        connect = new JButton("Connect");
        ClientListener listener = new ClientListener(this);
        connect.addActionListener(listener);
        panelServer.add(connect);
        disconnect = new JButton("Disconnect");
        disconnect.addActionListener(listener);
        disconnect.setEnabled(false);
        panelServer.add(disconnect);

        JPanel panelConsole = new JPanel(new BorderLayout());
        panelConsole.setBorder(BorderFactory.createTitledBorder("Console"));
        console = new JTextArea(25, 80);
        console.setEditable(false);
        panelConsole.add(new JScrollPane(console));

        JPanel panelCommand = new JPanel();
        panelCommand.add(new JLabel("Command"));
        command = new JTextField(20);
        panelCommand.add(command);
        execute = new JButton("Execute");
        execute.addActionListener(listener);
        execute.setEnabled(false);
        panelCommand.add(execute);
        interrupt = new JButton("Interrupt");
        interrupt.addActionListener(listener);
        interrupt.setEnabled(false);
        panelCommand.add(interrupt);

        add(panelServer, BorderLayout.NORTH);
        add(panelConsole, BorderLayout.CENTER);
        add(panelCommand, BorderLayout.SOUTH);

        pack();

        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientFrame("", "4400");
    }
}
