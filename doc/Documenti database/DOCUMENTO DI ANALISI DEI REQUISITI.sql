Iacopo Casalini - n. Matricola 753132 
Filice Martina - n. Matricola 752916 
Radice Samuele - n. Matricola 753722

Si desideri realizzare unʼapplicazione Client-Server in grado di realizzare ClimateMonitoring: ovvero un programma che consente agli utenti di monitorare il clima in varie città del globo.
Con questo documento possiamo così descrivere i requisiti e il funzionamento di questa applicazione sul monitoraggio climatico.
Il progetto consente nella raccolta dei dati climatici su tutte le città del mondo dove lʼutente, che sia registrato o no, possa effettuare ricerche o visualizzare i dati di una città da lui scelta.
In più sono possibili anche la creazione di nuovi centri di monitoraggio e anche la modifica dei dati climatici, effettuate però solo da operatori che sono registrati sulla piattaforma.
Le principali entità che risultano importanti per il funzionamento del progetto sono:
1. CoordinateMonitoraggio
    geoname_id
    name
    ascii_code
    country_code
    country_name
    latitude
    longitude

2. CentriMonitoraggio
    NomeCittà
    NomeArea
    ViaPiazza
    NumeroCivico
    CAP
    Comune
    Provincia
        
3.OperatoriRegistrati 
    username
    nome
    cognome
    codice_fiscale
    email
    password
    centro_monitoraggio

4. ParametriClimatici: 
    id
    Centro_monitoraggio
    AreaInteresse
    Data_rilevazione
    Temperatura
    Umidità
    PressioneAtmosferica
    VelocitàVento
    Precipitazioni
    Altitudine_dei_ghiacci
    Massa_dei_ghiacci
    
Note
Lʼapplicazione Client-Server deve essere in grado di far registrare gli operatori e che questʼultimi possano modificare i dati climatici di esistenti centri di monitoraggio e possano creare dei nuovi centri di monitoraggio senza nessuna difficoltà.
Deve essere in grado di far effettuare agli utenti non registrati e a quelli registrati, di conseguenza deve essere in grado di far registrare e loggare gli utenti che si vogliono registrare sulla piattaforma senza alcun problema, delle ricerche o semplicemente visualizzare il clima in una determinata città, restituendo a tale utente gli opportuni dati che desiderava.
Lʼapplicazione deve avere uno spazio di memorizzazione ampio e potente, in quanto deve memorizzare una vasta quantità di dati di migliaia e migliaia di città presenti in tutto quanto il globo.
Lʼapplicazione deve avere unʼinterfaccia utente che renda la navigazione e lʼutilizzo delle funzioni della piattaforma semplici e facili da intuire e utilizzare, evitando di far mettere in difficoltà lʼutente che vuole utilizzare la piattaforma.
DOCUMENTO DI ANALISI DEI REQUISITI 3
