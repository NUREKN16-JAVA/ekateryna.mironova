package ua.nure.kn.mironova.usermanagement.db;

import java.util.Collection;

import ua.nure.kn.mironova.usermanagement.User;

public interface UserDAO {
	/**
	 * Add new user to database
	 * @param user with null id
	 * @return user with auto generated id
	 * 			throw DatabaseException if any error occurs with DB
	 * @author master
	 */
	User create(final User user) throws DatabaseException;
	
	/**
	 * Find user in database
	 * @param id to search user with this id
	 * @return user with this id
	 * 			throw DatabaseException if any error occurs with DB
	 * @author master
	 */
	User find(final Long id) throws DatabaseException;
	
	/**
	 * Find user in database
	 * @return all users from database
	 * 			throw DatabaseException if any error occurs with DB
	 * @author master
	 */
	Collection<User> findAll() throws DatabaseException;
	
	/**
	 * Update information about user
	 * @param user whose information you want to change
	 * @return throw DatabaseException if any error occurs with DB
	 * @author master
	 */
	void update(final User user) throws DatabaseException;
	
	/**
	 * Delete user from database
	 * @param user you want to delete
	 * @return throw DatabaseException if any error occurs with DB
	 * @author master
	 */
	void delete(final User user) throws DatabaseException;
	
	/**
	 * Establishes connection to database
	 * @param connectionfactory
	 * @author master
	 */
	void setConnectionFactory(ConnectionFactory cf);
}
