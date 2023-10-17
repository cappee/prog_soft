import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {

   public static void main(String[] args) throws IOException {
      System.out.println("### tentativo di connessione...");
      Socket socket = new Socket("localhost", 4401);
      System.out.println("### connessione stabilita\n");

      //creo in e out
      Scanner in = new Scanner(socket.getInputStream());
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      //invio stringa "carte"
      out.println("carte");
      System.out.println("### stringa \"carte\" inviata");

      //ricevo interi
      LinkedList<Integer> numbers = new LinkedList<>();
      for (int i = 0; i < 5; i++) {
         numbers.add(in.nextInt());
      }
      System.out.println("Numeri ricevuti: " + numbers);

      //faccio cambiare i numeri all'utente
      Scanner read = new Scanner(System.in);
      System.out.print("Inserisci i numeri che vuoi cambiare (max 5): ");
      Scanner indexesToReplace = new Scanner(read.nextLine());
      StringBuffer sb = new StringBuffer();
      sb.append("cambio ");
      int counter = 0;
      while (indexesToReplace.hasNextInt() && counter <= 4) {
         sb.append(indexesToReplace.nextInt()).append(" ");
         counter++;
      }
      out.println(sb);
      System.out.println("### stringa \""+ sb + "\" inviata");

      //ricevo interi aggiornati
      LinkedList<Integer> numbersUpdated = new LinkedList<>();
      for (int i = 0; i < 5; i++) {
         numbersUpdated.add(in.nextInt());
      }
      System.out.println("Numeri aggiornati: " + numbersUpdated);

   }

}
