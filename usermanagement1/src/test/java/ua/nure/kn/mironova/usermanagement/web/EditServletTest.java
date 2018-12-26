package ua.nure.kn.mironova.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.mironova.usermanagement.User;

public class EditServletTest extends MockServletTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		createServlet(EditServlet.class);
	}

	@Test
	public void testEdit() {
		Date date = new Date();
		User user = new User(1000L, "John", "Doe", date);
		getMockUserDAO().expect("update", user);
		
		addRequestParameter("id","1000");
		addRequestParameter("firstName","John");
		addRequestParameter("lastName","Doe");
		addRequestParameter("date",DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
		
	}
	
	@Test
	public void testEditEmptyFirstName() {
		Date date = new Date();
		addRequestParameter("id","1000");
		addRequestParameter("lastName","Doe");
		addRequestParameter("date",DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton","Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error in session scope", errorMessage);
	}
	
	@Test
	public void testEditEmptyLastName() {
		Date date = new Date();
		addRequestParameter("id","1000");
		addRequestParameter("firstName","John");
		addRequestParameter("date",DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton","Ok");
		doPost();
		String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
		assertNotNull("Could not find error in session scope", errorMessage);
	}
	
	@Test
	public void testEditEmptyDate() {
		Date date = new Date();
		addRequestParameter("id","1000");
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
		assertNotNull("Could not find error message", errorMessage);
	}
}
