// Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)

package climatemonitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Finestra per la registrazione di un nuovo operatore.
 * Consente all'utente di inserire le proprie informazioni e registrarle nel database.
 */
public class RegistrazioneFrame extends JFrame {
    
	private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField codiceFiscaleField;
    private JTextField emailField;
    private JTextField usernameField;
    private JTextField centroField;
    private JPasswordField passwordField;

    private JButton registratiButton;
    private JButton indietroButton;

    private JPanel contentPane;
    private JPanel buttonPanel;

    private DatabaseManager databaseManager;
    private static PaginaIniziale paginaIniziale;

    /**
     * Costruttore della finestra di registrazione.
     * 
     * @param databaseManager Il gestore del database per interagire con il database.
     * @param paginaIniziale La finestra principale da visualizzare quando si clicca "Indietro" o dopo una registrazione riuscita.
     * @throws IllegalArgumentException Se il databaseManager è null.
     */
    public RegistrazioneFrame(DatabaseManager databaseManager, PaginaIniziale paginaIniziale) {
    	/*
        if (databaseManager == null) {
            throw new IllegalArgumentException("DatabaseManager non può essere null");
        }
        this.databaseManager = databaseManager;
        this.paginaIniziale = paginaIniziale;
*/
    	if (databaseManager == null) {
            try {
				this.databaseManager = new DatabaseManager();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
        setTitle("Registrazione");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPane = new JPanel(new GridLayout(0, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.WHITE);

        contentPane.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        contentPane.add(nomeField);

        contentPane.add(new JLabel("Cognome:"));
        cognomeField = new JTextField(20);
        contentPane.add(cognomeField);

        contentPane.add(new JLabel("Codice Fiscale:"));
        codiceFiscaleField = new JTextField(20);
        contentPane.add(codiceFiscaleField);

        contentPane.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        contentPane.add(emailField);

        contentPane.add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        contentPane.add(usernameField);

        contentPane.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        contentPane.add(passwordField);

        contentPane.add(new JLabel("Centro di monitoraggio:"));
        centroField = new JTextField(20);
        contentPane.add(centroField);

        registratiButton = new JButton("Registrati");
        registratiButton.addActionListener(e -> {
            try {
                salvaDati();
            } catch (SQLException | UsernameEsistenteException ex) {
                ex.printStackTrace();
                showErrorDialog("Errore durante la registrazione: " + ex.getMessage());
            }
        });

        indietroButton = new JButton("Indietro");
        indietroButton.addActionListener(e -> {
            dispose();
            if (paginaIniziale != null) {
                paginaIniziale.setVisible(true);
            }
        });

        buttonPanel = new JPanel(new BorderLayout());
        JPanel indietroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        indietroPanel.add(indietroButton);
        JPanel registratiPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        registratiPanel.add(registratiButton);

        buttonPanel.add(indietroPanel, BorderLayout.WEST);
        buttonPanel.add(registratiPanel, BorderLayout.EAST);

        add(contentPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Salva i dati inseriti nei campi di registrazione nel database.
     * 
     * @throws SQLException Se si verifica un errore durante l'interazione con il database.
     * @throws UsernameEsistenteException Se l'username è già presente nel database.
     */
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
    
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Metodo principale per avviare l'applicazione e visualizzare la finestra di registrazione.
     * 
     * @param args Argomenti della riga di comando.
     */
    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManager(); // Assicurati che questo non lanci eccezioni
            // Qui dovresti usare una finestra principale esistente invece di null
            new RegistrazioneFrame(databaseManager, paginaIniziale);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore durante l'inizializzazione del database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Eccezione lanciata quando si cerca di registrare un username già esistente.
     */
    class UsernameEsistenteException extends Exception {
        public UsernameEsistenteException(String message) {
            super(message);
        }
    }
}