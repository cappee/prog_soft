import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ClientThread.class.getName());

    private Socket socket;
    private PrintWriter printWriter;
    private final FrameUpdater frameUpdater;

    private boolean running;

    public ClientThread(String host, int port, FrameUpdater frameUpdater) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            frameUpdater.error();
            logger.severe("Error opening socket connection to " + host + ":" + port);
        }

        this.frameUpdater = frameUpdater;
    }

    public void run() {
        this.running = true;

        while (this.running) {
            if (!socket.isConnected()) {
                frameUpdater.error();
                logger.severe("Socket connection is not established");
                return;
            }

            Scanner scanner;
            try {
                scanner = new Scanner(socket.getInputStream());
                printWriter = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                logger.severe("Error opening input/output streams");
                this.running = false;
                frameUpdater.error();
                return;
            }

            printWriter.println("start");

            while (scanner.hasNextLine()) {
                System.out.println("RUN BY " + Thread.currentThread().getName());
                String line = scanner.nextLine();
                switch (line) {
                    case "ERROR":
                        try {
                            frameUpdater.error();

                            socket.close();
                            logger.warning("Received error message from server, closing socket connection...");
                        } catch (IOException e) {
                            logger.severe("Error closing socket connection after received error message from server");
                        }
                        break;
                    case "END":
                        frameUpdater.stop();
                        logger.info("Received end of transmission message from server");
                        break;
                    case "INTERRUPTED":
                        frameUpdater.stop();
                        logger.info("Message transmission interrupted by user");
                        break;
                    default:
                        // line format: <type>:<filename>.<extension>:<dimension> KB
                        String[] data = line.split(":");

                        frameUpdater.display(data[0], data[1], Double.parseDouble(data[2].substring(0, data[2].length() - 3)));
                        break;
                }
            }
        }
    }

    public void stop() {
        if (running) {
            running = false;
            printWriter.println("stop");
        }
    }

    public void close() {
        try {
            printWriter.println("disconnect");
            socket.close();
        } catch (IOException e) {
            logger.severe("Error closing socket connection after user decided to disconnect");
        }
    }
}
