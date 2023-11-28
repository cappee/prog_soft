import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainListener implements ActionListener {

    private final MainFrame frame;
    private MainThread runnable;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public MainListener(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connect":
                runnable = new MainThread(frame);
                break;
            case "Disconnect":
                runnable.disconnect();
                break;
            case "Get":
                executor.execute(runnable);
                break;
            case "Reset":
                frame.reset();
                break;
        }
    }
}
