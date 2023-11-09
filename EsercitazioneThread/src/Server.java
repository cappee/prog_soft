// un server in ascolto sulla porta 8080
// riceve un numero, da solo su una linea
// aspetta quel numero di secondi, poi risponde

// come eseguire:
//    scaricare
//    aprire la cartella dove si trova il file scaricato
//    F4, oppure "open folder in terminal"
//    javac Server.java
//    java Server

// indirizzo "localhost", porta 8080

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Single implements Runnable {
	Socket socket;

	@Override
	public void run() {
		PrintWriter pr;
		Scanner sc, i;
		String l;
		int time;
		InetSocketAddress sa;
		Inet4Address s4;

		System.out.println("connessione iniziata");
		sa = (InetSocketAddress) this.socket.getRemoteSocketAddress();
		s4 = (Inet4Address) sa.getAddress();
		System.out.println(s4.getHostAddress());
	
		try {
			pr = new PrintWriter(socket.getOutputStream());
			sc = new Scanner(socket.getInputStream());

			pr.println("CONNESSO");
			pr.flush();

			l = sc.nextLine();
			System.out.println("ricevuto: " + l);
			time = Integer.parseInt(l);

			try {
				Thread.sleep(time * 1000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

			pr.println("RISPOSTA");
			pr.flush();

			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("connessione terminata");
	}
}


public class Server {
	public static void main(String[] args)
			throws IOException, InterruptedException {
		ServerSocket ss = new ServerSocket(8080);
		Single s;

		while (true) {
			s = new Single();
			s.socket = ss.accept();
			Thread t = new Thread(s);
			t.start();
		}
	}
}
