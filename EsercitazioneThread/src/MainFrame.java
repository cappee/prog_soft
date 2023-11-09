import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class MainFrame extends JFrame implements ActionListener, FrameUpdater{

    private final JButton download = new JButton("Scarica");
    private final JButton wait = new JButton("Attendi");

    public MainFrame() {
        super("Esercitazione Thread");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        download.addActionListener(this);
        JButton stop = new JButton("Interrompi");
        stop.addActionListener(this);
        JButton show = new JButton("Visualizza");
        show.addActionListener(this);
        wait.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2,2));
        panel.add(download);
        panel.add(stop);
        panel.add(show);
        panel.add(wait);

        this.add(panel);

        this.setSize(340,160);
        this.setVisible(true);
    }

    private Thread thread;

    public static void main(String[] args) {
        new MainFrame();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Scarica":
                setDownloadEnabled(false);
                thread = new Thread(new DownloadThread("localhost", 8080, this));
                thread.start();
                break;
            case "Interrompi":
                if (thread != null && thread.isAlive()) {
                    thread.interrupt();
                    setDownloadEnabled(true);
                    System.out.println("scaricamento interrotto");
                }
                break;
            case "Visualizza":
                Set<Thread> threads = Thread.getAllStackTraces().keySet();
                int n = 0;
                System.out.println("--------------------------------");
                System.out.println("Thread attivi:");
                for (Thread t : threads) {
                    if (!(t.getName().equals("Timeout") || t.getName().contains("Thread-"))) continue;
                    System.out.println("#" + ++n + " " + t.getName()+ " " + t.getState());
                }
                System.out.println("--------------------------------");
                break;
            case "Attendi":
                if (thread.isAlive()) {
                    setWaitEnabled(false);
                }
                break;
        }
    }

    @Override
    public void setDownloadEnabled(boolean enabled) {
        download.setEnabled(enabled);
    }

    @Override
    public void setWaitEnabled(boolean enabled) {
        wait.setEnabled(enabled);
    }
}
