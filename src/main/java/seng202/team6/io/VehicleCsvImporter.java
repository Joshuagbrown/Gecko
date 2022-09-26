package seng202.team6.io;

import org.apache.commons.lang3.NotImplementedException;
import seng202.team6.exceptions.CsvLineException;
import seng202.team6.models.Vehicle;

public class VehicleCsvImporter extends CsvImporter<Vehicle> {

    @Override
    protected Vehicle readValueFromLine(String[] line) throws CsvLineException {
        throw new NotImplementedException();
    }
}
