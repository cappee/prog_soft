import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientListener implements ActionListener {

    private final ClientFrame frame;
    private ClientThread runnable;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ClientListener(ClientFrame frame) {
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connect":
                runnable = new ClientThread(frame);
                break;
            case "Disconnect":
                runnable.disconnect(true);
                break;
            case "Execute":
                executor.execute(runnable);
                break;
            case "Interrupt":
                runnable.interrupt();
                break;
        }
    }
}
