PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS stations (
    stationId INTEGER PRIMARY KEY AUTOINCREMENT,
    lat REAL NOT NULL,
    long REAL NOT NULL,
    name TEXT NOT NULL,
    objectId INTEGER UNIQUE NOT NULL,
    operator TEXT NOT NULL,
    owner TEXT NOT NULL,
    address TEXT NOT NULL,
    timeLimit INTEGER NOT NULL,
    is24Hours INTEGER NOT NULL,
    numberOfCarparks INTEGER NOT NULL,
    carparkCost INTEGER NOT NULL,
    chargingCost INTEGER NOT NULL,
    hasTouristAttraction INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS chargers (
    chargerId INTEGER PRIMARY KEY AUTOINCREMENT,
    stationId INTEGER NOT NULL,
    plugType TEXT NOT NULL,
    wattage INTEGER NOT NULL,
    operative INTEGER NOT NULL,
    FOREIGN KEY(stationId) REFERENCES stations(stationId)
)