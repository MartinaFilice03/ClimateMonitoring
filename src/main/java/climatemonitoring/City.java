//Autori: Casalini Iacopo (753132, Varese), Filice Martina (752916, Varese), Radice Samuele (753722, Varese)
package climatemonitoring;

/**
 * Questa classe rappresenta una città con informazioni dettagliate come ID geoname, nome,
 * codice paese, nome del paese e coordinate geografiche.
 */
public class City {
    private String geonameID;
    private String name;
    private String asciiName;
    private String countryCode;
    private String countryName;
    private double latitude;
    private double longitude;

    /**
     * Costruttore per inizializzare un oggetto
     * 
     * @param geonameID l'ID geoname della città
     * @param name il nome della città
     * @param asciiName il nome della città in formato ASCII
     * @param countryCode il codice del paese della città
     * @param countryName il nome del paese della città
     * @param latitude la latitudine della città
     * @param longitude la longitudine della città
     */
    public City(String geonameID, String name, String asciiName, String countryCode, String countryName, double latitude, double longitude) {
        this.geonameID = geonameID;
        this.name = name;
        this.asciiName = asciiName;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Restituisce l'ID geoname della città.
     * 
     * @return l'ID geoname della città
     */
    public String getGeonameID() { 
        return geonameID; 
    
    }

    /**
     * Imposta l'ID geoname della città.
     * 
     * @param geonameID l'ID geoname da impostare
     */
    public void setGeonameID(String geonameID) { 
        this.geonameID = geonameID; 
    }

    /**
     * Restituisce il nome della città.
     * 
     * @return il nome della città
     */
    public String getName() { 
        return name; 
    }

    /**
     * Imposta il nome della città.
     * 
     * @param name il nome da impostare
     */
    public void setName(String name) { 
        this.name = name; 
    }

    /**
     * Restituisce il nome della città in formato ASCII.
     * 
     * @return il nome ASCII della città
     */

    public String getAsciiName() { 
        return asciiName; 
    }

    /**
     * Imposta il nome della città in formato ASCII.
     * 
     * @param asciiName il nome ASCII da impostare
     */
    public void setAsciiName(String asciiName) { 
        this.asciiName = asciiName; 
    }

    /**
     * Restituisce il codice del paese della città.
     * 
     * @return il codice del paese
     */
    public String getCountryCode() { 
        return countryCode; 
    }

    /**
     * Imposta il codice del paese della città.
     * 
     * @param countryCode il codice del paese da impostare
     */
    public void setCountryCode(String countryCode) { 
        this.countryCode = countryCode; 
    }

    /**
     * Restituisce il nome del paese della città.
     * 
     * @return il nome del paese
     */
    public String getCountryName() { 
        return countryName; 
    }

    /**
     * Imposta il nome del paese della città.
     * 
     * @param countryName il nome del paese da impostare
     */
    public void setCountryName(String countryName) { 
        this.countryName = countryName; 
    }

    /**
     * Restituisce la latitudine della città.
     * 
     * @return la latitudine della città
     */
    public double getLatitude() { 
        return latitude; 
    }

    /**
     * Imposta la latitudine della città.
     * 
     * @param latitude la latitudine da impostare
     */
    public void setLatitude(double latitude) { 
        this.latitude = latitude; 
    }

    /**
     * Restituisce la longitudine della città.
     * 
     * @return la longitudine della città
     */
    public double getLongitude() { 
        return longitude;
    }

    /**
     * Imposta la longitudine della città.
     * 
     * @param longitude la longitudine da impostare
     */
    public void setLongitude(double longitude) { 
        this.longitude = longitude; 
    }

    /**
     * Restituisce una rappresentazione stringa del nome della città.
     * 
     * @return il nome della città
     */
    @Override
    public String toString() {
        return name;
    }
}