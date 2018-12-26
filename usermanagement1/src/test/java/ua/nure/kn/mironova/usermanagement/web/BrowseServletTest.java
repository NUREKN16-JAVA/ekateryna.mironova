package ua.nure.kn.mironova.usermanagement.web;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.mironova.usermanagement.User;

public class BrowseServletTest extends MockServletTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}

	@Test
	public void testBrowse() {
		User user = new User(1000L, "John", "Doe", new Date());
		List<User> list = Collections.singletonList(user);
		getMockUserDAO().expectAndReturn("findAll", list);
		doGet();
		Collection<User> collection = (Collection)getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotEquals("Could not find list of users in session", null, collection);
		assertSame(list, collection);
	}
	
	@Test
	public void testEdit() {
		User user = new User(1000L, "John", "Doe", new Date());
		getMockUserDAO().expectAndReturn("find", new Long(1000), user);
		addRequestParameter("editButton","Edit");
		addRequestParameter("id","1000");
		doPost();
		User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertSame(user, userInSession);		
	}
	
	 @Test
	    public void testDelete() {
	        User user = new User(1000L, "John", "Doe", new Date());
	        getMockUserDAO().expectAndReturn("find", new Long(1000), user);
	        getMockUserDAO().expect("delete", user);
	        getMockUserDAO().expect("findAll");
	        addRequestParameter("deleteButton", "Delete");
	        addRequestParameter("id", "1000");
	        doPost();
	        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("message"));
	    }
	    
	    @Test
	    public void testDetails() {
			User user = new User(1000L, "John", "Doe", new Date());
			getMockUserDAO().expectAndReturn("find", 1000L, user);
			addRequestParameter("detailsButton", "Details");
			addRequestParameter("id", "1000");
			doPost();
			User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
			assertNotNull("Could not find the user in session", userInSession);
			assertSame(user, userInSession);
	    }
	    
	    @Test
	    public void testDeleteError() {
	        addRequestParameter("deleteButton", "Delete");
	        doPost();
	        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
	    }
	    
	    @Test
	    public void testDetailsError() {
	        addRequestParameter("detailsButton", "Details");
	        doPost();
	        assertNotNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
	    }

}
