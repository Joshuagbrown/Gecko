package seng202.team6.services.io;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import seng202.team6.exceptions.ValidationException;
import seng202.team6.models.Station;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVImporter implements Importable<Station> {

    @Override
    public List<Station> readFromFile(File file) throws ValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.skip(1);
            String[] line;
            while ((line = reader.readNext()) != null) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new ValidationException(e);
        }
        return new ArrayList<>();
    }
}