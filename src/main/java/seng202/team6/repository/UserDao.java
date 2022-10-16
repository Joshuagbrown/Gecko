package seng202.team6.repository;

import java.sql.*;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.User;
import seng202.team6.models.UserLoginDetails;


/**
 * Class that controls the user information in the database
 */
public class UserDao implements DaoInterface<Integer, User> {
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    /**
     * Function to get all users from database.
     * @return a hashmap of integers and users
     */
    @Override
    public HashMap<Integer, User> getAll() {
        throw new UnsupportedOperationException();
    }


    /**
     * Function to get a user given its id.
     * @param id id of object to get.
     * @return the user
     */
    @Override
    public User getOne(int id) {
        String userSql = "SELECT * FROM users WHERE userId = (?)";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(userSql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getBytes("passwordHash"),
                        rs.getBytes("passwordSalt"),
                        rs.getString("address"),
                        rs.getString("name")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get login details from username.
     * @param username the username to check
     */
    public UserLoginDetails getLoginDetails(String username) {
        String userSql = "SELECT userId, passwordHash, passwordSalt "
                + "FROM users WHERE username = (?)";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(userSql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserLoginDetails(
                        rs.getInt("userId"),
                        rs.getBytes("passwordHash"),
                        rs.getBytes("passwordSalt")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /** Function used to add a new user to the database
     *
     * @param toAdd object of type T to add.
     * @return the insert id
     * @throws DatabaseException a database error
     */
    @Override
    public int add(User toAdd) throws DatabaseException {
        String userSql = "INSERT INTO users (username, passwordHash, passwordSalt, address, name)"
                + "values (?,?,?,?,?)";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(userSql)) {
            ps.setString(1, toAdd.getUsername());
            ps.setBytes(2, toAdd.getPasswordHash());
            ps.setBytes(3, toAdd.getPasswordSalt());
            ps.setString(4, toAdd.getAddress());
            ps.setString(5, toAdd.getName());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int insertId = -1;
            if (rs.next()) {
                insertId = rs.getInt(1);
            }
            return insertId;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                throw new DatabaseException("A duplicate entry was found", e);
            }
            throw new DatabaseException("There was an error with the database", e);
        }
    }


    /**
     * Function to delete a user from the database.
     * @param id id of object to delete.
     *           *
     */
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Function called to update a users information in the database.
     * @param toUpdate Object that needs to be updated (this object must be able to
     *                 identify itself and its previous self).
     * @throws DatabaseException a database error
     */
    @Override
    public void update(User toUpdate) throws DatabaseException {
        String userSql = "UPDATE users SET passwordHash=(?) , "
                + "passwordSalt=(?) , address=(?) , name=(?)"
                + "WHERE username=(?)";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(userSql)) {
            ps.setBytes(1, toUpdate.getPasswordHash());
            ps.setBytes(2, toUpdate.getPasswordSalt());
            ps.setString(3, toUpdate.getAddress());
            ps.setString(4, toUpdate.getName());
            ps.setString(5, toUpdate.getUsername());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("There was an error with the database", e);
        }
    }

}