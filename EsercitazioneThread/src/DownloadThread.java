import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class DownloadThread implements Runnable {

    private final Socket socket;
    private final FrameUpdater updater;

    public DownloadThread(String host, int port, FrameUpdater updater) {
        try {
            this.socket = new Socket(host, port);
            this.updater = updater;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner console = new Scanner(System.in);

            System.out.println(in.nextLine());

            System.out.print("inserire numero da inviare: ");
            int num = Integer.parseInt(console.nextLine());
            out.println(num);

            Thread timeout = new Thread(new TimeoutThread(Thread.currentThread()), "Timeout");
            timeout.start();

            while (in.hasNextLine() && !Thread.currentThread().isInterrupted()) {
                System.out.println(in.nextLine());
                timeout.interrupt();
            }
            if (timeout.isAlive()) timeout.interrupt();
        } catch (IOException e) {
            System.out.println("errore di scaricamento");
            updater.setDownloadEnabled(true);
        }
        updater.setWaitEnabled(true);
        updater.setDownloadEnabled(true);
    }
}

