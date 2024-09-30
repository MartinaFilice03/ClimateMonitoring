Iacopo Casalini - n. Matricola 753132 
Filice Martina - n. Matricola 752916 
Radice Samuele - n. Matricola 753722

Questo documento contiene i principali script SQL che consentono la creazione della base di dati e le principali query SQL che consentono il funzionamento sei servizi dati dalla piattaforma.
SCRIPT SQL PER LA CREAZIONE DELLE TABELLE
1.Tabella CoordianteMonitoraggio
Questa tabella memorizza le informazioni sulle città, inclusi anche i dettagli
geografici delle varie città
  CREATE TABLE "CoordinateMonitoraggio" {
    geoname_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    ascii_name VARCHAR(255),
    country_code VARCHAR(3),
    country_name VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
  };

Descrizione dei campi:
- geoname_id = Identifica univocamente la città (chiave primaria); name  Nome della città;
- ascii_name = Nome della città in formato ASCII;
- country_code = Codice del paese; 
- country_name = Nome del paese; 
- latitude = Latitudine;
- longitude = Longitudine;

2. Tabella CentriMonitoraggio
Questa tabella memorizza i dettagli sui centri di monitoraggio, comprese le
informazioni sullʼindirizzo
  CREATE TABLE "CentriMonitoraggio" {
    NomeCittà VARCHAR(255), PRIMARY KEY,
    NomeArea VARCHAR(255),
    ViaPiazza VARCHAR(255),
    NumeroCivico VARCHAR(10),
    CAP VARCHAR(5),
    Comune VARCHAR(255),
    Provincia VARCHAR(255)
  };

Descrizione dei campi:
- NomeCittà = Nome della città (chiave primaria); NomeArea  Nome dellʼarea del centro;
- ViaPiazza = Via o piazza del centro; NumeroCivico  Numero civico;
- CAP = Codice di avviamento postale;
- Comune = Nome del comune;
- Provincia = Nome della provincia;

3. Tabella OperatoriRegistrati
Questa tabella contiene le informazioni sugli operatori registrati nel sistema
 CREATE TABLE "OperatoriRegistrati" {
    username VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255),
    cognome VARCHAR(255),
    codice_fiscale VARCHAR(16),
    email VARCHAR(255),
    password VARCHAR(255),
    centro_monitoraggio VARCHAR(255) REFERENCES "CentriMonitoraggio"(NomeCittà)
 };

Descrizione dei campi:
- username = Nome utente (chiave primaria);
- nome = Nome;
- cognome = Cognome;
- codice_fiscale = Codice fiscale dellʼutente;
- email = Email dellʼutente;
- password = Password impostata dallʼutente;
- centro_monitoraggio = Nome della città del centro di monitoraggio (chiave esterna)

4. Tabella ParametriClimatici
Questa tabella memorizza i dati climatici rilevati dai centri di monitoraggio
  CREATE TABLE "ParametriClimatici" {
    id SERIAL PRIMARY KEY,
    Centro_monitoraggio VARCHAR(255) REFERENCES "CentriMonitoraggio"(NomeCittà),
    AreaInteresse VARCHAR(255),
    Data_rilevazione TIMESTAMP WITH TIME ZONE,
    Temperatura DOUBLE PRECISION,
    Umidità DOUBLE PRECISION,
    PressioneAtmosferica DOUBLE PRECISION,
    VelocitàVento DOUBLE PRECISION,
    Precipitazioni INTEGER,
    Altitudine_dei_ghiacci INTEGER,
    Massa_dei_ghiacci INTEGER,
    Note TEXT
  };

Descrizione dei campi:
- id = Identificatore unico del record (chive primaria);
- Centro_monitoraggio = Nome della città del centro di monitoraggio (chiave esterna);
- AreaInteresse = Area di interesse per la rilevazione; Data_rilevazione  Data e ora della rilevazione; Temperatura  Temperatura rilevata;
- Umidità = Umidità rilevata;
- PressioneAtmosferica = Pressione atmosferica rilevata;
- VelocitàVento = Velocità del vento rilevata;
- Precipitazioni = Quantità di precipitazioni misurate;
- Altitudine_dei_ghiacci = Altitudine rilevata del ghiaccio, misurata probabilmente in metri;
- Massa_dei_ghiacci = Massa del ghiaccio, presumibilmente misurata in kg o tonnellate;
- Note = Note aggiunte dallʼoperatore;

QUERY SQL PER LE FUNZIONI DELLA PIATTAFORMA
1. Query di inserimento delle città = questa query inserisce una nuova città nella tabella CoordinateMonitoraggio
    String query = INSERT INTO "CoordinateMonitoraggio" (geoname_id, name, ascii_name, country_code, country_name, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?, ?);

2. Query di verifica username esistente = questa query verifica se uno username esiste già nella tabella OperatoriRegistrati
    String query = SELECT COUNT(*) FROM "OperatoriRegistrati" WHERE "username" = ?;

3. Query di login = questa query verifica le credenziali dellʼutente nella tabella OperatoriRegistrati
    String query = SELECT * FROM "OperatoriRegistrati" WHERE "username" = ? AND "password" = ? 

4. Query di inserimento dei dati dellʼoperatore = questa query inserisce i dati di un operatore nella tabella OperatoriRegistrati
    String query = INSERT INTO "OperatoriRegistrati" (username, nome, cognome, codice_fiscale, email, password, centro_monitoraggio) VALUES (?, ?, ?, ?, ?, ?, ?);

5. Query di inserimento dei parametri climatici = questa query inserisce i parametri climatici nella tabella ParametriClimatici
    String query = INSERT INTO "ParametriClimatici" (Centro_monitoraggio, AreaInteresse, Data_rilevazione, Temperatura, Umidità, PressioneAtmosferica, VelocitàVento, Precipitazioni, Altitudine_dei_ghiacci, Massa_dei_ghiacci, Note) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

6. Query di recupero dei centri di monitoraggio per un utente = questa query recupera i centri di monitoraggio associati a un dato utente
    String query = SELECT NomeCentroMonitoraggio FROM "CentriMonitoraggio" JOIN "OperatoriRegistrati" 
    ON "CentriMonitoraggio".NomeCentroMonitoraggio = "OperatoriRegistrati".centro_monitoraggio 
    WHERE "OperatoriRegistrati".username = ?;