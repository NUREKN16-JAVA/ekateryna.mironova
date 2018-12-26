package ua.nure.kn.mironova.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DAOFactory {
	private static final String DAO_FACTORY = "dao.factory";
	protected static final String USER_DAO = "dao.ua.nure.kn.mironova.usermanagement.db.UserDAO";
	protected static Properties properties;
	private static DAOFactory instance;
	
	static {
		properties = new Properties();
		try {
			properties.load(DAOFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
		}
		catch (IOException e) {
			throw new RuntimeException (e);
		}
	}
    
    public static synchronized DAOFactory getInstance() {
    	if (instance==null) {
    		try {
				Class<?> factoryClass = Class.forName(properties
						.getProperty(DAO_FACTORY));
				instance = (DAOFactory)factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException (e);
			}
    	}
        return instance;
    }
	
	protected DAOFactory() {
	}
	
	protected ConnectionFactory getConnectionFactory() {
		return new ConnectionFactoryImpl(properties);
	}
	
	public abstract UserDAO getUserDAO();
	
	public static void init(Properties prop) {
		properties = prop;
		instance = null;
		
	}
}
