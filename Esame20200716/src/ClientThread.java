import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ClientThread.class.getName());
    private Socket socket;
    private PrintWriter out;
    private final ClientFrame frame;

    public ClientThread(ClientFrame frame) {
        this.frame = frame;
        try {
            socket = new Socket(frame.host.getText(), Integer.parseInt(frame.port.getText()));
        } catch (NumberFormatException | IOException e) {
            logger.severe("Error while connecting to the server: " + e.getMessage());
            return;
        }
        frame.connect.setEnabled(false);
        frame.disconnect.setEnabled(true);
        frame.execute.setEnabled(true);
        frame.console.setText("");
    }

    @Override
    public void run() {
        frame.execute.setEnabled(false);
        frame.interrupt.setEnabled(true);
        frame.disconnect.setEnabled(false);
        Scanner in;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            logger.severe("Error while getting the I/O stream: " + e.getMessage());
            return;
        }
        send(frame.command.getText());
        frame.command.setText("");
        label:
        while (in.hasNextLine()) {
            String line = in.nextLine();
            switch (line) {
                case "END":
                    frame.console.append("========== Download completato ==========\n");
                    frame.execute.setEnabled(true);
                    frame.interrupt.setEnabled(false);
                    frame.disconnect.setEnabled(true);

                    break label;
                case "INTERRUPTED":
                    frame.console.append("========== Download interrotto ==========\n");
                    frame.execute.setEnabled(true);
                    frame.interrupt.setEnabled(false);
                    frame.disconnect.setEnabled(true);

                    break label;
                case "ERROR":
                    frame.console.append("Comando errato (connessione chiusa dal server)\n");
                    disconnect(false);

                    break label;
                default:
                    frame.console.append(line + "\n");
                    break;
            }
        }
    }

    public void interrupt() {
        send("INTERRUPT");
    }

    public void disconnect(boolean send) {
        if (send) send("DISCONNECT");
        try {
            socket.close();
        } catch (IOException e) {
            logger.severe("Error while closing the socket: " + e.getMessage());
        }
        frame.connect.setEnabled(true);
        frame.disconnect.setEnabled(false);
        frame.execute.setEnabled(false);
        frame.interrupt.setEnabled(false);
        frame.command.setText("");
    }

    private void send(String command) {
        out.println(command);
        out.flush();
    }
}
