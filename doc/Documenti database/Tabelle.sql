CREATE TABLE "CoordinateMonitoraggio" (
    geoname_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    ascii_name VARCHAR(255),
    country_code VARCHAR(3),
    country_name VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

CREATE TABLE "CentriMonitoraggio" (
    NomeCittà VARCHAR(255) PRIMARY KEY,
    NomeArea VARCHAR(255),
    ViaPiazza VARCHAR(255),
    NumeroCivico VARCHAR(10),
    CAP VARCHAR(10),
    Comune VARCHAR(255),
    Provincia VARCHAR(255)
);

CREATE TABLE "OperatoriRegistrati" (
    username VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(255),
    cognome VARCHAR(255),
    codice_fiscale VARCHAR(16),
    email VARCHAR(255),
    password VARCHAR(255),
    centro_monitoraggio VARCHAR(255) REFERENCES "CentriMonitoraggio"(NomeCittà)
);

CREATE TABLE "ParametriClimatici" (
    id SERIAL PRIMARY KEY,
    Centro_monitoraggio VARCHAR(255) REFERENCES "CentriMonitoraggio"(NomeCittà),
    AreaInteresse VARCHAR(255),  -- Rimosso il constraint su questa colonna
    Data_rilevazione TIMESTAMP WITH TIME ZONE,
    Temperatura DOUBLE PRECISION,
    Umidità DOUBLE PRECISION,
    PressioneAtmosferica DOUBLE PRECISION,
    VelocitàVento DOUBLE PRECISION,
    Note TEXT
);