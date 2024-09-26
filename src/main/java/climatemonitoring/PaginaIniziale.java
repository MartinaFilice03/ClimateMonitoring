// Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)

package src.main.java.climatemonitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PaginaIniziale extends JFrame implements ActionListener, Serializable {
    // CAMPI
    private JButton registrazioneButton;
    private JButton registratiButton;
    private JButton loginButton;
    private JButton CercaAreaGeograficaButton;
    private JButton visualizzaAreaButton;
    
    private static Connection connection;

    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceFiscaleField;
    private JTextField emailField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField centroField;

    private DatabaseManager databaseManager;

    private JPanel imagePanel;
    private JPanel mainPanel;
    private JPanel buttonPanel;

    private ImageIcon imageIcon;
    private JLabel imageLabel;
    private JLabel titleLabel;

    // Variabile statica per controllare se la connessione è stata già effettuata
    private static boolean isDatabaseConnected = false;

    // COSTRUTTORE
    public PaginaIniziale() {
        try {
            // Se la connessione non è stata effettuata, connettiti al database e mostra il messaggio
            if (!isDatabaseConnected) {
                String url = "jdbc:postgresql://localhost:5432/ClimateMonitoring";
                ClientCM.writer.println("getCredentials");
                String username = (String) ClientCM.in.readObject();
                String password = (String) ClientCM.in.readObject();
                
    
                Connection connection = DriverManager.getConnection(url, username, password);
                databaseManager = new DatabaseManager(connection);
    
                JOptionPane.showMessageDialog(this, "Connessione al server stabilita");
                isDatabaseConnected = true;
            }
    
            //setTitle("Climate Monitoring Program");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(400, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
    
            titleLabel = new JLabel("Climate Monitoring Program", SwingConstants.CENTER);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setOpaque(true);
            titleLabel.setBackground(Color.BLUE);
            add(titleLabel, BorderLayout.NORTH);
    
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.BLUE);
    
            //imageIcon = new ImageIcon(getClass().getResource("/climate.png"));
            imageIcon = new ImageIcon("C:/Users/hp/ClimateMonitoring/src/main/resources/climate.png");
            imageLabel = new JLabel(imageIcon);
            imagePanel = new JPanel();
            imagePanel.setBackground(Color.BLUE);
            imagePanel.add(imageLabel);
            add(imagePanel, BorderLayout.CENTER);
    
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(Color.BLUE);
    
            registrazioneButton = new JButton("Registrazione");
            registrazioneButton.addActionListener(this);
            buttonPanel.add(registrazioneButton);
    
            registratiButton = new JButton("Registrati");
            registratiButton.addActionListener(this);
            buttonPanel.add(registratiButton);
            registratiButton.setVisible(false);
    
            loginButton = new JButton("Login");
            loginButton.addActionListener(this);
            buttonPanel.add(loginButton);
    
            CercaAreaGeograficaButton = new JButton("Cerca Area Geografica");
            CercaAreaGeograficaButton.addActionListener(this);
            buttonPanel.add(CercaAreaGeograficaButton);
    
            visualizzaAreaButton = new JButton("Visualizza Area Geografica");
            visualizzaAreaButton.addActionListener(this);
            buttonPanel.add(visualizzaAreaButton);
    
            add(buttonPanel, BorderLayout.SOUTH);
    
            pack();
            setVisible(true);
    
            nomeField = new JTextField(20);
            cognomeField = new JTextField(20);
            codiceFiscaleField = new JTextField(20);
            emailField = new JTextField(20);
            usernameField = new JTextField(20);
            passwordField = new JTextField(20);
            centroField = new JTextField(20);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore durante l'inizializzazione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registrazioneButton) {
            new RegistrazioneFrame(databaseManager, this);  // Passa l'istanza corrente di PaginaIniziale
            setVisible(false); // Nascondi la finestra corrente
        } else if (e.getSource() == loginButton) {
            new LoginFrame(databaseManager);
            setVisible(false); // Nascondi la finestra corrente
        } else if (e.getSource() == CercaAreaGeograficaButton) {
            new CercaAreaGeograficaFrame(databaseManager, this).setVisible(true);
            setVisible(false); // Nascondi la finestra corrente
        } else if (e.getSource() == visualizzaAreaButton) {
            try {
                new VisualizzaAreaGeograficaFrame(databaseManager, this, connection).setVisible(true);
                setVisible(false); // Nascondi la finestra corrente
            } catch (SQLException ex) {
            }
        }
    }

    private void salvaDati() throws SQLException, UsernameEsistenteException {
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String codiceFiscale = codiceFiscaleField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String centroMonitoraggio = centroField.getText();

        // Controlla se il database è inizializzato
        if (databaseManager != null) {
            //databaseManager.inserisciDati(nome, cognome, codiceFiscale, email, username, password, centroMonitoraggio);
            ClientCM.writer.println("salvaDati");
            ClientCM.writer.println(nome);
            ClientCM.writer.println(cognome);
            ClientCM.writer.println(codiceFiscale);
            ClientCM.writer.println(email);
            ClientCM.writer.println(username);
            ClientCM.writer.println(password);
            ClientCM.writer.println(centroMonitoraggio);
            
        	JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!");
            // Torna alla pagina principale
            new PaginaIniziale(); // Crea una nuova istanza della pagina iniziale
            setVisible(false);
        } else {
            showErrorDialog("Errore: databaseManager non inizializzato correttamente.");
        }
    }

    // Metodo per mostrare un messaggio di errore
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaIniziale());
    }
}

class UsernameEsistenteException extends Exception {
    public UsernameEsistenteException(String message) {
        super(message);
    }
}