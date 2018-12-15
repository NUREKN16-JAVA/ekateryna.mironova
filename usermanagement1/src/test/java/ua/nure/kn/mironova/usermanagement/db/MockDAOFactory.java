package ua.nure.kn.mironova.usermanagement.db;

import com.mockobjects.dynamic.Mock;

public class MockDAOFactory extends DAOFactory {
	
	private Mock mockUserDAO;

	public MockDAOFactory() {
	mockUserDAO = new Mock(UserDAO.class);
	}

	public Mock getMockUserDAO() {
		return mockUserDAO;
	}
	@Override
	public UserDAO getUserDAO() {
		return (UserDAO) mockUserDAO.proxy();
	}

}
