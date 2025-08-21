# 🌍 ClimateMonitoring

Guida completa all’installazione, configurazione e utilizzo del progetto **ClimateMonitoring**, sviluppato in Java con Maven.

## 🛠️ 1. Installazione di Maven

### 🔸 macOS

1. **Installare Homebrew** (se non già presente):

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

2. **Installare Maven**:

```bash
brew install maven
```

3. **Verificare l’installazione**:

```bash
mvn -version
```

### 🔹 Windows

1. **Scaricare Maven**:  
   Vai al sito ufficiale: https://maven.apache.org/download.cgi  
   Scarica il file `.zip` binario (es. _Binary zip archive_).

2. **Estrai l’archivio** in una cartella (es: `C:\Programmi\Apache\Maven`).

3. **Configura le variabili d'ambiente**:
   - `M2_HOME`: punta alla directory dove hai estratto Maven (es: `C:\Programmi\Apache\Maven`)
   - Aggiungi `%M2_HOME%\bin` alla variabile `PATH`

4. **Verifica l’installazione**:

```bash
mvn -v
```

## 📁 2. Struttura del Progetto

```
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
```

## 🧾 3. Esempio di `pom.xml`

```xml
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
```

## 🔧 4. Comandi Utili Maven

| Comando                         | Descrizione                                           |
|--------------------------------|-------------------------------------------------------|
| `mvn clean`                    | Pulisce i file compilati precedenti                  |
| `mvn compile`                  | Compila le classi del progetto                       |
| `mvn test`                     | Esegue i test (se presenti)                          |
| `mvn package`                  | Crea il file `.jar` nella cartella `target`          |
| `java -jar target/NomeFile.jar` | Esegue il file `.jar` generato                       |

⚠️ Puoi anche eseguire il `.jar` con doppio clic: si aprirà la finestra dell’applicazione (server/client).

## 🚀 5. Creazione di un Nuovo Progetto Maven da Terminale

Esegui:

```bash
mvn archetype:generate \
  -DgroupId=com.example \
  -DartifactId=my-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

- `my-app`: nome del progetto (modificabile)
- `com.example`: package di base
- Gli altri parametri definiscono il tipo di progetto Maven

## 👥 6. Collaboratori
- Martina Filice
- Iacopo Luigi Antonio Casalini
- Samuele Radice

## 📌 Note Finali

- Assicurati di avere **Java 17** installato.
- Se vedi errori nel build GitHub, verifica la configurazione Maven o disattiva il CI.
