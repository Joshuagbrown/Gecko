package seng202.team6.io;

import org.apache.commons.lang3.NotImplementedException;
import seng202.team6.exceptions.CsvLineException;
import seng202.team6.models.Vehicle;


/**
 * Function to import the vehicle CSV file.
 */
public class VehicleCsvImporter extends CsvImporter<Vehicle> {


    /**
     * Function to read the values from the lines in the CSV.
     * @param line the line to read from
     * @return a vehicle
     * @throws CsvLineException an error reading from the CSV
     */
    @Override
    protected Vehicle readValueFromLine(String[] line) throws CsvLineException {

        try {
            int year = Integer.parseInt(line[0]);
            String make = line[1];
            String  model = line[2];
            String plugType = "NULL";
            int userId = -1;

            return new Vehicle(make, model, plugType, year, userId);


        } catch (NumberFormatException e) {
            throw new CsvLineException(e);
        }
    }
}
