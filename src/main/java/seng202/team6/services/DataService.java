package seng202.team6.services;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.io.CSVImporter;
import seng202.team6.models.Station;
import seng202.team6.repository.StationDao;

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
            CSVImporter csvImporter = new CSVImporter();
            List<Station> stations = csvImporter.readFromFile(file);
            for (Station station : stations) {
                dao.add(station);
            }

        } catch (Exception e) {
            log.error(e);
        }
    }


    public static void main(String[] args) throws URISyntaxException {
        DataService serv = new DataService();

        try {
            serv.loadDataFromCsv(new File(DataService.class.getResource("/small.csv").toURI()));
        } catch (NullPointerException e) {
            System.out.println("NullPointerException produced in DataService class trying "
                    + " to load data.");
        }

    }
}
