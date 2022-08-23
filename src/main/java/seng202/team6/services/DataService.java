package seng202.team6.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.io.CSVImporter;
import seng202.team6.models.Station;
import seng202.team6.repository.StationDAO;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class DataService {
    private static final Logger log = LogManager.getLogger();
    private final StationDAO dao = new StationDAO();

    public void loadDataFromCSV(File file) {
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
        serv.loadDataFromCSV(new File(DataService.class.getResource("/small.csv").toURI()));
    }
}
