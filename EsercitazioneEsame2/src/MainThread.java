import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

public class MainThread implements Runnable {

    private static final Logger logger = Logger.getLogger(MainThread.class.getName());

    MainFrame frame;
    Socket socket;
    Scanner scanner;
    PrintWriter writer;

    ArrayList<Color> colors = new ArrayList<>(
            Arrays.asList(Color.WHITE, Color.BLACK, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE, Color.ORANGE)
    );

    public MainThread(MainFrame frame, String host, int port) {
        this.frame = frame;
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            logger.severe("Error while connecting to the server: " + e.getMessage());
        }
        frame.connect.setEnabled(false);
        frame.disconnect.setEnabled(true);
        frame.start.setEnabled(true);
        try {
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            logger.severe("Error while getting the I/O stream: " + e.getMessage());
        }

    }

    @Override
    public void run() {
        try {
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            logger.severe("Error while getting the I/O stream: " + e.getMessage());
        }

        writer.println("start");
        this.frame.stop.setEnabled(true);
        this.frame.disconnect.setEnabled(false);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.equals("-1;-1")) {
                this.frame.stop.setEnabled(false);
                this.frame.start.setEnabled(true);
                this.frame.disconnect.setEnabled(true);
                break;
            }

            Color color;
            try {
                color = colors.get(Integer.parseInt(line.split(";")[0]));
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                logger.severe("Error while parsing the color: " + e.getMessage());
                break;
            }


            switch (line.split(";")[1]) {
                case "SX":
                    frame.flagSX.setBackground(color);
                    break;
                case "CX":
                    frame.flagCX.setBackground(color);
                    break;
                case "DX":
                    frame.flagDX.setBackground(color);
                    break;
            }
        }
    }

    public void stop() {
        writer.println("stop");
    }

    public void disconnect() {
        writer.println("disconnect");
        try {
            socket.close();
        } catch (IOException e) {
            logger.severe("Error while closing the socket: " + e.getMessage());
        }
        this.frame.connect.setEnabled(true);
        this.frame.disconnect.setEnabled(false);
        this.frame.start.setEnabled(false);
    }
}
