package ua.nure.kn.mironova.usermanagement.db;

public class DAOFactoryImpl extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		UserDAO result = null;
		try {
			Class<?> clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDAO) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
