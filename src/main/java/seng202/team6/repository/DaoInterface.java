package seng202.team6.repository;

import seng202.team6.exceptions.DuplicateEntryException;

import java.util.HashMap;

/**
 * Interface for Database Access Objects (DAOs) that provides common functionality for
 * database access.
 * @author Morgan English.
 */
public interface DaoInterface<T> {
    /**
     * Gets all of T from the database.
     * @param sql string sql that want to excute.
     * @return List of all objects type T from the database.
     */
    HashMap<Integer, T> getAll(String sql);

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
     * @throws DuplicateEntryException the error.
     */
    int add(T toAdd) throws DuplicateEntryException;

    /**
     * Deletes and object from database that matches id given.
     * @param id id of object to delete.
     *           *
     * */
    void delete(int id);

    /**
     * Updates an object in the database.
     * @param toUpdate Object that needs to be updated (this object must be able to
     *                 identify itself and its previous self).
     *
     */
    void update(T toUpdate);

}
