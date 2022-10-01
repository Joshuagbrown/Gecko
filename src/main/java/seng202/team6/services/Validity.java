package seng202.team6.services;

import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.io.IOException;
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
    public static boolean checkValue(String value) {
        return isNumeric(value);
    }

    /**
     * Check the string to be made of alphabet and number and space.
     * @param plugType the string that need to be check.
     * @return boolean of true or false.
     */
    public static boolean checkPlugType(String plugType) {
        return plugType.matches("(?=.*\\S)[a-zA-Z0-9\\s]*");
    }

}
