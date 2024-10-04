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

    private DatabaseManager databaseManager;
    private Connection connection;

    // Formattazione per i dati climatici
    private DecimalFormat temperaturaFormat = new DecimalFormat("#.##");

    /**
     * Costruttore della classe VisualizzaAreaGeograficaFrame.
     * 
     * @param databaseManager il gestore del database utilizzato per recuperare e salvare i dati
     * @throws SQLException se si verifica un errore durante la connessione al database
     */
    public VisualizzaAreaGeograficaFrame(DatabaseManager databaseManager, PaginaIniziale paginaIniziale, Connection connection) throws SQLException {
        this.databaseManager = (databaseManager == null) ? initializeDatabaseManager() : databaseManager;
        this.paginaIniziale = (paginaIniziale == null) ? new PaginaIniziale() : paginaIniziale;
        this.connection = connection != null ? connection : this.databaseManager.getConnection();

        setupUI(); // Metodo che configura l'interfaccia grafica

        setVisible(true);
    }

    /**
     * Configura l'interfaccia grafica della finestra.
     */
    private void setupUI() {
        setTitle("Visualizza Area Geografica");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Pannello per l'inserimento del nome dell'area
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Nome Area:"));
        areaNameField = new JTextField(20);
        inputPanel.add(areaNameField);
        add(inputPanel, BorderLayout.NORTH);

        // Area di testo per mostrare le informazioni
        infoTextArea = new JTextArea(15, 30);
        infoTextArea.setEditable(false);
        add(new JScrollPane(infoTextArea), BorderLayout.CENTER);

        // Pannello per i commenti
        JPanel commentPanel = new JPanel(new BorderLayout());
        commentTextArea = new JTextArea(5, 30);
        commentTextArea.setBorder(BorderFactory.createTitledBorder("Aggiungi Commento"));
        commentPanel.add(new JScrollPane(commentTextArea), BorderLayout.CENTER);
        add(commentPanel, BorderLayout.EAST);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        visualizzaButton = new JButton("Visualizza");
        homeButton = new JButton("Torna alla Home");
        saveCommentButton = new JButton("Salva Commento");
        paginaInizialeButton = new JButton("Torna alla Pagina Iniziale");

        // Aggiungi ActionListener ai pulsanti
        visualizzaButton.addActionListener(this);
        saveCommentButton.addActionListener(this);
        homeButton.addActionListener(this);
        paginaInizialeButton.addActionListener(this);

        buttonPanel.add(visualizzaButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(saveCommentButton);
        buttonPanel.add(paginaInizialeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Inizializza il DatabaseManager nel caso in cui non sia fornito al costruttore.
     */
    private DatabaseManager initializeDatabaseManager() throws SQLException {
        try {
            ClientCM.writer.println("getCredentials");
            String username = (String) ClientCM.in.readObject();
            String password = (String) ClientCM.in.readObject();
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ClimateMonitoring", username, password);
            return new DatabaseManager(conn);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new SQLException("Errore durante l'inizializzazione del DatabaseManager.");
        }
    }

    /**
     * Visualizza le informazioni dell'area selezionata.
     */
    private void visualizzaArea() {
        String areaName = areaNameField.getText().trim();
        if (areaName.isEmpty()) {
            infoTextArea.setText("Inserisci un nome di area valido.");
            return;
        }

        try {
            displayAreaInformation(areaName);
            loadComment(areaName);
        } catch (SQLException e) {
            showError("Errore durante il caricamento dell'area: " + e.getMessage());
        }
    }

    /**
     * Carica e visualizza le informazioni dell'area.
     */
    private void displayAreaInformation(String areaName) throws SQLException {
        String query = "SELECT * FROM \"CoordinateMonitoraggio\" WHERE \"name\" = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, areaName);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Informazioni per l'area: ").append(rs.getString("name")).append("\n");
                    sb.append("GeoName ID: ").append(rs.getString("geoname_id")).append("\n");
                    sb.append("Latitudine: ").append(rs.getString("latitude")).append("\n");
                    sb.append("Longitudine: ").append(rs.getString("longitude")).append("\n");
                    infoTextArea.setText(sb.toString());
                    displayClimateData(areaName);
                } else {
                    infoTextArea.setText("Area non trovata.");
                }
            }
        }
    }

    /**
     * Mostra i dati climatici associati a un'area.
     */
    private void displayClimateData(String areaName) throws SQLException {
        String climateQuery = "SELECT * FROM \"ParametriClimatici\" WHERE \"areainteresse\" = ?";
        try (PreparedStatement pst = connection.prepareStatement(climateQuery)) {
            pst.setString(1, areaName);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder("\nDati Climatici:\n");
                    do {
                        sb.append("Data Rilevazione: ").append(rs.getString("data_rilevazione")).append("\n");
                        sb.append("Temperatura: ").append(temperaturaFormat.format(rs.getDouble("temperatura"))).append(" °C\n");
                        sb.append("Umidità: ").append(rs.getDouble("umidita")).append(" %\n");
                        sb.append("Pressione: ").append(rs.getDouble("pressione")).append(" hPa\n");
                        // Aggiungi altre informazioni climatiche qui
                    } while (rs.next());
                    infoTextArea.append(sb.toString());
                } else {
                    infoTextArea.append("Nessun dato climatico disponibile.");
                }
            }
        }
    }

    /**
     * Carica il commento per l'area specificata.
     */
    private void loadComment(String areaName) {
        ClientCM.writer.println("loadComment");
        ClientCM.writer.println(areaName);
        try {
            ResultSet rs = (ResultSet) ClientCM.in.readObject();
            if (rs != null && rs.next()) {
                commentTextArea.setText(rs.getString("note"));
            } else {
                commentTextArea.setText("");
            }
        } catch (Exception e) {
            showError("Errore durante il caricamento del commento.");
        }
    }

    /**
     * Salva il commento inserito dall'utente.
     */
    private void salvaCommento() {
        String areaName = areaNameField.getText().trim();
        String comment = commentTextArea.getText().trim();

        if (areaName.isEmpty() || comment.isEmpty()) {
            showError("Inserisci un nome di area e un commento validi.");
            return;
        }

        try {
            ClientCM.writer.println("saveComment");
            ClientCM.writer.println(areaName);
            ClientCM.writer.println(comment);
            showMessage("Commento salvato con successo.");
        } catch (Exception e) {
            showError("Errore durante il salvataggio del commento.");
        }
    }

    /**
     * Mostra un messaggio di errore in una finestra di dialogo.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Mostra un messaggio di successo in una finestra di dialogo.
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Ritorna alla pagina iniziale.
     */
    private void tornaPaginaIniziale() {
        PaginaIniziale.setVisible(true);
        dispose();
    }

    /**
     * Azione per tornare alla home.
     */
    private void tornaHome() {
        JOptionPane.showMessageDialog(this, "Tornando alla home...");
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == visualizzaButton) {
            visualizzaArea();
        } else if (source == saveCommentButton) {
            salvaCommento();
        } else if (source == homeButton) {
            tornaHome();
        } else if (source == paginaInizialeButton) {
            tornaPaginaIniziale();
        }
    }
}
