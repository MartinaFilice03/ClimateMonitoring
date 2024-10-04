// Autori: Casalini Iacopo (753132, Varese); Filice Martina (752916, Varese) e Radice Samuele (753722, Varese)

package climatemonitoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * La classe DatabaseManager gestisce tutte le operazioni di interazione con il database
 * per il sistema di monitoraggio climatico. Permette la gestione di utenti, inserimento di parametri climatici,
 * verifica di centri di monitoraggio e aree di interesse.
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
    private static String USER = ServerCM.dbUsername;
    private static String PASS = ServerCM.dbPassword;
    
    private Connection connection;

    // Costruttore con connessione passata
    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    // Costruttore senza connessione passata
    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection(DB_URL, ServerCM.dbUsername, ServerCM.dbPassword);
    }

    // Costruttore con URL, username e password personalizzati
    public DatabaseManager(String dbUrl, String dbUsername, String dbPassword) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    // Metodo per verificare se un username è già presente nel database
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

    // Metodo per verificare le credenziali dell'utente nel database
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

    // Metodo per inserire i dati dell'operatore nel database
    public void inserisciDati(String nome, String cognome, String codiceFiscale, String email, String username, String password, String centroMonitoraggio) {
        String query = "INSERT INTO \"OperatoriRegistrati\" (nome, cognome, codice_fiscale, email, username, password, centro_monitoraggio) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
            statement.setString(3, codiceFiscale);
            statement.setString(4, email);
            statement.setString(5, username);
            statement.setString(6, password);
            statement.setString(7, centroMonitoraggio); // Nome città
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

    // Metodo per verificare se un centro esiste
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

    // Metodo per inserire i parametri climatici
    public void inserisciParametriClimatici(String centroMonitoraggio, String areaMonitoraggio, String dataRilevazione, 
        String temperatura, String umidita, String pressioneAtmosferica, String velocitaVento, 
        String precipitazioni, String altitudineGhiacci, String massaGhiacci, String note) throws SQLException {
    
        String query = "INSERT INTO \"ParametriClimatici\" (\"centro_monitoraggio\",\"areainteresse\", \"data_rilevazione\", \"temperatura\", \"umidità\", \"pressioneatmosferica\", \"velocitàvento\", \"precipitazioni\", \"altitudine_dei_ghiacci\", \"massa_dei_ghiacci\", \"note\") " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, centroMonitoraggio);
            stmt.setString(2, areaMonitoraggio);
            stmt.setString(3, dataRilevazione);
            stmt.setString(4, temperatura);
            stmt.setString(5, umidita);
            stmt.setString(6, pressioneAtmosferica);
            stmt.setString(7, velocitaVento);
            stmt.setString(8, precipitazioni);
            stmt.setString(9, altitudineGhiacci);
            stmt.setString(10, massaGhiacci);
            stmt.setString(11, note);
            stmt.executeUpdate();
        }
    }
    
    // Metodo per recuperare i centri monitoraggio per un dato utente
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
    
    // Metodo per ottenere tutte le aree di monitoraggio
    public Set<String> getTutteAreeMonitoraggio() {
        Set<String> aree = new HashSet<>();
        String query = "SELECT \"name\" FROM \"CoordinateMonitoraggio\"";
        try (PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                aree.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Errore durante il recupero delle aree di monitoraggio: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return aree;
    }

    // Metodo per chiudere la connessione al database
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

    // Metodo per ottenere la connessione al database
    public Connection getConnection(String USER, String PASS) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return connection;
    }

    // Metodo per chiudere le risorse ResultSet e PreparedStatement
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

    public Connection getConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
    }
}