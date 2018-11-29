package ua.nure.kn.mironova.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DAOFactory {
	private static final String USER_DAO = "dao.ua.nure.kn.mironova.usermanagement.db.UserDAO";
	private final Properties properties;
	private final static DAOFactory INSTANCE = new DAOFactory();
    
    public static DAOFactory getInstance() {
        return INSTANCE;
    }
	
	private DAOFactory() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private ConnectionFactory getConnectionFactory() {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}
	
	public UserDAO getUserDAO() {
		UserDAO result = null;
		Class clazz;
		try {
			clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDAO) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
}
