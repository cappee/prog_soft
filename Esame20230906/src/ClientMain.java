import java.util.logging.Logger;

public class ClientMain {

    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    public static void main(String[] args) {
        runClientGUI();
    }

    private static void runClientGUI() {
        new ClientFrame("", 4400).setVisible(true);
        logger.info("Client GUI started");
    }

}
