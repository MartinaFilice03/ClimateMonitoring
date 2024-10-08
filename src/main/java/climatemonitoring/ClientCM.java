// Autori: Casalini Iacopo (753132, Varese); Filice Martina (752916, Varese) e Radice Samuele (753722, Varese)

package climatemonitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

/**
 * La classe ClientCM si occupa della connessione a un server di monitoraggio climatico,
 * utilizzando socket per la comunicazione tra client e server.
 * Consente la trasmissione e ricezione di messaggi e oggetti attraverso flussi di input/output.
 * Inoltre, l'applicazione avvia un'interfaccia grafica Swing.
 */
public class ClientCM {
	static Socket socket;
	public static ObjectOutputStream out;
	public static ObjectInputStream in;
	public static PrintWriter writer;
    public static void main(String[] args) {
        try {
        	try {
        		// Si connette al server che gira su localhost (127.0.0.1) e la porta 5000
                socket = new Socket("127.0.0.1", 5000);
                System.out.println("Connesso al server!");

                // Creazione dei flussi di input e output per la comunicazione
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
                
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                // Invia un messaggio al server - writer.println()
                // Riceve la risposta dal server - reader.readLine()
                
                // Avvio app
                SwingUtilities.invokeLater(() -> new PaginaIniziale());

               
        	}finally {
        		 // Chiude le risorse
                //socket.close();
        	}
            
        } catch (UnknownHostException ex) {
            System.out.println("Host sconosciuto.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Errore di I/O.");
            ex.printStackTrace();
        }
    }
}