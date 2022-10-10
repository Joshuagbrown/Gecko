PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS stations (
    stationId INTEGER PRIMARY KEY AUTOINCREMENT,
    userId INTEGER DEFAULT -1 NOT NULl,
    lat REAL NOT NULL,
    long REAL NOT NULL,
    name TEXT NOT NULL,
    operator TEXT NOT NULL,
    owner TEXT NOT NULL,
    address TEXT NOT NULL,
    timeLimit INTEGER NOT NULL,
    is24Hours INTEGER NOT NULL,
    numberOfCarparks INTEGER NOT NULL,
    carparkCost INTEGER NOT NULL,
    chargingCost INTEGER NOT NULL,
    hasTouristAttraction INTEGER NOT NULL,
    FOREIGN KEY(userId) REFERENCES users(userId)
);

CREATE TABLE IF NOT EXISTS chargers (
    chargerId INTEGER PRIMARY KEY AUTOINCREMENT,
    stationId INTEGER NOT NULL,
    plugType TEXT NOT NULL,
    wattage INTEGER NOT NULL,
    operative INTEGER NOT NULL,
    FOREIGN KEY(stationId) REFERENCES stations(stationId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS vehicles (
    vehicleId INTEGER PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    make TEXT NOT NULL,
    model TEXT NOT NULL,
    plugType TEXT NOT NULL,
    year INTEGER NOT NULL,
    FOREIGN KEY(userId) REFERENCES users(userId)
);

CREATE TABLE IF NOT EXISTS users (
    userId INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    passwordHash BLOB NOT NULL,
    passwordSalt BLOB NOT NULL,
    address TEXT,
    name TEXT
);

CREATE TABLE IF NOT EXISTS journeys (
    journeyId INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS addresses (
    addressId INTEGER PRIMARY KEY AUTOINCREMENT,
    journeyId INTEGER NOT NULL,
    address TEXT NOT NULL,
    addressOrder INTEGER NOT NULL,
    FOREIGN KEY(journeyId) REFERENCES journeys(journeyId) ON DELETE CASCADE
);