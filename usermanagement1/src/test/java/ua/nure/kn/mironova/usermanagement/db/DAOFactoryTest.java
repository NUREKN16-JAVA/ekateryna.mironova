package ua.nure.kn.mironova.usermanagement.db;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DAOFactoryTest {
	
	/**
     * Testing method for {@link DAOFactory#.getUserDao()}
     */
	@Test
	public void testGetUserDAO() {
		try {
			DAOFactory daoFactory = DAOFactory.getInstance();
			assertNotNull("DAOFactory instance is null", daoFactory);
			UserDAO userDAO = daoFactory.getUserDAO();
			assertNotNull("UserDAO instance is null", userDAO);
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
