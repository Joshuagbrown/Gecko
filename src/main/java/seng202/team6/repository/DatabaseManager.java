package seng202.team6.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton class responsible for interaction with SQLite database.
 * @author Morgan English.
 */
public class DatabaseManager {
    private static DatabaseManager instance = null;
    private static final Logger log = LogManager.getLogger();
    private final String url;

    /**
     * Private constructor for singleton purposes.
     * Creates database if it does not already exist in specified location.
     * @param urlIn the url link.
     */
    private DatabaseManager(String urlIn) {
        if (urlIn == null || urlIn.isEmpty()) {
            this.url = getDatabasePath();
        } else {
            this.url = urlIn;
        }
        if (!checkDatabaseExists(url)) {
            createDatabaseFile(url);
            resetDB();
        }
    }

    /**
     * Singleton method to get current Instance if exists otherwise create it.
     *
     * @return the single instance DatabaseSingleton.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager(null);
        }

        return instance;
    }

    /**
     * Connect to the database.
     *
     * @return database connection.
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            log.error(e);
        }
        return conn;
    }

    /**
     * Initialises the database if it does not exist using the sql script included in resources.
     */
    public void resetDB() {
        try {
            InputStream drop = getClass().getResourceAsStream("/sql/drop.sql");
            InputStream create = getClass().getResourceAsStream("/sql/create.sql");
            executeSqlScript(drop);
            executeSqlScript(create);
        } catch (NullPointerException e) {
            log.error("Error loading database initialisation files", e);
        }
    }

    /**
     * Gets path to the database relative to the jar file.
     *
     * @return jdbc encoded url location of database.
     */
    private String getDatabasePath() {
        String path = DatabaseManager.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        File jarDir = new File(path);
        return "jdbc:sqlite:" + jarDir.getParentFile() + "/database.db";
    }

    /**
     * Check that a database exists in the expected location.
     *
     * @param url expected location to check for database.
     * @return True if database exists else false.
     */
    private boolean checkDatabaseExists(String url) {
        File f = new File(url.substring(12));
        return f.exists();
    }

    /**
     * Creates a database file at the location specified by the url.
     *
     * @param url url to creat database.
     */
    private void createDatabaseFile(String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                String metaDriverLog = String.format("A new database has been created. "
                        + "The driver name is %s", meta.getDriverName());
                log.info(metaDriverLog);
            }
        } catch (SQLException e) {
            log.error(String.format("Error creating new database file url:%s", url));
            log.error(e);
        }
    }

    /**
     * Reads and executes all statements within the sql file provided
     * Note that each statement must be separated by '--SPLIT' this is not a desired limitation but
     * allows for a much wider range of statement types.
     *
     * @param sqlFile input stream of file containing sql statements for execution
     *                (separated by --SPLIT).
     */
    private void executeSqlScript(InputStream sqlFile) {
        String s;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(sqlFile))) {
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

            try (Connection conn = this.connect();
                Statement statement = conn.createStatement()) {
                statement.executeUpdate(sb.toString());
            }
        } catch (FileNotFoundException e) {
            log.error("Error could not find specified database initialisation file", e);
        } catch (IOException e) {
            log.error("Error working with database initialisation file", e);
        } catch (SQLException e) {
            log.error("Error executing sql statements in database initialisation file", e);
        }
    }
}