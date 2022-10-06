package seng202.team6.services;

import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import seng202.team6.controller.MainScreenController;



public class Validity {
    private MainScreenController controller;

    /**
     * Contructor for validity.
     * @param mainScreenController the controller to use.
     */
    public Validity(MainScreenController mainScreenController) {
        this.controller = mainScreenController;
    }

    /**
     * Checks the validity of the name.
     * @param name the name being checked.
     * @return true if the name is valid otherwise false
     */
    public static boolean checkName(String name) {
        return name.matches("[a-zA-Z\\s]+");
    }

    /**
     * Checks the validity of the username.
     * @param username the username being checked.
     * @return true if the username is valid otherwise false
     */
    public static boolean checkUserName(String username) {
        return username.matches("[a-zA-Z0-9]+");
    }

    /**
     * Checks the validity of the password.
     * @param password the password being checked.
     * @return true if the password is valid otherwise false
     */
    public static boolean checkPassword(String password) {
        return password.length() >= 8;
    }

    /**
     * Checks if the passwords match.
     * @param password first password
     * @param confirm the confirmation password
     * @return true if the both passwords match
     */
    public static boolean matchPassword(String password, String confirm) {
        return Objects.equals(password, confirm);
    }

    /**
     * Check whether an address is valid.
     * @param address the address to check
     */
    public boolean checkAddress(String address) throws IOException, InterruptedException {
        return controller.getMapToolBarController().geoCode(address) != null;
    }

    /**
     * Check the input value is a interger.
     * @param value the string value that want to check.
     * @return boolean of true or false.
     */
    public static boolean checkVehicleYear(String value) {
        LocalDate currentdate = LocalDate.now();

        return isNumeric(value) && Integer.parseInt(value) <  (2 + currentdate.getYear())
                && Integer.parseInt(value) > 1980;
    }

    /**
     * Check the string to be made of alphabet and number and space.
     * @param plugType the string that need to be check.
     * @return boolean of true or false.
     */
    public static boolean checkPlugType(String plugType) {
        return plugType.matches("[a-zA-Z0-9\\s]+");
    }

    /**
     * Checks whether it is a valid station name.
     * @param name the new name of the station
     * @return true if valid name otherwise false
     */
    public boolean checkStationName(String name) {
        return name.matches("[a-zA-Z+&, ]+") && name.length() > 0;
    }


    /**
     * Checks whether latitude is within bounds for NZ.
     * @param lat the given latitude
     * @return true if latitude is valid, false if not
     */
    public boolean checkLat(String lat) {
        Double num = tryParseDouble(lat);
        if (num == null) {
            return false;
        } else {
            return num > (-46.56069) && num < (-35.22676);
        }
    }

    /**
     * Checks whether longitude is within bounds for NZ.
     * @param lon the given longitude
     * @return true if the longitude is valid, false otherwise
     */
    public boolean checkLon(String lon) {
        Double num = tryParseDouble(lon);
        if (num == null) {
            return false;
        } else {
            return num > (-176.55973) && num < (178.00417);
        }
    }


    /**
     * Checks the given operator/owner is a valid input.
     * @param op the provided operator/owner
     * @return true if the operator/owner is valid, false otherwise
     */
    public boolean checkOp(String op) {
        return op.matches("[a-zA-Z() ]+");
    }


    /**
     * Checks the given integer is valid and within range.
     * @param integerVal integer to check
     * @return true if the string is a valid integer within range, false otherwise
     */
    public boolean checkInts(String integerVal) {
        Integer num = tryParse(integerVal);
        if (num == null) {
            return false;
        } else {
            return num >= 0 && num < 300;
        }
    }

    /**
     * Helper function for checking the integer function.
     * @param text the number to be checked
     * @return an integer if it was a valid text, null otherwise
     */
    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    /**
     * Helper function for checking the double function.
     * @param text the number to be checked
     * @return a double if it was a valid text, null otherwise
     */
    public static Double tryParseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Function to check if the provided string can represent a vai integer within given range.
     * @param wat the given input
     * @return true if valid, false if not
     */
    public Boolean checkWattage(String wat) {

        Integer num = tryParse(wat);
        if (num == null) {
            return false;
        } else {
            return num > 0 && num <= 500;
        }

    }
}
