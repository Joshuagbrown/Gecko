package seng202.team6.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import javafx.beans.value.WritableObjectValue;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.io.StationCsvImporter;
import seng202.team6.models.Station;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.StationDao;

/**
 * Service class to handle accessing and storing the necessary information.
 * @author Philip Dolbel.
 */
public class DataService {
    private static final Logger log = LogManager.getLogger();
    private final StationDao dao = new StationDao();


    /**
     * Loads required data from the provided CSV file. Implements the CsvImporter class to
     * read the file and then adds the station information to the station dao.
     *
     * @param file file to retrieve necessary data from.
     */
    public void loadDataFromCsv(File file, WritableObjectValue<Pair<Integer, Integer>> value)
            throws CsvFileException, DatabaseException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        List<Station> stations = stationCsvImporter.readFromFile(file);
        int i = 0;
        for (Station station : stations) {
            dao.add(station);
            value.set(new Pair<>(++i, stations.size()));
        }
    }

    /**
     * Create the table of in the database.
     */
    public void createTables() {
        try {
            InputStream in = getClass().getResourceAsStream("/sql/create.sql");
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader((new InputStreamReader(in)));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            String sql = sb.toString();
            try (Connection conn = DatabaseManager.getInstance().connect();
                 Statement statement = conn.createStatement()) {
                statement.executeUpdate(sql);
            }

        } catch (IOException | SQLException e) {
            log.error("Error loading database initialisation file", e);
        }

    }

    /**
     * Fetch data from the database, either all, or from the sql query.
     * @param sql The sql query to run if exists
     * @return A hashmap of the data returned
     */
    public Map<Integer, Station> fetchAllData(String sql) {
        return dao.getAll(sql);
    }
}