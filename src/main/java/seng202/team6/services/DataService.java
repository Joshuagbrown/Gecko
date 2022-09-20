package seng202.team6.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.io.CsvImporter;
import seng202.team6.models.Station;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.StationDao;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Service class to handle accessing and storing the necessary information.
 * @author Philip Dolbel
 */
public class DataService {
    private static final Logger log = LogManager.getLogger();
    private final StationDao dao = new StationDao();


    /**
     * Loads required data from the provided CSV file. Implements the CsvImporter class to
     * read the file and then adds the station information to the station dao.
     *
     * @param file file to retrieve necessary data from
     */
    public void loadDataFromCsv(File file) {
        try {
            CsvImporter csvImporter = new CsvImporter();
            List<Station> stations = csvImporter.readFromFile(file);
            for (Station station : stations) {
                dao.add(station);
            }

        } catch (Exception e) {
            log.error(e);
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
                sb.append(line + System.lineSeparator());
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
     * Update all the with the sql query.
     * @param sql the sql query.
     * @return list of the stations that match the sql query.
     */
    public List<Station> fetchAllData(String sql) {
        return dao.getAll(sql);
    }


    public Station getStationById(int id) {
        return dao.getOne(id);
    }
}