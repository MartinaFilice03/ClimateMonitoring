// Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)

package climatemonitoring;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Questa classe fornisce un'interfaccia grafica per cercare aree geografiche basate
 * su nome, stato o coordinate geografiche. Permette agli utenti di inserire una ricerca, visualizzare i risultati
 * e gestire la cronologia delle ricerche effettuate.
 */
public class CercaAreaGeograficaFrame extends JFrame implements ActionListener {
    // CAMPI
	public ServerCM server;
	
	private static PaginaIniziale paginaIniziale;
	
    private final JTextField searchField;
    private final JTextField latitudeField;
    private final JTextField longitudeField;
    private final JTextArea historyArea;

    private final JButton searchButton;
    private final JButton clearButton;
    private final JButton homeButton;
    private final JButton paginaInizialeButton;

    private final JPanel searchPanel;
    private final JPanel buttonPanel;
    private final JPanel historyPanel;
    private final JScrollPane scrollPane;

    private DatabaseManager databaseManager;
    
    private final ArrayList<String> searchHistory;

    /**
     * Costruttore della classe 
     * 
     * @param databaseManager oggetto per gestire le operazioni di database
     */
    public CercaAreaGeograficaFrame(DatabaseManager databaseManager, PaginaIniziale paginaIniziale) {
        /*if (databaseManager == null) {
            try {
                databaseManager = new DatabaseManager(); // Inizializza con il costruttore predefinito
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore di connessione al database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }

            this.databaseManager = databaseManager;
        */

    	if (databaseManager == null) {
            try {
            	ClientCM.writer.println("getCredentials");
                try {
                	String username = (String) ClientCM.in.readObject();
                	System.out.println(username);
					String password = (String) ClientCM.in.readObject();
					System.out.println(password);
					Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ClimateMonitoring", username, password);
					this.databaseManager = new DatabaseManager(connection);
                } catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} // Inizializza con il costruttore predefinito
        } else {
            this.databaseManager = databaseManager;
        }
    	
    	if(paginaIniziale == null) {
    		CercaAreaGeograficaFrame.paginaIniziale = new PaginaIniziale();
    	}else {
    		CercaAreaGeograficaFrame.paginaIniziale = paginaIniziale;
    	}
    	
        // Impostazioni del frame
        setTitle("Cerca Area Geografica");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello per la ricerca per denominazione
        searchPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.add(new JLabel("Nome o Stato:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchPanel.add(new JLabel("Latitudine:"));
        latitudeField = new JTextField(10);
        searchPanel.add(latitudeField);

        searchPanel.add(new JLabel("Longitudine:"));
        longitudeField = new JTextField(10);
        searchPanel.add(longitudeField);

        add(searchPanel, BorderLayout.NORTH);

        // Pannello per i pulsanti
        buttonPanel = new JPanel();
        searchButton = new JButton("Cerca");
        clearButton = new JButton("Cancella Cronologia");
        homeButton = new JButton("Torna alla Home");
        paginaInizialeButton = new JButton("Torna alla Pagina Iniziale");

        homeButton.addActionListener(this);

        searchButton.addActionListener(this);
        clearButton.addActionListener(this);
        homeButton.addActionListener(this);
        paginaInizialeButton.addActionListener(this);

        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(paginaInizialeButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Pannello per la cronologia di ricerca
        historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Cronologia Ricerca"));

        historyArea = new JTextArea(10, 40);
        historyArea.setEditable(false);
        scrollPane = new JScrollPane(historyArea);
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        add(historyPanel, BorderLayout.SOUTH);

        searchHistory = new ArrayList<>();
    }

    /**
     * Cerca aree geografiche per nome o stato
     * 
     * @param searchTerm il termine di ricerca per nome o stato
     */
    

    /**
     * Cerca l'area geografica più vicina a una coppia di coordinate
     * 
     * @param latitude la latitudine della posizione di ricerca
     * @param longitude la longitudine della posizione di ricerca
     */
    

    /**
     * Calcola la distanza tra due coppie di coordinate geografiche
     * 
     * @param lat1 la latitudine della prima posizione
     * @param lon1 la longitudine della prima posizione
     * @param lat2 la latitudine della seconda posizione
     * @param lon2 la longitudine della seconda posizione
     * @return la distanza tra le due posizioni in chilometri
     */
    

    /**
     * Mostra i risultati della ricerca in una finestra
     * 
     * @param risultati la lista di aree geografiche trovate
     */
    private void mostraRisultati(List<AreaGeografica> risultati) {
        if (risultati.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna area trovata", "Risultati Ricerca", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder risultatiString = new StringBuilder();
            for (AreaGeografica area : risultati) {
                risultatiString.append(area.toString()).append("\n");
            }
            JOptionPane.showMessageDialog(this, risultatiString.toString(), "Risultati Ricerca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Aggiorna l'area di testo con la cronologia delle ricerche effettuate
     */
    private void updateHistory() {
        historyArea.setText(""); // Cancella il contenuto dell'area di testo
        for (int i = searchHistory.size() - 1; i >= 0; i--) {
            historyArea.insert(searchHistory.get(i) + "\n", 0);
        }
    }

    /**
     * Cancella la cronologia delle ricerche.
     */
    private void clearSearchHistory() {
        searchHistory.clear();
        updateHistory();
    }

    /**
     * Gestisce gli eventi dei pulsanti dell'interfaccia utente.
     * 
     * @param e l'evento di azione generato dai pulsanti
     */
    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
    	boolean log = LoginFrame.loggato;
        if (e.getSource() == paginaInizialeButton) {
        	dispose();
            new PaginaIniziale().setVisible(true);
        } else if (e.getSource() == homeButton && log == true) {
            new HomeFrame(databaseManager).setVisible(true);
            setVisible(false);
        } else if (e.getSource() == searchButton) {
            String searchTerm = searchField.getText().trim();
            String latString = latitudeField.getText().trim();
            String lonString = longitudeField.getText().trim();
            
			double latitude = 0.0;
			double longitude = 0.0;
            
            if (!searchTerm.isEmpty()) {
            	ClientCM.writer.println("searchByNameOrState");
            	ClientCM.writer.println(searchTerm);
            	ClientCM.writer.println("getRisultati");
                List<AreaGeografica> risultati = null;
				try {
					risultati = (List<AreaGeografica>) ClientCM.in.readObject();
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
                mostraRisultati(risultati);
                searchHistory.add("Ricerca per nome/stato: " + searchTerm);
                updateHistory();
            } else if (!latString.isEmpty() && !lonString.isEmpty()) {
                //server.searchByCoordinates(latitude, longitude);
            	latitude = Double.parseDouble(latString);
            	longitude = Double.parseDouble(lonString);
            	ClientCM.writer.println("searchByCoordinates");
            	ClientCM.writer.println(latitude);
				ClientCM.writer.println(longitude);
                AreaGeografica closestArea = null;
				try {
					closestArea = (AreaGeografica) ClientCM.in.readObject();
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
                if (closestArea != null) {
                    mostraRisultati(List.of(closestArea));
                    searchHistory.add("Ricerca per coordinate: Latitudine=" + latitude + ", Longitudine=" + longitude);
                } else {
                    JOptionPane.showMessageDialog(this, "Nessuna area trovata per le coordinate.", "Risultati Ricerca", JOptionPane.INFORMATION_MESSAGE);
                }
                updateHistory();
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci un termine di ricerca o delle coordinate.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == clearButton) {
            clearSearchHistory();
        } else if (log == false) {
        	JOptionPane.showMessageDialog(null, "Per accedere a questa funzione devi essere registrato.", "Attenzione", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo principale per avviare l'applicazione.
     * 
     * @param args argomenti della riga di comando
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseManager dbManager = new DatabaseManager();
                new CercaAreaGeograficaFrame(dbManager, paginaIniziale).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore di connessione al database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}