# ** Guida all'Installazione e Configurazione di Maven

## ** 1. Installazione di Maven

### **macOS**
a) Installare Homebrew (se non già presente)

Apri il terminale e inserisci il seguente comando:
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

b) Installare Maven

Una volta installato Homebrew, esegui:
brew install maven

c) Verificare l'installazione

Controlla che Maven sia installato correttamente con:
mvn -version

### **Windows**
a) Scaricare Maven

Vai al sito ufficiale:
- https://maven.apache.org/download.cgi
Scarica l’archivio ZIP con la versione binaria (es: Binary zip archive)

b) Estrarre Maven
Estrai il contenuto in una directory a tua scelta (es: C:\Programmi\Apache\Maven)

c) Impostare le variabili d'ambiente

Vai su:

Pannello di Controllo > Sistema e Sicurezza > Sistema > Impostazioni di sistema avanzate > Variabili d'ambiente
Aggiungi:

M2_HOME: punta alla directory dove hai estratto Maven
(es: C:\Programmi\Apache\Maven)
Aggiungi Maven al PATH: inserisci %M2_HOME%\bin
d) Verificare l’installazione

Apri il Prompt dei Comandi e digita:

mvn -v
Dovresti vedere un output simile a:

Apache Maven 3.x.x (rxxxxxxxxxxxxx)
Java version: 1.8.0_xxx, vendor: Oracle Corporation


2. Creazione e Struttura di un Progetto Maven

a) Struttura consigliata del progetto
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

b) Esempio di file pom.xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>climate-monitoring</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Driver JDBC PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.6.0</version>
        </dependency>
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

3. Comandi Utili di Maven

Comando	Descrizione
mvn clean -> Pulisce i file compilati precedenti
mvn compile -> Compila le classi del progetto
mvn test	-> Compila ed esegue i test (se presenti)
mvn package -> Crea un file .jar nella cartella target
java -jar target/NomeFile.jar -> Esegue il file JAR generato

Puoi anche eseguire il file .jar con doppio clic, e si aprirà la pagina corrispondente al progetto (Server/Client).

⚙4. Creazione di un Progetto Maven da Terminale (Windows/macOS)

Esegui il seguente comando nel terminale o nel Prompt dei Comandi:

mvn archetype:generate \
  -DgroupId=com.example \
  -DartifactId=my-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false

Note:

my-app: è il nome del progetto, modificabile
com.example: è il groupId, modificabile
Gli altri parametri definiscono il tipo di progetto e non vanno cambiati se non necessario
