package ua.nure.kn.mironova.usermanagement.web;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.mironova.usermanagement.User;

import org.junit.Before;
import org.junit.Test;

public class AddServletTest extends MockServletTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		createServlet(AddServlet.class);
	}

	@Test
	public void testAdd() {
		Date date = new Date();
		User newUser = new User("John", "Doe",date);
		User user = new User(new Long(1000), "John", "Doe",date);
		getMockUserDAO().expectAndReturn("create",newUser, user);
		addRequestParameter("firstName","John");
		addRequestParameter("lastName","Doe");
		addRequestParameter("date",DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton","Ok");
		doPost();
	}
	
	@Test
	public void testAddEmptyFirstName() {
		Date date = new Date();
		addRequestParameter("lastName","Doe");
		addRequestParameter("date",DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton","Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error in session scope", errorMessage);
	}
	
	@Test
	public void testAddEmptyLastName() {
		Date date = new Date();
		addRequestParameter("firstName","John");
		addRequestParameter("date",DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton","Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error in session scope", errorMessage);
	}
	
	@Test
	public void testAddEmptyDate() {
		Date date = new Date();
		addRequestParameter("firstName","John");
		addRequestParameter("lastName","Doe");
		addRequestParameter("okButton","Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error in session scope", errorMessage);	
	}
	
	@Test
	public void testEditEmptyDateIncorrect() {
		addRequestParameter("id", "1000");
		addRequestParameter("firstName", "John");
		addRequestParameter("lastName", "Doe");
		addRequestParameter("date", "aksdjwqi");
		addRequestParameter("okButton", "Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error in session scope", errorMessage);
	}

}
