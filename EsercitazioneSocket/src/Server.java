import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("### tentativo di avvio del server...");
        ServerSocket server = new ServerSocket(4401);
        System.out.println("### server avviato correttamente\n");

        Socket socket;
        while (true) {
            socket = server.accept();

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            LinkedList<Integer> numbers = new LinkedList<>();

            //handle request
            while (in.hasNextLine()) {
                String request = in.nextLine();
                System.out.println("### ricevuta richiesta: " + request);

                if (request.equals("carte")) {
                    //genero numeri
                    for (int i=0; i<5; i++) {
                        int n;
                        for (n = (int) (Math.random() * 10 + 1); numbers.contains(n); n = (int) (Math.random() * 10 + 1)) {}
                        numbers.add(n);

                        out.print(numbers.get(i));
                        out.print(" ");
                    }
                    out.println();
                    System.out.println("### numeri generati: " + numbers);
                } else if (request.contains("cambio")) {
                    //ricevo numeri da cambiare
                    Scanner indexesToReplace = new Scanner(request);
                    indexesToReplace.skip("cambio");
                    while (indexesToReplace.hasNextInt()) {
                        int i = indexesToReplace.nextInt() - 1;
                        if (i < 0 || i > 4) {
                            System.out.println("### richiesta non valida");
                            continue;
                        }
                        //genero numero finchè non è diverso da quello attuale o da quelli già generati
                        int newNumber;
                        for (newNumber = (int) (Math.random() * 10 + 1); newNumber == numbers.get(i) && numbers.contains(newNumber); newNumber = (int) (Math.random() * 10 + 1)) {}
                        //sostituisco numero
                        numbers.set(i, newNumber);
                    }
                    //invio numeri aggiornati
                    for (int i=0; i<5; i++) {
                        out.print(numbers.get(i));
                        out.print(" ");
                    }
                    out.println();
                    System.out.println("### numeri aggiornati: " + numbers);
                } else {
                    System.out.println("### richiesta non valida");
                }
            }
            socket.close();
        }

    }

}
