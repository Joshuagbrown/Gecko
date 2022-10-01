package seng202.team6.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.WritableObjectValue;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.CsvFileException;
import seng202.team6.exceptions.CsvLineException;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.io.StationCsvImporter;
import seng202.team6.models.Station;
import seng202.team6.models.User;
import seng202.team6.repository.DatabaseManager;
import seng202.team6.repository.FilterBuilder;
import seng202.team6.repository.StationDao;
import seng202.team6.repository.UserDao;

/**
 * Service class to handle accessing and storing the necessary information.
 * @author Philip Dolbel.
 */
public class DataService {
    private static final Logger log = LogManager.getLogger();
    private final StationDao dao = new StationDao();

    private final UserDao userDao = new UserDao();


    /**
     * Loads required data from the provided CSV file. Implements the CsvImporter class to
     * read the file and then adds the station information to the station dao.
     *
     * @param file file to retrieve necessary data from.
     */
    public List<CsvLineException> loadDataFromCsv(
            File file,
            WritableObjectValue<Pair<Integer, Integer>> value
    ) throws CsvFileException, DatabaseException {
        StationCsvImporter stationCsvImporter = new StationCsvImporter();
        List<CsvLineException> errors = new ArrayList<>();
        List<Station> stations = stationCsvImporter.readFromFile(file, errors);
        int i = 0;
        for (Station station : stations) {
            dao.add(station);
            value.set(new Pair<>(++i, stations.size()));
        }
        return errors;
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
     * Fetch all data from the database.
     */
    public Map<Integer, Station> fetchData() {
        return dao.getAll();
    }

    /**
     * Fetch data from the database filtered by the FilterBuilder.
     */
    public Map<Integer, Station> fetchData(FilterBuilder builder) {
        return dao.getFromFilterBuilder(builder);
    }

    /**
     * Add a user to the database.
     * @param user the user to add
     */
    public void addUser(User user) throws DatabaseException {
        userDao.add(user);
    }

    /**
     * Update a user in the database.
     * @param user the user to update, must have userId != null
     */
    public void updateUser(User user) throws DatabaseException {
        userDao.update(user);
    }

    /**
     * Get a station from the database by id.
     * @param id the id
     */
    public Station getStation(int id) {
        return dao.getOne(id);
    }

    /**
     * Get the station DAO.
     * @return the station DAO
     */
    public StationDao getStationDao() {
        return dao;
    }

}