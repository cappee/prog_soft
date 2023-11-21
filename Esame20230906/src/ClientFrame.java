import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientFrame extends JFrame implements ActionListener, FrameUpdater {

    private final JButton connect = new JButton("Connect");
    private final JButton disconnect = new JButton("Disconnect");
    private final JTextField host = new JTextField(12);
    private final JTextField port = new JTextField(5);

    private final JTextArea log = new JTextArea(10, 20);
    private final JTextArea pdf = new JTextArea(10, 20);
    private final JTextArea mp3 = new JTextArea(10, 20);

    private final JTextField dimension = new JTextField(10);
    private final JButton start = new JButton("Start");
    private final JButton stop = new JButton("Stop");
    private final JButton clear = new JButton("Clear");

    private ClientThread clientThread;
    private double totalDim = 0;

    public ClientFrame(String host, int port) {
        this.setTitle("Gabriele Cappellaro 2044279");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelServer = new JPanel();
        panelServer.add(new JLabel("Server Address:"));
        panelServer.add(this.host);
        panelServer.add(new JLabel("Port:"));
        panelServer.add(this.port);
        panelServer.add(this.connect);
        panelServer.add(this.disconnect);
        this.host.setText(host);
        this.port.setText(String.valueOf(port));
        this.connect.addActionListener(this);
        this.disconnect.addActionListener(this);
        this.disconnect.setEnabled(false);

        JPanel panelLog = new JPanel(new BorderLayout());
        panelLog.setBorder(BorderFactory.createTitledBorder("Log"));
        panelLog.add(new JScrollPane(log));
        JPanel panelPDF = new JPanel(new BorderLayout());
        panelPDF.setBorder(BorderFactory.createTitledBorder(".pdf"));
        panelPDF.add(new JScrollPane(pdf));
        JPanel panelMP3 = new JPanel(new BorderLayout());
        panelMP3.setBorder(BorderFactory.createTitledBorder(".mp3"));
        panelMP3.add(new JScrollPane(mp3));
        JPanel panelDisplay = new JPanel(new GridLayout(1, 3));
        panelDisplay.add(panelLog);
        panelDisplay.add(panelPDF);
        panelDisplay.add(panelMP3);
        this.log.setEditable(false);
        this.pdf.setEditable(false);
        this.mp3.setEditable(false);

        JPanel panelControls = new JPanel();
        panelControls.add(new JLabel("Dimensione:"));
        panelControls.add(dimension);
        panelControls.add(this.start);
        panelControls.add(this.stop);
        panelControls.add(this.clear);
        this.dimension.setEditable(false);
        this.start.addActionListener(this);
        this.start.setEnabled(false);
        this.stop.addActionListener(this);
        this.stop.setEnabled(false);
        this.clear.addActionListener(this);
        this.clear.setEnabled(false);

        this.add(panelServer, BorderLayout.NORTH);
        this.add(panelDisplay, BorderLayout.CENTER);
        this.add(panelControls, BorderLayout.SOUTH);

        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connect":
                clientThread = new ClientThread(host.getText(), Integer.parseInt(port.getText()), this);

                connect.setEnabled(false);
                disconnect.setEnabled(true);
                start.setEnabled(true);
                clear.setEnabled(true);
                break;
            case "Disconnect":
                clientThread.close();

                connect.setEnabled(true);
                disconnect.setEnabled(false);
                start.setEnabled(false);
                stop.setEnabled(false);
                clear.setEnabled(false);
                break;
            case "Start":
                clear();
                new Thread(clientThread).start();

                start.setEnabled(false);
                disconnect.setEnabled(false);
                stop.setEnabled(true);
                clear.setEnabled(false);
                break;
            case "Stop":
                clientThread.stop();
                break;
            case "Clear":
                clear();
                break;
        }
    }

    private void clear() {
        log.setText("");
        pdf.setText("");
        mp3.setText("");
        dimension.setText("");
        totalDim = 0;
    }

    @Override
    public void display(String type, String filename, Double dim) {
        SwingUtilities.invokeLater(() -> {
            switch (type) {
                case "PDF":
                    pdf.append(filename.split("\\.")[0] + "\n");
                    break;
                case "MP3":
                    mp3.append(filename.split("\\.")[0] + "\n");
                    break;
            }
            log.append(filename + " " + dim + "KB\n");
            totalDim += dim;
            dimension.setText(String.format("%.2f KB", totalDim));
        });
    }

    @Override
    public void stop() {
        SwingUtilities.invokeLater(() -> {
            disconnect.setEnabled(true);
            start.setEnabled(true);
            stop.setEnabled(false);
            clear.setEnabled(true);
        });
    }

    @Override
    public void error() {
        SwingUtilities.invokeLater(() -> {
            connect.setEnabled(true);
            disconnect.setEnabled(false);
            start.setEnabled(false);
            stop.setEnabled(false);
            clear.setEnabled(false);
        });
    }
}
