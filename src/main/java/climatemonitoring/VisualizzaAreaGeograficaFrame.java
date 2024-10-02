// Autori: Casalini Iacopo (753132, Varese); Filice Martina (752916, Varese) e Radice Samuele (753722, Varese)

package climatemonitoring;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

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

import src.main.java.climatemonitoring.HomeFrame;

/**
 * Questa classe rappresenta una finestra per visualizzare informazioni su un'area geografica
 * e gestire commenti associati. La finestra consente di inserire il nome di un'area geografica,
 * visualizzare le sue informazioni e i dati climatici, e salvare commenti per l'area.
 */
public class VisualizzaAreaGeograficaFrame extends JFrame implements ActionListener {

    // CAMPI
    private JTextField areaNameField;
    private JTextArea infoTextArea;
    private JTextArea commentTextArea;

    private static PaginaIniziale paginaIniziale;
    
    private JButton visualizzaButton;
    private JButton homeButton;
    private JButton saveCommentButton;
    private JButton paginaInizialeButton;

    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JPanel inputPanel;
    private JPanel commentPanel;
    private static Connection connection;

    private DatabaseManager databaseManager;

    private DecimalFormat temperaturaFormat = new DecimalFormat("#.##");
    private DecimalFormat umiditaFormat = new DecimalFormat("#.##");
    private DecimalFormat pressioneAtmosfericaFormat = new DecimalFormat("#.##");
    private DecimalFormat velocitaVentoFormat = new DecimalFormat("#.##");
    private DecimalFormat precipitazioniFormat = new DecimalFormat("#.##");
    private DecimalFormat altitudineGhiacciFormat = new DecimalFormat("#.##");
    private DecimalFormat massaGhiacciFormat = new DecimalFormat("#.##");

    /**
     * Costruttore della classe VisualizzaAreaGeograficaFrame.
     * 
     * @param databaseManager il gestore del database utilizzato per recuperare e salvare i dati
     * @throws SQLException se si verifica un errore durante la connessione al database
     */
    public VisualizzaAreaGeograficaFrame(DatabaseManager databaseManager, PaginaIniziale paginaIniziale, Connection connection) throws SQLException {
    	
    	if (databaseManager == null) {
            try {
            	ClientCM.writer.println("getCredentials");
                try {
                	String username = (String) ClientCM.in.readObject();
                	System.out.println(username);
					String password = (String) ClientCM.in.readObject();
					System.out.println(password);
					Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ClimateMonitoring", username, password);
					this.databaseManager = new DatabaseManager(conn);
                } catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} // Inizializza con il costruttore predefinito
        } else {
            this.databaseManager = databaseManager;
        }
    	
    	if(paginaIniziale == null) {
    		this.paginaIniziale = new PaginaIniziale();
    	}else {
    		this.paginaIniziale = paginaIniziale;
    	}
    	
        setTitle("Visualizza Area Geografica");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Pannello per l'inserimento del nome dell'area
        inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Nome Area:"));
        areaNameField = new JTextField(20);
        inputPanel.add(areaNameField);
        add(inputPanel, BorderLayout.NORTH);

        // Area di testo per mostrare le informazioni
        infoTextArea = new JTextArea(15, 30);
        infoTextArea.setEditable(false);
        scrollPane = new JScrollPane(infoTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Pannello per i commenti
        commentPanel = new JPanel(new BorderLayout());
        commentTextArea = new JTextArea(5, 30);
        commentTextArea.setBorder(BorderFactory.createTitledBorder("Aggiungi Commento"));
        commentPanel.add(new JScrollPane(commentTextArea), BorderLayout.CENTER);

        // Pannello per i pulsanti
        buttonPanel = new JPanel();
        visualizzaButton = new JButton("Visualizza");
        homeButton = new JButton("Torna alla Home");
        saveCommentButton = new JButton("Salva Commento");
        paginaInizialeButton = new JButton("Torna alla Pagina Iniziale");

        visualizzaButton.addActionListener(e -> {
            String areaName = areaNameField.getText().trim();
            if (areaName.isEmpty()) {
                infoTextArea.setText("Inserisci un nome di area valido.");
            } else {
                try {
					displayAreaInformation(areaName);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                ClientCM.writer.println("loadComment");
                ClientCM.writer.println(areaName);
                
                ClientCM.writer.println("getResultSet");
                ResultSet rs = null;
				try {
					rs = (ResultSet) ClientCM.in.readObject();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                try {
					if (rs != null && rs.next()) {
					    commentTextArea.setText(rs.getString("note"));
					} else {
					    commentTextArea.setText("");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            
            }
        });

        
        saveCommentButton.addActionListener(this);
        
        
        homeButton.addActionListener(this);
        paginaInizialeButton.addActionListener(this);

        buttonPanel.add(visualizzaButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(saveCommentButton);
        buttonPanel.add(paginaInizialeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        add(commentPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null); // Centra la finestra sullo schermo

        // Connessione al database
        ClientCM.writer.println("connectToDatabase");

        setVisible(true);
    }

    /**
     * Carica e visualizza il commento associato a una determinata area geografica.
     * 
     * @param areaName il nome dell'area geografica per cui caricare il commento
     */
    

    /**
     * Salva un commento per una determinata area geografica.
     * 
     * @param areaName il nome dell'area geografica per cui salvare il commento
     * @param comment il commento da salvare
     * @throws SQLException 
     */
    

    private void displayAreaInformation(String areaName) throws SQLException {
        // Query per recuperare le informazioni dell'area
        String areaQuery = "SELECT \"geoname_id\", \"name\", \"ascii_name\", \"country_code\", \"country_name\", \"latitude\", \"longitude\" FROM \"CoordinateMonitoraggio\" WHERE \"name\" = ?";
        
    	if(connection == null) {
    		this.connection = databaseManager.getConnection(ServerCM.dbUsername, ServerCM.dbPassword);
    	}else {
    		this.connection = connection;
    	}

        try (PreparedStatement pstArea = connection.prepareStatement(areaQuery)) {
            pstArea.setString(1, areaName);
            try (ResultSet rsArea = pstArea.executeQuery()) {
                if (rsArea.next()) {
                    // Recupera i dati dal ResultSet
                    String geonameId = rsArea.getString("geoname_id");
                    String name = rsArea.getString("name");
                    String asciiName = rsArea.getString("ascii_name");
                    String countryCode = rsArea.getString("country_code");
                    String countryName = rsArea.getString("country_name");
                    String latitude = rsArea.getString("latitude");
                    String longitude = rsArea.getString("longitude");
                    
                    // Costruisci il messaggio da visualizzare
                    StringBuilder sb = new StringBuilder();
                    sb.append("Informazioni per l'area: ").append(name).append("\n\n");
                    sb.append("GeoName ID: ").append(geonameId).append("\n");
                    sb.append("Nome: ").append(name).append("\n");
                    sb.append("Nome ASCII: ").append(asciiName).append("\n");
                    sb.append("Codice Paese: ").append(countryCode).append("\n");
                    sb.append("Nome Paese: ").append(countryName).append("\n");
                    sb.append("Latitudine: ").append(latitude).append("\n");
                    sb.append("Longitudine: ").append(longitude).append("\n");
                    
                    // Aggiungi il prospetto dei parametri climatici
                    sb.append("\nDati Climatici:\n");
                    String climateQuery = "SELECT \"data_rilevazione\", \"temperatura\", \"umidità\", \"pressioneatmosferica\", \"velocitàvento\", \"precipitazioni\", \"altitudine_dei_ghiacci\", \"massa_dei_ghiacci\" FROM \"ParametriClimatici\" WHERE \"areainteresse\" = ?";
                    
                    try (PreparedStatement pstClimate = connection.prepareStatement(climateQuery)) {
                        pstClimate.setString(1, areaName);
                        try (ResultSet rsClimate = pstClimate.executeQuery()) {
                            if (rsClimate.next()) {
                                do {
                                    sb.append("Data Rilevazione: ").append(rsClimate.getString("data_rilevazione")).append("\n");
                                    sb.append("Temperatura: ").append(rsClimate.getString("temperatura")).append(" °C\n");
                                    sb.append("Umidità: ").append(rsClimate.getString("umidità")).append(" %\n");
                                    sb.append("Pressione Atmosferica: ").append(rsClimate.getString("pressioneatmosferica")).append(" hPa\n");
                                    sb.append("Velocità del Vento: ").append(rsClimate.getString("velocitàvento")).append(" km/h\n");
                                    sb.append("Precipitazioni: ").append(rsClimate.getString("precipitazioni")).append(" mm\n");
                                    sb.append("Altitudine dei Ghiacci: ").append(rsClimate.getString("altitudine_dei_ghiacci")).append(" m\n");
                                    sb.append("Massa dei Ghiacci: ").append(rsClimate.getString("massa_dei_ghiacci")).append(" tonnellate\n");
                                    sb.append("\n");
                                } while (rsClimate.next());
                            } else {
                                sb.append("Nessun dato climatico trovato per questa area.");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        sb.append("Errore durante il recupero dei dati climatici.");
                    }
                    
                    infoTextArea.setText(sb.toString());
                } else {
                    infoTextArea.setText("Area non trovata.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            infoTextArea.setText("Errore durante la lettura dei dati dal database.");
        }
    }
    

    /**
     * Gestisce gli eventi di azione generati dai pulsanti della finestra.
     * 
     * @param e l'evento di azione da gestire
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	boolean log = LoginFrame.loggato;
        Object source = e.getSource();
        if (source == homeButton && log == true) {
            new HomeFrame(databaseManager).setVisible(true);
            setVisible(false);
        } else if (source == paginaInizialeButton) {
        	dispose();
        	new PaginaIniziale().setVisible(true);
        }else if(source == saveCommentButton) {
        	ClientCM.writer.println("saveComment");
            ClientCM.writer.println(areaNameField.getText().trim());
            ClientCM.writer.println(commentTextArea.getText().trim());
        }else if (log == false) {
        	JOptionPane.showMessageDialog(null, "Per accedere a questa funzione devi essere registrato.", "Attenzione", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Punto di ingresso dell'applicazione, che avvia la finestra di visualizzazione dell'area geografica.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseManager dbManager = new DatabaseManager();
                try {
					new VisualizzaAreaGeograficaFrame(dbManager, paginaIniziale, connection).setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore di connessione al database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}