package ua.nure.kn.mironova.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryImpl implements ConnectionFactory {
	private String driver; 
	private String url;
	private String user;
	private String password;
	
	public ConnectionFactoryImpl(Properties properties) {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
	}
	
	public ConnectionFactoryImpl(final String driver,
			final String url, 
			final String user,
			final String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	@Override
	public Connection createConnection() throws DatabaseException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e); //db exception wrong driver name
		}
		
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
