//Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)
package climatemonitoring;

import java.io.Serializable;

/**
 * Questa classe rappresenta una regione geografica con un nome, stato, latitudine e longitudine.
 * Fornisce i metodi per ottenere le informazioni sui vari attributi dell'area geografica.
 */
public class AreaGeografica implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
    private String stato;
    private double latitudine;
    private double longitudine;

    /**
     * Costruttore della classe
     * 
     * @param nome il nome dell'area geografica
     * @param stato il nome dello stato in cui si trova l'area geografica
     * @param latitudine la latitudine dell'area geografica
     * @param longitudine la longitudine dell'area geografica
     */
    public AreaGeografica(String nome, String stato, double latitudine, double longitudine) {
        this.nome = nome;
        this.stato = stato;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    /**
     * Restituisce il nome dell'area geografica.
     * 
     * @return il nome dell'area geografica
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il nome dello stato in cui si trova l'area geografica.
     * 
     * @return il nome dello stato
     */
    public String getStato() {
        return stato;
    }

    /**
     * Restituisce la latitudine dell'area geografica.
     * 
     * @return la latitudine dell'area geografica
     */
    public double getLatitudine() {
        return latitudine;
    }

    /**
     * Restituisce la longitudine dell'area geografica.
     * 
     * @return la longitudine dell'area geografica
     */
    public double getLongitudine() {
        return longitudine;
    }

    /**
     * Restituisce una rappresentazione testuale dell'area geografica.
     * 
     * @return una stringa contenente il nome, stato, latitudine e longitudine dell'area geografica
     */
    @Override
    public String toString() {
        return "Nome: " + nome + "\nStato: " + stato + "\nLatitudine: " + latitudine + "\nLongitudine: " + longitudine;
    }
}