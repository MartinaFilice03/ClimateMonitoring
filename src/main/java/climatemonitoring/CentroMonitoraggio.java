// Autori: Casalini Iacopo (753132, Varese); Filice Martina (752916, Varese) e Radice Samuele (753722, Varese)

package climatemonitoring;

import java.io.Serializable;

/**
 * Questa classe rappresenta un centro di monitoraggio con tutti i suoi dettagli.
 */
public class CentroMonitoraggio implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String nomeCitta;
    private final String nomeArea;
    private final String viaPiazza;
    private final String numeroCivico;
    private final String cap;
    private final String comune;
    private final String provincia;

    /**
     * Costruttore per inizializzare un centro di monitoraggio.
     * 
     * @param nomeCitta il nome della città
     * @param nomeArea il nome dell'area di monitoraggio
     * @param viaPiazza la via o piazza
     * @param numeroCivico il numero civico
     * @param cap il CAP
     * @param comune il comune
     * @param provincia la provincia
     */
    public CentroMonitoraggio(String nomeCitta, String nomeArea, String viaPiazza, String numeroCivico, String cap, String comune, String provincia) {
        this.nomeCitta = nomeCitta;
        this.nomeArea = nomeArea;
        this.viaPiazza = viaPiazza;
        this.numeroCivico = numeroCivico;
        this.cap = cap;
        this.comune = comune;
        this.provincia = provincia;
    }

    public String getNomeCitta() {
        return nomeCitta;
    }

    public String getNomeArea() {
        return nomeArea;
    }

    public String getViaPiazza() {
        return viaPiazza;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public String getCap() {
        return cap;
    }

    public String getComune() {
        return comune;
    }

    public String getProvincia() {
        return provincia;
    }
}