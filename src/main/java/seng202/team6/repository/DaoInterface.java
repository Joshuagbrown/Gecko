package seng202.team6.repository;

import java.util.Map;
import seng202.team6.exceptions.DatabaseException;

/**
 * Interface for Database Access Objects (DAOs) that provides common functionality for
 * database access.
 * @author Morgan English.
 */
public interface DaoInterface<K,T> {
    /**
     * Gets all of T from the database.
     * @return List of all objects type T from the database.
     */
    Map<K, T> getAll();

    /**
     * Gets a single object of type T from the database by id.
     * @param id id of object to get.
     * @return Object of type T that has id given.
     */
    T getOne(int id);


    /**
     * Adds a single object of type T to database.
     * @param toAdd object of type T to add.
     * @return true if no error, false if sql error.
     * @throws DatabaseException the error.
     */
    int add(T toAdd) throws DatabaseException;

    /**
     * Deletes and object from database that matches id given.
     * @param id id of object to delete.
     *           *
     * */
    void delete(int id) throws DatabaseException;

    /**
     * Updates an object in the database.
     * @param toUpdate Object that needs to be updated (this object must be able to
     *                 identify itself and its previous self).
     *
     */
    void update(T toUpdate) throws DatabaseException;

}
