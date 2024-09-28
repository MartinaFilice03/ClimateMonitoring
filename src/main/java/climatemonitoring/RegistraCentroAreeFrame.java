// Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)

package climatemonitoring;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Questa classe rappresenta una finestra per la registrazione di un centro di monitoraggio e delle aree di interesse.
 * Consente all'utente di inserire i dettagli del centro e delle aree di interesse, e li salva nel database.
 */
public class RegistraCentroAreeFrame extends JFrame {
    private JTextField nomeCentroField;
    private JTextField viaField;
    private JTextField numeroCivicoField;
    private JTextField capField;
    private JTextField comuneField;
    private JTextField provinciaField;
    private JTextArea nomeAreaArea;

    private static String usernameAutenticato;  // Mantenuto il campo usernameAutenticato

    private JButton registraButton;
    private JButton homeButton;

    private JPanel panel;

    private DatabaseManager databaseManager;

    /**
     * Costruttore per creare una finestra di registrazione con il gestore del database e l'username autenticato
     * 
     * @param databaseManager il gestore del database da utilizzare
     * @param usernameAutenticato il nome utente autenticato
     */
    public RegistraCentroAreeFrame(DatabaseManager databaseManager, String usernameAutenticato) {
        this.databaseManager = databaseManager;
        this.usernameAutenticato = usernameAutenticato;  // Inizializzato il campo usernameAutenticato

        setTitle("Registra Centro Monitoraggio e Aree di Interesse");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    /**
     * Inizializza i componenti dell'interfaccia utente.
     */
    private void initComponents() {
        panel = new JPanel(new GridLayout(11, 2));

        panel.add(new JLabel("Nome Centro Monitoraggio:"));
        nomeCentroField = new JTextField();
        panel.add(nomeCentroField);

        panel.add(new JLabel("Via/Piazza:"));
        viaField = new JTextField();
        panel.add(viaField);

        panel.add(new JLabel("Numero Civico:"));
        numeroCivicoField = new JTextField();
        panel.add(numeroCivicoField);

        panel.add(new JLabel("CAP:"));
        capField = new JTextField();
        panel.add(capField);

        panel.add(new JLabel("Comune:"));
        comuneField = new JTextField();
        panel.add(comuneField);

        panel.add(new JLabel("Provincia:"));
        provinciaField = new JTextField();
        panel.add(provinciaField);

        panel.add(new JLabel("Nome Area di Interesse:"));
        nomeAreaArea = new JTextArea();
        panel.add(new JScrollPane(nomeAreaArea));

        registraButton = new JButton("Registra");
        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientCM.writer.println("registraCentroAree");
				ClientCM.writer.println(nomeCentro);
				ClientCM.writer.println(via);
				ClientCM.writer.println(numeroCivico);
				ClientCM.writer.println(cap);
				ClientCM.writer.println(comune);
				ClientCM.writer.println(provincia);
				ClientCM.writer.println(nomeArea);
            }
        });
        panel.add(registraButton);

        homeButton = new JButton("Torna alla Home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeFrame(databaseManager).setVisible(true);
                setVisible(false);
            }
        });
        panel.add(homeButton);

        add(panel);
    }

    /**
     * Registra il centro di monitoraggio e le aree di interesse nel database.
     * Verifica il formato dei dati e gestisce le eccezioni SQL.
     */
    String nomeCentro = nomeCentroField.getText().trim();
    String via = viaField.getText().trim();
    String numeroCivico = numeroCivicoField.getText().trim();
    String cap = capField.getText().trim();
    String comune = comuneField.getText().trim();
    String provincia = provinciaField.getText().trim();
    String nomeArea = nomeAreaArea.getText().trim(); // Nome dell'area di interesse
    
    

    /**
     * Salva i dati del centro di monitoraggio nel database.
     * 
     * @param centro il centro di monitoraggio da salvare
     * @throws SQLException se si verifica un errore durante l'inserimento nel database
     */
    
}