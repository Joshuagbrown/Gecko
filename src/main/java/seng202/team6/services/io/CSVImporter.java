package seng202.team6.services.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVImporter implements Importable<CSVRecord> {

    @Override
    public List<CSVRecord> readFromFile(File file) {
        try (CSVParser parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : parser) {
                System.out.println(csvRecord);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }
}
