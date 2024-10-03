// Autori: Casalini Iacopo (753132, Varese); Filice Martina (752916, Varese) e Radice Samuele (753722, Varese)
package climatemonitoring;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Questa classe rappresenta una finestra che consente all'utente di inserire
 * parametri climatici per un'area di interesse specifica. La finestra include campi per inserire dati come temperatura,
 * umidità, pressione atmosferica e velocità del vento. Verifica anche se l'area di interesse esiste e gestisce
 * l'inserimento dei dati nel database.
 */
public class InserisciParametriClimaticiFrame extends JFrame implements ActionListener {
    private DecimalFormat temperaturaFormat;
    private DecimalFormat umiditaFormat;
    private DecimalFormat pressioneAtmosfericaFormat;
    private DecimalFormat velocitaVentoFormat;

    private JTextField noteField;
    private JTextField tempField;
    private JTextField umidField;
    private JTextField pressField;
    private JTextField ventoField;
    private JTextField areaField;
    private JTextField centroField;
    private JTextField precipitazioniField;
    private JTextField altitudineGhiacciField;
    private JTextField massaGhiacciField;

    private JButton inserisciParametriButton;
    private JButton homeButton;

    private DatabaseManager databaseManager;
    private Set<String> areeMonitoraggio;

    /**
     * Costruttore per creare una finestra con il gestore del database.
     * 
     * @param databaseManager il gestore del database da utilizzare
     * @throws SQLException se si verifica un errore durante l'interazione con il database
     */
    public InserisciParametriClimaticiFrame(DatabaseManager databaseManager) throws SQLException {
        this.databaseManager = databaseManager;

        // Inizializza la lista delle aree di monitoraggio
        //this.areeMonitoraggio = databaseManager.getTutteAreeMonitoraggio();
        ClientCM.writer.println("getTutteAreeMonitoraggio");
        try {
			Set<String> Aree = (Set<String>) ClientCM.in.readObject();
			this.areeMonitoraggio = Aree;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        if (this.areeMonitoraggio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna area di monitoraggio disponibile!", "Errore", JOptionPane.WARNING_MESSAGE);
        }    

        temperaturaFormat = new DecimalFormat("0.0");
        umiditaFormat = new DecimalFormat("0.0");
        pressioneAtmosfericaFormat = new DecimalFormat("0.0");
        velocitaVentoFormat = new DecimalFormat("0.0");

        setTitle("Inserisci Parametri Climatici");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 2));

        add(new JLabel("Note:"));
        noteField = new JTextField();
        add(noteField);

        add(new JLabel("Area di interesse:"));
        areaField = new JTextField();
        add(areaField);
        
        add(new JLabel("Centro di monitoraggio:"));
        centroField = new JTextField();
        add(centroField);

        add(new JLabel("Temperatura (°C):"));
        tempField = new JTextField();
        add(tempField);

        add(new JLabel("Umidità (%):"));
        umidField = new JTextField();
        add(umidField);

        add(new JLabel("Pressione Atmosferica (hPa):"));
        pressField = new JTextField();
        add(pressField);

        add(new JLabel("Velocità Vento (km/h):"));
        ventoField = new JTextField();
        add(ventoField);

        // Nuovi campi aggiunti
        add(new JLabel("Precipitazioni (mm):"));
        precipitazioniField = new JTextField();
        add(precipitazioniField);

        add(new JLabel("Altitudine dei Ghiacci (m):"));
        altitudineGhiacciField = new JTextField();
        add(altitudineGhiacciField);

        add(new JLabel("Massa dei Ghiacci (kg):"));
        massaGhiacciField = new JTextField();
        add(massaGhiacciField);

        inserisciParametriButton = new JButton("Inserisci Parametri");
        inserisciParametriButton.addActionListener(this);
        add(inserisciParametriButton);

        homeButton = new JButton("Torna alla Home");
        homeButton.addActionListener(this);
        add(homeButton);

        setVisible(true);
    }

    /**
     * Gestisce gli eventi di clic sui bottoni
     * @param e l'evento di clic
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inserisciParametriButton) {
            try {
                String note = noteField.getText();
                String areaDiMonitoraggio = areaField.getText().trim();
                String centroMonitoraggio = centroField.getText().trim();
                String temperatura = tempField.getText().trim();
                String tempFormattata = temperatura;
                String umidita = umidField.getText().trim();
                String pressioneAtmosferica = pressField.getText().trim();
                String velocitaVento = ventoField.getText().trim();

                // Nuovi campi aggiunti
                String precipitazioni = precipitazioniField.getText().trim();
                String altitudineGhiacci = altitudineGhiacciField.getText().trim();
                String massaGhiacci = massaGhiacciField.getText().trim();

                LocalDate data = LocalDate.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataRilevazione = data.format(formato);
               
                // Verifica se l'area di monitoraggio esiste
                if (areeMonitoraggio.contains(areaDiMonitoraggio)) {
                    ClientCM.writer.println("inserisciParametriClimatici");
                    ClientCM.writer.println(centroMonitoraggio);
					ClientCM.writer.println(areaDiMonitoraggio);
					ClientCM.writer.println(dataRilevazione);
					ClientCM.writer.println(tempFormattata);
					ClientCM.writer.println(umidita);
					ClientCM.writer.println(pressioneAtmosferica);
					ClientCM.writer.println(velocitaVento);
					ClientCM.writer.println(precipitazioni);
					ClientCM.writer.println(altitudineGhiacci);
					ClientCM.writer.println(massaGhiacci);
					ClientCM.writer.println(note);
					
					JOptionPane.showMessageDialog(this, "Parametri inseriti con successo!");
                } else {
                    JOptionPane.showMessageDialog(this, "L'area di monitoraggio specificata non esiste!", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tutti i campi devono essere compilati!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == homeButton) {
            new HomeFrame(databaseManager).setVisible(true);
            dispose();
        }
    }

    /**
     * Metodo main per avviare l'applicazione e mostrare la finestra per l'inserimento dei parametri climatici.
     * 
     * @param args argomenti della riga di comando
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new InserisciParametriClimaticiFrame(
                        new DatabaseManager()
                ).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}