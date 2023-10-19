import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ascoltatore implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Stampa":
                System.out.println(Main.text.getText());
                break;
            case "Cancella":
                Main.text.setText(">>> ");
                break;
            case "Mostra":
                Main.label.setText(Main.text.getText());
                Main.frame2.pack();
                Main.frame2.setVisible(true);
                Main.setVisibleFrame1(false);
                break;
            case "Esci":
                System.exit(0);
                break;
            case "OK":
                Main.frame2.setVisible(false);
                Main.setVisibleFrame1(true);
                break;
        }
        try {
            //usato per rallentare il programma ???
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
