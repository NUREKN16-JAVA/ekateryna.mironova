package ua.nure.kn.mironova.usermanagement.web;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

import ua.nure.kn.mironova.usermanagement.db.DAOFactory;
import ua.nure.kn.mironova.usermanagement.db.MockDAOFactory;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter{
	private Mock mockUserDAO;
	
	/**
	 * @param mockUserDAO the mockUserDAO to set
	 */
	public void setMockUserDAO(Mock mockUserDAO) {
		this.mockUserDAO = mockUserDAO;
	}
	
	/**
	 * @return the mockUserDAO
	 */
	public Mock getMockUserDAO() {
		return mockUserDAO;
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		Properties properties = new Properties();
		properties.setProperty("dao.factory", MockDAOFactory.class.getName());
		DAOFactory.init(properties);
		setMockUserDAO(((MockDAOFactory)DAOFactory.getInstance()).getMockUserDAO());
	}

	@After
	public void tearDown() throws Exception {
		getMockUserDAO().verify();
	}

}
