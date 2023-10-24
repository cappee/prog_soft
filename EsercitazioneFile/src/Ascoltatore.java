import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Ascoltatore implements ActionListener {

    static Logger log = Logger.getLogger("Ascoltatore");
    private final JTextArea text;
    private final JLabel label;

    public Ascoltatore(JTextArea jTextArea, JLabel jLabel) {
        this.text = jTextArea;
        this.label = jLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try(Socket socket = new Socket(e.getActionCommand(), 80)) {
            //invio comando GET
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.print("GET /\r\n\r\n");
            out.flush();
            log.info("command GET sent");

            //gestisco la risposta
            if (socket.isConnected()) {
                Scanner in = new Scanner(socket.getInputStream());

                //creo il file
                File file = new File(text.getText());
                while (in.hasNextLine()) {
                    //scrivo nel file
                    try (PrintWriter fw = new PrintWriter(file)) {
                        fw.println(in.nextLine());
                        fw.flush();
                        label.setText("risposta scritta nel file");
                    } catch (FileNotFoundException ex) {
                        log.warning("FileNotFoundException thrown");
                        label.setText("file non creato/trovato");
                    }
                }
                log.info("socket response written in file");
            }
        } catch (IOException ex) {
            log.warning("unable to connect");
        }
    }
}
