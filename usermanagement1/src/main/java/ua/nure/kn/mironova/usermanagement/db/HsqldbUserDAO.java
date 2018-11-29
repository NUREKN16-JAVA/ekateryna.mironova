package ua.nure.kn.mironova.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.kn.mironova.usermanagement.User;

class HsqldbUserDAO implements UserDAO {
	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String SELECT_ONE_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users where id = (?)";
	private static final String INSERT_QUERY = "INSERT INTO users(firstname, lastname, dateofbirth) VALUES(?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname = (?), lastname = (?), dateofbirth = (?) WHERE id = (?)";
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id = (?)";
	
	private ConnectionFactory connectionFactory;
	
	/**
	 * @return the connectionFactory
	 */
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/**
	 * @param connectionFactory the connectionFactory to set
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public HsqldbUserDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public HsqldbUserDAO(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	public User create(User user) throws DatabaseException {
		try { 
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection
					.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateofBirth().getTime()));
			int n = statement.executeUpdate();
			if (n != 1)
				throw new DatabaseException("Number of the inserted rows: " + n);
			CallableStatement callableStatement = connection
					.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if(keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return user;
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public User find(Long id) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection
					.prepareStatement(SELECT_ONE_QUERY);
			statement.setLong(1, id.longValue());
			ResultSet resultSet = statement.executeQuery();
			statement.close();
			connection.close();
			if (!resultSet.next()) {
                throw new DatabaseException("Wrong user id =" + id);
            }
			else {
				User result = new User();
				result.setId(new Long(resultSet.getLong(1)));
				result.setFirstName(resultSet.getString(2));
				result.setLastName(resultSet.getString(3));
				result.setDateofBirth(resultSet.getDate(4));
				return result;
			}
			
		} catch (DatabaseException e) {
			throw e;
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
	}

	@Override
	public Collection<User> findAll() throws DatabaseException {
		Collection<User> result = new LinkedList();
		
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateofBirth(resultSet.getDate(4));
				result.add(user);
			}
			statement.close();
			connection.close();
		} catch (DatabaseException e) {
			throw e;
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return result;
		
	}

	@Override
	public void update(User user) throws DatabaseException {
		try { 
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection
					.prepareStatement(UPDATE_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateofBirth().getTime()));
			statement.setLong(4, user.getId().longValue());
			int n = statement.executeUpdate();
			if (n != 1)
				throw new DatabaseException("Number of the updated rows: " + n);
			statement.close();
			connection.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}

	}

	@Override
	public void delete(User user) throws DatabaseException {
		try {
			Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, user.getId().longValue());
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the deleted rows: " + n);
            }
            statement.close();
            connection.close();
		} catch (DatabaseException e) {
			throw e;
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
