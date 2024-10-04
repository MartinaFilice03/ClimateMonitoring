// Autori: Casalini Iacopo (753132, Varese); Filice Martina (752916, Varese) e Radice Samuele (753722, Varese)

package climatemonitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import climatemonitoring.RegistrazioneFrame.UsernameEsistenteException;

public class ServerCM {
	static DatabaseManager databaseManager;
	public static String dbUsername;
	public static String dbPassword;
	public static final String DB_URL = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
	static List<AreaGeografica> risultati = new ArrayList<>();
    static AreaGeografica closestArea = null;
    static Connection connection;
    static ResultSet rs;
    static Socket serverSocket;
    static Socket clientSocket;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;
	
	// COSTRUTTORE
	public ServerCM(DatabaseManager databaseManager) {
		super();
		ServerCM.databaseManager = databaseManager;
	}
	
	public static void main(String[] args) {
    	String dbHost = "localhost";
        String dbPort = "5432";
        String dbName = "ClimateMonitoring";
        dbUsername = JOptionPane.showInputDialog("Inserisci il nome utente:");
        dbPassword = JOptionPane.showInputDialog("Inserisci la password:");
        String dbUrl = String.format("jdbc:postgresql://%s:%s/%s", dbHost, dbPort, dbName);
        
        // CREA E AVVIA SERVER
        if(databaseManager == null) {
        	try {
            	databaseManager = new DatabaseManager(dbUrl, dbUsername, dbPassword);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Errore nell'inizializzazione di DatabaseManager: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
        }

        // CARICA DRIVER JDBC
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Driver JDBC PostgreSQL non trovato: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // GESTIONE SOCKET
        try {
        	try {
        		// Crea un server socket che ascolta sulla porta 5000
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("Server in ascolto sulla porta 5000...");
                JOptionPane.showMessageDialog(null, "Server avviato.");

                // Attende che un client si connetta
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione accettata da " + clientSocket.getInetAddress());

                // Creazione dei flussi di input e output per la comunicazione
                InputStream input = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                
                while(true) {
                	String request = "";
                	request = reader.readLine();
                    System.out.println("ricevuta richiesta: " + request);
                	switch(request) {
                	case "salvaDati":
                		String nome = reader.readLine();
                		String cognome = reader.readLine();
                		String codiceFiscale = reader.readLine();
                		String email = reader.readLine();
                		String username = reader.readLine();
                		String password = reader.readLine();
                		String centroMonitoraggio = reader.readLine();
                		try {
    						salvaDati(nome, cognome, codiceFiscale, email, username, password, centroMonitoraggio);
    					} catch (SQLException e) {
    						e.printStackTrace();
    					} catch (UsernameEsistenteException e) {
    						e.printStackTrace();
    					}
                		break;

                	case "verificaUsernameEsistente":
                		String user = reader.readLine();
                		try {
    						boolean result = verificaUsernameEsistente(user);
    						out.writeObject(result);
    					} catch (SQLException e) {
    						e.printStackTrace();
    					}
                		break;

                	case "login":
                		String u = reader.readLine();
                		String p = reader.readLine();
                		try {
                			boolean risultato = login(u, p);
    						out.writeObject(risultato);
    					} catch (SQLException e) {
    						e.printStackTrace();
    					}
                		break;

                	case "inserisciDati":
                		String n = reader.readLine();
                		String c = reader.readLine();
                		String cf = reader.readLine();
                		String e = reader.readLine();
                		String us = reader.readLine();
                		String pw = reader.readLine();
                		String cm = reader.readLine();
                		inserisciDati(n, c, cf, e, us, pw, cm);
                		break;

                	case "verificaCentroEsistente":
                		String x = reader.readLine();
                		try {
    						boolean y = verificaCentroEsistente(x);
    						out.writeObject(y);
    					} catch (SQLException e1) {
    						e1.printStackTrace();
    					}
                		break;
                		
                	case "getCentriForUser":
                		String a = reader.readLine();
                		try {
    						List<String> list = getCentriForUser(a);
    						out.writeObject(list);
    					} catch (SQLException e1) {
    						e1.printStackTrace();
    					}
                		break;

                	case "inserisciParametriClimatici":
                		String centroM = reader.readLine();
                		String areaMonitoraggio = reader.readLine();
                		String dataRilevazione = reader.readLine();
                		String temperatura = reader.readLine();
                		String umidita = reader.readLine();
                		String pressioneAtmosferica = reader.readLine();
                		String velocitàVento = reader.readLine();
                		String precipitazioni = reader.readLine();
                		String altitudineGhiacci = reader.readLine();
                		String massaGhiacchi = reader.readLine();
                		String note = reader.readLine();
                		try {
    						inserisciParametriClimatici(centroM, areaMonitoraggio, dataRilevazione, temperatura, umidita, pressioneAtmosferica, velocitàVento, precipitazioni, altitudineGhiacci, massaGhiacchi, note);
    					} catch (SQLException e1) {
    						e1.printStackTrace();
    					}
                		break;

                	case "getTutteAreeMonitoraggio":
                		Set<String> Aree = getTutteAreeMonitoraggio();
                		out.writeObject(Aree);
                		break;

                	case "chiudiConnessione":
                		chiudiConnessione();
                		break;

                	case "getConnection":
                		try {
    						Connection connection = getConnection();
    						out.writeObject(connection);
    					} catch (SQLException e1) {
    						e1.printStackTrace();
    					}
                		break;

                	case "isValidCodiceFiscale":
                		String codFiscale = reader.readLine();
                		boolean isValid = isValidCodiceFiscale(codFiscale);
                		out.writeObject(isValid);
                		break;

                	case "isValidEmail":
                		String ema = reader.readLine();
                		boolean isValidema = isValidCodiceFiscale(ema);
                		out.writeObject(isValidema);
                		break;

                	case "registraDati":
                		String nom = reader.readLine();
                		String cognom = reader.readLine();
                		String codiceFiscal = reader.readLine();
                		String emai = reader.readLine();
                		String usernam = reader.readLine();
                		String passwor = reader.readLine();
                		String centroMonitoraggi = reader.readLine();
                		try {
    						registraDati(nom, cognom, codiceFiscal, emai, usernam, passwor, centroMonitoraggi);
    					} catch (SQLException e1) {
    						e1.printStackTrace();
    					} catch (UsernameEsistenteException e1) {
    						e1.printStackTrace();
    					}
                		break;

                	case "searchByNameOrState":
                		String area = reader.readLine();
                		searchByNameOrState(area);
                		break;

                	case "getRisultati":
                		List<AreaGeografica> risultati = getRisultati();
                		out.writeObject(risultati);
                		break;

                	case "searchByCoordinates":
                		String latitude = reader.readLine();
                		System.out.println("ricevuta lat: " + latitude);
                		String longitude = reader.readLine();
                		System.out.println("ricevuta long: " + longitude);
                		double la = Double.parseDouble(latitude);
                		double lo = Double.parseDouble(longitude);
                		searchByCoordinates(la, lo);
                		AreaGeografica closestArea = getClosestArea();
                		ServerCM.out.writeObject(closestArea);
                		break;
						
                	case "getClosestArea":
                		try {
    						AreaGeografica areaG = getClosestArea();
    						out.writeObject(areaG);
    					} catch (IOException e1) {
    						e1.printStackTrace();
    					}
                		break;

                	case "calculateDistance":
                		double lat1 = reader.read();
                		double lon1 = reader.read();
                		double lat2 = reader.read();
                		double lon2 = reader.read();
                		double distance = calculateDistance(lat1, lon1, lat2, lon2);
                		out.writeObject(distance);
                		break;

                	case "connectToDatabase":
                		connectToDatabase();
                		break;

                	case "loadComment":
                		String stringa = reader.readLine();
                		loadComment(stringa);
                		break;

                	case "getResultSet":
                		ResultSet rs = getResultSet();
                		out.writeObject(rs);
                		break;

                	case "saveComment":
                		String areaName = reader.readLine();
                		String comment = reader.readLine();
                		saveComment(areaName, comment);
                		break;

                	case "registraCentroAree":
                		String name = reader.readLine();
                		String via = reader.readLine();
                		String numeroCivico = reader.readLine();
                		String cap = reader.readLine();
                		String comune = reader.readLine();
                		String provincia = reader.readLine();
                		String nomeArea = reader.readLine();
                		try {
    						registraCentroAree(name, via, numeroCivico, cap, comune, provincia, nomeArea);
    					} catch (SQLException e1) {
    						e1.printStackTrace();
    					}
                		break;
						
                	case "salvaCentroNelDatabase":
                		try {
    						CentroMonitoraggio centro = (CentroMonitoraggio) in.readObject();
    						salvaCentroNelDatabase(centro);
    					} catch (ClassNotFoundException | SQLException e1) {
    						e1.printStackTrace();
    					}
                		break;	
	
                	case "getCredentials":
                		out.writeObject(dbUsername);
                		out.writeObject(dbPassword);
                		break;
                	}
                }

    	         // Invia un messaggio al server - writer.println()
    	         // Riceve la risposta dal server - reader.readLine()
        	}finally {
        		// Chiude le risorse
                //clientSocket.close();
                //serverSocket.close();
        	}
            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	// *** METODI REMOTI ***
    public static void salvaDati(String nome, String cognome, String codiceFiscale, String email, String username, String password, String centroMonitoraggio) throws SQLException, UsernameEsistenteException {
        // Controlla se il database è inizializzato
        if (databaseManager != null) {
            databaseManager.inserisciDati(nome, cognome, codiceFiscale, email, username, password, centroMonitoraggio);
            JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!");
        } else {
        	JOptionPane.showMessageDialog(null, "Errore: databaseManager non inizializzato correttamente.");
        }
    }
    
    public static boolean verificaUsernameEsistente(String username) throws SQLException {
    	return databaseManager.verificaCentroEsistente(username);
    }
    
    public static boolean login(String username, String password) throws SQLException{
    	return databaseManager.login(username, password);
    }
    
    public static void inserisciDati(String nome, String cognome, String codiceFiscale, String email, String username, String password, String centroMonitoraggio) {
    	databaseManager.inserisciDati(nome, cognome, codiceFiscale, email, username, password, centroMonitoraggio);
    }
    
    public static boolean verificaCentroEsistente(String centro) throws SQLException{
    	return databaseManager.verificaCentroEsistente(centro);
    }
    
    public static void inserisciParametriClimatici(String centroMonitoraggio, String areaMonitoraggio, String dataRilevazione, 
            String temperatura, String umidita, String pressioneAtmosferica, String velocitaVento, 
            String precipitazioni, String altitudineGhiacci, String massaGhiacci, String note) throws SQLException{
    	databaseManager.inserisciParametriClimatici(centroMonitoraggio, areaMonitoraggio, dataRilevazione, temperatura, umidita, pressioneAtmosferica, velocitaVento, precipitazioni, altitudineGhiacci, massaGhiacci, note);
    }
    
    public static List<String> getCentriForUser(String username) throws SQLException{
    	return databaseManager.getCentriForUser(username);
    }
    
    public static Set<String> getTutteAreeMonitoraggio(){
    	return databaseManager.getTutteAreeMonitoraggio();
    }
    
    public static void chiudiConnessione() {
    	databaseManager.chiudiConnessione();
    }
    
    public static Connection getConnection() throws SQLException{
    	return databaseManager.getConnection(ServerCM.dbUsername, ServerCM.dbPassword);
    }
    
    public static boolean isValidCodiceFiscale(String codiceFiscale) {
        return codiceFiscale.length() == 16;
    }
    
    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.indexOf('@') < email.length() - 1;
    }
    
    public static void registraDati(String nome, String cognome, String codiceFiscale, String email, String username, String password, String centroMonitoraggio) throws SQLException, UsernameEsistenteException {
        if (nome.isEmpty() || cognome.isEmpty() || codiceFiscale.isEmpty() || email.isEmpty() || username.isEmpty()
                || password.isEmpty() || centroMonitoraggio.isEmpty()) {
        	JOptionPane.showMessageDialog(null, "Per favore, completa tutti i campi obbligatori.");
            return;
        }
    
        if (!isValidCodiceFiscale(codiceFiscale)) {
        	JOptionPane.showMessageDialog(null, "Il codice fiscale non è valido.");
            return;
        }
    
        if (!isValidEmail(email)) {
        	JOptionPane.showMessageDialog(null, "L'email non è valida.");
            return;
        }
    
        if (databaseManager.verificaUsernameEsistente(username)) {
        	JOptionPane.showMessageDialog(null, "Username già presente nel database.");
            return;
        }
    
        if (!databaseManager.verificaCentroEsistente(centroMonitoraggio)) {
        	JOptionPane.showMessageDialog(null, "Il centro di monitoraggio non esiste.");
            return;
        }
    
        if (password.length() < 8) {
        	JOptionPane.showMessageDialog(null, "La password deve essere lunga almeno 8 caratteri.");
            return;
        }
    
        databaseManager.inserisciDati(nome, cognome, codiceFiscale, email, username, password, centroMonitoraggio);
        JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    public static void searchByNameOrState(String searchTerm) {
        String query = "SELECT * FROM \"CoordinateMonitoraggio\" WHERE \"name\" LIKE ? OR \"country_name\" LIKE ?";
        
        try (var pst = databaseManager.getConnection(ServerCM.dbUsername, ServerCM.dbPassword).prepareStatement(query)) {
            pst.setString(1, "%" + searchTerm + "%");
            pst.setString(2, "%" + searchTerm + "%");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                AreaGeografica area = new AreaGeografica(
                    rs.getString("name"),
                    rs.getString("country_name"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")
                );
                risultati.add(area);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante la ricerca: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static List<AreaGeografica> getRisultati(){
    	return risultati;
    }
    
    public static void searchByCoordinates(double latitude, double longitude) {
        double closestDistance = Double.MAX_VALUE;
        String query = "SELECT * FROM \"CoordinateMonitoraggio\"";
        
        try (var stmt = databaseManager.getConnection(ServerCM.dbUsername, ServerCM.dbPassword).createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                double areaLat = rs.getDouble("latitude");
                double areaLon = rs.getDouble("longitude");
                double distance = calculateDistance(latitude, longitude, areaLat, areaLon);
                
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestArea = new AreaGeografica(
                        rs.getString("name"),
                        rs.getString("country_name"),
                        areaLat,
                        areaLon
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante la ricerca: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static AreaGeografica getClosestArea() {
    	return closestArea;
    }
    
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    
    public static void connectToDatabase() {
        String url = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
        String username = ServerCM.dbUsername;
        String password = ServerCM.dbPassword;

        try {
            connection = DriverManager.getConnection(url, ServerCM.dbUsername, ServerCM.dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore di connessione al database.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void loadComment(String areaName) {
        String query = "SELECT \"note\" FROM \"ParametriClimatici\" WHERE \"areainteresse\" = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, areaName);
            ResultSet rs = pst.executeQuery();
            } catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Errore durante il caricamento del commento.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static ResultSet getResultSet() {
    	return rs;
    }
    
    public static void saveComment(String areaName, String comment) {
        String query = "UPDATE \"ParametriClimatici\" SET \"note\" = ? WHERE \"areainteresse\" = ?";
        if(connection == null) {
    		try {
				connection = databaseManager.getConnection(ServerCM.dbUsername, ServerCM.dbPassword);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, comment);
            pst.setString(2, areaName);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Nessuna area trovata con il nome specificato.", "Errore", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Commento salvato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante il salvataggio del commento.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void registraCentroAree(String nomeCentro, String via, String numeroCivico, String cap, String comune, String provincia, String nomeArea) throws SQLException {
        CentroMonitoraggio centro = new CentroMonitoraggio(
            nomeCentro,
            nomeArea,
            via,
            numeroCivico,
            cap,
            comune,
            provincia
        );
        
        try {
            System.out.println("Tentativo di salvataggio del centro nel database...");
            salvaCentroNelDatabase(centro);
            JOptionPane.showMessageDialog(null, "Centro registrato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante la registrazione del centro: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void salvaCentroNelDatabase(CentroMonitoraggio centro) throws SQLException {
        String sql = "INSERT INTO \"CentriMonitoraggio\" (NomeCittà, NomeArea, ViaPiazza, NumeroCivico, CAP, Comune, Provincia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseManager.getConnection(ServerCM.dbUsername, ServerCM.dbPassword);  // Usa getConnection() per ottenere la connessione
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, centro.getNomeCitta());
            statement.setString(2, centro.getNomeArea());
            statement.setString(3, centro.getViaPiazza());
            statement.setString(4, centro.getNumeroCivico());
            statement.setString(5, centro.getCap());
            statement.setString(6, centro.getComune());
            statement.setString(7, centro.getProvincia());
            statement.executeUpdate();
        }
    }
  
}