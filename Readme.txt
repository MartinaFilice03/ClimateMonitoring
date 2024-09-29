***********************************************************************************
PROGETTO CLIMATE MONITORING
LABORATORIO B, CORSO DI LAUREA TRIENNALE IN INFORMATICA
UNIVERSITA' DEGLI STUDI DELL'INSUBRIA

PROGETTO REALIZZATO DA:
Casalini Iacopo matricola 753132; Filice Martina matricola 752916 e Radice Samuele matricola 75372.
***********************************************************************************

++CONTENUTI DELL'ARCHIVIO++
	--> ServerCM.jar e SimpleClient-SimpleClient.jar: file jar eseguibile
	--> LABORATORIO B: si trovano i file .java dentro la cartella src/main/java/climatemonitoring, i file .classes per far partire i comandi maven, tutta la documentazione
	--> Database: PostgreSQL/pgAdmin
	--> Interfaccia grafica(GUI): per svolgere la grafica abbiamo utilizzato la java Swing e abbiamo importato anche la java awt
	--> Diagrammi UML utilizzati: abbiamo utilizzato il Class Diagram, lo State Diagram e l'Interaction Diagram che è composto dal Sequence e il Communication Diagram
	--> Design Pattern: alcuni design pattern utilizzati sono l'MVC per la grafica, il Singleton ad esempio per le istanze di Database Manager, l'Observer per gestire la concorrenza... 
	--> Gestione della concorrenza: 
	    1. Observer Pattern: abbiamo utilizzato questo pattern per notificare le modifiche agli oggetti interessati in modo asincrono. La gestione della concorrenza in questo caso riguarda la sincronizzazione delle notifiche per evitare che più notifiche siano gestite contemporaneamente causando problemi di stato inconsistente
	    2. Thread per Gestione delle Connessioni Client: per ogni client che si connette al server, viene creato un nuovo thread attraverso la classe ClientHandler. Questo permette al server di gestire simultaneamente più connessioni client, evitando che una connessione bloccata possa impedire il funzionamento del server
 	    3. Risorsa e chiusura pulita: il metodo chiudiConnessione() del DatabaseManager, invocato alla chiusura del server viene utilizzato per far vedere come le risorse vengono correttamente rilasciate

	--> Comandi Maven:
            1. mvn clean (pulizia del progetto) -> rimuove tutti i file generati durante le precedenti compilazioni, inclusi i file compilati e le directory target. Serve a garantire che il progetto venga ricostruito da zero senza influenze da compilazioni precedenti.
	    2. mvn compile (compilazione del progetto) -> compila il codice sorgente principale del progetto e genera i file .class necessari. Durante questo processo, Maven gestisce automaticamente tutte le dipendenze specificate nel pom.xml.
            3. mvn install (installazione del progetto) -> compila il progetto e copia il file JAR risultante nel repository locale di Maven. In questo modo, il file JAR può essere facilmente utilizzato come dipendenza in altri progetti Maven. Viene utilizzato anche per installare tutte le dipendenze e i plug-in necessari alla build
            4. mvn javadoc:javadoc (generazione della documentazione javadoc) -> genera la documentazione Javadoc per il progetto, includendo tutte le classi pubbliche e le loro descrizioni. La documentazione viene generata nella cartella target/site/apidocs e può essere distribuita insieme al progetto per una migliore comprensione del codice sorgente
            5. mvn package (creazione del pacchetto eseguibile jar) -> esegue la compilazione e crea un file JAR eseguibile nella directory target. Il file JAR risultante include tutte le classi compilate e le risorse necessarie per l'esecuzione del programma. È questo il file che può essere distribuito e utilizzato dagli utenti finali
            6. mvn clean compile (pulizia e compilazione in un unico comandi) -> combina i comandi di pulizia e compilazione in un unico passaggio. Questo comando è utile per garantire una compilazione pulita del progetto senza la necessità di eseguire due comandi separati
	--> javadoc: documentazione javadoc del progetto
	--> ManualeTecnico.pdf : manuale tecnico
	--> ManualeUtente.pdf : manuale utente

++REQUISITI++
L'applicazione richiede Java JDK 17 e un sistema operativo tra Windows 11 e macOS
NOTA: l'applicazione è stata sviluppata su un Windows 11 e due macOs ed è stata testata su un Windows 11 e su un macOs.
Devi essere registrato al database di supporto all’esecuzione dei servizi della piattaforma CM (Climate Monitoring)

++AVVIARE L'APPLICAZIONE++
Per avviare correttamente l'applicazione "Climate Monitoring", segui i passaggi indicati di seguito:

NB: prima di avviare il client e server nota che questi file si trovano nella cartella target quindi eventualmente con la riga di comando naviga nella directory target per trovare i file.
Ad esempio se ti trovi nella cartella LABORATORIO B dovrai scrivere cd target per poi trovare i file .jar di server e client

1. Avviare il Server: per prima cosa, avvia il server eseguendo il file ServerCM.jar
Quando esegui il server, ti verrà richiesto di inserire le seguenti informazioni relative al database:
- Nome utente del database.
- Password del database.
Dopo aver inserito queste informazioni, il server si avvierà e mostrerà un messaggio di conferma

2. Avviare il Client: una volta che il server è in esecuzione, avvia il client eseguendo il file SimpleClient-SimpleClient.jar
Il client invierà una richiesta al server per avviare l'interfaccia grafica (GUI).
Se la connessione è riuscita, il server risponderà con un messaggio di conferma e verrà avviata la GUI dell'applicazione

3. Utilizzo dell'Applicazione:
Dopo l'avvio della GUI, puoi iniziare a interagire con l'applicazione "Climate Monitoring" utilizzando le funzionalità disponibili