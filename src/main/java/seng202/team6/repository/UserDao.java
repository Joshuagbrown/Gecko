package seng202.team6.repository;

import java.sql.*;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DatabaseException;
import seng202.team6.models.User;
import seng202.team6.models.UserLoginDetails;
import seng202.team6.services.AlertMessage;

public class UserDao implements DaoInterface<Integer, User> {
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    @Override
    public HashMap<Integer, User> getAll() {
        throw new UnsupportedOperationException();
    }

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
        } catch (SQLException e) {
            AlertMessage.createMessage("Error", "An error occurred getting user from the "
                                                + "database. Please see the"
                                                + "log for more details.");
            log.error("Error getting user from database", e);
        }
        return null;
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
        } catch (SQLException e) {
            AlertMessage.createMessage("Error", "An error occurred getting user login details from "
                                                + "the database. Please see the "
                                                + "log for more details.");
            log.error("Error getting user from database", e);
        }
        return null;
    }

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



    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }

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