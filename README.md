1. Installazione di Maven su macOS
Su macOS, puoi installare Maven utilizzando Homebrew, un gestore di pacchetti per macOS

a) Installare Homebrew (se non è già installato): apri il terminale e inserisci il seguente comando per installare Homebrew:
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

b)Installare Maven: una volta installato Homebrew, puoi installare Maven con il comando 
brew install maven
- Per verificare l'installazione di Maven e controllare la versione installata, esegui:
mvn -version

2. Configurazione del Progetto Maven
a) Struttura del progetto: assicurati che il tuo progetto segua la seguente struttura standard di Maven:
project-root/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── laboratoriob/
│   │   │       └── PaginaIniziale.java
│   │   └── resources/
│   │       └── climate.png
│   └── test/
│       └── java/
├── pom.xml

b) File pom.xml: il file pom.xml è il cuore del progetto Maven. Dovrebbe contenere le dipendenze e le configurazioni del progetto. Un esempio di pom.xml potrebbe essere:
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>climate-monitoring</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- PostgreSQL JDBC Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version>
        </dependency>
        <!-- Altre dipendenze qui -->
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

3. Comandi Maven per Compilare ed Eseguire il Progetto
a) Pulizia del Progetto

Prima di compilare, puoi eseguire un comando di pulizia per rimuovere tutti i file compilati in precedenza: mvn clean

b) Compilazione del Progetto
Per compilare il progetto, esegui il comando: mvn test
Questo comando compilerà tutte le classi Java nel progetto

c) Esecuzione dei Test (Opzionale)
Se hai dei test nel progetto, puoi eseguirli con: mvn test

d) Creazione del Pacchetto (Jar)
Per creare un file JAR eseguibile del progetto:
mvn package
Questo comando creerà un file JAR nella cartella target del progetto.

e) Esecuzione del Progetto
Per eseguire il progetto, puoi usare il comando seguente, specificando il file JAR generato (ad esempio):
java -jar target/ServerCM.jar

Oppure puoi fare il doppio click direttamente sul file jar e si apre la pagina di Server/Client in base a quale file .jar è stato svolto il doppio click

1. Installazione di Maven su Windows

a) Sul sito ufficiale https://maven.apache.org/download.cgi scegliere la versione binaria di Maven (ad es., "Binary zip archive") e scaricare il file .zip.

b) Estrarre il contenuto della cartella compressa in una directory a vostra scelta.

c) Andare su Pannello di controllo > Sistema e sicurezza > Sistema > Impostazioni di sistema avanzate > Variabili d'ambiente, e creare due nuove variabili d'ambiente:
    I) La prima chiamata "M2_HOME" che punta alla directory appena estratta
    II) La seconda chiamata "PATH" che punta alla medesima cartella del punto I

d) Verificare l'installazione aprendo il Prompt dei Comandi e digitare "mvn -v", se avete fatto tutto correttamente il risultato dovrebbe essere simile a questo:
    Apache Maven 3.x.x (rxxxxxxxxxxxxx)
    Maven home: C:\Programmi\Apache\Maven
    Java version: 1.8.0_xxx, vendor: Oracle Corporation
    Java home: C:\Programmi\Java\jdk1.8.0_xxx\jre
    Default locale: en_US, platform encoding: Cp1252
    OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

2. Creazione di un progetto Maven
Per creare un progetto Maven si può agire dal Prompt dei Comandi e digitare la stringa seguente:
    mvn archetype:generate -DgroupId=com.example -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

    in cui:
    I) my-app è il nome del vostro progetto che potete cambiare
    II) com.example è l'identificativo del gruppo del progetto in formato dominio (anch'esso è modificabile)
    III) gli altri parametri sono descrittivi dell'azione che state compiendo e modificarli è sconsigliato