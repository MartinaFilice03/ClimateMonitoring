//Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)

package climatemonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * La classe DatabaseManager gestisce la connessione e le operazioni sul database per l'applicazione di monitoraggio climatico.
 * Include metodi per la registrazione, autenticazione degli utenti, gestione delle aree di monitoraggio e inserimento dei parametri climatici.
 * Gestisce la connessione al database PostgreSQL tramite JDBC.
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
    private static final String USER = "postgres";
    private static final String PASS = "password";
    
    private Connection connection;

    /**
     * Costruttore che utilizza una connessione esistente.
     * 
     * @param connection Connessione esistente al database.
     */
    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Costruttore che crea una nuova connessione al database usando le credenziali predefinite.
     * 
     * @throws SQLException Se la connessione al database fallisce.
     */
    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Costruttore che crea una nuova connessione al database con URL, username e password personalizzati.
     * 
     * @param dbUrl L'URL del database.
     * @param dbUsername L'username per la connessione al database.
     * @param dbPassword La password per la connessione al database.
     * @throws SQLException Se la connessione al database fallisce.
     */
    public DatabaseManager(String dbUrl, String dbUsername, String dbPassword) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    /**
     * Metodo per verificare se un username esiste già nel database.
     * 
     * @param username L'username da verificare.
     * @return true se l'username esiste, false altrimenti.
     */
    public boolean verificaUsernameEsistente(String username) {
        String query = "SELECT COUNT(*) FROM \"OperatoriRegistrati\" WHERE \"username\" = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante la verifica dell'username: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Metodo per autenticare un utente verificando username e password.
     * 
     * @param username L'username dell'utente.
     * @param password La password dell'utente.
     * @return true se l'utente è autenticato correttamente, false altrimenti.
     * @throws SQLException Se si verifica un errore durante il controllo nel database.
     */
    public boolean login(String username, String password) throws SQLException {
        String query = "SELECT * FROM \"OperatoriRegistrati\" WHERE \"username\" = ? AND \"password\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante il login: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Metodo per inserire i dati di un nuovo operatore nel database.
     * 
     * @param nome Il nome dell'operatore.
     * @param cognome Il cognome dell'operatore.
     * @param codiceFiscale Il codice fiscale dell'operatore.
     * @param email L'email dell'operatore.
     * @param username L'username dell'operatore.
     * @param password La password dell'operatore.
     * @param centroMonitoraggio Il centro di monitoraggio associato all'operatore.
     */
    public void inserisciDati(String nome, String cognome, String codiceFiscale, String email, String username, String password, String centroMonitoraggio) {
        String query = "INSERT INTO \"OperatoriRegistrati\" (nome, cognome, codice_fiscale, email, username, password, centro_monitoraggio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, codiceFiscale);
            statement.setString(4, email);
            statement.setString(5, username);
            statement.setString(6, password);
            statement.setString(7, centroMonitoraggio);
            int righeInserite = statement.executeUpdate();
            if (righeInserite > 0) {
                JOptionPane.showMessageDialog(null, "Dati inseriti correttamente nel database.");
            } else {
                JOptionPane.showMessageDialog(null, "Nessuna riga inserita nel database.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante l'inserimento dei dati nel database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo per verificare se un centro di monitoraggio esiste nel database.
     * 
     * @param centro Il nome del centro di monitoraggio.
     * @return true se il centro esiste, false altrimenti.
     * @throws SQLException Se si verifica un errore durante la verifica nel database.
     */
    public boolean verificaCentroEsistente(String centro) throws SQLException {
        String query = "SELECT COUNT(*) FROM \"CentriMonitoraggio\" WHERE \"nomecittà\" = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, centro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Metodo per inserire i parametri climatici nel database.
     * 
     * @param areaMonitoraggio L'area di monitoraggio.
     * @param dataRilevazione La data e ora della rilevazione.
     * @param temperatura La temperatura rilevata.
     * @param umidita Il livello di umidità.
     * @param pressioneAtmosferica La pressione atmosferica.
     * @param velocitaVento La velocità del vento.
     * @param precipitazioni Le precipitazioni rilevate.
     * @param altitudineGhiacci L'altitudine dei ghiacci rilevata.
     * @param massaGhiacci La massa dei ghiacci rilevata.
     * @param note Note aggiuntive riguardanti i parametri.
     * @throws SQLException Se si verifica un errore durante l'inserimento nel database.
     */
    public void inserisciParametriClimatici(String areaMonitoraggio, long dataRilevazione, 
        double temperatura, double umidita, double pressioneAtmosferica, double velocitaVento, 
        double precipitazioni, double altitudineGhiacci, double massaGhiacci, String note) throws SQLException {
    
        String query = "INSERT INTO \"ParametriClimatici\" (\"centro_monitoraggio\", \"data_rilevazione\", \"temperatura\", \"umidità\", \"pressioneatmosferica\", \"velocitàvento\", \"precipitazioni\", \"altitudine_dei_ghiacci\", \"massa_dei_ghiacci\", \"note\") " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, areaMonitoraggio);
            stmt.setTimestamp(2, new Timestamp(dataRilevazione));
            stmt.setDouble(3, temperatura);
            stmt.setDouble(4, umidita);
            stmt.setDouble(5, pressioneAtmosferica);
            stmt.setDouble(6, velocitaVento);
            stmt.setDouble(7, precipitazioni);
            stmt.setDouble(8, altitudineGhiacci);
            stmt.setDouble(9, massaGhiacci);
            stmt.setString(10, note);
            stmt.executeUpdate();
        }
    }

    /**
     * Metodo per ottenere una lista dei centri di monitoraggio associati a un utente.
     * 
     * @param username L'username dell'utente.
     * @return Lista dei centri di monitoraggio associati all'utente.
     * @throws SQLException Se si verifica un errore durante il recupero dei dati.
     */
    public List<String> getCentriForUser(String username) throws SQLException {
        List<String> centri = new ArrayList<>();
        String query = "SELECT \"nomecittà\" " +
                       "FROM \"CentriMonitoraggio\" " +
                       "JOIN \"OperatoriRegistrati\" ON \"CentriMonitoraggio\".\"NomeCentroMonitoraggio\" = \"OperatoriRegistrati\".\"centro_monitoraggio\" " +
                       "WHERE \"OperatoriRegistrati\".\"username\" = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    centri.add(resultSet.getString("nomecittà"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante il recupero dei centri per l'utente: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return centri;
    }

    /**
     * Metodo per ottenere tutte le aree di monitoraggio.
     * 
     * @return Set contenente tutte le aree di monitoraggio.
     */
    public Set<String> getTutteAreeMonitoraggio() {
        Set<String> aree = new HashSet<>();
        String query = "SELECT \"nomecittà\" FROM \"CentriMonitoraggio\"";
        try (PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                aree.add(resultSet.getString("nomecittà"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante il recupero delle aree di monitoraggio: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return aree;
    }

    /**
     * Metodo per chiudere la connessione al database.
     */
    public void chiudiConnessione() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                JOptionPane.showMessageDialog(null, "Connessione al database Postgres chiusa.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante la chiusura della connessione al database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo per ottenere la connessione al database. Se la connessione è chiusa o nulla, ne crea una nuova.
     * 
     * @return La connessione al database.
     * @throws SQLException Se si verifica un errore durante la connessione.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return connection;
    }

    /**
     * Metodo per chiudere risorse come ResultSet e PreparedStatement.
     * 
     * @param resultSet Il ResultSet da chiudere.
     * @param preparedStatement Il PreparedStatement da chiudere.
     */
    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante la chiusura del ResultSet: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        try {
            if (preparedStatement != null) preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante la chiusura del PreparedStatement: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
