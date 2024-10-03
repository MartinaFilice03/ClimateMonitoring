// Autori: Casalini Iacopo (753132, Varese); Filice Martina (752916, Varese) e Radice Samuele (753722, Varese)

package climatemonitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Questa rappresenta la finestra principale dell'applicazione.
 * Gestisce le azioni degli utenti e permette di accedere alle diverse funzionalità dell'applicazione.
 */
public class HomeFrame extends JFrame implements ActionListener {
    private JButton inserisciParametriClimaticiButton;
    private JButton cercaAreaGeograficaButton;
    private JButton visualizzaAreaGeograficaButton;
    private JButton registraCentroAreeButton;

    private static PaginaIniziale paginaIniziale;
    
    private JPanel mainPanel;
    private GridBagConstraints gbc;

    private JLabel titleLabel;

    private DatabaseManager databaseManager;
    private String usernameAutenticato;

    /**
     * Costruttore per creare una finestra principale con un gestore del database.
     * Questo costruttore utilizza un valore di default per {@code usernameAutenticato}.
     * 
     * @param databaseManager il gestore del database da utilizzare
     */
    public HomeFrame(DatabaseManager databaseManager) {
        this(databaseManager, null); 
    }

    /**
     * Costruttore per creare una finestra principale con il gestore del database e un username autenticato.
     * 
     * @param databaseManager il gestore del database da utilizzare
     * @param usernameAutenticato lo username dell'utente autenticato (può essere {@code null} se non si desidera fornire un username)
     */
    public HomeFrame(DatabaseManager databaseManager, String usernameAutenticato) {
        this.databaseManager = databaseManager;
        this.usernameAutenticato = usernameAutenticato;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello per il titolo
        titleLabel = new JLabel("Benvenuto", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLUE);
        titleLabel.setPreferredSize(new Dimension(600, 80));
        add(titleLabel, BorderLayout.NORTH);

        // Pannello principale per contenere i bottoni
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLUE);
        add(mainPanel, BorderLayout.CENTER);

        // Configurazione dei bottoni con GridBagConstraints per centrarli
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Bottone Cerca Area Geografica
        cercaAreaGeograficaButton = new JButton("Cerca Area Geografica");
        cercaAreaGeograficaButton.addActionListener(this);
        cercaAreaGeograficaButton.setFont(new Font("Arial", Font.PLAIN, 24));
        cercaAreaGeograficaButton.setPreferredSize(new Dimension(400, 60));
        mainPanel.add(cercaAreaGeograficaButton, gbc);

        // Bottone Inserisci Parametri Climatici
        gbc.gridy++;
        inserisciParametriClimaticiButton = new JButton("Inserisci Parametri Climatici");
        inserisciParametriClimaticiButton.addActionListener(this);
        inserisciParametriClimaticiButton.setFont(new Font("Arial", Font.PLAIN, 24));
        inserisciParametriClimaticiButton.setPreferredSize(new Dimension(400, 60));
        mainPanel.add(inserisciParametriClimaticiButton, gbc);

        // Bottone Visualizza Area Geografica
        gbc.gridy++;
        visualizzaAreaGeograficaButton = new JButton("Visualizza Area Geografica");
        visualizzaAreaGeograficaButton.addActionListener(this);
        visualizzaAreaGeograficaButton.setFont(new Font("Arial", Font.PLAIN, 24));
        visualizzaAreaGeograficaButton.setPreferredSize(new Dimension(400, 60));
        mainPanel.add(visualizzaAreaGeograficaButton, gbc);

        // Bottone Registra Centro Aree
        gbc.gridy++;
        registraCentroAreeButton = new JButton("Registra Centro Aree");
        registraCentroAreeButton.addActionListener(this);
        registraCentroAreeButton.setFont(new Font("Arial", Font.PLAIN, 24));
        registraCentroAreeButton.setPreferredSize(new Dimension(400, 60));
        mainPanel.add(registraCentroAreeButton, gbc);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Gestisce gli eventi di clic sui bottoni.
     * 
     * @param e l'evento di clic
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == inserisciParametriClimaticiButton) {
                new InserisciParametriClimaticiFrame(databaseManager);
                setVisible(false);
            } else if (e.getSource() == cercaAreaGeograficaButton) {
                new CercaAreaGeograficaFrame(databaseManager, paginaIniziale).setVisible(true);
                setVisible(false);
            } else if (e.getSource() == visualizzaAreaGeograficaButton) {
            	Connection connection = databaseManager.getConnection(ServerCM.dbUsername, ServerCM.dbPassword);
                new VisualizzaAreaGeograficaFrame(databaseManager, paginaIniziale, connection).setVisible(true);
                setVisible(false);
            } else if (e.getSource() == registraCentroAreeButton) {
                new RegistraCentroAreeFrame(databaseManager, usernameAutenticato).setVisible(true);
                setVisible(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Potresti sostituire questo con una finestra di dialogo
            JOptionPane.showMessageDialog(this, "Errore SQL: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo main per avviare l'applicazione e mostrare la finestra principale.
     * 
     * @param args gli argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseManager dbManager = null;
            try {
                dbManager = new DatabaseManager();
                String usernameAutenticato = "exampleUsername"; // Sostituisci con il valore reale
                new HomeFrame(dbManager, usernameAutenticato).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore di connessione al database: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}