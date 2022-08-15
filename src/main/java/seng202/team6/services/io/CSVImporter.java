package seng202.team6.services.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVImporter implements Importable<CSVRecord> {

    @Override
    public List<CSVRecord> readFromFile(File file) {

        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setSkipHeaderRecord(true).build().parse(new FileReader(file))) {
            for (CSVRecord csvRecord : parser.getRecords()) {
                if (!csvRecord.isConsistent()) {
                    throw new RuntimeException("File is inconsistent");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }
}
