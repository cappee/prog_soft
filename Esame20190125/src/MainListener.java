import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainListener implements ActionListener {

    MainFrame frame;
    MainThread thread;

    public MainListener(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connect":
                thread = new MainThread(frame, frame.host.getText(), Integer.parseInt(frame.port.getText()));
                break;
            case "Disconnect":
                thread.disconnect();
                break;
            case "Start":
                new Thread(thread).start();
                break;
            case "Stop":
                thread.stop();
                break;
        }
    }
}
