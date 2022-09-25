package seng202.team6.repository;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team6.exceptions.DuplicateEntryException;
import seng202.team6.models.User;
import seng202.team6.models.UserLoginDetails;
import seng202.team6.models.Vehicle;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.HashMap;

public class UserDao implements DaoInterface<User> {
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private static final Logger log = LogManager.getLogger();

    @Override
    public HashMap<Integer, User> getAll(String sql) {
        throw new NotImplementedException();
    }

    @Override
    public User getOne(int id) {
        throw new NotImplementedException();
    }

    public UserLoginDetails getLoginDetails(String username) {
        String userSql = "SELECT userId, passwordHash, passwordSalt FROM users WHERE username = (?)";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(userSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new UserLoginDetails(
                        rs.getInt("userId"),
                        rs.getBlob("passwordHash").getBytes(0, 20),
                        rs.getBlob("passwordSalt").getBytes(0, 16)
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(User toAdd) throws DuplicateEntryException {
        String userSql = "INSERT INTO users (username, passwordHash, passwordSalt, address, name)"
                + "values (?,?,?,?,?)";
        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(userSql)) {
            ps.setString(1, toAdd.getUsername());
            ps.setBlob(2, new SerialBlob(toAdd.getPasswordHash()));
            ps.setBlob(3, new SerialBlob(toAdd.getPasswordSalt()));
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
                throw new DuplicateEntryException("Duplicate Entry");
            }
            log.error(e.getMessage());
            return -1;
        }
    }



    @Override
    public void delete(int id) {
        throw new NotImplementedException();
    }

    @Override
    public void update(User toUpdate) {
        String userSql = "UPDATE users SET passwordHash=? , "
                + "passwordSalt=? , address=? , name=?"
                + "WHERE username=?";

        try (Connection conn = databaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(userSql)) {
            ps.setString(1, toUpdate.getUsername());
            ps.setBlob(2, new SerialBlob(toUpdate.getPasswordHash()));
            ps.setBlob(3, new SerialBlob(toUpdate.getPasswordSalt()));
            ps.setString(4, toUpdate.getAddress());
            ps.setString(5, toUpdate.getName());
            ps.setString(6, toUpdate.getName());

            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

}