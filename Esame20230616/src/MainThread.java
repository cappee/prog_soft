import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainThread implements Runnable {

    private final MainFrame frame;
    private Socket socket;
    private PrintWriter writer;

    public MainThread(MainFrame frame) {
        this.frame = frame;
        try {
            socket = new Socket(frame.host.getText(), Integer.parseInt(frame.port.getText()));
        } catch (IOException|NumberFormatException e) {
            return;
        }
        frame.connect.setEnabled(false);
        frame.disconnect.setEnabled(true);
        frame.get.setEnabled(true);
        frame.reset.setEnabled(true);
        frame.reset();
        frame.log.setText("");
        frame.numero.setText("");
    }

    @Override
    public void run() {
        Scanner scanner;
        try {
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            return;
        }
        int numero;
        try {
            numero = Integer.parseInt(frame.numero.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Numero invalido");
            return;
        }
        if (numero < 0 || numero > 9) {
            JOptionPane.showMessageDialog(frame, "Numero non compreso tra 0 e 9");
            return;
        }
        writer.println("GET:" + frame.numero.getText());
        frame.disconnect.setEnabled(false);
        frame.get.setEnabled(false);
        frame.reset();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("END")) {
                frame.disconnect.setEnabled(true);
                frame.get.setEnabled(true);
                break;
            } else if (line.equals("ERROR")) {
                disconnect();
                break;
            }

            frame.log.append(line + "\n");

            char[] chars = line.split(":")[1].toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '1') {
                    frame.setPixelColor(i / 5, i % 5, Color.BLACK);
                }
            }
        }
    }

    public void disconnect() {
        try {
            writer.println("DISCONNECT");
            socket.close();
        } catch (IOException e) {
            return;
        }
        frame.connect.setEnabled(true);
        frame.disconnect.setEnabled(false);
        frame.get.setEnabled(false);
        frame.reset.setEnabled(false);
        frame.reset();
        frame.log.setText("");
        frame.numero.setText("");
    }

}
