// Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)

package climatemonitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Questa classe rappresenta una finestra di login per l'autenticazione degli utenti.
 * Consente agli utenti di inserire un nome utente e una password e verifica le credenziali tramite il {@code DatabaseManager}.
 * Se l'autenticazione ha successo, apre la finestra principale dell'applicazione; altrimenti, mostra un messaggio di errore.
 */
public class LoginFrame extends JFrame implements ActionListener {
	public static boolean loggato = false;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel panel;
    private JPanel buttonPanel;
    private JLabel titleLabel;
    private JButton indietroButton;
    private JButton loginButton;
    private DatabaseManager databaseManager;

    /**
     * Costruttore per creare una finestra di login con il gestore del database
     * 
     * @param databaseManager il gestore del database da utilizzare
     */
    public LoginFrame(DatabaseManager databaseManager) {
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
                } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Inizializza con il costruttore predefinito
        } else {
            this.databaseManager = databaseManager;
        }
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Pannello per il titolo
        titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Pannello per i campi di input
        panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        panel.add(passwordField);
        add(panel, BorderLayout.CENTER);

        // Pannello per i bottoni
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        indietroButton = new JButton("Indietro");
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PaginaIniziale(); // Riapre la finestra principale quando si clicca su "Indietro"
            }
        });
        buttonPanel.add(indietroButton);

        // Aggiungi il buttonPanel al frame
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Centra la finestra sullo schermo
        setVisible(true);
    }

    /**
     * Gestisce gli eventi di clic sui bottoni.
     * 
     * @param e l'evento di clic
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        ClientCM.writer.println("login");
		ClientCM.writer.println(username);
		ClientCM.writer.println(password);
		Object autenticato;
		boolean a = false;
		try {
			autenticato = ClientCM.in.readObject();
			a = (boolean) autenticato;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (a) {
		    JOptionPane.showMessageDialog(this, "Accesso consentito!");
		    loggato = true;
		    // Dopo il login avvenuto con successo, passa alla HomeFrame
		    HomeFrame homeFrame = new HomeFrame(databaseManager);
		    homeFrame.setVisible(true);
		    dispose(); // Chiudi la finestra di login dopo il login
		} else {
		    JOptionPane.showMessageDialog(this, "Accesso non consentito. Riprova.", "Errore", JOptionPane.ERROR_MESSAGE);
		    usernameField.setText("");
		    passwordField.setText("");
		}
    }

    /**
     * Metodo main per avviare l'applicazione e mostrare la finestra di login.
     */
    public static void main(String[] args) {
    	String username = ServerCM.dbUsername;
    	String password = ServerCM.dbPassword;
        SwingUtilities.invokeLater(() -> {
            try {
                // Assicurati che i parametri siano corretti
                DatabaseManager dbManager = new DatabaseManager("jdbc:postgresql://localhost:5432/ClimateMonitoring", username, password);
                new LoginFrame(dbManager);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Impossibile connettersi al database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}